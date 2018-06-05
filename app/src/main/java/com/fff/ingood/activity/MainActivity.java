package com.fff.ingood.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.flow.FlowLogic;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.flow.PreferenceManager;
import com.fff.ingood.global.Constants;
import com.fff.ingood.ui.CircleProgressBarDialog;

public class MainActivity extends AppCompatActivity implements FlowLogic.FlowLogicCaller {

    Activity mActivity;
    CircleProgressBarDialog mWaitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.getInstance(this);
        mWaitingDialog = new CircleProgressBarDialog();
        mActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mWaitingDialog.show(getSupportFragmentManager(), MainActivity.class.getName());
        FlowManager.getInstance().goLoginFlow(this);
    }

    @Override
    public void returnFlow(Integer iStatusCode, FlowLogic.FLOW flow, Class<?> clsFlow) {
        mWaitingDialog.dismiss();

        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(Constants.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(MainActivity.class)) {
                startActivity(new Intent(this, clsFlow));
            }
        } else {
            Toast.makeText(mActivity, "statusCode = " + iStatusCode, Toast.LENGTH_SHORT).show();
        }
    }
}
