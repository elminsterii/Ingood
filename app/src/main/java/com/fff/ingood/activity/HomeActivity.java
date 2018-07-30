package com.fff.ingood.activity;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.adapter.ActivityListAdapter;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.logic.IgActivityLogicExecutor;
import com.fff.ingood.logic.IgActivityQueryLogic;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.tools.TimeHelper;

import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.data.IgActivity.IGA_STATUS_CLOSED;
import static com.fff.ingood.global.GlobalProperty.GOOD_IGACTIVITY_THRESHOLD;
import static com.fff.ingood.global.GlobalProperty.IS_SHOW_CLOSED_IGACTIVITY;
import static com.fff.ingood.global.GlobalProperty.MAX_QUERY_QUANTITY_IGACTIVITY_ONCE;
import static com.fff.ingood.global.GlobalProperty.POPULARITY_IGACTIVITY_THRESHOLD;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/24.
 */

public class HomeActivity extends BaseActivity implements IgActivityQueryLogic.IgActivityQueryLogicCaller {

    private static final String DEFAULT_TAG_IN_TAG_BAR = "default_tag";

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

    List<IgActivity> m_lsIgActivities;

    private HomeActivity mActivity;
    private IgActivityLogicExecutor mIgActivityExecutor;

    private IgActivity preSearchCondition;
    private boolean m_bIsShowExpireIgActivity;

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

        if(m_bIsInitialize) {
            showWaitingDialog(HomeActivity.class.getName());
            refresh();
        }
    }

    @Override
    public void onBackPressed() {
        closeSearchView();
    }

    @Override
    protected void preInit() {

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

        preSearchCondition = new IgActivity();
        setConditionByDefaultTab(preSearchCondition, getResources().getText(R.string.tag_recently).toString());
    }

    @Override
    protected void initListener() {
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
                                mActivity.startActivity(new Intent(mActivity, PersonDataActivity.class));
                                break;
                            case R.id.menuItemLogout :
                                FlowManager.getInstance().goLogoutFlow(mActivity);
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

                if(bIsDefaultTag)
                    setConditionByDefaultTab(preSearchCondition, strTabText);
                else
                    preSearchCondition.setTags(strTabText);

                showWaitingDialog(HomeActivity.class.getName());
                refresh();
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

                    m_bIsShowExpireIgActivity = false;
                    IgActivity activityCondition = new IgActivity();
                    activityCondition.setTags(query);
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

        TabLayout.Tab tabRecently = mTabLayoutTagBar.newTab();
        tabRecently.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabRecently.setText(R.string.tag_recently);
        mTabLayoutTagBar.addTab(tabRecently);

        TabLayout.Tab tabPopularity = mTabLayoutTagBar.newTab();
        tabPopularity.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabPopularity.setText(R.string.tag_popularity);
        mTabLayoutTagBar.addTab(tabPopularity);

        TabLayout.Tab tabGood = mTabLayoutTagBar.newTab();
        tabGood.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabGood.setText(R.string.tag_good);
        mTabLayoutTagBar.addTab(tabGood);

        TabLayout.Tab tabNearly = mTabLayoutTagBar.newTab();
        tabNearly.setTag(DEFAULT_TAG_IN_TAG_BAR);
        tabNearly.setText(R.string.tag_nearly);
        mTabLayoutTagBar.addTab(tabNearly);

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
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }

    private void setConditionByDefaultTab(IgActivity igCondition, String strTabContext) {
        if(!StringTool.checkStringNotNull(strTabContext))
            return;

        if(strTabContext.contentEquals(getResources().getText(R.string.tag_recently))) {
            String strCurTime = TimeHelper.getCurTime();
            String strTimeAfterOneWeek = TimeHelper.getTimeByDaysBasedCurrent(7);

            igCondition.setDateBegin(strCurTime);
            igCondition.setDateEnd(strTimeAfterOneWeek);
            m_bIsShowExpireIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_popularity))) {
            igCondition.setAttention(POPULARITY_IGACTIVITY_THRESHOLD);
            m_bIsShowExpireIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_good))) {
            igCondition.setGood(GOOD_IGACTIVITY_THRESHOLD);
            m_bIsShowExpireIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_nearly))) {
            igCondition.setLocation(PersonManager.getInstance().getPerson().getLocation());
            m_bIsShowExpireIgActivity = false;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_my_igactivity))) {
            igCondition.setPublisherEmail(PersonManager.getInstance().getPerson().getEmail());
            m_bIsShowExpireIgActivity = true;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_my_attend_igactivity))) {
            igCondition.setAttendees(PersonManager.getInstance().getPerson().getId());
            m_bIsShowExpireIgActivity = true;
        } else if(strTabContext.contentEquals(getResources().getText(R.string.tag_my_save_igactivity))) {
            String strSaveIgActivitiesId = PersonManager.getInstance().getPerson().getSaveIgActivities();
            igCondition.setId(strSaveIgActivitiesId);
            m_bIsShowExpireIgActivity = true;
        }
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

        if(IS_SHOW_CLOSED_IGACTIVITY || m_bIsShowExpireIgActivity) {
            m_lsIgActivities.addAll(lsActivities);
        } else {
            for(IgActivity activity : lsActivities) {
                if(!activity.getStatus().equals(IGA_STATUS_CLOSED))
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
