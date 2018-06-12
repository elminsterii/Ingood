package com.fff.ingood.logic;

import com.fff.ingood.data.Person;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonTaskExecutor {

    public void doPersonLogin(PersonLoginLogic.PersonLoginLogicCaller caller, Person person) {
        Logic fl = new PersonLoginLogic(caller, person);
        fl.doLogic();
    }
}
