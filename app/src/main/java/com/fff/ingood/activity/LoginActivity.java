package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.FlowLogic;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.ui.CircleProgressBarDialog;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

/**
 * Created by yoie7 on 2018/5/3.
 */

public class LoginActivity extends BaseActivity {

    private EditText mEditText_Account;
    private EditText mEditText_Password;
    private Button mButton_SignIn;
    private Button mButton_Register;

    CircleProgressBarDialog mWaitingDialog;

    private LoginActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_signin_page);
        super.onCreate(savedInstanceState);

        mWaitingDialog = new CircleProgressBarDialog();
        mActivity = this;
    }

    @Override
    protected  void onResume() {
        super.onResume();
    }

    @Override
    protected void initView(){
        super.initView();

        mEditText_Account = findViewById(R.id.edit_account);
        mEditText_Password = findViewById(R.id.edit_pwd);
        mButton_SignIn = findViewById(R.id.btn_signin);
        mButton_Register = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData(){
        super.initData();
    }

    @Override
    protected void initListener(){
        mButton_SignIn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaitingDialog.show(getSupportFragmentManager(), LoginActivity.class.getName());

                Person person = new Person();
                person.setEmail(mEditText_Account.getText().toString());
                person.setPassword(mEditText_Password.getText().toString());

                FlowManager.getInstance().goLoginFlow(mActivity, person);
            }
        });

        mButton_Register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowManager.getInstance().goRegisterFlow(mActivity);
            }
        });
    }

    @Override
    public void returnFlow(Integer iStatusCode, FlowLogic.FLOW flow, Class<?> clsFlow) {
        if(mWaitingDialog != null
                && mWaitingDialog.getDialog() != null
                && mWaitingDialog.getDialog().isShowing())
            mWaitingDialog.dismiss();

        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(LoginActivity.class)) {
                startActivity(new Intent(this, clsFlow));
                mActivity.finish();
            }
        } else {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }
}
