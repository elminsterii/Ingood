package com.fff.ingood.activity;

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

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.tools.SerializableHashMap;

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


    private Person mUser = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_primary_page);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView(){
        super.initView();

        mEditText_Account = findViewById(R.id.edit_account);
        mEditText_Password = findViewById(R.id.edit_pwd);
        mEditText_Name = findViewById(R.id.edit_name);
        mSpinner_Age = findViewById(R.id.spinner_age);
        mSpinner_Gender = findViewById(R.id.spinner_gender);
        mButton_Next = findViewById(R.id.btn_done);
    }

    @Override
    protected void initData(){
        super.initData();
    }

    @Override
    protected void initListener(){
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

                    mUser.setEmail(mEditText_Account.getText().toString());
                    mUser.setPassword(mEditText_Password.getText().toString());
                    mUser.setName(mEditText_Name.getText().toString());
                    mUser.setGender(mSpinner_Gender.getSelectedItemPosition() == 1 ? "M":"F");
                    mUser.setAge(String.valueOf(mSpinner_Age.getSelectedItemPosition()+AGE_LIMITATION -1));

                    Intent intent = new Intent(mActivity, RegisterLocationPageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", mUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

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


