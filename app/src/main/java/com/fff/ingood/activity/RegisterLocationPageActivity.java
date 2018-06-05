package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.flow.FlowLogic;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.Constants;


/**
 * Created by yoie7 on 2018/5/21.
 */

public class RegisterLocationPageActivity extends BaseActivity {

    private Button mButton_Next;
    private Spinner mSpinner_Location;
    private Person mUser = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_register_location_page);
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
        mSpinner_Location = findViewById(R.id.spinner_location);
    }

    @Override
    protected void initData(){
        super.initData();
        mUser = (Person)getIntent().getSerializableExtra("user");
    }

    @Override
    protected void initListener(){
        mButton_Next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSpinner_Location.getSelectedItemPosition() != 0){
                    mUser.setLocation(mSpinner_Location.getSelectedItem().toString());
                    FlowManager.getInstance().goRegisterFlow(mActivity);
                }
                else{
                    Toast.makeText(RegisterLocationPageActivity.this, "Please choose Your Location!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void returnFlow(Integer iStatusCode, FlowLogic.FLOW flow, Class<?> clsFlow) {
        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(Constants.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(RegisterLocationPageActivity.class)) {
                Intent intent = new Intent(mActivity, clsFlow);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", mUser);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        } else {
            Toast.makeText(mActivity, "statusCode = " + iStatusCode, Toast.LENGTH_SHORT).show();
        }
    }
}
