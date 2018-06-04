package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.flow.PreferenceManager;
import com.fff.ingood.flow.RegisterFlowLogic;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonLogInTask;
import com.fff.ingood.task.DoPersonVerifyTask;
import com.fff.ingood.tools.ParserUtils;

import static com.fff.ingood.activity.RegisterPrimaryPageActivity.API_RESPONSE_TAG;

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
        PreferenceManager.getInstance().setRegisterCurFlow(RegisterFlowLogic.REGISTER_FLOW_VERIFY);
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

            }
        });

        mButton_Send.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {



                Person userForVerify = new Person();
                userForVerify.setEmail(mUser.getEmail());
                DoPersonVerifyTask task = new DoPersonVerifyTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                if (ParserUtils.getStringByTag(API_RESPONSE_TAG,strResponse).contains("0")) {
                                    Toast.makeText(RegisterVerifyPageActivity.this, "doVerify OK", Toast.LENGTH_SHORT).show();
                                    Person temp = ParserUtils.getPersonAttr(strResponse);
                                    if(!temp.getVerifyCode().isEmpty()
                                            && temp.getVerifyCode() != ""
                                            && temp.getVerifyCode().equals(mEditText_VerifyCode.getText().toString())){
                                        mUser.setVerifyCode(temp.getVerifyCode());
                                        Class clsFlow = FlowManager.getInstance().goRegisterFlow();
                                        Intent intent = new Intent(mActivity, clsFlow);
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("user", mUser);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }


                                }
                            }
                        });
                task.execute(userForVerify);

            }
        });
    }


}
