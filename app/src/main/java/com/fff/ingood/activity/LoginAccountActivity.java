package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.global.SystemUIManager;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

public class LoginAccountActivity extends BaseActivity {

    private EditText mEditText_Account;
    private EditText mEditText_Password;
    private ImageButton mBtnBack;
    private TextView mTextViewTitle;
    private Button mButton_SignIn;
    private ImageButton mImageButton_PwdEye;
    private Boolean mIsPwdEyeCheck = true;
    private LoginAccountActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.signin_account_page);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        FlowManager.getInstance().goLoginFlow(mActivity);
    }


    @Override
    protected void preInit() {
        mActivity = this;
    }

    @Override
    protected void initView(){
        mEditText_Account = findViewById(R.id.edit_account);
        mEditText_Password = findViewById(R.id.edit_pwd);
        mButton_SignIn = findViewById(R.id.btnLogin);
        mImageButton_PwdEye = findViewById(R.id.pwd_eye);
        mBtnBack = findViewById(R.id.imgBack);
        mTextViewTitle = findViewById(R.id.textViewTitle);
    }

    @Override
    protected void initData(){
        mTextViewTitle.setText(R.string.sign_in);
    }

    @Override
    protected void initListener(){
        mButton_SignIn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaitingDialog(LoginActivity.class.getName());

                Person person = new Person();
                person.setEmail(mEditText_Account.getText().toString());
                person.setPassword(mEditText_Password.getText().toString());

                FlowManager.getInstance().goLoginFlow(mActivity, person);
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

        mBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    protected void initSystemUI() {
        SystemUIManager.getInstance(SystemUIManager.ACTIVITY_LIST.ACT_LOGINACC).setSystemUI(this);
    }

    @Override
    public void returnFlow(Integer iStatusCode, Flow.FLOW flow, Class<?> clsFlow) {
        hideWaitingDialog();

        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(LoginActivity.class)) {
                mActivity.startActivity(new Intent(this, clsFlow));
                mActivity.finish();
            }
        } else {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }
}
