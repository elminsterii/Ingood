package com.fff.ingood.flow;

import android.content.Context;
import android.content.SharedPreferences;

import com.fff.ingood.data.Person;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PreferenceManager {

    private static PreferenceManager m_instance = null;
    private static Logger m_logger;

    private SharedPreferences m_prefLogin;
    private SharedPreferences m_prefRegister;

    //---------------------------------------- Preference information ----------------------------------------
    //---------------------------------------- Preference Login ----------------------------------------
    public static final String PREF_NAME_LOGIN = "preflogin";
    private static final String PREF_KEY_LOGIN_SUCCESS = PREF_NAME_LOGIN + "success";
    private static final String PREF_KEY_LOGIN_EMAIL = PREF_NAME_LOGIN + "email";
    private static final String PREF_KEY_LOGIN_PASSWORD = PREF_NAME_LOGIN + "password";
    private static final String PREF_KEY_LOGIN_KEEP_LOGIN = PREF_NAME_LOGIN + "keeplogin";

    //---------------------------------------- Preference Register ----------------------------------------
    public static final String PREF_NAME_REGISTER = "prefregister";
    private static final String PREF_KEY_REGISTER_SUCCESS = PREF_NAME_REGISTER + "success";
    private static final String PREF_KEY_REGISTER_EMAIL_VERIFY = PREF_NAME_REGISTER + "emailverify";
    private static final String PREF_KEY_REGISTER_ANONYMOUS = PREF_NAME_REGISTER + "anonymous";
    private static final String PREF_KEY_REGISTER_CUR_FLOW = PREF_NAME_REGISTER + "curflow";



    private PreferenceManager() {

    }

    private void initialize(Context context) {
        if(context == null) {
            m_logger.log(Level.WARNING, "context is null");
            return;
        }

        m_prefLogin = context.getSharedPreferences(PREF_NAME_LOGIN, Context.MODE_PRIVATE);
        m_prefRegister = context.getSharedPreferences(PREF_NAME_LOGIN, Context.MODE_PRIVATE);
        m_logger = Logger.getLogger(PreferenceManager.class.getName());
    }

    public static PreferenceManager getInstance(Context context) {
        if(m_instance == null) {
            m_instance = new PreferenceManager();
            m_instance.initialize(context);
        }
        return m_instance;
    }

    public static PreferenceManager getInstance() {
        if(m_instance == null) {
            m_logger.log(Level.WARNING, "PreferenceManager is null");
        }
        return m_instance;
    }




    //----------------------------------------  Login Functions ----------------------------------------
    public void setLoginData(String strLoginEmail, String strPassword) {
        m_prefLogin.edit().putString(PREF_KEY_LOGIN_EMAIL, strLoginEmail).apply();
        m_prefLogin.edit().putString(PREF_KEY_LOGIN_PASSWORD, strPassword).apply();
    }

    public boolean getLoginSuccess() {
        m_prefLogin.getBoolean(PREF_KEY_LOGIN_SUCCESS, false);
        return true;
    }

    public void setLoginSuccess(boolean bSuccess) {
        m_prefLogin.edit().putBoolean(PREF_KEY_LOGIN_SUCCESS, bSuccess).apply();
    }

    public boolean getKeepLogin() {
        m_prefLogin.getBoolean(PREF_KEY_LOGIN_KEEP_LOGIN, false);
        return true;
    }

    public void setKeepLogin(boolean bKeepLogin) {
        m_prefLogin.edit().putBoolean(PREF_KEY_LOGIN_KEEP_LOGIN, bKeepLogin).apply();
    }

    //----------------------------------------  Register Functions ----------------------------------------
    public boolean getRegisterAnonymous() {
        return m_prefRegister.getBoolean(PREF_KEY_REGISTER_ANONYMOUS, false);
    }

    public void setRegisterAnonymous(boolean bIsAnonymous) {
        m_prefRegister.edit().putBoolean(PREF_KEY_REGISTER_ANONYMOUS, bIsAnonymous).apply();
    }

    public boolean getRegisterEmailVerify() {
        return m_prefRegister.getBoolean(PREF_KEY_REGISTER_EMAIL_VERIFY, false);
    }

    public void setRegisterEmailVerify(boolean bIsEmailVerify) {
        m_prefRegister.edit().putBoolean(PREF_KEY_REGISTER_EMAIL_VERIFY, bIsEmailVerify).apply();
    }

    public boolean getRegisterSuccess() {
        return m_prefRegister.getBoolean(PREF_KEY_REGISTER_SUCCESS, false);
    }

    public void setRegisterSuccess(boolean bSuccess) {
        m_prefRegister.edit().putBoolean(PREF_KEY_REGISTER_SUCCESS, bSuccess).apply();
        m_prefRegister.edit().putBoolean(PREF_KEY_REGISTER_EMAIL_VERIFY, bSuccess).apply();
    }

    public void setRegisterCurFlow(int flow){
        m_prefRegister.edit().putInt(PREF_KEY_REGISTER_CUR_FLOW, flow).apply();

    }
    public int getRegisterCurFlow(){
        return m_prefRegister.getInt(PREF_KEY_REGISTER_CUR_FLOW, -1);

    }

}
