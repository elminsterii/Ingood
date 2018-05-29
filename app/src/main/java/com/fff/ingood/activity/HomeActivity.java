package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonLogInTask;
import com.fff.ingood.task.DoPersonLogOutTask;
import com.fff.ingood.tools.ParserUtils;

import java.util.HashMap;

import static com.fff.ingood.activity.RegisterPrimaryPageActivity.API_RESPONSE_TAG;

/**
 * Created by yoie7 on 2018/5/24.
 */

public class HomeActivity extends BaseActivity {
    private TextView mTextView_Display;
    private Button mButton_LogOut;

    private Person mUser = new Person();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_homepage);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(){
        super.initView();
        mTextView_Display = findViewById(R.id.text_display);
        mButton_LogOut = findViewById(R.id.btn_logout);


        //bind view content
    }

    @Override
    protected void initData(){
        super.initData();
        Bundle bundle = getIntent().getExtras();
        String strResponse = bundle.getString("personData");
        mUser.setPassword(bundle.getString("pwd"));
        mTextView_Display.setText(strResponse);
        mUser = ParserUtils.getPersonAttr(strResponse);

    }
    @Override
    protected void initListner(){
        super.initListner();
        mButton_LogOut.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> registerList = new HashMap<String, Object>();
                registerList.put(Person.ATTRIBUTES_PERSON_ACCOUNT, mUser.getEmail());
                registerList.put(Person.ATTRIBUTES_PERSON_PASSWORD, mUser.getPassword());

                DoPersonLogOutTask task = new DoPersonLogOutTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {

                                if (ParserUtils.getStringByTag(API_RESPONSE_TAG, strResponse).contains("0")) {
                                    Toast.makeText(HomeActivity.this, "doLogOut OK", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mActivity, LoginActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(HomeActivity.this, "doLogOut Failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                task.execute(registerList);


            }
        });
    }
}
