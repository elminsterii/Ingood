package com.fff.ingood.activity;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.adapter.ActivityListAdapter;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.SystemUIManager;
import com.fff.ingood.logic.IgActivityLogicExecutor;
import com.fff.ingood.logic.IgActivityQueryLogic;
import com.fff.ingood.tools.StringTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/24.
 */

public class HomeActivity extends BaseActivity implements IgActivityQueryLogic.IgActivityQueryLogicCaller {

    private static final int MAX_QUERY_QUANTITY_IGACTIVITY_ONCE = 10;

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

    List<IgActivity> m_lsActivities;

    private HomeActivity mActivity;
    private IgActivityLogicExecutor mActivityMgr;
    private IgActivity preSearchCondition;

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
        mActivityMgr = new IgActivityLogicExecutor();
        m_lsActivities = new ArrayList<>();

        makeTodayTags();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mActivityListAdapter = new ActivityListAdapter(m_lsActivities, this);

        mViewActivityList.setLayoutManager(mLayoutManager);
        mViewActivityList.setNestedScrollingEnabled(true);
        mViewActivityList.setHasFixedSize(true);
        mViewActivityList.setAdapter(mActivityListAdapter);

        mSearchViewSearchBar.setSubmitButtonEnabled(true);

        //@@ test
        preSearchCondition = new IgActivity();
        preSearchCondition.setTags("關懷");
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
                                //TODO - go to personal information page
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

                String strTag = tab.getText().toString();

                if(StringTool.checkStringNotNull(strTag)) {
                    showWaitingDialog(HomeActivity.class.getName());

                    preSearchCondition.setTags(strTag);
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
                    activityCondition.setTags(query);
                    mActivityMgr.doSearchIgActivitiesIds(mActivity, activityCondition);
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
                refresh();
            }
        });

        mFabPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - enter publish flow
            }
        });
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_HOME).setSystemUI(this);
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

        if(m_lsActivities == null || m_lsActivities.size() == 0)
            m_lsActivities = lsActivities;
        else
            m_lsActivities.addAll(lsActivities);

        mActivityListAdapter.updateActivityList(m_lsActivities);
        mActivityListAdapter.notifyDataSetChanged();

        m_curQueryIndex += lsActivities.size();
    }

    @Override
    public void returnIgActivitiesIds(String strActivitiesIds) {
        if(!StringTool.checkStringNotNull(strActivitiesIds))
            return;

        m_lsActivities.clear();
        m_arrIgActivitiesIds = null;
        m_curQueryIndex = 0;

        String[] arrIgActivitiesIds = strActivitiesIds.split(",");
        m_arrIgActivitiesIds = arrIgActivitiesIds;

        if(arrIgActivitiesIds.length <= MAX_QUERY_QUANTITY_IGACTIVITY_ONCE) {
            mActivityMgr.doGetIgActivitiesData(this, strActivitiesIds);
        } else
            queryIgActivity(0);
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
        mActivityMgr.doGetIgActivitiesData(this, strIgActivitiesIds);
    }

    private void refresh() {
        if(preSearchCondition != null) {
            m_lsActivities.clear();
            mActivityListAdapter.updateActivityList(m_lsActivities);
            mActivityListAdapter.notifyDataSetChanged();

            mActivityMgr.doSearchIgActivitiesIds(mActivity, preSearchCondition);
        }
    }

    private void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
    }

    private void closeSearchView() {
        hideSoftInput();
        mSearchViewSearchBar.onActionViewCollapsed();

        if(mImgIngoodIcon.getVisibility() != View.VISIBLE)
            mImgIngoodIcon.setVisibility(View.VISIBLE);
    }

    private void makeTodayTags() {
        //@@ test
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText(R.string.tag_care));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText(R.string.tag_environmental_protection));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText(R.string.tag_education));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText(R.string.tag_manpower));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText(R.string.tag_animal));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Sport"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Music"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Culture"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Test"));
    }

    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            return lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1;
        }
        return false;
    }
}
