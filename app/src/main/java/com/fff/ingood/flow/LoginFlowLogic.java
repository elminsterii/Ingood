package com.fff.ingood.flow;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LoginFlowLogic extends FlowLogic {
    @Override
    public FLOW decideFlow() {
        FLOW fRes;

        boolean bIsRegisterSuccess = PreferenceManager.getInstance().getRegisterSuccess();
        boolean bIsKeepLogin = PreferenceManager.getInstance().getKeepLogin();

        if(bIsRegisterSuccess && bIsKeepLogin) {
            fRes = FLOW.FLOW_HOME;
        } else {
            fRes = FLOW.FLOW_LOGIN;
        }

        return fRes;
    }
}
