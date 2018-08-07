package com.fff.ingood.third_party;

import android.content.Context;

import com.facebook.CallbackManager;
import com.fff.ingood.data.Person;

public class FaceBookSignInManager {
    private static FaceBookSignInManager m_instance = null;
    private Person mFaceBookSignInAccount = null;
    private CallbackManager mCallbackManager;

    private FaceBookSignInManager() {

    }

    public static FaceBookSignInManager getInstance(Context context) {
        if(m_instance == null) {
            m_instance = new FaceBookSignInManager();
            m_instance.initialize(context);
        }
        return m_instance;
    }

    public static FaceBookSignInManager getInstance() {
        return m_instance;
    }

    private void initialize(Context context) {
        mCallbackManager = CallbackManager.Factory.create();


    }

    public void setFBSignInAccount(Person fbSignInAccount) {
        mFaceBookSignInAccount = fbSignInAccount;
    }

    public Person getFBSignInAccount() {
        return mFaceBookSignInAccount;
    }



    public CallbackManager getFBCallBackMgr() {
        return mCallbackManager;
    }

}
