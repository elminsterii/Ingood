package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fff.ingood.R;
import com.fff.ingood.flow.FlowLogic;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.flow.PreferenceManager;
import com.fff.ingood.ui.CircleProgressBarDialog;

public class MainActivity extends AppCompatActivity implements FlowLogic.FlowLogicCaller {

    CircleProgressBarDialog mWaitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.getInstance(this);
        mWaitingDialog = new CircleProgressBarDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mWaitingDialog.show(getSupportFragmentManager(), MainActivity.class.getName());
        FlowManager.getInstance().goLoginFlow(this);
    }

    @Override
    public void returnFlow(boolean bSuccess, FlowLogic.FLOW flow, Class<?> clsFlow) {
        mWaitingDialog.dismiss();

        FlowManager.getInstance().setCurFlow(flow);

        if(clsFlow != null)
            startActivity(new Intent(this, clsFlow));
    }
}
