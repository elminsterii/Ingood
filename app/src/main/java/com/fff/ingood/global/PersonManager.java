package com.fff.ingood.global;

import android.graphics.Bitmap;

import com.fff.ingood.data.Person;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonLoginLogic;

/**
 * Created by ElminsterII on 2018/6/11.
 */
public class PersonManager implements PersonLoginLogic.PersonLoginLogicCaller {

    private static PersonManager m_instance = null;
    private Person mPerson = null;

    private PersonManagerRefreshEvent m_personManagerRefreshEvent = null;
    private Bitmap m_bmPersonIcon = null;
    private boolean m_bLoginSuccess = false;

    public interface PersonManagerRefreshEvent {
        void onRefreshDone(Person person);
    }

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

    public Bitmap getPersonIcon() {
        return m_bmPersonIcon;
    }

    public void setPersonIcon(Bitmap bmPersonIcon) {
        this.m_bmPersonIcon = bmPersonIcon;
    }

    public void refresh() {
        refresh(null);
    }

    public void refresh(PersonManagerRefreshEvent cb) {
        m_personManagerRefreshEvent = cb;

        if(mPerson == null)
            return;

        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonLogin(this, mPerson);
    }

    public boolean isLoginSuccess() {
        return m_bLoginSuccess;
    }

    public void setLoginSuccess(boolean m_bLoginSuccess) {
        this.m_bLoginSuccess = m_bLoginSuccess;
    }

    @Override
    public void returnLoginPerson(Person person) {
        mPerson = person;
        if(m_personManagerRefreshEvent != null)
            m_personManagerRefreshEvent.onRefreshDone(mPerson);
    }

    @Override
    public void returnStatus(Integer iStatusCode) {

    }
}
