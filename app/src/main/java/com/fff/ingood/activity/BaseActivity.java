package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.ServerResponse;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/3.
 */

@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity implements Flow.FlowLogicCaller {
    private static final int SYSTEM_UI_FLAG_INGOOD = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

    protected BaseActivity mActivity;
    private int mCurAPIVersion;

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle(getTitle());

        mCurAPIVersion = android.os.Build.VERSION.SDK_INT;
        mActivity = this;

        initView();
        initData();
        initListener();
        setSystemUI();
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

    @Override
    public void returnFlow(Integer iStatusCode, Flow.FLOW flow, Class<?> clsFlow) {
        FlowManager.getInstance().setCurFlow(flow);

        if(clsFlow != null && iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            mActivity.startActivity(new Intent(this, clsFlow));
            mActivity.finish();
        }
        else
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
    }
}
