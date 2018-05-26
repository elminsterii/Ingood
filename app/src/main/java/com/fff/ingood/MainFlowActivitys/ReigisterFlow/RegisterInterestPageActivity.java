package com.fff.ingood.MainFlowActivitys.ReigisterFlow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fff.ingood.Adapter.RadioListAdapter;
import com.fff.ingood.DataStructure.BaseActivity;
import com.fff.ingood.DataStructure.PersonAttributes;
import com.fff.ingood.HttpConnect.Task.Abstract.AsyncResponder;
import com.fff.ingood.HttpConnect.Task.Implement.DoPersonRegisterTask;
import com.fff.ingood.MainFlowActivitys.HomePageActivity;
import com.fff.ingood.R;
import com.fff.ingood.Tool.ParserUtils;
import com.fff.ingood.Tool.SerializableHashMap;

import java.util.ArrayList;
import java.util.HashMap;

import static com.fff.ingood.MainFlowActivitys.ReigisterFlow.RegisterPrimaryPageActivity.API_REQUEST_TAG;
import static com.fff.ingood.MainFlowActivitys.ReigisterFlow.RegisterPrimaryPageActivity.API_RESPONSE_TAG;

/**
 * Created by yoie7 on 2018/5/21.
 */

public class RegisterInterestPageActivity extends BaseActivity {

    private String[] interests_item = { "SPORT", "MUSIC", "FOOD", "BOOK", "MOVIE",
            "CULTURE", "OUTSIDE", "INDOOR"};
    private HashMap<String, Object> mRegisterList = new HashMap<>();
    private Button mButton_Done;
    private ListView mInterestsListView;

    private RadioListAdapter mRadioListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_interest_page);
        super.onCreate(savedInstanceState);
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
        Bundle bundle = getIntent().getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get("mapList");
        mRegisterList = serializableHashMap.getObjectItems();
        ArrayList<Boolean> radioStateList = new ArrayList<Boolean>();
        for(int i = 0; i< interests_item.length; i++){
            radioStateList.add(false);
        }
        mRadioListAdapter = new RadioListAdapter(this, interests_item, radioStateList);
        mInterestsListView.setAdapter(mRadioListAdapter);
    }

    @Override
    protected void initListner(){

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

                mRegisterList.put(PersonAttributes.ATTRIBUTES_PERSON_INTERESTS, ParserUtils.listStringToString(interestsTagList, ','));
                mRegisterList.put(API_REQUEST_TAG, "5454");

                DoPersonRegisterTask task = new DoPersonRegisterTask(mActivity,
                        new AsyncResponder<String>() {
                            @Override
                            public void onSuccess(String strResponse) {
                                Toast.makeText(RegisterInterestPageActivity.this, "doRegister OK", Toast.LENGTH_SHORT).show();

                                if (ParserUtils.getValueByTag(API_RESPONSE_TAG,strResponse).contains("0")) {
                                    Toast.makeText(RegisterInterestPageActivity.this, "doRegister OK", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mActivity, HomePageActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(RegisterInterestPageActivity.this, "doRegister Failed", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });
                task.execute(mRegisterList);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
