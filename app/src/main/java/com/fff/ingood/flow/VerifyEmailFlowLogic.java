package com.fff.ingood.flow;

import com.fff.ingood.activity.RegisterVerifyPageActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.ServerResponse;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoPersonVerifyTask;
import com.fff.ingood.tools.ParserUtils;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class VerifyEmailFlowLogic extends FlowLogic {

    public interface VerifyEmailFlowLogicCaller extends FlowLogicCaller{
        void returnFlow(Integer iStatusCode, FLOW flow, Class<?> clsFlow);
        void returnVerifyCode(String strCode);
    }

    private VerifyEmailFlowLogicCaller mCaller;
    private Person mPerson;

    VerifyEmailFlowLogic(VerifyEmailFlowLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPerson = person;
    }

    @Override
    protected FLOW doLogic() {
        DoPersonVerifyTask<Person> task = new DoPersonVerifyTask<>(new AsyncResponder<String>() {
            @Override
            public void onSuccess(String strResponse) {
                String strStatusCode = ParserUtils.getStringByTag(ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE, strResponse);

                if (strStatusCode != null && strStatusCode.equals(ServerResponse.STATUS_CODE_SUCCESS)) {
                    Person person = ParserUtils.getPersonAttr(strResponse);
                    if(person != null) {
                        mCaller.returnVerifyCode(person.getVerifyCode());
                    }
                    else
                        mCaller.returnFlow(Integer.parseInt(strStatusCode), FLOW.FL_REGISTER_VERIFY, RegisterVerifyPageActivity.class);
                }
            }

            @Override
            public void onFailure() {
                mCaller.returnFlow(STATUS_CODE_NWK_FAIL_INT, FLOW.FL_REGISTER_VERIFY, RegisterVerifyPageActivity.class);
            }
        });
        task.execute(mPerson);

        return FLOW.FL_REGISTER_VERIFY;
    }
}
