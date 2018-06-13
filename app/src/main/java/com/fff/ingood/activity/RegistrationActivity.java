package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.fragment.RegistrationFormFragment;

public class RegistrationActivity extends BaseActivity {

    private FragmentManager mFragmentMgr;
    private Button mButtonNext;

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentViewId(R.layout.activity_registration);
        super.onCreate(savedInstanceState);

        mFragmentMgr = getSupportFragmentManager();
        mFragmentMgr.beginTransaction().add(R.id.layoutFragmentContainer, RegistrationFormFragment.newInstance());
        mFragmentMgr.beginTransaction().commit();
    }

    @Override
    protected void initView() {
        mButtonNext = findViewById(R.id.btnNext);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mButtonNext.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistrationFormFragment fragmentRegistrationForm = (RegistrationFormFragment)mFragmentMgr.findFragmentById(R.id.fragmentRegistrationForm);
                Person personNew = fragmentRegistrationForm.getUser();
                if(personNew != null) {

                }
                //FlowManager.getInstance().goRegisterFlow(mActivity);
            }
        });
    }
}
