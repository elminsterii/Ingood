package com.fff.ingood.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.fff.ingood.R;
import com.fff.ingood.data.Person;
import com.fff.ingood.fragment.RegistrationFormFragment;
import com.fff.ingood.fragment.RegistrationInterestFragment;
import com.fff.ingood.fragment.RegistrationLocationFragment;
import com.fff.ingood.fragment.RegistrationVerifyFragment;

public class RegistrationActivity extends BaseFragmentActivity {

    private static final String TAG_FRAGMENT_REGISTRATION_FORM = RegistrationFormFragment.class.getName();
    private static final String TAG_FRAGMENT_REGISTRATION_VERIFY = RegistrationVerifyFragment.class.getName();
    private static final String TAG_FRAGMENT_REGISTRATION_LOCATION = RegistrationLocationFragment.class.getName();
    private static final String TAG_FRAGMENT_REGISTRATION_INTEREST = RegistrationInterestFragment.class.getName();

    private RegistrationFormFragment mFragmentRegistrationForm;
    private RegistrationVerifyFragment mFragmentRegistrationVerify;
    private RegistrationLocationFragment mFragmentRegistrationLocation;

    private FragmentManager mFragmentMgr;
    private Button mButtonNext;

    private Fragment mCurFragment;
    private String mCurFragmentTag;
    private boolean bInitialize = false;

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentViewId(R.layout.activity_registration);
        super.onCreate(savedInstanceState);

        mFragmentMgr = getSupportFragmentManager();

        if(savedInstanceState != null) {
            mFragmentRegistrationForm = (RegistrationFormFragment) mFragmentMgr.findFragmentByTag(TAG_FRAGMENT_REGISTRATION_FORM);
            mFragmentRegistrationVerify = (RegistrationVerifyFragment) mFragmentMgr.findFragmentByTag(TAG_FRAGMENT_REGISTRATION_VERIFY);
            mFragmentRegistrationLocation = (RegistrationLocationFragment) mFragmentMgr.findFragmentByTag(TAG_FRAGMENT_REGISTRATION_LOCATION);
        } else {
            mFragmentRegistrationForm = RegistrationFormFragment.newInstance();
            mFragmentRegistrationVerify = RegistrationVerifyFragment.newInstance();
            mFragmentRegistrationLocation = RegistrationLocationFragment.newInstance();
        }
        mCurFragment = mFragmentRegistrationForm;
        mCurFragmentTag = TAG_FRAGMENT_REGISTRATION_FORM;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showFragment(mCurFragment, mCurFragmentTag);
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
            @SuppressLint("CommitTransaction")
            @Override
            public void onClick(View v) {
                if(mCurFragment instanceof RegistrationFormFragment) {
                    Person personNew = mFragmentRegistrationForm.getUser();

                    if (personNew != null)
                        showFragment(mFragmentRegistrationVerify, TAG_FRAGMENT_REGISTRATION_VERIFY);
                } else if(mCurFragment instanceof RegistrationVerifyFragment) {
                    if(mFragmentRegistrationVerify.isVerifyPass())
                        showFragment(mFragmentRegistrationLocation, TAG_FRAGMENT_REGISTRATION_LOCATION);
                }
            }
        });
    }

    @SuppressLint("CommitTransaction")
    private void showFragment(Fragment fragment, String strTag) {
        if(bInitialize)
            if (mCurFragment == fragment)
                return;
        else
            bInitialize = true;

        mFragmentMgr.executePendingTransactions();

        for(Fragment fr : mFragmentMgr.getFragments())
            mFragmentMgr.beginTransaction().hide(fr);

        if(!fragment.isAdded())
            mFragmentMgr.beginTransaction().add(R.id.layoutFragmentContainer, fragment, strTag).show(fragment).commit();
        else
            mFragmentMgr.beginTransaction().show(fragment).commit();

        mCurFragment = fragment;
        mCurFragmentTag = strTag;
    }
}
