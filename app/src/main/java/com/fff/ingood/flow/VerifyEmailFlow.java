package com.fff.ingood.flow;

import com.fff.ingood.activity.RegisterVerifyPageActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.task2.wrapper.PersonVerifyTaskWrapper;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class VerifyEmailFlow extends Flow implements PersonVerifyTaskWrapper.PersonVerifyTaskWrapperCallback {

    public interface VerifyEmailFlowLogicCaller extends FlowLogicCaller{
        void returnFlow(Integer iStatusCode, FLOW flow, Class<?> clsFlow);
        void returnVerifyCode(String strCode);
    }

    private VerifyEmailFlowLogicCaller mCaller;
    private Person mPerson;

    VerifyEmailFlow(VerifyEmailFlowLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPerson = person;
    }

    @Override
    protected FLOW doLogic() {
        PersonVerifyTaskWrapper task = new PersonVerifyTaskWrapper(this);
        task.execute(mPerson);

        return FLOW.FL_REGISTER_VERIFY;
    }

    @Override
    public void onVerifyCodeIncoming(String strVerifyCode) {
        mCaller.returnVerifyCode(strVerifyCode);
    }

    @Override
    public void onFailure(Integer iStatusCode) {
        mCaller.returnFlow(iStatusCode, FLOW.FL_REGISTER_VERIFY, RegisterVerifyPageActivity.class);
    }
}
