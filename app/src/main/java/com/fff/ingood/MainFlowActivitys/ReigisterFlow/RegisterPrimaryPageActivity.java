package com.fff.ingood.MainFlowActivitys.ReigisterFlow;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fff.ingood.DataStructure.BaseActivity;
import com.fff.ingood.DataStructure.PersonAttributes;
import com.fff.ingood.R;
import com.fff.ingood.Tool.SerializableHashMap;

import java.util.HashMap;

/**
 * Created by yoie7 on 2018/5/4.
 */

public class RegisterPrimaryPageActivity extends BaseActivity {

    public static final int AGE_LIMITATION = 18;
    public static final String API_RESPONSE_TAG = "status_code";
    public static final String API_REQUEST_TAG = "verifycode";


    private EditText mEditText_Account;
    private EditText mEditText_Password;
    private EditText mEditText_Name;
    private Spinner mSpinner_Age;
    private Spinner mSpinner_Gender;
    private Button mButton_Next;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_primary_page);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView(){
        super.initView();

        mEditText_Account = (EditText) findViewById(R.id.edit_account);
        mEditText_Password = (EditText) findViewById(R.id.edit_pwd);
        mEditText_Name = (EditText) findViewById(R.id.edit_name);
        mSpinner_Age = (Spinner) findViewById(R.id.spinner_age);
        mSpinner_Gender = (Spinner) findViewById(R.id.spinner_gender);
        mButton_Next = (Button)findViewById(R.id.btn_done);

    }

    @Override
    protected void initData(){
        super.initData();
    }

    @Override
    protected void initListner(){


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

        mButton_Next.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(isDataValid()){
                    HashMap<String, Object> registerList = new HashMap<String, Object>();
                    registerList.put(PersonAttributes.ATTRIBUTES_PERSON_ACCOUNT, mEditText_Account.getText().toString());
                    registerList.put(PersonAttributes.ATTRIBUTES_PERSON_PASSWORD, mEditText_Password.getText().toString());
                    registerList.put(PersonAttributes.ATTRIBUTES_PERSON_NAME, mEditText_Name.getText().toString());
                    registerList.put(PersonAttributes.ATTRIBUTES_PERSON_AGE, mSpinner_Age.getSelectedItemPosition()+AGE_LIMITATION -1);
                    registerList.put(PersonAttributes.ATTRIBUTES_PERSON_GENDER, mSpinner_Gender.getSelectedItemPosition() == 1 ? "M":"F");

                    //String jString = JsonUtils.createJsonString(registerList);

                    Intent intent = new Intent(mActivity, RegisterLocationPageActivity.class);
                    SerializableHashMap hashMapList = new SerializableHashMap();
                    hashMapList.setObjectItems(registerList);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("mapList", hashMapList);
                    intent.putExtras(bundle);
                    startActivity(intent);



                }







                /*   DO Person Register Test
                Map<String, Object> jsonMap1 = new HashMap<String, Object>();
                jsonMap1.put(ATTRIBUTES_PERSON_ACCOUNT, "yoie1@gmail.com");
                jsonMap1.put(ATTRIBUTES_PERSON_PASSWORD, "12345678");
                jsonMap1.put(REGISTER_ATTRIBUTES_PERSON_NAME, "yyyyy");
                jsonMap1.put(REGISTER_ATTRIBUTES_PERSON_GENDER, "M");

                jsonMap1.put(REGISTER_ATTRIBUTES_PERSON_LOCATION, "12345ggg");
                jsonMap1.put(REGISTER_ATTRIBUTES_PERSON_AGE, 54);
                jsonMap1.put(REGISTER_ATTRIBUTES_PERSON_INTERESTS, "ball");
                jsonMap1.put(REGISTER_ATTRIBUTES_PERSON_NEW_PASSWORD, "wqwqwqdddddddwqw");


                DoPersonRegisterTask task = new DoPersonRegisterTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                Toast.makeText(RegisterPrimaryPageActivity.this, "doRegister OK", Toast.LENGTH_SHORT).show();

                                boolean result = false;
                                if (JsonUtils.getValueByTag(API_RESPONSE_TAG,strResponse).contains("0")) {
                                    result = true;
                                    Toast.makeText(RegisterPrimaryPageActivity.this, "doRegister OK", Toast.LENGTH_SHORT).show();
                                }
                                else
                                result =  false;


                            }
                        });
                task.execute(jsonMap1);
                */

                //   DO Person Query Test

                /*Map<String, Object> jsonMap1 = new HashMap<String, Object>();
                jsonMap1.put(PersonAttributes.ATTRIBUTES_PERSON_ACCOUNT, "yoie1@gmail.com");

                DoPersonQueryTask task = new DoPersonQueryTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                Toast.makeText(RegisterPrimaryPageActivity.this, "doRegister OK", Toast.LENGTH_SHORT).show();
                                PersonAttributes person = JsonUtils.getPersonAttr(strResponse);
                                boolean result = false;
                                if (JsonUtils.getValueByTag(API_RESPONSE_TAG,strResponse).contains("0")) {
                                    result = true;
                                    Toast.makeText(RegisterPrimaryPageActivity.this, "doRegister OK", Toast.LENGTH_SHORT).show();
                                }
                                else
                                result =  false;


                            }
                        });
                task.execute(jsonMap1);
                */

            }

        });
    }

    private boolean isDataValid(){
        boolean isValid = true;
        if (!Patterns.EMAIL_ADDRESS.matcher(mEditText_Account.getText().toString().trim()).matches()
                || TextUtils.isEmpty(mEditText_Account.getText().toString().trim())) {
            Toast.makeText(mActivity, "invalid email", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if(TextUtils.isEmpty(mEditText_Password.getText().toString().trim())
                || mEditText_Password.getText().toString().length() < 8){
            Toast.makeText(mActivity, "invalid password", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if(TextUtils.isEmpty(mEditText_Name.getText().toString().trim())){
            Toast.makeText(mActivity, "invalid name", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if(mSpinner_Gender.getSelectedItemPosition() == 0){
            Toast.makeText(mActivity, "please choose your gender", Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        if(mSpinner_Age.getSelectedItemPosition() == 0){
            Toast.makeText(mActivity, "please choose your age", Toast.LENGTH_SHORT).show();
            isValid = false;
        }


        return isValid;


    }





}


