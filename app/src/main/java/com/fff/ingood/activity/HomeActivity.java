package com.fff.ingood.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.fff.ingood.R;
import com.fff.ingood.adapter.ActivityListAdapter;
import com.fff.ingood.data.ActivityAttr;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.ui.CircleProgressBarDialog;

import java.util.ArrayList;

/**
 * Created by yoie7 on 2018/5/24.
 */

public class HomeActivity extends BaseActivity {

    private RecyclerView mViewActivityList;
    private RecyclerView.Adapter mActivityListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout mLayoutMenu;
    private NavigationView mNvMenu;
    private ImageView mImgMenuBtn;
    private TabLayout mTabLayoutTagBar;

    ArrayList<ActivityAttr> m_lsActivities;
    CircleProgressBarDialog mWaitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_homepage);
        super.onCreate(savedInstanceState);

        mWaitingDialog = new CircleProgressBarDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        super.initView();

        mViewActivityList = findViewById(R.id.viewActivityList);
        mLayoutMenu = findViewById(R.id.layoutMenu);
        mNvMenu = findViewById(R.id.nvMenu);
        mImgMenuBtn = findViewById(R.id.imgMenuBtn);
        mTabLayoutTagBar = findViewById(R.id.layoutTagBar);
    }

    @Override
    protected void initData() {
        super.initData();

        m_lsActivities = new ArrayList<>();

        //@@ test code
        final int TEST_SIZE = 20;
        for(int i=0; i<TEST_SIZE; i++)
            m_lsActivities.add(new ActivityAttr());

        //@@ test code
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Sport"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Music"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Culture"));

        mLayoutManager = new LinearLayoutManager(this);
        mActivityListAdapter = new ActivityListAdapter(m_lsActivities);

        mViewActivityList.setLayoutManager(mLayoutManager);
        mViewActivityList.setNestedScrollingEnabled(true);
        mViewActivityList.setHasFixedSize(true);
        mViewActivityList.setAdapter(mActivityListAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();

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

        mNvMenu.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
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
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
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
                    mLayoutMenu.openDrawer(Gravity.LEFT);
            }
        });
    }
}
