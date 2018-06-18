package com.fff.ingood.activity;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import com.fff.ingood.R;
import com.fff.ingood.ui.HeadZoomScrollView;

public class IgActivityDetailActivity extends BaseActivity {

    private ConstraintLayout mLayoutIgActivityDetail;
    private HeadZoomScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ig_detail);
    }

    @Override
    protected void initView() {
        mLayoutIgActivityDetail = findViewById(R.id.layoutIgActivityDetail);
        mScrollView = new HeadZoomScrollView(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }
}
