package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.task2.wrapper.PersonLoginTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonLoginLogic extends Logic implements PersonLoginTaskWrapper.PersonLoginTaskWrapperCallback {

    public interface PersonLoginLogicCaller extends Logic.LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnLoginPerson(Person person);
    }

    private PersonLoginLogicCaller mCaller;
    private Person mPersonCondition;

    PersonLoginLogic(PersonLoginLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPersonCondition = person;
    }

    @Override
    protected void doLogic() {

        boolean bIsLoginSuccess = PreferenceManager.getInstance().getLoginSuccess();
        boolean bIsKeepLogin = PreferenceManager.getInstance().getKeepLogin();

        if((bIsLoginSuccess && bIsKeepLogin)
                || (mPersonCondition != null)) {

            if(mPersonCondition == null) {
                mPersonCondition = new Person();
                mPersonCondition.setEmail(PreferenceManager.getInstance().getLoginEmail());
                mPersonCondition.setPassword(PreferenceManager.getInstance().getLoginPassword());
            }

            PersonLoginTaskWrapper loginWrapper = new PersonLoginTaskWrapper(this);
            loginWrapper.execute(mPersonCondition);
        } else {
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        }
    }

    @Override
    public void onLoginSuccess(Person person) {
        PreferenceManager.getInstance().setLoginEmail(person.getEmail());
        PreferenceManager.getInstance().setLoginPassword(person.getPassword());
        PreferenceManager.getInstance().setLoginSuccess(true);
        PreferenceManager.getInstance().setKeepLogin(true);

        PersonManager.getInstance().setPerson(person);

        mCaller.returnLoginPerson(person);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onLoginFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
