package com.fff.ingood.flow;

import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.task2.wrapper.PersonLogoutTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LogoutFlowLogic extends FlowLogic implements PersonLogoutTaskWrapper.PersonLogoutTaskWrapperCallback {

    LogoutFlowLogic(FlowLogicCaller caller) {
        super(caller);
    }

    @Override
    protected FLOW doLogic() {
        PersonLogoutTaskWrapper task = new PersonLogoutTaskWrapper(this);
        task.execute(PersonManager.getInstance().getPerson());

        return FLOW.FL_LOGIN;
    }

    @Override
    public void onLogoutSuccess() {
        PreferenceManager.getInstance().setLoginSuccess(false);
        PreferenceManager.getInstance().setLoginEmail("");
        PreferenceManager.getInstance().setLoginPassword("");
        PersonManager.getInstance().setPerson(null);

        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_LOGIN, LoginActivity.class);
    }

    @Override
    public void onLogoutFailure(Integer iStatusCode) {
        mCaller.returnFlow(iStatusCode, FLOW.FL_LOGIN, LoginActivity.class);
    }
}
