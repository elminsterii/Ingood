package com.fff.ingood.global;

import android.graphics.Bitmap;

import com.fff.ingood.data.Person;
import com.fff.ingood.logic.PersonIconComboLogic_PersonMainIconDownload;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonLoginLogic;

/**
 * Created by ElminsterII on 2018/6/11.
 */
public class PersonManager implements PersonLoginLogic.PersonLoginLogicCaller
        , PersonIconComboLogic_PersonMainIconDownload.PersonMainIconDownloadLogicCaller {

    private static PersonManager m_instance = null;
    private Person mPerson;

    private PersonManagerRefreshEvent m_personManagerRefreshEvent = null;
    private Bitmap m_bmPersonIcon;

    public interface PersonManagerRefreshEvent {
        void onRefreshDone(Person person);
        void onRefreshIconDone(Bitmap bmPersonIcon);
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

    public void refreshIcon() {
        downloadPersonPhoto();
    }

    private void downloadPersonPhoto() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonMainIconDownload(this, getPerson().getEmail());
    }

    @Override
    public void returnLoginPerson(Person person) {
        mPerson = person;
        if(m_personManagerRefreshEvent != null)
            m_personManagerRefreshEvent.onRefreshDone(mPerson);
    }

    @Override
    public void returnPersonMainIcon(Bitmap bmPersonIcon) {
        if(bmPersonIcon != null)
            m_bmPersonIcon = bmPersonIcon;

        if(m_personManagerRefreshEvent != null)
            m_personManagerRefreshEvent.onRefreshIconDone(m_bmPersonIcon);
    }

    @Override
    public void returnStatus(Integer iStatusCode) {

    }
}
