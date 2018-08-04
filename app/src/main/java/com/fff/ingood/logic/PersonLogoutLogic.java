package com.fff.ingood.logic;

import com.fff.ingood.global.PersonManager;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.task.wrapper.PersonLogoutTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonLogoutLogic extends Logic implements PersonLogoutTaskWrapper.PersonLogoutTaskWrapperCallback {

    public interface PersonLogoutLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void onLogoutSuccess();
    }

    private PersonLogoutLogicCaller mCaller;

    PersonLogoutLogic(PersonLogoutLogicCaller caller) {
        super(caller);
        mCaller = caller;
    }

    @Override
    protected void doLogic() {
        PersonLogoutTaskWrapper logoutWrapper = new PersonLogoutTaskWrapper(this);
        logoutWrapper.execute(PersonManager.getInstance().getPerson());
    }

    @Override
    public void onLogoutSuccess() {
        PreferenceManager.getInstance().setLoginSuccess(false);
        PreferenceManager.getInstance().setLoginEmail("");
        PreferenceManager.getInstance().setLoginPassword("");
        PersonManager.getInstance().setPerson(null);
        PersonManager.getInstance().setPersonIcon(null);

        mCaller.onLogoutSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onLogoutFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
