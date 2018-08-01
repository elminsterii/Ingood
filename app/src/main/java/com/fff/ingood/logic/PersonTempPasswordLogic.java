package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.PersonTempPasswordTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonTempPasswordLogic extends Logic implements PersonTempPasswordTaskWrapper.PersonTempPasswordTaskWrapperCallback {


    public interface PersonTempPasswordLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void onPersonTempPasswordSentSuccess();
    }

    private PersonTempPasswordLogic.PersonTempPasswordLogicCaller mCaller;
    private String m_strEmail;

    public PersonTempPasswordLogic(PersonTempPasswordLogicCaller caller, String strEmail) {
        super(caller);
        mCaller = caller;
        m_strEmail = strEmail;
    }

    @Override
    protected void doLogic() {
        PersonTempPasswordTaskWrapper tempPasswordWrapper = new PersonTempPasswordTaskWrapper(this);
        tempPasswordWrapper.execute(m_strEmail);
    }

    @Override
    public void onPersonTempPasswordSentSuccess() {
        mCaller.onPersonTempPasswordSentSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onPersonTempPasswordSentFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}