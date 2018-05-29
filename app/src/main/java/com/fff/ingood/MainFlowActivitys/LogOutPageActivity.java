package com.fff.ingood.MainFlowActivitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fff.ingood.activity.BaseActivity;
import com.fff.ingood.R;

public class LogOutPageActivity extends BaseActivity {

    private Button return_mainPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_logout_page);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initView(){
        super.initView();
        return_mainPage = findViewById(R.id.rtn_mainpage);
    }

    @Override
    protected void initData(){
        super.initData();
    }

    @Override
    protected void initListner() {
        return_mainPage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, SignInPageActivity.class);
                startActivity(intent);
            }
        });
    }


}
