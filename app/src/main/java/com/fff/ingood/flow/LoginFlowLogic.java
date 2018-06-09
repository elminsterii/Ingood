package com.fff.ingood.flow;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonLogInTask;
import com.fff.ingood.tools.ParserUtils;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

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

            DoPersonLogInTask task = new DoPersonLogInTask(new AsyncResponder<String>() {
                @Override
                public void onSuccess(String strResponse) {
                    String strStatusCode = ParserUtils.getStringByTag(ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE, strResponse);

                    if (strStatusCode != null && strStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS)) {
                        PreferenceManager.getInstance().setLoginEmail(mPerson.getEmail());
                        PreferenceManager.getInstance().setLoginPassword(mPerson.getPassword());
                        PreferenceManager.getInstance().setLoginSuccess(true);
                        PreferenceManager.getInstance().setKeepLogin(true);

                        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_HOME, HomeActivity.class);
                    }
                    else {
                        mCaller.returnFlow(Integer.parseInt(strStatusCode), FLOW.FL_LOGIN, LoginActivity.class);
                    }
                }

                @Override
                public void onFailure() {
                    mCaller.returnFlow(STATUS_CODE_NWK_FAIL_INT, FLOW.FL_LOGIN, LoginActivity.class);
                }
            });
            task.execute(mPerson);
        } else {
            mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_LOGIN, LoginActivity.class);
            flow = FLOW.FL_LOGIN;
        }

        return flow;
    }
}
