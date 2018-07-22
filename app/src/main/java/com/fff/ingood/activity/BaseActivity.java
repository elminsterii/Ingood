package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.ui.CircleProgressBarDialog;

import java.util.Objects;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/3.
 */

@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity implements Flow.FlowLogicCaller {
    protected BaseActivity mActivity;

    private static CircleProgressBarDialog mWaitingDialog;

    protected abstract void preInit();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initSystemUI();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setTitle(getTitle());

        mActivity = this;
        mWaitingDialog = new CircleProgressBarDialog();

        preInit();
        initView();
        initData();
        initListener();
        initSystemUI();
    }

//    @SuppressLint("NewApi")
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if(mCurAPIVersion >= Build.VERSION_CODES.KITKAT && hasFocus) {
//            getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_INGOOD);
//        }
//    }

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

    protected void showWaitingDialog(final String strTag) {
        if(!StringTool.checkStringNotNull(strTag))
            return;

        if(mWaitingDialog.getDialog() != null
                && !mWaitingDialog.getDialog().isShowing()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWaitingDialog.show(getSupportFragmentManager(), strTag);
                }
            });
        } else if(mWaitingDialog.getDialog() == null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWaitingDialog.show(getSupportFragmentManager(), strTag);
                }
            });
        }
    }

    protected void hideWaitingDialog() {
        if(mWaitingDialog.getDialog() != null
                && mWaitingDialog.getDialog().isShowing())
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWaitingDialog.dismiss();
                }
            });
    }

    protected void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        assert inputMethodManager != null;
        inputMethodManager.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
    }
}
