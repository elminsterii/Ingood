package com.fff.ingood.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;

import java.util.Objects;

public class RegistrationFormFragment extends BaseFragment {
    public static final int AGE_LIMITATION = 18;

    private EditText mEditTextAccount;
    private EditText mEditTextPassword;
    private EditText mEditTextConfirmPassword;

    private EditText mEditTextDisplayName;
    private Spinner mSpinnerAge;
    private Spinner mSpinnerGender;

    private ImageButton mImageButton_PwdEye;
    private Boolean mIsPwdEyeCheck = true;
    private ImageButton mImageButton_ConfirmPwdEye;
    private Boolean mIsConfirmPwdEyeCheck = true;

    public static RegistrationFormFragment newInstance() {
        return new RegistrationFormFragment();
    }

    public Person getUser() {
        Person personNew = null;

        if(isDataValid()) {
            personNew = new Person();
            personNew.setEmail(mEditTextAccount.getText().toString());
            personNew.setPassword(mEditTextPassword.getText().toString());
            personNew.setName(mEditTextDisplayName.getText().toString());
            personNew.setGender(mSpinnerGender.getSelectedItemPosition() == 1 ? "M":"F");
            personNew.setAge(String.valueOf(mSpinnerAge.getSelectedItemPosition() + AGE_LIMITATION - 1));
        }
        return personNew;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_registration_form, container, false);
    }

    @Override
    protected void initView() {
        mEditTextAccount = Objects.requireNonNull(getActivity()).findViewById(R.id.edit_account);
        mEditTextPassword = getActivity().findViewById(R.id.edit_pwd);
        mEditTextConfirmPassword = getActivity().findViewById(R.id.edit_pwd_confirm);

        mEditTextDisplayName = getActivity().findViewById(R.id.edit_name);
        mSpinnerAge = getActivity().findViewById(R.id.spinner_age);
        mSpinnerGender = getActivity().findViewById(R.id.spinner_gender);

        mImageButton_PwdEye = getActivity().findViewById(R.id.pwd_eye);
        mImageButton_ConfirmPwdEye = getActivity().findViewById(R.id.pwd_confirm_eye);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mSpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mImageButton_PwdEye.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsPwdEyeCheck) {
                    mIsPwdEyeCheck = false;
                    mImageButton_PwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIsPwdEyeCheck = true;
                    mImageButton_PwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mImageButton_ConfirmPwdEye.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mIsConfirmPwdEyeCheck){
                    mIsConfirmPwdEyeCheck = false;
                    mImageButton_ConfirmPwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditTextConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else{
                    mIsConfirmPwdEyeCheck = true;
                    mImageButton_ConfirmPwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditTextConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private boolean isDataValid(){
        if (!Patterns.EMAIL_ADDRESS.matcher(mEditTextAccount.getText().toString().trim()).matches()
                || TextUtils.isEmpty(mEditTextAccount.getText().toString().trim())) {
            mEditTextAccount.requestFocus();
            Toast.makeText(getActivity(), getResources().getText(R.string.register_email_format_wrong), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(mEditTextPassword.getText().toString().trim())
                || mEditTextPassword.getText().toString().length() < 8) {
            mEditTextPassword.requestFocus();
            Toast.makeText(getActivity(), getResources().getText(R.string.register_password_format_wrong), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(mEditTextConfirmPassword.getText().toString().trim())
                || mEditTextConfirmPassword.getText().toString().length() < 8) {
            mEditTextConfirmPassword.requestFocus();
            Toast.makeText(getActivity(), getResources().getText(R.string.register_confirm_password_format_wrong), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!mEditTextConfirmPassword.getText().toString().equals(mEditTextPassword.getText().toString())) {
            mEditTextConfirmPassword.requestFocus();
            Toast.makeText(getActivity(), getResources().getText(R.string.register_password_not_consistent), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(mEditTextDisplayName.getText().toString().trim())) {
            mEditTextDisplayName.requestFocus();
            Toast.makeText(getActivity(), getResources().getText(R.string.register_displayname_format_wrong), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mSpinnerGender.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_gender_choose), Toast.LENGTH_SHORT).show();
            return false;
        }

        if(mSpinnerAge.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_age_choose), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
