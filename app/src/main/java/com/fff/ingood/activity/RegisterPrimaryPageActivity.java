package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.ServerResponse;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;


/**
 * Created by yoie7 on 2018/5/4.
 */

public class RegisterPrimaryPageActivity extends BaseActivity {

    public static final int AGE_LIMITATION = 18;

    private EditText mEditText_Account;
    private EditText mEditText_Password;
    private EditText mEditText_ConfirmPassword;

    private EditText mEditText_Name;
    private Spinner mSpinner_Age;
    private Spinner mSpinner_Gender;
    private Button mButton_Next;

    private ImageButton mImageButton_PwdEye;
    private Boolean mIsPwdEyeCheck = true;
    private ImageButton mImageButton_ConfirmPwdEye;
    private Boolean mIsConfirmPwdEyeCheck = true;

    private Person mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_primary_page);
        super.onCreate(savedInstanceState);

        mUser = new Person();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initView() {
        super.initView();

        mEditText_Account = findViewById(R.id.edit_account);
        mEditText_Password = findViewById(R.id.edit_pwd);
        mEditText_ConfirmPassword = findViewById(R.id.edit_pwd_confirm);

        mEditText_Name = findViewById(R.id.edit_name);
        mSpinner_Age = findViewById(R.id.spinner_age);
        mSpinner_Gender = findViewById(R.id.spinner_gender);
        mButton_Next = findViewById(R.id.btn_done);

        mImageButton_PwdEye = findViewById(R.id.pwd_eye);
        mImageButton_ConfirmPwdEye = findViewById(R.id.pwd_confirm_eye);
    }

    @Override
    protected void initData(){
        super.initData();
    }

    @Override
    protected void initListener() {
        mSpinner_Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        mButton_Next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDataValid()){

                    mUser.setEmail(mEditText_Account.getText().toString());
                    mUser.setPassword(mEditText_Password.getText().toString());
                    mUser.setName(mEditText_Name.getText().toString());
                    mUser.setGender(mSpinner_Gender.getSelectedItemPosition() == 1 ? "M":"F");
                    mUser.setAge(String.valueOf(mSpinner_Age.getSelectedItemPosition()+AGE_LIMITATION -1));

                    FlowManager.getInstance().goRegisterFlow(mActivity);
                }
            }
        });

        mImageButton_PwdEye.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsPwdEyeCheck) {
                    mIsPwdEyeCheck = false;
                    mImageButton_PwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditText_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIsPwdEyeCheck = true;
                    mImageButton_PwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditText_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mImageButton_ConfirmPwdEye.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsConfirmPwdEyeCheck){
                    mIsConfirmPwdEyeCheck = false;
                    mImageButton_ConfirmPwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditText_ConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else{
                    mIsConfirmPwdEyeCheck = true;
                    mImageButton_ConfirmPwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditText_ConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private boolean isDataValid(){
        boolean isValid = true;
        if (!Patterns.EMAIL_ADDRESS.matcher(mEditText_Account.getText().toString().trim()).matches()
                || TextUtils.isEmpty(mEditText_Account.getText().toString().trim())) {
            Toast.makeText(mActivity, getResources().getText(R.string.register_email_format_wrong), Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if(TextUtils.isEmpty(mEditText_Password.getText().toString().trim())
                || mEditText_Password.getText().toString().length() < 8){
            Toast.makeText(mActivity, getResources().getText(R.string.register_password_format_wrong), Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if(TextUtils.isEmpty(mEditText_Name.getText().toString().trim())){
            Toast.makeText(mActivity, getResources().getText(R.string.register_displayname_format_wrong), Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if(mSpinner_Gender.getSelectedItemPosition() == 0){
            Toast.makeText(mActivity, getResources().getText(R.string.register_gender_choose), Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if(mSpinner_Age.getSelectedItemPosition() == 0){
            Toast.makeText(mActivity, getResources().getText(R.string.register_age_choose), Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void returnFlow(Integer iStatusCode, Flow.FLOW flow, Class<?> clsFlow) {
        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(RegisterPrimaryPageActivity.class)) {
                PersonManager.getInstance().setPerson(mUser);
                mActivity.startActivity(new Intent(mActivity, clsFlow));
                mActivity.finish();
            }
        } else {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }
}


