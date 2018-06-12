package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.task2.wrapper.PersonVerifyTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonVerifyLogic extends Logic implements PersonVerifyTaskWrapper.PersonVerifyTaskWrapperCallback {

    public interface PersonVerifyLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnVerifyCode(String strCode);
    }

    private PersonVerifyLogicCaller mCaller;
    private Person mPersonCondition;

    PersonVerifyLogic(PersonVerifyLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPersonCondition = person;
    }

    @Override
    protected void doLogic() {
        PersonVerifyTaskWrapper task = new PersonVerifyTaskWrapper(this);
        task.execute(mPersonCondition);
    }

    @Override
    public void onVerifyCodeIncoming(String strVerifyCode) {
        mCaller.returnVerifyCode(strVerifyCode);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
