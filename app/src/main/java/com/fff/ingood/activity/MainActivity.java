package com.fff.ingood.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.ui.CircleProgressBarDialog;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class MainActivity extends AppCompatActivity implements Flow.FlowLogicCaller {

    Activity mActivity;
    CircleProgressBarDialog mWaitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.getInstance(this);
        ServerResponse.getInstance(this);

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
    public void returnFlow(Integer iStatusCode, Flow.FLOW flow, Class<?> clsFlow) {
        if(mWaitingDialog != null
                && mWaitingDialog.getDialog() != null
                && mWaitingDialog.getDialog().isShowing())
            mWaitingDialog.dismiss();

        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(MainActivity.class)) {
                mActivity.startActivity(new Intent(this, clsFlow));
            }
        } else {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }
}
