package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.PersonSaveIgActivityTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class PersonSaveIgActivityLogic extends Logic implements PersonSaveIgActivityTaskWrapper.PersonSaveIgActivityTaskWrapperCallback {

    public interface PersonSaveIgActivityLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void saveIgActivitySuccess();
    }

    private PersonSaveIgActivityLogicCaller mCaller;
    private String m_strEmail;
    private String m_strActivityId;
    private PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE m_svValue;

    PersonSaveIgActivityLogic(PersonSaveIgActivityLogicCaller caller, String strEmail, String strActivityId
            , PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE svValue) {
        super(caller);
        mCaller = caller;
        m_strEmail = strEmail;
        m_strActivityId = strActivityId;
        m_svValue = svValue;
    }

    @Override
    protected void doLogic() {
        PersonSaveIgActivityTaskWrapper task = new PersonSaveIgActivityTaskWrapper(this);
        task.execute(m_strEmail, m_strActivityId, m_svValue);
    }

    @Override
    public void onSaveIgActivitySuccess() {
        mCaller.saveIgActivitySuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onSaveIgActivityFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
