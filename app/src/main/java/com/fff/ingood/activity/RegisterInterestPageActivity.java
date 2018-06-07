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
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.ui.CircleProgressBarDialog;

import java.util.ArrayList;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;

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
    private RegisterInterestPageActivity mActivity;

    CircleProgressBarDialog mWaitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_interest_page);
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
        mButton_Done = findViewById(R.id.btn_next);
        mInterestsListView = findViewById(R.id.interest_list);

        mWaitingDialog = new CircleProgressBarDialog();
    }

    @Override
    protected void initData(){
        super.initData();
        mUser = (Person)getIntent().getSerializableExtra("user");

        ArrayList<Boolean> radioStateList = new ArrayList<>();
        for (String ignored : interests_item)
            radioStateList.add(false);

        mRadioListAdapter = new RadioListAdapter(this, interests_item, radioStateList);
        mInterestsListView.setAdapter(mRadioListAdapter);
    }

    @Override
    protected void initListener(){

        mButton_Done.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWaitingDialog.show(getSupportFragmentManager(), RegisterInterestPageActivity.class.getName());

                ArrayList<Boolean> radioStateList;
                radioStateList = mRadioListAdapter.getRadioStateList();
                ArrayList<String> interestsTagList = new ArrayList<>();
                for(int i = 0; i < radioStateList.size(); i++){
                    if(radioStateList.get(i)){
                        interestsTagList.add(interests_item[i]);
                    }
                }

                mUser.setInterests(ParserUtils.listStringToString(interestsTagList, ','));
                FlowManager.getInstance().goRegisterPersonFlow(mActivity, mUser);
            }
        });
    }

    @Override
    public void returnFlow(Integer iStatusCode, FlowLogic.FLOW flow, Class<?> clsFlow) {
        if(mWaitingDialog != null
                && mWaitingDialog.getDialog() != null
                && mWaitingDialog.getDialog().isShowing())
            mWaitingDialog.dismiss();

        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(RegisterInterestPageActivity.class)) {

                if(flow.equals(FlowLogic.FLOW.FL_LOGIN)) {
                    //Register success, and go login.
                    FlowManager.getInstance().goLoginFlow(mActivity, mUser);
                } else {
                    Intent intent = new Intent(mActivity, clsFlow);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user", mUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        } else {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }
}
