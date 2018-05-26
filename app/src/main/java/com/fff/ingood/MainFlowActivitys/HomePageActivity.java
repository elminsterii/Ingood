package com.fff.ingood.MainFlowActivitys;

import android.os.Bundle;
import android.widget.TextView;

import com.fff.ingood.DataStructure.BaseActivity;
import com.fff.ingood.R;

/**
 * Created by yoie7 on 2018/5/24.
 */

public class HomePageActivity extends BaseActivity {
    private TextView mTextViewDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_homepage);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(){
        super.initView();
        mTextViewDisplay = findViewById(R.id.text_display);


        //bind view content
    }

    @Override
    protected void initData(){
        super.initData();
        Bundle bundle = getIntent().getExtras();
        String strResponse = bundle.getString("personData");
        mTextViewDisplay.setText(strResponse);

    }
    @Override
    protected void initListner(){
        super.initListner();
    }
}
