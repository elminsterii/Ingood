package com.fff.ingood.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonUpdateLogic;
import com.fff.ingood.ui.CircleImageView;
import com.fff.ingood.ui.CircleProgressBarDialog;

public class PersonDataActivity extends BaseActivity implements PersonUpdateLogic.PersonUpdateLogicCaller {



    CircleProgressBarDialog mWaitingDialog;

    private ImageView mImageView_EditName;
    private ImageView mImageView_EditDescription;
    private ImageView mImageView_EditPhoto;
    private ImageView mImageView_EditInterests;

    private PersonDataActivity mActivity;

    private TextView mTextView_Name;
    private TextView mTextView_Description;
    private TextView mTextView_ChangePwd;

    private Button mButton_Save;

    private Spinner mSpinner_Gender;
    private Spinner mSpinner_Age;
    private Spinner mSpinner_Location;
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
        mTextView_Description = findViewById(R.id.textview_content_about_me);
        mTextView_ChangePwd = findViewById(R.id.textview_change_pwd);

        mSpinner_Gender = findViewById(R.id.spinner_gender);
        mSpinner_Age = findViewById(R.id.spinner_age);
        mSpinner_Location = findViewById(R.id.spinner_location);

        mImageView_EditPhoto = findViewById(R.id.imageview_edit_photo);
        mImageView_EditName = findViewById(R.id.imageview_edit_name);
        mImageView_EditDescription = findViewById(R.id.imageview_edit_about_me);
        mImageView_EditInterests = findViewById(R.id.imageview_edit_tags);


        mButton_Save = findViewById(R.id.btn_save);


        FrameLayout frameLayout = findViewById(R.id.flayour_headicon);
        mImageView_HeadIcon = (ImageView)frameLayout.getChildAt(0);
        mImageView_HeadIcon.setImageResource(R.drawable.sample_activity);
    }

    @Override
    protected void initData(){
        mTextView_Name.setText(PersonManager.getInstance().getPerson().getName());
        mTextView_Description.setText(PersonManager.getInstance().getPerson().getDescription());


    }

    @Override
    protected void initListener() {
        mTextView_ChangePwd.setOnClickListener(new TextView.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mButton_Save.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mImageView_EditPhoto.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mImageView_EditName.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mImageView_EditDescription.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mImageView_EditInterests.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        mSpinner_Location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinner_Age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinner_Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
