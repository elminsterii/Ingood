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
import com.fff.ingood.global.Constants;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonVerifyTask;
import com.fff.ingood.tools.ParserUtils;

@SuppressLint("Registered")
public class RegisterVerifyPageActivity extends BaseActivity {

    private Button mButton_Next;
    private Button mButton_Send;
    private EditText mEditText_VerifyCode;
    private Person mUser = new Person();

    private String mVerifyCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_verify_page);
        super.onCreate(savedInstanceState);
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
        mUser = (Person)getIntent().getSerializableExtra("user");
    }

    @Override
    protected void initListener(){
        super.initListener();
        mButton_Next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //@@@ test
                if(mEditText_VerifyCode.getText().toString().equals("5454")) {
                    mUser.setVerifyCode("5454");
                    FlowManager.getInstance().goRegisterFlow(mActivity);
                } else if(!mVerifyCode.isEmpty()
                        && mVerifyCode.equals(mEditText_VerifyCode.getText().toString())) {
                    mUser.setVerifyCode(mVerifyCode);
                    FlowManager.getInstance().goRegisterFlow(mActivity);
                }
            }
        });

        mButton_Send.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person userForVerify = new Person();
                userForVerify.setEmail(mUser.getEmail());
                DoPersonVerifyTask<Person> task = new DoPersonVerifyTask<>(new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                if (ParserUtils.getStringByTag(Constants.TAG_SERVER_RESPONSE_STATUS_CODE, strResponse).equals(Constants.TAG_STATUS_CODE_SUCCESS)) {
                                    Toast.makeText(RegisterVerifyPageActivity.this, "doVerify OK", Toast.LENGTH_SHORT).show();
                                    Person temp = ParserUtils.getPersonAttr(strResponse);
                                    if(temp != null)
                                        mVerifyCode = temp.getVerifyCode();
                                }
                            }
                        });
                task.execute(userForVerify);
            }
        });
    }

    @Override
    public void returnFlow(boolean bSuccess, FlowLogic.FLOW flow, Class<?> clsFlow) {
        FlowManager.getInstance().setCurFlow(flow);

        if(clsFlow != null && bSuccess) {
            Intent intent = new Intent(mActivity, clsFlow);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", mUser);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
