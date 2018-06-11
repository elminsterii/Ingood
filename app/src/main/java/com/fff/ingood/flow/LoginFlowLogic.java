package com.fff.ingood.flow;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.task2.wrapper.PersonLoginTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LoginFlowLogic extends FlowLogic implements PersonLoginTaskWrapper.PersonLoginTaskWrapperCallback {

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

            PersonLoginTaskWrapper loginWrapper = new PersonLoginTaskWrapper(this);
            loginWrapper.execute(mPerson);
        } else {
            mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_LOGIN, LoginActivity.class);
            flow = FLOW.FL_LOGIN;
        }
        return flow;
    }

    @Override
    public void onLoginSuccess(Person person) {
        PreferenceManager.getInstance().setLoginEmail(person.getEmail());
        PreferenceManager.getInstance().setLoginPassword(person.getPassword());
        PreferenceManager.getInstance().setLoginSuccess(true);
        PreferenceManager.getInstance().setKeepLogin(true);

        PersonManager.getInstance().setPerson(person);

        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_HOME, HomeActivity.class);
    }

    @Override
    public void onLoginFailure(Integer iStatusCode) {
        mCaller.returnFlow(iStatusCode, FLOW.FL_LOGIN, LoginActivity.class);
    }
}
