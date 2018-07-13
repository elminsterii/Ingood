package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.tools.StringTool;
import com.fff.ingood.ui.CircleProgressBarDialog;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by ElminsterII on 2018/6/13.
 */

@SuppressLint("Registered")
public abstract class BaseFragmentActivity extends AppCompatActivity implements Flow.FlowLogicCaller {

    protected BaseFragmentActivity mActivity;

    private CircleProgressBarDialog mWaitingDialog;
    private int mContentViewId = 0;

    protected abstract void preInit();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initSystemUI();

    protected void setContentViewId(int iContentViewId) {
        mContentViewId = iContentViewId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mContentViewId);

        super.setTitle(getTitle());

        mActivity = this;
        mWaitingDialog = new CircleProgressBarDialog();

        preInit();
        initView();
        initData();
        initListener();
        initSystemUI();
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

    protected void showWaitingDialog(final String strTag) {
        if(!StringTool.checkStringNotNull(strTag))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWaitingDialog.show(getSupportFragmentManager(), strTag);
            }
        });
    }

    protected void hideWaitingDialog() {
        if(mWaitingDialog.getDialog() != null
                && mWaitingDialog.getDialog().isShowing())
            mWaitingDialog.dismiss();
    }
}
