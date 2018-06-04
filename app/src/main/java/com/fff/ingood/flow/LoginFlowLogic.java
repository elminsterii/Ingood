package com.fff.ingood.flow;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonLogInTask;
import com.fff.ingood.tools.ParserUtils;

import static com.fff.ingood.activity.RegisterPrimaryPageActivity.API_RESPONSE_TAG;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LoginFlowLogic extends FlowLogic {

    private Person mPerson;

    LoginFlowLogic(FlowLogicCaller caller, Person person) {
        super(caller);
        mPerson = person;
    }

    LoginFlowLogic(FlowLogicCaller caller) {
        super(caller);
    }

    @Override
    protected FLOW doLogic() {
        FLOW flow = FLOW.FL_UNKNOWN;

        boolean bIsLoginSuccess = PreferenceManager.getInstance().getLoginSuccess();
        boolean bIsKeepLogin = PreferenceManager.getInstance().getKeepLogin();

        if((bIsLoginSuccess && bIsKeepLogin)
                || (mPerson != null)) {

            if(mPerson == null) {
                mPerson = new Person();
                mPerson.setEmail(PreferenceManager.getInstance().getLoginEmail());
                mPerson.setPassword(PreferenceManager.getInstance().getLoginPassword());
            }

            DoPersonLogInTask<Person> task = new DoPersonLogInTask<>(new AsyncResponder<String>() {
                @Override
                public void onSuccess(String strResponse) {
                    if (ParserUtils.getStringByTag(API_RESPONSE_TAG, strResponse).equals("0")) {
                        PreferenceManager.getInstance().setLoginEmail(mPerson.getEmail());
                        PreferenceManager.getInstance().setLoginPassword(mPerson.getPassword());
                        PreferenceManager.getInstance().setLoginSuccess(true);
                        PreferenceManager.getInstance().setKeepLogin(true);

                        mCaller.returnFlow(true, FLOW.FL_HOME, HomeActivity.class);
                    }
                    else {
                        mCaller.returnFlow(false, FLOW.FL_LOGIN, LoginActivity.class);
                    }
                }

                @Override
                public void onFailure() {
                    mCaller.returnFlow(false, FLOW.FL_LOGIN, LoginActivity.class);
                }
            });
            task.execute(mPerson);
        } else {
            mCaller.returnFlow(false, FLOW.FL_LOGIN, LoginActivity.class);
            flow = FLOW.FL_LOGIN;
        }

        return flow;
    }
}
