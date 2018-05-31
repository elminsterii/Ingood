package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.adapter.ActivityListAdapter;

import java.util.ArrayList;

/**
 * Created by yoie7 on 2018/5/24.
 */

public class HomeActivity extends BaseActivity {

    private RecyclerView mRecyclerViewActivityList;
    private RecyclerView.Adapter mActivityListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DrawerLayout mDrawerLayoutMenu;
    private NavigationView mNavigationViewMenu;
    private ImageView mImageViewMenuBtn;
    private TabLayout mTabLayoutTagBar;
    private SearchView mSearchViewSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_homepage);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();

        mRecyclerViewActivityList = findViewById(R.id.recycleViewActivityList);
        mDrawerLayoutMenu = findViewById(R.id.drawer_layout);
        mNavigationViewMenu = findViewById(R.id.nvView);
        mImageViewMenuBtn = findViewById(R.id.imageViewMenuBtn);
        mTabLayoutTagBar = findViewById(R.id.tabLayout_TagBar);
        mSearchViewSearchBar = findViewById(R.id.searchViewSearchBar);
    }

    @Override
    protected void initData() {
        super.initData();

        ArrayList<String> lsTempData = new ArrayList<>();

        //@@ test code
        final int TEST_SIZE = 20;
        for(int i=0; i<TEST_SIZE; i++)
            lsTempData.add("Activities");

        //@@ test code
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Sport"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("Music"));
        mTabLayoutTagBar.addTab(mTabLayoutTagBar.newTab().setText("??"));

        mLayoutManager = new LinearLayoutManager(this);
        mActivityListAdapter = new ActivityListAdapter(lsTempData);

        mRecyclerViewActivityList.setLayoutManager(mLayoutManager);
        mRecyclerViewActivityList.setNestedScrollingEnabled(true);
        mRecyclerViewActivityList.setHasFixedSize(true);
        mRecyclerViewActivityList.setAdapter(mActivityListAdapter);
    }

    @Override
    protected void initListener() {
        super.initListener();

        mRecyclerViewActivityList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        mNavigationViewMenu.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        if(menuItem.getItemId() == R.id.menuItemPersonal) {
                            //TODO - go to personal information page
                            //FlowManager.getInstance().goLoginFlow(mActivity);
                        }

                        mDrawerLayoutMenu.closeDrawers();

                        return true;
                    }
                });

        mDrawerLayoutMenu.addDrawerListener(
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

        mImageViewMenuBtn.setClickable(true);
        mImageViewMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mDrawerLayoutMenu.isDrawerOpen(GravityCompat.START))
                    mDrawerLayoutMenu.openDrawer(Gravity.LEFT);
            }
        });

    }
}
