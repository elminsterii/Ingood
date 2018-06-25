package com.fff.ingood.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fff.ingood.R;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.global.IgActivityHelper;
import com.fff.ingood.ui.HeadZoomScrollView;

import static com.fff.ingood.data.IgActivity.TAG_IGACTIVITY;

public class IgActivityDetailActivity extends BaseActivity {

    private AppCompatImageView mImageViewBack;
    private HeadZoomScrollView mZoomView;
    private ImageView mImageViewIgActivityMain;
    private TextView mTextViewTitle;
    private TextView mTextViewDate;
    private TextView mTextViewLocation;

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
        mImageViewBack = findViewById(R.id.imageViewBack);
        mZoomView = findViewById(R.id.zoomViewIgActivity);
        mImageViewIgActivityMain = findViewById(R.id.imageViewIgActivityMain);
        mTextViewTitle = findViewById(R.id.textViewIgActivityTitle);
        mTextViewDate = findViewById(R.id.textViewIgActivityDate);
        mTextViewLocation = findViewById(R.id.textViewIgActivityLocation);
    }

    @Override
    protected void initData() {
        if(mIgActivity == null)
            return;
        String strDate = IgActivityHelper.makeDateStringByIgActivity(mIgActivity);

        mTextViewTitle.setText(mIgActivity.getName());
        mTextViewDate.setText(strDate);
        mTextViewLocation.setText(mIgActivity.getLocation());
    }

    @Override
    protected void initListener() {
        mImageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
