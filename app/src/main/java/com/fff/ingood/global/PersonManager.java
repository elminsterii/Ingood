package com.fff.ingood.global;

import com.fff.ingood.data.Person;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonLoginLogic;

/**
 * Created by ElminsterII on 2018/6/11.
 */
public class PersonManager implements PersonLoginLogic.PersonLoginLogicCaller{

    private static PersonManager m_instance = null;
    private Person mPerson;

    private PersonManager() {

    }

    public static PersonManager getInstance() {
        if(m_instance == null)
            m_instance = new PersonManager();
        return m_instance;
    }

    public Person getPerson() {
        return mPerson;
    }

    public void setPerson(Person person) {
        this.mPerson = person;
    }

    public void refresh() {
        if(mPerson == null)
            return;

        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonLogin(this, mPerson);
    }

    @Override
    public void returnLoginPerson(Person person) {
        mPerson = person;
    }

    @Override
    public void returnStatus(Integer iStatusCode) {

    }
}
