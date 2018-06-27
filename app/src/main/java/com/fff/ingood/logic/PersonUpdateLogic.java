package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.task.wrapper.PersonUpdateTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

public class PersonUpdateLogic extends Logic implements PersonUpdateTaskWrapper.PersonUpdateTaskWrapperCallback {

    public interface PersonUpdateLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void onUpdateSuccess();
    }

    private PersonUpdateLogic.PersonUpdateLogicCaller mCaller;
    private Person mPersonCondition;

    PersonUpdateLogic(PersonUpdateLogic.PersonUpdateLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPersonCondition = person;
    }

    @Override
    protected void doLogic() {
        PersonUpdateTaskWrapper logoutWrapper = new PersonUpdateTaskWrapper(this);
        logoutWrapper.execute(mPersonCondition);
    }

    @Override
    public void onUpdateSuccess() {
        PreferenceManager.getInstance().setLoginSuccess(false);
        PreferenceManager.getInstance().setLoginEmail("");
        PreferenceManager.getInstance().setLoginPassword("");
        PersonManager.getInstance().setPerson(null);

        mCaller.onUpdateSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onUpdateFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
