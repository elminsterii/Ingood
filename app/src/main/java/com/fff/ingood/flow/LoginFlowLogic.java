package com.fff.ingood.flow;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LoginFlowLogic extends FlowLogic {

    LoginFlowLogic(FLOW curflow) {
        super(curflow);
    }

    @Override
    public FLOW nextFlow() {
        FLOW fRes;

        boolean bIsLoginSuccess = PreferenceManager.getInstance().getLoginSuccess();
        boolean bIsKeepLogin = PreferenceManager.getInstance().getKeepLogin();

        if(bIsLoginSuccess && bIsKeepLogin) {
            fRes = FLOW.FLOW_HOME;
        } else {
            fRes = FLOW.FLOW_LOGIN;
        }

        return fRes;
    }
}
