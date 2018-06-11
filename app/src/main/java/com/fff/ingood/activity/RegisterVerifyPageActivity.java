package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.FlowLogic;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.flow.VerifyEmailFlowLogic;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.ServerResponse;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

@SuppressLint("Registered")
public class RegisterVerifyPageActivity extends BaseActivity implements VerifyEmailFlowLogic.VerifyEmailFlowLogicCaller {

    private Button mButton_Next;
    private Button mButton_Send;
    private EditText mEditText_VerifyCode;

    private String mVerifyCode;
    private RegisterVerifyPageActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_verify_page);
        super.onCreate(savedInstanceState);

        mActivity = this;
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void initView(){
        super.initView();
        mButton_Next = findViewById(R.id.btn_next);
        mButton_Send = findViewById(R.id.btn_send);
        mEditText_VerifyCode = findViewById(R.id.edit_verify);
    }

    @Override
    protected void initData(){
        super.initData();
    }

    @Override
    protected void initListener(){
        super.initListener();
        mButton_Next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //@@@ test
                Person person = PersonManager.getInstance().getPerson();
                if(mEditText_VerifyCode.getText().toString().equals("5454")) {
                    person.setVerifyCode("5454");
                    FlowManager.getInstance().goRegisterFlow(mActivity);
                } else if((mVerifyCode != null && !mVerifyCode.isEmpty())
                        && mVerifyCode.equals(mEditText_VerifyCode.getText().toString())) {
                    person.setVerifyCode(mVerifyCode);
                    FlowManager.getInstance().goRegisterFlow(mActivity);
                } else {
                    Toast.makeText(mActivity, getResources().getText(R.string.server_res_12_verify_code_wrong), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mButton_Send.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowManager.getInstance().goVerifyEmailFlow(mActivity, PersonManager.getInstance().getPerson());
            }
        });
    }

    @Override
    public void returnFlow(Integer iStatusCode, FlowLogic.FLOW flow, Class<?> clsFlow) {
        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(RegisterVerifyPageActivity.class)) {
                startActivity(new Intent(mActivity, clsFlow));
                mActivity.finish();
            }
        } else {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void returnVerifyCode(String strCode) {
        mVerifyCode = strCode;
    }
}
