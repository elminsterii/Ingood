package com.fff.ingood.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.ui.HeadZoomScrollView;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;

public class IgActivityDetailActivity extends BaseActivity {

    private HeadZoomScrollView mZoomView;
    private ImageView mImageViewIgActivityMain;
    private TextView mTextViewTitle;

    private IgActivity mIgActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_ig_detail);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void preInit() {
        mIgActivity = (IgActivity)getIntent().getSerializableExtra(TAG_IGACTIVITY);
    }

    @Override
    protected void initView() {
        mZoomView = findViewById(R.id.zoomViewIgActivity);
        mImageViewIgActivityMain = findViewById(R.id.imageViewIgActivityMain);
        mTextViewTitle = findViewById(R.id.textViewIgActivityTitle);
    }

    @Override
    protected void initData() {
        mTextViewTitle.setText(mIgActivity.getName());
    }

    @Override
    protected void initListener() {

    }
}
