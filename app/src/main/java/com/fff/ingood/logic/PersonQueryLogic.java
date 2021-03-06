package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.wrapper.PersonQueryTaskWrapper;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonQueryLogic extends Logic implements PersonQueryTaskWrapper.PersonQueryTaskWrapperCallback {

    public interface PersonQueryLogicCaller extends LogicCaller {
        void returnPersons(List<Person> lsPersons, String strTag);
        void returnStatus(Integer iStatusCode);
    }

    private PersonQueryLogicCaller mCaller;
    private String m_strPersonPrimaryKey;
    private boolean m_bByEmail;
    private String m_strTag;

    PersonQueryLogic(PersonQueryLogicCaller caller, String strPersonPrimaryKey, boolean bByEmail, String strTag) {
        super(caller);
        mCaller = caller;
        m_strPersonPrimaryKey = strPersonPrimaryKey;
        m_bByEmail = bByEmail;
        m_strTag = strTag;
    }

    @Override
    protected void doLogic() {
        PersonQueryTaskWrapper checkExistWrapper = new PersonQueryTaskWrapper(this);
        if(m_bByEmail)
            checkExistWrapper.executeByEmails(m_strPersonPrimaryKey);
        else
            checkExistWrapper.executeByIds(m_strPersonPrimaryKey);
    }

    @Override
    public void onSuccess(List<Person> lsPersons) {
        mCaller.returnPersons(lsPersons, m_strTag);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
