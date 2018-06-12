package com.fff.ingood.flow;

import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonLogoutLogic;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LogoutFlow extends Flow implements PersonLogoutLogic.PersonLogoutLogicCaller{

    LogoutFlow(FlowLogicCaller caller) {
        super(caller);
    }

    @Override
    protected FLOW doLogic() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonLogout(this);

        return FLOW.FL_LOGIN;
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        mCaller.returnFlow(iStatusCode, FLOW.FL_LOGIN, LoginActivity.class);
    }

    @Override
    public void onLogoutSuccess() {
        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_LOGIN, LoginActivity.class);
    }
}
