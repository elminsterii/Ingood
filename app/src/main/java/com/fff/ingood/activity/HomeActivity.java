package com.fff.ingood.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fff.ingood.R;
import com.fff.ingood.adapter.ActivityListAdapter;

import java.util.ArrayList;

/**
 * Created by yoie7 on 2018/5/24.
 */

public class HomeActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_homepage);
        super.onCreate(savedInstanceState);

        mRecyclerView = findViewById(R.id.recycleViewActivityList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);

        // specify an adapter (see also next example)
        ArrayList<String> lsTempData = new ArrayList<>();
        lsTempData.add("Activity 01");
        lsTempData.add("Activity 02");
        lsTempData.add("Activity 03");
        lsTempData.add("Activity 04");
        lsTempData.add("Activity 05");

        mAdapter = new ActivityListAdapter(lsTempData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected void initView(){
        super.initView();
    }

    @Override
    protected void initData(){
        super.initData();
    }

    @Override
    protected void initListner(){
        super.initListner();
    }
}
