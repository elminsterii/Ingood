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

    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount mGoogleSignInAccount;

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
        mGoogleSignInAccount = null;

        GoogleSignInOptions mGso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, mGso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);

        if(account != null)
            mGoogleSignInAccount = account;
    }

    public void setGoogleSignInAccount(GoogleSignInAccount googleSignInAccount) {
        mGoogleSignInAccount = googleSignInAccount;
    }

    public GoogleSignInAccount getGoogleSignInAccount() {
        return mGoogleSignInAccount;
    }

    public boolean isSignIn() {
        return mGoogleSignInAccount != null;
    }

    public GoogleSignInClient geGoogleSignInClient() {
        return mGoogleSignInClient;
    }
}
