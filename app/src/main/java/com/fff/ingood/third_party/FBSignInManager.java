package com.fff.ingood.third_party;

import android.content.Context;

import com.fff.ingood.data.Person;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

public class FBSignInManager {
    private static FBSignInManager m_instance = null;
    private Person mFBSignInAccount = null;
    private FBSignInManager() {

    }

    public static FBSignInManager getInstance(Context context) {
        if(m_instance == null) {
            m_instance = new FBSignInManager();
            m_instance.initialize(context);
        }
        return m_instance;
    }

    public static FBSignInManager getInstance() {
        return m_instance;
    }

    private void initialize(Context context) {


    }

    public void setFBSignInAccount(Person fbSignInAccount) {
        mFBSignInAccount = fbSignInAccount;
    }

    public Person getGoogleSignInAccount() {
        return mFBSignInAccount;
    }

    public boolean isSignIn() {
        return mFBSignInAccount != null;
    }


}
