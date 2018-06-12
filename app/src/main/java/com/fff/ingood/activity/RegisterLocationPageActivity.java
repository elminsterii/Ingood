package com.fff.ingood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.fff.ingood.R;
import com.fff.ingood.flow.Flow;
import com.fff.ingood.flow.FlowManager;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.ServerResponse;

import static com.fff.ingood.global.ServerResponse.getServerResponseDescriptions;


/**
 * Created by yoie7 on 2018/5/21.
 */

public class RegisterLocationPageActivity extends BaseActivity {

    private Button mButton_Next;
    private Spinner mSpinner_Location;

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
        mButton_Next = findViewById(R.id.btn_next);
        mSpinner_Location = findViewById(R.id.spinner_location);
    }

    @Override
    protected void initData(){

    }

    @Override
    protected void initListener(){
        mButton_Next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSpinner_Location.getSelectedItemPosition() != 0){
                    PersonManager.getInstance().getPerson().setLocation(mSpinner_Location.getSelectedItem().toString());
                    FlowManager.getInstance().goRegisterFlow(mActivity);
                }
                else{
                    Toast.makeText(RegisterLocationPageActivity.this, getResources().getText(R.string.register_location_choose), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void returnFlow(Integer iStatusCode, Flow.FLOW flow, Class<?> clsFlow) {
        FlowManager.getInstance().setCurFlow(flow);

        if(iStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS_INT)) {
            if(clsFlow != null
                    && !clsFlow.isInstance(RegisterLocationPageActivity.class)) {
                mActivity.startActivity(new Intent(mActivity, clsFlow));
                mActivity.finish();
            }
        } else {
            Toast.makeText(mActivity, getServerResponseDescriptions().get(iStatusCode), Toast.LENGTH_SHORT).show();
        }
    }
}
