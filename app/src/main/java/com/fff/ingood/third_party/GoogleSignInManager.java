package com.fff.ingood.third_party;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

/**
 * Created by ElminsterII on 2018/8/6.
 */
public class GoogleSignInManager {
    private static GoogleSignInManager m_instance = null;

    private Context mContext;
    private GoogleSignInOptions mGso;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;

    private boolean m_bIsLogin;

    private GoogleSignInManager() {

    }

    public static GoogleSignInManager getInstance(Context context) {
        if(m_instance == null) {
            m_instance = new GoogleSignInManager();
            m_instance.initialize(context);
        }
        return m_instance;
    }

    public static GoogleSignInManager getInstance() {
        return m_instance;
    }

    private void initialize(Context context) {
        mContext = context;
        mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(mContext, mGso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(mContext);

        if(account != null) {
            mGoogleSignInAccount = account;
            m_bIsLogin = true;
        } else
            m_bIsLogin = false;
    }

    public boolean isLogin() {
        return m_bIsLogin;
    }

    public GoogleSignInClient geGoogleSignInClient() {
        return mGoogleSignInClient;
    }
}
