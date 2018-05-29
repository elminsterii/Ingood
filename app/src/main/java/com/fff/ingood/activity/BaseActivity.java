package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by yoie7 on 2018/5/3.
 */

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {
    private static final int SYSTEM_UI_FLAG_INGOOD = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    public Activity mActivity;
    private int mCurAPIVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle(getTitle());

        mCurAPIVersion = android.os.Build.VERSION.SDK_INT;
        mActivity = this;

        initView();
        initData();
        initListner();
        setSystemUI();
    }

    protected void initView() {
    }

    protected void initData() {
    }

    protected void initListner() {
    }

    private void setSystemUI() {
        if (mCurAPIVersion >= 21) {
            final View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD);

            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int visibility) {
                    if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD);
                    }
                }
            });
        }
    }

    @SuppressLint("NewApi")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(mCurAPIVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD);
        }
    }
}
