package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.PersonIconDeleteTaskWrapper;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class PersonIconDeleteLogic extends Logic implements PersonIconDeleteTaskWrapper.PersonIconDeleteTaskWrapperCallback {

    public interface PersonIconDeleteLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
    }

    private PersonIconDeleteLogicCaller mCaller;
    private String m_strEmail;
    private String m_strPassword;
    private List<String> m_lsIconsName;

    PersonIconDeleteLogic(PersonIconDeleteLogicCaller caller, String strEmail, String strPassword, List<String> lsIconsName) {
        super(caller);
        mCaller = caller;
        m_strEmail = strEmail;
        m_strPassword = strPassword;
        m_lsIconsName = lsIconsName;
    }

    @Override
    protected void doLogic() {
        PersonIconDeleteTaskWrapper task = new PersonIconDeleteTaskWrapper(this);
        task.execute(m_strEmail, m_strPassword, m_lsIconsName);
    }

    @Override
    public void onDeleteIconSuccess() {
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onDeleteIconFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
