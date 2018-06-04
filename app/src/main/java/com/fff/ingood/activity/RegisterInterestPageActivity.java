package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.adapter.RadioListAdapter;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.flow.PreferenceManager;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonLogInTask;
import com.fff.ingood.task.DoPersonRegisterTask;
import com.fff.ingood.tools.ParserUtils;

import java.util.ArrayList;

import static com.fff.ingood.activity.RegisterPrimaryPageActivity.API_RESPONSE_TAG;

/**
 * Created by yoie7 on 2018/5/21.
 */

public class RegisterInterestPageActivity extends BaseActivity {

    private String[] interests_item = { "SPORT", "MUSIC", "FOOD", "BOOK", "MOVIE",
            "CULTURE", "OUTSIDE", "INDOOR"};
    private Button mButton_Done;
    private ListView mInterestsListView;

    private RadioListAdapter mRadioListAdapter;

    private Person mUser = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_interest_page);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void initView(){
        super.initView();
        mButton_Done = findViewById(R.id.btn_next);
        mInterestsListView = findViewById(R.id.interest_list);
    }

    @Override
    protected void initData(){
        super.initData();
        mUser = (Person)getIntent().getSerializableExtra("user");
        ArrayList<Boolean> radioStateList = new ArrayList<Boolean>();
        for(int i = 0; i< interests_item.length; i++){
            radioStateList.add(false);
        }
        mRadioListAdapter = new RadioListAdapter(this, interests_item, radioStateList);
        mInterestsListView.setAdapter(mRadioListAdapter);
    }

    @Override
    protected void initListener(){

        mButton_Done.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Boolean> radioStateList;
                radioStateList = mRadioListAdapter.getRadioStateList();
                ArrayList<String> interestsTagList = new ArrayList<>();
                for(int i = 0; i < radioStateList.size(); i++){
                    if(radioStateList.get(i)){
                        interestsTagList.add(interests_item[i]);
                    }
                }

                mUser.setVerifyCode("5454");
                mUser.setInterests(ParserUtils.listStringToString(interestsTagList, ','));
                DoPersonRegisterTask task = new DoPersonRegisterTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                if (ParserUtils.getStringByTag(API_RESPONSE_TAG,strResponse).contains("0")) {
                                    Toast.makeText(RegisterInterestPageActivity.this, "doRegister OK", Toast.LENGTH_SHORT).show();

                                    PreferenceManager.getInstance().setRegisterSuccess(true);

                                    Person userForLogin = new Person();
                                    userForLogin.setEmail(mUser.getEmail());
                                    userForLogin.setPassword(mUser.getPassword());

                                    DoPersonLogInTask task = new DoPersonLogInTask(mActivity,
                                            new AsyncResponder<String>() {
                                                @Override
                                                public void onSuccess(String strResponse) {
                                                    if (ParserUtils.getStringByTag(API_RESPONSE_TAG,strResponse).contains("0")) {
                                                        Toast.makeText(RegisterInterestPageActivity.this, "doLogin OK", Toast.LENGTH_SHORT).show();
                                                        Class clsFlow = FlowManager.getInstance().goRegisterFlow();

                                                        if(clsFlow != null) {
                                                            Intent intent = new Intent(mActivity, clsFlow);
                                                            Bundle bundle = new Bundle();
                                                            bundle.putString("personData", strResponse);
                                                            intent.putExtras(bundle);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                }
                                            });
                                    task.execute(userForLogin);
                                }
                                else {
                                    Toast.makeText(RegisterInterestPageActivity.this, "doRegister Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                task.execute(mUser);
            }
        });
    }
}
