package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.global.SystemUIManager;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/3.
 */

public class LoginActivity extends BaseActivity {

    private Button mButton_SignIn;
    private Button mButton_Register;

    private LoginActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_signin_page);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void preInit() {
        mActivity = this;
    }

    @Override
    protected void initView(){
        mButton_SignIn = findViewById(R.id.btn_signin);
        mButton_Register = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData(){

    }

    @Override
    protected void initListener(){
        mButton_SignIn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowManager.getInstance().goLoginAccountFlow(mActivity);
            }
        });

        mButton_Register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowManager.getInstance().goRegistrationFlow(mActivity);
            }
        });
    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_LOGIN).setSystemUI(this);
    }

    @Override
    public void returnFlow(Integer iStatusCode, Flow.FLOW flow, Class<?> clsFlow) {
        hideWaitingDialog();

        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(LoginActivity.class)) {
                mActivity.startActivity(new Intent(this, clsFlow));
                mActivity.finish();
            }
        } else {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }
}