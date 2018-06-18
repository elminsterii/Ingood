package com.fff.ingood.activity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
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
import com.fff.ingood.logic.ActivityLogic;
import com.fff.ingood.logic.ActivityLogicExecutor;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.ui.CircleProgressBarDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;
import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/24.
 */

public class HomeActivity extends BaseActivity implements ActivityLogic.ActivityLogicCaller {

    private RecyclerView mViewActivityList;
    private ActivityListAdapter mActivityListAdapter;
    private DrawerLayout mLayoutMenu;
    private NavigationView mNvMenu;
    private ImageView mImgMenuBtn;
    private ImageView mImgIngoodIcon;
    private TabLayout mTabLayoutTagBar;
    private SearchView mSearchViewSearchBar;
    private FloatingActionButton mFabPublishBtn;

    List<IgActivity> m_lsActivities;
    CircleProgressBarDialog mWaitingDialog;

    private HomeActivity mActivity;
    private ActivityLogicExecutor mActivityMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_homepage);
        super.onCreate(savedInstanceState);

        mWaitingDialog = new CircleProgressBarDialog();
        mActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        closeSearchView();
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
    }

    @Override
    protected void initData() {
        mActivityMgr = new ActivityLogicExecutor();
        m_lsActivities = new ArrayList<>();

        //@@ test code
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Sport"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Music"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Culture"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Test"));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mActivityListAdapter = new ActivityListAdapter(m_lsActivities);

        mViewActivityList.setLayoutManager(mLayoutManager);
        mViewActivityList.setNestedScrollingEnabled(true);
        mViewActivityList.setHasFixedSize(true);
        mViewActivityList.setAdapter(mActivityListAdapter);

        mFabPublishBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorTransparent)));

        mSearchViewSearchBar.setSubmitButtonEnabled(true);
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

                IgActivity activityCondition = new IgActivity();
                String strTag = tab.getText().toString();

                if(StringTool.checkStringNotNull(strTag)) {
                    activityCondition.setTags(strTag);

                    mWaitingDialog.show(getSupportFragmentManager(), HomeActivity.class.getName());
                    mActivityMgr.doSearchActivitiesIds(mActivity, activityCondition);
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
                mActivityListAdapter.setTagBarWidth(mTabLayoutTagBar.getWidth());
                if(m_lsActivities.size() == 0) {
                    mWaitingDialog.show(getSupportFragmentManager(), HomeActivity.class.getName());
                    //@@ test code
                    IgActivity activityCondition = new IgActivity();
                    activityCondition.setTags("GOGO");
                    mActivityMgr.doSearchActivitiesIds(mActivity, activityCondition);
                }
                mTabLayoutTagBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        mSearchViewSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hideSoftInput();

                if(StringTool.checkStringNotNull(query)) {
                    mWaitingDialog.show(getSupportFragmentManager(), HomeActivity.class.getName());

                    IgActivity activityCondition = new IgActivity();
                    activityCondition.setTags(query);
                    mActivityMgr.doSearchActivitiesIds(mActivity, activityCondition);
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

        mFabPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO - enter publish flow
            }
        });
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(mWaitingDialog != null
                && mWaitingDialog.getDialog() != null
                && mWaitingDialog.getDialog().isShowing())
            mWaitingDialog.dismiss();

        if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void returnActivities(List<IgActivity> lsActivities) {
        if(mWaitingDialog != null
                && mWaitingDialog.getDialog() != null
                && mWaitingDialog.getDialog().isShowing())
            mWaitingDialog.dismiss();

        m_lsActivities = lsActivities;
        mViewActivityList.setAdapter(null);
        mViewActivityList.setAdapter(mActivityListAdapter);
        mActivityListAdapter.updateActivityList(lsActivities);
    }

    @Override
    public void returnActivitiesIds(String strActivitiesIds) {
        mActivityMgr.doGetActivitiesData(this, strActivitiesIds);
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
}
