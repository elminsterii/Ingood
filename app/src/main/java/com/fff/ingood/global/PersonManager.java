package com.fff.ingood.global;

import com.fff.ingood.data.Person;

/**
 * Created by ElminsterII on 2018/6/11.
 */
public class PersonManager {

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
}
