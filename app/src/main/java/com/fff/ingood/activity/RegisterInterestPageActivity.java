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
import com.fff.ingood.flow.FlowLogic;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.flow.PreferenceManager;
import com.fff.ingood.task.AsyncResponder;
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
        ArrayList<Boolean> radioStateList = new ArrayList<>();
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

                mUser.setInterests(ParserUtils.listStringToString(interestsTagList, ','));
                mWaitingDialog.show(getSupportFragmentManager(), RegisterInterestPageActivity.class.getName());

                DoPersonRegisterTask<Person> task = new DoPersonRegisterTask<>(new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                mWaitingDialog.dismiss();
                                if (ParserUtils.getStringByTag(API_RESPONSE_TAG, strResponse).equals("0")) {
                                    Toast.makeText(RegisterInterestPageActivity.this, "doRegister OK", Toast.LENGTH_SHORT).show();

                                    PreferenceManager.getInstance().setRegisterSuccess(true);

                                    Person person = new Person();
                                    person.setEmail(mUser.getEmail());
                                    person.setPassword(mUser.getPassword());
                                    FlowManager.getInstance().goLoginFlow(mActivity, person);
                                } else {
                                    Toast.makeText(RegisterInterestPageActivity.this, "doRegister Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure() {
                                mWaitingDialog.dismiss();
                                Toast.makeText(RegisterInterestPageActivity.this, "doRegister Failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                task.execute(mUser);
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
