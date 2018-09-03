package com.fff.ingood.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fff.ingood.BuildConfig;
import com.fff.ingood.R;
import com.fff.ingood.adapter.ActivityListAdapter;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.logic.IgActivityLogicExecutor;
import com.fff.ingood.logic.IgActivityQueryLogic;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.tools.TimeHelper;
import com.fff.ingood.ui.ConfirmDialog;
import com.fff.ingood.ui.WarningDialog;

import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.data.IgActivity.IGA_STATUS_CLOSED;
import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;
import static com.fff.ingood.global.GlobalProperty.ADMIN_ACCOUNT_01;
import static com.fff.ingood.global.GlobalProperty.CHAR_SEARCH_TEXT_HEAD_IS_EMAIL;
import static com.fff.ingood.global.GlobalProperty.CHAR_SEARCH_TEXT_HEAD_IS_TAG;
import static com.fff.ingood.global.GlobalProperty.GOOD_IGACTIVITY_THRESHOLD;
import static com.fff.ingood.global.GlobalProperty.MAX_QUERY_QUANTITY_IGACTIVITY_ONCE;
import static com.fff.ingood.global.GlobalProperty.POPULARITY_IGACTIVITY_THRESHOLD;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/24.
 */

public class HomeActivity extends BaseActivity implements IgActivityQueryLogic.IgActivityQueryLogicCaller {

    private static final String DEFAULT_TAG_IN_TAG_BAR = "default_tag";
    private static final String DEF_ORDER_BY_GOOD = "0";

    private RecyclerView mViewActivityList;
    private ActivityListAdapter mActivityListAdapter;
    private DrawerLayout mLayoutMenu;
    private NavigationView mNvMenu;
    private ImageView mImgMenuBtn;
    private ImageView mImgIngoodIcon;
    private TabLayout mTabLayoutTagBar;
    private SearchView mSearchViewSearchBar;
    private FloatingActionButton mFabPublishBtn;
    private SwipeRefreshLayout mLayoutSwipeRefresh;

    //header UI
    private ImageView mImgMenuPersonThumbnail;
    private TextView mTextViewMenuPersonName;
    private TextView mTextViewMenuPersonEmail;

    List<IgActivity> m_lsIgActivities;

    private HomeActivity mActivity;
    private IgActivityLogicExecutor mIgActivityExecutor;

    private IgActivity preSearchCondition;
    private boolean m_bIsShowExpireIgActivity;
    private boolean m_bIsShowOfficialIgActivity;

    private boolean m_bIsInitialize = false;

    private String[] m_arrIgActivitiesIds;
    private int m_curQueryIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_homepage);
        super.onCreate(savedInstanceState);

        mActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        PersonManager.getInstance().setLoginSuccess(true);

        if(m_bIsInitialize) {
            showWaitingDialog(HomeActivity.class.getName());
            refresh();
        }
    }

    @Override
    public void onBackPressed() {
        if(mImgIngoodIcon.getVisibility() != View.VISIBLE)
            closeSearchView();
        else
            super.onBackPressed();
    }

    @Override
    protected void preInit() {
        Intent intent = getIntent();
        if(intent != null) {
            IgActivity activity = (IgActivity) getIntent().getSerializableExtra(TAG_IGACTIVITY);
            if(activity != null) {
                preSearchCondition = activity;
                m_bIsShowExpireIgActivity = true;
                m_bIsShowOfficialIgActivity = false;
            }
        }
    }

    @Override
    protected void initView() {
        mViewActivityList = findViewById(R.id.viewActivityList);
        mLayoutMenu = findViewById(R.id.layoutMenu);
        mNvMenu = findViewById(R.id.nvMenu);
        mImgMenuBtn = findViewById(R.id.imgMenuBtn);
        mImgIngoodIcon = findViewById(R.id.imgIngoodIcon);
        mTabLayoutTagBar = findViewById(R.id.layoutTagBar);
        mSearchViewSearchBar = findViewById(R.id.searchViewSearchBar);
        mFabPublishBtn = findViewById(R.id.fabPublishAction);
        mLayoutSwipeRefresh = findViewById(R.id.layoutSwipeRefresh);

        if(mNvMenu != null) {
            View viewMenuHeader = mNvMenu.getHeaderView(0);
            FrameLayout frameLayout = viewMenuHeader.findViewById(R.id.layoutDrawerPersonThumbnail);
            mImgMenuPersonThumbnail = (ImageView)frameLayout.getChildAt(0);
            mTextViewMenuPersonName = viewMenuHeader.findViewById(R.id.textViewDrawerPersonName);
            mTextViewMenuPersonEmail = viewMenuHeader.findViewById(R.id.textViewDrawerPersonEmail);
        }
    }

    @Override
    protected void initData() {
        mIgActivityExecutor = new IgActivityLogicExecutor();
        m_lsIgActivities = new ArrayList<>();

        makeDefaultTags();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mActivityListAdapter = new ActivityListAdapter(m_lsIgActivities, this);

        mViewActivityList.setLayoutManager(mLayoutManager);
        mViewActivityList.setNestedScrollingEnabled(true);
        mViewActivityList.setHasFixedSize(true);
        mViewActivityList.setAdapter(mActivityListAdapter);

        mSearchViewSearchBar.setSubmitButtonEnabled(true);

        mImgMenuPersonThumbnail.setImageResource(R.drawable.ic_person_black_36dp);
        mTextViewMenuPersonName.setText(PersonManager.getInstance().getPerson().getName());
        mTextViewMenuPersonEmail.setText(PersonManager.getInstance().getPerson().getEmail());

        if(preSearchCondition == null) {
            preSearchCondition = new IgActivity();
            setConditionByDefaultTab(preSearchCondition, getResources().getText(R.string.tag_all).toString());
        }
    }

    @Override
    protected void initListener() {
        mImgMenuPersonThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutMenu.closeDrawers();

                Intent intent = new Intent(mActivity, PersonDataActivity.class);
                intent.putExtra(Person.TAG_PERSON, PersonManager.getInstance().getPerson());
                mActivity.startActivity(intent);
            }
        });

        mViewActivityList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(isLastItemDisplaying(mViewActivityList)) {
                    if(m_curQueryIndex >= m_arrIgActivitiesIds.length)
                        return;

                    showWaitingDialog(HomeActivity.class.getName());
                    queryIgActivity(m_curQueryIndex);
                }
            }
        });

        mNvMenu.setNavigationItemSelectedListener (
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);

                        switch(menuItem.getItemId()) {
                            case R.id.menuItemPersonal :
                                Intent intent = new Intent(mActivity, PersonDataActivity.class);
                                intent.putExtra(Person.TAG_PERSON, PersonManager.getInstance().getPerson());
                                mActivity.startActivity(intent);
                                break;
                            case R.id.menuItemAbout :
                                String strVersion =  getResources().getText(R.string.ingood_app_version) + BuildConfig.VERSION_NAME;
                                String strCopyright = getResources().getText(R.string.menu_drawer_copy_right).toString();
                                String strAboutMessage = strVersion + "\n\n" + strCopyright;

                                ConfirmDialog.newInstance(new ConfirmDialog.ConfirmDialogEvent() {
                                    @Override
                                    public void onPositiveClick(DialogInterface dialog) {
                                        dialog.dismiss();
                                    }
                                }, strAboutMessage).show(getSupportFragmentManager(), HomeActivity.class.getName());
                                break;
                            case R.id.menuItemLogout :
                                WarningDialog.newInstance(new WarningDialog.WarningDialogEvent() {
                                    @Override
                                    public void onPositiveClick(DialogInterface dialog) {
                                        FlowManager.getInstance().goLogoutFlow(mActivity);
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onNegativeClick(DialogInterface dialog) {
                                        dialog.dismiss();
                                    }
                                }, getResources().getText(R.string.dialog_logout_confirm_message).toString())
                                        .show(getSupportFragmentManager(), HomeActivity.class.getName());
                                break;
                        }

                        mLayoutMenu.closeDrawers();
                        return true;
                    }
                });

        mLayoutMenu.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(@NonNull View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(@NonNull View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        mImgMenuBtn.setClickable(true);
        mImgMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mLayoutMenu.isDrawerOpen(GravityCompat.START))
                    mLayoutMenu.openDrawer(Gravity.START);
            }
        });

        mTabLayoutTagBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText() == null)
                    return;

                boolean bIsDefaultTag = false;
                if(tab.getTag() != null) {
                    String strTag = (String)tab.getTag();
                    if(StringTool.checkStringNotNull(strTag))
                        bIsDefaultTag = true;
                }

                preSearchCondition = new IgActivity();
                String strTabText = tab.getText().toString();

                boolean bNeedRefresh = true;
                if(bIsDefaultTag)
                    bNeedRefresh = setConditionByDefaultTab(preSearchCondition, strTabText);
                else
                    preSearchCondition.setTags(strTabText);

                if(bNeedRefresh) {
                    showWaitingDialog(HomeActivity.class.getName());
                    refresh();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayoutTagBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!m_bIsInitialize) {
                    mActivityListAdapter.setTagBarWidth(mTabLayoutTagBar.getWidth());
                    refresh();
                    m_bIsInitialize = true;
                }
                mTabLayoutTagBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mSearchViewSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideSoftInput();

                if(StringTool.checkStringNotNull(query)) {
                    showWaitingDialog(HomeActivity.class.getName());

                    IgActivity activityCondition = new IgActivity();
                    if(isSearchByEmail(query)) {
                        activityCondition.setPublisherEmail(query.substring(1, query.length()));
                    } else if(isSearchByTag(query)) {
                        activityCondition.setTags(query.substring(1, query.length()));
                    } else {
                        //search by IgActivity name.
                        activityCondition.setName(query);
                    }

                    activityCondition.setGood(DEF_ORDER_BY_GOOD);
                    m_bIsShowExpireIgActivity = true;
                    m_bIsShowOfficialIgActivity = false;
                    mIgActivityExecutor.doSearchIgActivitiesIds(mActivity, activityCondition);
                    preSearchCondition = activityCondition;
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        mSearchViewSearchBar.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImgIngoodIcon.setVisibility(View.GONE);
            }
        });

        mSearchViewSearchBar.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mImgIngoodIcon.setVisibility(View.VISIBLE);
                return false;
            }
        });

        mLayoutSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mActivityListAdapter.clear();
                refresh();
            }
        });

        mFabPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, IgActivityPublishActivity.class);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_HOME).setSystemUI(this);
    }

    private void queryIgActivity(int startIndex) {
        if(startIndex >= m_arrIgActivitiesIds.length)
            return;

        int endIndex = (startIndex + MAX_QUERY_QUANTITY_IGACTIVITY_ONCE - 1);

        if(endIndex >= m_arrIgActivitiesIds.length)
            endIndex = m_arrIgActivitiesIds.length - 1;

        String strIgActivitiesIds = StringTool.cutStringBySymbolAndRange(m_arrIgActivitiesIds
                , startIndex
                , endIndex
                , ",");
        mIgActivityExecutor.doGetIgActivitiesData(this, strIgActivitiesIds);
    }

    private void refresh() {
        if(PersonManager.getInstance().getPersonIcon() != null) {
            mImgMenuPersonThumbnail.setImageBitmap(PersonManager.getInstance().getPersonIcon());
            mTextViewMenuPersonName.setText(PersonManager.getInstance().getPerson().getName());
        }

        if(preSearchCondition != null) {
            m_lsIgActivities.clear();
            mActivityListAdapter.updateActivityList(m_lsIgActivities);
            mActivityListAdapter.notifyDataSetChanged();

            String strIgActivitiesId = preSearchCondition.getId();
            if(!StringTool.checkStringNotNull(strIgActivitiesId))
                mIgActivityExecutor.doSearchIgActivitiesIds(mActivity, preSearchCondition);
            else {
                resetSearchData(strIgActivitiesId);
                queryIgActivityByIds(strIgActivitiesId);
            }
        }
    }

    private void closeSearchView() {
        hideSoftInput();
        mSearchViewSearchBar.onActionViewCollapsed();

        if(mImgIngoodIcon.getVisibility() != View.VISIBLE)
            mImgIngoodIcon.setVisibility(View.VISIBLE);
    }

    private void makeDefaultTags() {
        TabLayout.Tab tabAll = mTabLayoutTagBar.newTab();
        tabAll.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabAll.setText(R.string.tag_all);
        mTabLayoutTagBar.addTab(tabAll);

        TabLayout.Tab tabOffer = mTabLayoutTagBar.newTab();
        tabOffer.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabOffer.setText(R.string.tag_offer);
        mTabLayoutTagBar.addTab(tabOffer);

        TabLayout.Tab tabRecommend = mTabLayoutTagBar.newTab();
        tabRecommend.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabRecommend.setText(R.string.tag_recommend);
        mTabLayoutTagBar.addTab(tabRecommend);

        TabLayout.Tab tabMyIgActivity = mTabLayoutTagBar.newTab();
        tabMyIgActivity.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabMyIgActivity.setText(R.string.tag_my_igactivity);
        mTabLayoutTagBar.addTab(tabMyIgActivity);

        TabLayout.Tab tabMyAttendIgActivity = mTabLayoutTagBar.newTab();
        tabMyAttendIgActivity.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabMyAttendIgActivity.setText(R.string.tag_my_attend_igactivity);
        mTabLayoutTagBar.addTab(tabMyAttendIgActivity);

        TabLayout.Tab tabMySavedIgActivity = mTabLayoutTagBar.newTab();
        tabMySavedIgActivity.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabMySavedIgActivity.setText(R.string.tag_my_save_igactivity);
        mTabLayoutTagBar.addTab(tabMySavedIgActivity);

        TabLayout.Tab tabOfficial = mTabLayoutTagBar.newTab();
        tabOfficial.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabOfficial.setText(R.string.tag_official);
        mTabLayoutTagBar.addTab(tabOfficial);
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }

    private boolean setConditionByDefaultTab(final IgActivity igCondition, String strTabContext) {
        boolean bRefreshOutside = true;
        if(!StringTool.checkStringNotNull(strTabContext))
            return false;

        if(strTabContext.contentEquals(getResources().getText(R.string.tag_all))) {
            igCondition.setGood("0");
            m_bIsShowExpireIgActivity = false;
            m_bIsShowOfficialIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_official))) {
            igCondition.setPublisherEmail(ADMIN_ACCOUNT_01);
            m_bIsShowExpireIgActivity = false;
            m_bIsShowOfficialIgActivity = true;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_offer))) {
            igCondition.setGood(DEF_ORDER_BY_GOOD);
            igCondition.setMaxOffer("1");
            m_bIsShowExpireIgActivity = false;
            m_bIsShowOfficialIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_recommend))) {
            igCondition.setGood(DEF_ORDER_BY_GOOD);
            igCondition.setAttention(POPULARITY_IGACTIVITY_THRESHOLD);
            m_bIsShowExpireIgActivity = false;
            m_bIsShowOfficialIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_recently))) {
            //ignored
            String strCurTime = TimeHelper.getCurTime();
            String strTimeAfterOneWeek = TimeHelper.getTimeByDaysBasedCurrent(7);
            igCondition.setGood(DEF_ORDER_BY_GOOD);
            igCondition.setDateBegin(strCurTime);
            igCondition.setDateEnd(strTimeAfterOneWeek);
            m_bIsShowExpireIgActivity = false;
            m_bIsShowOfficialIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_ongoing))) {
            //ignored
            igCondition.setGood(DEF_ORDER_BY_GOOD);
            String strCurTime = TimeHelper.getCurTime();
            igCondition.setDateBegin(strCurTime);
            m_bIsShowExpireIgActivity = false;
            m_bIsShowOfficialIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_popularity))) {
            //ignored
            igCondition.setAttention(POPULARITY_IGACTIVITY_THRESHOLD);
            m_bIsShowExpireIgActivity = false;
            m_bIsShowOfficialIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_good))) {
            //ignored
            igCondition.setGood(GOOD_IGACTIVITY_THRESHOLD);
            m_bIsShowExpireIgActivity = false;
            m_bIsShowOfficialIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_nearly))) {
            //ignored
            igCondition.setGood(DEF_ORDER_BY_GOOD);
            igCondition.setLocation(PersonManager.getInstance().getPerson().getLocation());
            m_bIsShowExpireIgActivity = false;
            m_bIsShowOfficialIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_my_igactivity))) {
            igCondition.setPublisherEmail(PersonManager.getInstance().getPerson().getEmail());
            m_bIsShowExpireIgActivity = true;
            m_bIsShowOfficialIgActivity = true;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_my_attend_igactivity))) {
            igCondition.setAttendees(PersonManager.getInstance().getPerson().getId());
            m_bIsShowExpireIgActivity = true;
            m_bIsShowOfficialIgActivity = true;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_my_save_igactivity))) {
            bRefreshOutside = false;
            showWaitingDialog(HomeActivity.class.getName());
            PersonManager.getInstance().refresh(new PersonManager.PersonManagerRefreshEvent() {
                @Override
                public void onRefreshDone(Person person) {
                    String strSaveIgActivitiesId = person.getSaveIgActivities();
                    igCondition.setId(strSaveIgActivitiesId);
                    m_bIsShowExpireIgActivity = true;
                    m_bIsShowOfficialIgActivity = true;
                    refresh();
                }
            });
        }
        return bRefreshOutside;
    }

    private void resetSearchData(String strActivitiesIds) {
        m_lsIgActivities.clear();
        m_arrIgActivitiesIds = strActivitiesIds.split(",");
        m_curQueryIndex = 0;
    }

    private void queryIgActivityByIds(String strActivitiesIds) {
        if(m_arrIgActivitiesIds.length <= MAX_QUERY_QUANTITY_IGACTIVITY_ONCE) {
            mIgActivityExecutor.doGetIgActivitiesData(this, strActivitiesIds);
        } else
            queryIgActivity(0);
    }

    private boolean isSearchByEmail(String strSearchText) {
        return strSearchText.charAt(0) == CHAR_SEARCH_TEXT_HEAD_IS_EMAIL;
    }

    private boolean isSearchByTag(String strSearchText) {
        return strSearchText.charAt(0) == CHAR_SEARCH_TEXT_HEAD_IS_TAG;
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        hideWaitingDialog();
        mLayoutSwipeRefresh.setRefreshing(false);

        if(iStatusCode == null)
            return;

        if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnIgActivities(List<IgActivity> lsActivities) {
        hideWaitingDialog();

        if(m_bIsShowExpireIgActivity && m_bIsShowOfficialIgActivity) {
            m_lsIgActivities.addAll(lsActivities);
        } else {
            for(IgActivity activity : lsActivities) {
                if(!m_bIsShowExpireIgActivity) {
                    if(activity.getStatus().equals(IGA_STATUS_CLOSED)) {
                        continue;
                    }
                }
                if(!m_bIsShowOfficialIgActivity) {
                    if(activity.getPublisherEmail().equals(ADMIN_ACCOUNT_01)) {
                        continue;
                    }
                }
                m_lsIgActivities.add(activity);
            }
        }

        mActivityListAdapter.updateActivityList(m_lsIgActivities);
        mActivityListAdapter.notifyDataSetChanged();

        m_curQueryIndex += lsActivities.size();
    }

    @Override
    public void returnIgActivitiesIds(String strActivitiesIds) {
        if(!StringTool.checkStringNotNull(strActivitiesIds))
            return;

        resetSearchData(strActivitiesIds);
        queryIgActivityByIds(strActivitiesIds);
    }
}
