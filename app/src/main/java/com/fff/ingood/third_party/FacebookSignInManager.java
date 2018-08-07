package com.fff.ingood.third_party;

import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.fff.ingood.data.Person;

public class FacebookSignInManager {
    private static FacebookSignInManager m_instance = null;
    private Person mFaceBookSignInAccount = null;
    private CallbackManager mCallbackManager;
    private LoginManager mLoginManager;

    private FacebookSignInManager() {
    }

    public static FacebookSignInManager getInstance() {
        if(m_instance == null) {
            m_instance = new FacebookSignInManager();
            m_instance.initialize();
        }
        return m_instance;
    }

    private void initialize() {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();
    }

    public void setFBSignInAccount(Person fbSignInAccount) {
        mFaceBookSignInAccount = fbSignInAccount;
    }

    public Person getFBSignInAccount() {
        return mFaceBookSignInAccount;
    }

    public CallbackManager getCallbackManager() {
        return mCallbackManager;
    }

    public LoginManager getLoginManager() {
        return mLoginManager;
    }
}
