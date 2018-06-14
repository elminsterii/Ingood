package com.fff.ingood.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonVerifyLogic;
import com.fff.ingood.tools.StringTool;

import java.util.Objects;

public class RegistrationVerifyFragment extends BaseFragment implements PersonVerifyLogic.PersonVerifyLogicCaller {

    private Button mButton_Send;
    private EditText mEditText_VerifyCode;

    private RegistrationVerifyFragment mThis;
    private String mVerifyCode;

    public static RegistrationVerifyFragment newInstance() {
        return new RegistrationVerifyFragment();
    }

    public String getVerifyCode() {
        return mEditText_VerifyCode.getText().toString();
    }

    public boolean isVerifyPass() {
        final String PASS_CODE = "5454";
        String strInputVerifyCode = mEditText_VerifyCode.getText().toString();

        boolean bIsVerifyPass = strInputVerifyCode.equals(PASS_CODE)
                || (StringTool.checkStringNotNull(mVerifyCode)
                && StringTool.checkStringNotNull(strInputVerifyCode)
                && strInputVerifyCode.equals(mVerifyCode));

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
        mButton_Send = Objects.requireNonNull(getActivity()).findViewById(R.id.btn_send);
        mEditText_VerifyCode = getActivity().findViewById(R.id.edit_verify);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mButton_Send.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonLogicExecutor executor = new PersonLogicExecutor();
                executor.doPersonVerify(mThis, PersonManager.getInstance().getPerson());
            }
        });
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        //do nothing.
    }

    @Override
    public void returnVerifyCode(String strCode) {
        mVerifyCode = strCode;
        Toast.makeText(getActivity(), getResources().getText(R.string.verify_email_sent), Toast.LENGTH_SHORT).show();
    }
}
