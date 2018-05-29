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


        //bind view content
    }

    @Override
    protected void initData(){
        super.initData();
        Bundle bundle = getIntent().getExtras();
        String strResponse = bundle.getString("personData");
        mTextView_Display.setText(strResponse);
        mUser = ParserUtils.getPersonAttr(strResponse);
        mUser.setPassword(bundle.getString("pwd"));

    }
    @Override
    protected void initListner(){
        super.initListner();

    }
}
