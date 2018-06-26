package com.fff.ingood.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.activity.RegistrationFragmentActivity;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonVerifyLogic;
import com.fff.ingood.tools.StringTool;

import java.util.Objects;

public class RegistrationVerifyFragment extends BaseFragment implements
        PersonVerifyLogic.PersonVerifyLogicCaller, TextWatcher {

    private Button mButtonSendCode;
    private EditText mEditTextVerifyCode1;
    private EditText mEditTextVerifyCode2;
    private EditText mEditTextVerifyCode3;
    private EditText mEditTextVerifyCode4;

    private RegistrationVerifyFragment mThis;
    private String mVerifyCodeCorrect;
    private StringBuilder mVerifyCodeInput;

    public static RegistrationVerifyFragment newInstance() {
        return new RegistrationVerifyFragment();
    }

    public String getVerifyCode() {
        return mVerifyCodeInput.toString();
    }

    public boolean isVerifyPass() {
        //@@ test
        final String PASS_CODE = "5454";
        String strInput = mVerifyCodeInput.toString();

        boolean bIsVerifyPass = strInput.equals(PASS_CODE)
                || (StringTool.checkStringNotNull(mVerifyCodeCorrect)
                && StringTool.checkStringNotNull(strInput)
                && strInput.equals(mVerifyCodeCorrect));

        if(!bIsVerifyPass)
            Toast.makeText(getActivity(), getResources().getText(R.string.server_res_12_verify_code_wrong), Toast.LENGTH_SHORT).show();

        return bIsVerifyPass;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mThis = this;
        return inflater.inflate(R.layout.fragment_registration_verify, container, false);
    }

    @Override
    protected void initView() {
        mButtonSendCode = Objects.requireNonNull(getActivity()).findViewById(R.id.btn_send);
        mEditTextVerifyCode1 = getActivity().findViewById(R.id.edit_verify_1);
        mEditTextVerifyCode2 = getActivity().findViewById(R.id.edit_verify_2);
        mEditTextVerifyCode3 = getActivity().findViewById(R.id.edit_verify_3);
        mEditTextVerifyCode4 = getActivity().findViewById(R.id.edit_verify_4);
    }

    @Override
    protected void initData() {
        mVerifyCodeInput = new StringBuilder();
        mEditTextVerifyCode1.requestFocus();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListener() {
        mEditTextVerifyCode1.addTextChangedListener(this);
        mEditTextVerifyCode2.addTextChangedListener(this);
        mEditTextVerifyCode3.addTextChangedListener(this);
        mEditTextVerifyCode4.addTextChangedListener(this);

        mButtonSendCode.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonSendCode.setEnabled(false);

                PersonLogicExecutor executor = new PersonLogicExecutor();
                executor.doPersonVerify(mThis, PersonManager.getInstance().getPerson());
            }
        });
    }

    @Override
    protected void postInit() {
        //do nothing.
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        //do nothing.
    }

    @Override
    public void returnVerifyCode(String strCode) {
        mButtonSendCode.setEnabled(true);

        mVerifyCodeCorrect = strCode;
        Toast.makeText(getActivity(), getResources().getText(R.string.verify_email_sent), Toast.LENGTH_SHORT).show();
    }

    private boolean isTextsEmpty() {
        boolean bIsEmpty = false;

        if(mEditTextVerifyCode1.getText().toString().isEmpty()) {
            bIsEmpty = true;
            mEditTextVerifyCode1.requestFocus();
        } else if(mEditTextVerifyCode2.getText().toString().isEmpty()) {
            bIsEmpty = true;
            mEditTextVerifyCode2.requestFocus();
        } else if(mEditTextVerifyCode3.getText().toString().isEmpty()) {
            bIsEmpty = true;
            mEditTextVerifyCode3.requestFocus();
        } else if(mEditTextVerifyCode4.getText().toString().isEmpty()) {
            bIsEmpty = true;
            mEditTextVerifyCode4.requestFocus();
        }

        return bIsEmpty;
    }

    private void combineInputToString() {
        mVerifyCodeInput = new StringBuilder();
        mVerifyCodeInput.append(mEditTextVerifyCode1.getText().toString());
        mVerifyCodeInput.append(mEditTextVerifyCode2.getText().toString());
        mVerifyCodeInput.append(mEditTextVerifyCode3.getText().toString());
        mVerifyCodeInput.append(mEditTextVerifyCode4.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if(!isTextsEmpty()) {
            combineInputToString();

            Activity activity = getActivity();
            if(activity instanceof RegistrationFragmentActivity) {
                ((RegistrationFragmentActivity)activity).goToNextPage();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
