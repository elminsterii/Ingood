package com.fff.ingood.DataStructure;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yoie7 on 2018/5/3.
 */

public class BaseActivity extends AppCompatActivity {
    public Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //bind initial layout
        initView();
        initData();
        initListner();
    }

    protected void initView(){
        //bind view content
        super.setTitle(getTitle());
    }
    protected void initData(){
        //set data
        mActivity = this;
    }
    protected void initListner(){
        //bind listner
    }





}
