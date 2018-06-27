package com.fff.ingood.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonUpdateLogic;
import com.fff.ingood.ui.CircleProgressBarDialog;

public class PersonDataActivity extends BaseActivity implements PersonUpdateLogic.PersonUpdateLogicCaller {



    CircleProgressBarDialog mWaitingDialog;

    private PersonDataActivity mActivity;

    private TextView mTextView_Name;
    private TextView mTextView_Gender;
    private TextView mTextView_Age;
    private TextView mTextView_Location;
    private ImageView mImageView_HeadIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_persondata);
        super.onCreate(savedInstanceState);

        mWaitingDialog = new CircleProgressBarDialog();
        mActivity = this;
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

    }

    @Override
    protected void initView(){
        mTextView_Name = findViewById(R.id.textview_name);
        mTextView_Gender = findViewById(R.id.textview_gender);
        mTextView_Age = findViewById(R.id.textview_age);
        mTextView_Location = findViewById(R.id.textview_location);
        mImageView_HeadIcon = findViewById(R.id.imageview_headicon);

    }

    @Override
    protected void initData(){
        mTextView_Name.setText(PersonManager.getInstance().getPerson().getName());
        mTextView_Age.setText(PersonManager.getInstance().getPerson().getAge());
        mTextView_Gender.setText(PersonManager.getInstance().getPerson().getGender());
        mTextView_Location.setText(PersonManager.getInstance().getPerson().getLocation());

    }

    @Override
    protected void initListener() {
        mTextView_Name.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person();
                new PersonLogicExecutor().doPersonUpdate(mActivity, person);

            }
        });
    }


    @Override
    public void returnStatus(Integer iStatusCode) {

    }

    @Override
    public void onUpdateSuccess() {

    }
}
