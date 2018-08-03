package com.fff.ingood.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.logic.PersonCheckExistLogic;
import com.fff.ingood.logic.PersonLogicExecutor;

import java.util.Objects;

import static com.fff.ingood.global.GlobalProperty.AGE_LIMITATION;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT;

public class RegistrationFormFragment extends BaseFragment implements PersonCheckExistLogic.PersonCheckExistLogicCaller {
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

    private boolean m_bIsEmailExist = true;

    private RegistrationFormFragment mThis;

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
            personNew.setGender(String.valueOf(mSpinnerGender.getSelectedItem()));
            personNew.setAge(String.valueOf(mSpinnerAge.getSelectedItemPosition() + AGE_LIMITATION - 1));
        }
        return personNew;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mThis = this;
        return inflater.inflate(R.layout.fragment_registration_form, container, false);
    }

    @Override
    protected void initView() {
        mEditTextDisplayName = Objects.requireNonNull(getActivity()).findViewById(R.id.edit_name);
        mEditTextAccount = getActivity().findViewById(R.id.edit_account);
        mEditTextPassword = getActivity().findViewById(R.id.edit_pwd);
        mEditTextConfirmPassword = getActivity().findViewById(R.id.edit_pwd_confirm);

        mSpinnerAge = getActivity().findViewById(R.id.spinner_age);
        mSpinnerGender = getActivity().findViewById(R.id.spinner_gender);

        mImageButton_PwdEye = getActivity().findViewById(R.id.pwd_eye);
        mImageButton_ConfirmPwdEye = getActivity().findViewById(R.id.pwd_confirm_eye);
    }

    @Override
    protected void initData() {
        String[] arrAges = getResources().getStringArray(R.array.user_age_list);
        ArrayAdapter<String> spinnerAgeAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_item, arrAges);
        spinnerAgeAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerAge.setAdapter(spinnerAgeAdapter);

        String[] arrGender = getResources().getStringArray(R.array.user_gender_list);
        ArrayAdapter<String> spinnerGenderAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.spinner_item, arrGender);
        spinnerGenderAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerGender.setAdapter(spinnerGenderAdapter);
    }

    @Override
    protected void initListener() {

        mEditTextAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    checkEmailExistFromServer();
            }
        });

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
                if(mIsConfirmPwdEyeCheck) {
                    mIsConfirmPwdEyeCheck = false;
                    mImageButton_ConfirmPwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_y));
                    mEditTextConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    mIsConfirmPwdEyeCheck = true;
                    mImageButton_ConfirmPwdEye.setImageDrawable(getResources().getDrawable(R.drawable.view_g));
                    mEditTextConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    @Override
    protected void postInit() {

    }

    private boolean checkDisplayNameValid() {
        if(TextUtils.isEmpty(mEditTextDisplayName.getText().toString().trim())) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_displayname_format_wrong), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextDisplayName, getResources().getColor(R.color.colorWarningRed));
            return false;
        }
        setViewUnderlineColor(mEditTextDisplayName, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private boolean checkEmailValid() {
        if (!Patterns.EMAIL_ADDRESS.matcher(mEditTextAccount.getText().toString().trim()).matches()
                || TextUtils.isEmpty(mEditTextAccount.getText().toString().trim())) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_email_format_wrong), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextAccount, getResources().getColor(R.color.colorWarningRed));
            return false;
        }

        if(m_bIsEmailExist) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_email_was_exist), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextAccount, getResources().getColor(R.color.colorWarningRed));
            return false;
        }

        setViewUnderlineColor(mEditTextAccount, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private boolean checkPasswordValid() {
        if(TextUtils.isEmpty(mEditTextPassword.getText().toString().trim())
                || mEditTextPassword.getText().toString().length() < 8) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_password_format_wrong), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextPassword, getResources().getColor(R.color.colorWarningRed));
            return false;
        }
        setViewUnderlineColor(mEditTextPassword, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private boolean checkConfirmPasswordValid() {
        if(TextUtils.isEmpty(mEditTextConfirmPassword.getText().toString().trim())
                || mEditTextConfirmPassword.getText().toString().length() < 8) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_confirm_password_format_wrong), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextConfirmPassword, getResources().getColor(R.color.colorWarningRed));
            return false;
        }
        setViewUnderlineColor(mEditTextConfirmPassword, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private boolean checkConfirmPasswordEqualPassword() {
        if(!mEditTextConfirmPassword.getText().toString().equals(mEditTextPassword.getText().toString())) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_password_not_consistent), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mEditTextConfirmPassword, getResources().getColor(R.color.colorWarningRed));
            return false;
        }
        setViewUnderlineColor(mEditTextConfirmPassword, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private boolean checkGenderValid() {
        if(mSpinnerGender.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_gender_choose), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mSpinnerGender, getResources().getColor(R.color.colorWarningRed));
            return false;
        }
        setViewUnderlineColor(mSpinnerGender, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private boolean checkAgeValid() {
        if(mSpinnerAge.getSelectedItemPosition() == 0) {
            Toast.makeText(getActivity(), getResources().getText(R.string.register_age_choose), Toast.LENGTH_SHORT).show();
            setViewUnderlineColor(mSpinnerAge, getResources().getColor(R.color.colorWarningRed));
            return false;
        }
        setViewUnderlineColor(mSpinnerAge, getResources().getColor(R.color.colorTextHint));
        return true;
    }

    private boolean isDataValid() {
        return checkDisplayNameValid()
                && checkEmailValid()
                && checkPasswordValid()
                && checkConfirmPasswordValid()
                && checkConfirmPasswordEqualPassword()
                && checkGenderValid()
                && checkAgeValid();
    }

    private void checkEmailExistFromServer() {
        String strEmail = mEditTextAccount.getText().toString();
        Person person = new Person();
        person.setEmail(strEmail);

        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonCheckExist(mThis, person);
    }

    private void setViewUnderlineColor(View view, int iColor) {
        ColorStateList colorStateList = ColorStateList.valueOf(iColor);
        ViewCompat.setBackgroundTintList(view, colorStateList);
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(iStatusCode.equals(STATUS_CODE_FAIL_USER_ALREADY_EXIST_INT))
            m_bIsEmailExist = true;
    }

    @Override
    public void onPersonNotExist() {
        m_bIsEmailExist = false;
    }
}
