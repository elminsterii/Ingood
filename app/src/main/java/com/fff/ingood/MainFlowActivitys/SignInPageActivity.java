package com.fff.ingood.MainFlowActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fff.ingood.activity.BaseActivity;
import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.activity.RegisterInterestPageActivity;
import com.fff.ingood.activity.RegisterPrimaryPageActivity;
import com.fff.ingood.R;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonLogInTask;
import com.fff.ingood.tools.ParserUtils;

import java.util.HashMap;

import static com.fff.ingood.activity.RegisterPrimaryPageActivity.API_RESPONSE_TAG;

/**
 * Created by yoie7 on 2018/5/3.
 */

public class SignInPageActivity extends BaseActivity{

    private EditText mEditText_Account;
    private EditText mEditText_Password;
    private Button mButton_SignIn;
    private Button mButton_Register;
    private HashMap<String, Object> mRegisterList = new HashMap<>();

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
    protected void initListner() {
        mButton_SignIn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> registerList = new HashMap<String, Object>();
                registerList.put(Person.ATTRIBUTES_PERSON_ACCOUNT, mRegisterList.get(Person.ATTRIBUTES_PERSON_ACCOUNT));
                registerList.put(Person.ATTRIBUTES_PERSON_PASSWORD, mRegisterList.get(Person.ATTRIBUTES_PERSON_PASSWORD));

                DoPersonLogInTask task = new DoPersonLogInTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                if (ParserUtils.getStringByTag(API_RESPONSE_TAG,strResponse).contains("0")) {
                                    Toast.makeText(SignInPageActivity.this, "doLogin OK", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mActivity, LogOutPageActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("personData", strResponse);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            }
                        });
                task.execute(registerList);
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
