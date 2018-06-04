package com.fff.ingood.flow;

import com.fff.ingood.activity.LoginActivity;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LogoutFlowLogic extends FlowLogic {

    LogoutFlowLogic(FlowLogicCaller caller) {
        super(caller);
    }

    @Override
    protected FLOW doLogic() {
        PreferenceManager.getInstance().setLoginSuccess(false);
        PreferenceManager.getInstance().setLoginEmail("");
        PreferenceManager.getInstance().setLoginPassword("");

        //TODO - run logout task

        mCaller.returnFlow(true, FLOW.FL_LOGIN, LoginActivity.class);
        return FLOW.FL_LOGIN;
    }
}
