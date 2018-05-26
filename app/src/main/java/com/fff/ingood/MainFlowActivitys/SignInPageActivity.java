package com.fff.ingood.MainFlowActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fff.ingood.DataStructure.BaseActivity;
import com.fff.ingood.MainFlowActivitys.ReigisterFlow.RegisterInterestPageActivity;
import com.fff.ingood.MainFlowActivitys.ReigisterFlow.RegisterPrimaryPageActivity;
import com.fff.ingood.R;

/**
 * Created by yoie7 on 2018/5/3.
 */

public class SignInPageActivity extends BaseActivity{

    private EditText mEditText_Account;
    private EditText mEditText_Password;
    private Button mButton_SignIn;
    private Button mButton_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_signin_page);
        super.onCreate(savedInstanceState);
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
    protected void initListner(){
        mButton_SignIn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, RegisterInterestPageActivity.class);
                startActivity(intent);

            }
        });

        mButton_Register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, RegisterPrimaryPageActivity.class);
                startActivity(intent);
            }
        });
    }
}
