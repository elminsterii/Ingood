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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        mRecyclerView = findViewById(R.id.recycleViewActivityList);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);

        ArrayList<String> lsTempData = new ArrayList<>();

        final int TEST_SIZE = 20;
        for(int i=0; i<TEST_SIZE; i++)
            lsTempData.add("Activities");

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
