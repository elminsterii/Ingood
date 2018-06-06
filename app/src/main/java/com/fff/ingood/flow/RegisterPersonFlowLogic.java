package com.fff.ingood.flow;

import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.activity.RegisterInterestPageActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonRegisterTask;
import com.fff.ingood.tools.ParserUtils;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class RegisterPersonFlowLogic extends FlowLogic {

    private Person mPerson;

    RegisterPersonFlowLogic(FlowLogic.FlowLogicCaller caller, Person person) {
        super(caller);
        mPerson = person;
    }

    @Override
    protected FLOW doLogic() {
        DoPersonRegisterTask<Person> task = new DoPersonRegisterTask<>(new AsyncResponder<String>() {
            @Override
            public void onSuccess(String strResponse) {
                String strStatusCode = ParserUtils.getStringByTag(ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE, strResponse);

                if (strStatusCode != null && strStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS)) {
                    PreferenceManager.getInstance().setRegisterSuccess(true);
                    mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_LOGIN, LoginActivity.class);
                } else {
                    mCaller.returnFlow(Integer.parseInt(strStatusCode), FLOW.FL_REGISTER_INTEREST, RegisterInterestPageActivity.class);
                }
            }
            @Override
            public void onFailure() {
                mCaller.returnFlow(STATUS_CODE_NWK_FAIL_INT, FLOW.FL_REGISTER_INTEREST, RegisterInterestPageActivity.class);
            }
        });
        task.execute(mPerson);

        return FLOW.FL_REGISTER_INTEREST;
    }
}
