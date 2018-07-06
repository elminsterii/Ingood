package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.wrapper.PersonSaveIgActivityTaskWrapper;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonLogicExecutor {

    public void doPersonLogin(PersonLoginLogic.PersonLoginLogicCaller caller, Person person) {
        Logic fl = new PersonLoginLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonLogout(PersonLogoutLogic.PersonLogoutLogicCaller caller) {
        Logic fl = new PersonLogoutLogic(caller);
        fl.doLogic();
    }

    public void doPersonRegister(PersonRegisterLogic.PersonRegisterLogicCaller caller, Person personNew) {
        Logic fl = new PersonRegisterLogic(caller, personNew);
        fl.doLogic();
    }

    public void doPersonUnregister(PersonUnregisterLogic.PersonUnregisterLogicCaller caller, Person person) {
        Logic fl = new PersonUnregisterLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonUpdate(PersonUpdateLogic.PersonUpdateLogicCaller caller, Person person) {
        Logic fl = new PersonUpdateLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonVerify(PersonVerifyLogic.PersonVerifyLogicCaller caller, Person person) {
        Logic fl = new PersonVerifyLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonCheckExist(PersonCheckExistLogic.PersonCheckExistLogicCaller caller, Person person) {
        Logic fl = new PersonCheckExistLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonQuery(PersonQueryLogic.PersonQueryLogicCaller caller, String strPersonPrimaryKey, boolean bByEmail) {
        Logic fl = new PersonQueryLogic(caller, strPersonPrimaryKey, bByEmail);
        fl.doLogic();
    }

    public void doSaveIgActivity(PersonSaveIgActivityLogic.PersonSaveIgActivityLogicCaller caller, String strEmail, String strActivityId
            , PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE svValue) {
        Logic fl = new PersonSaveIgActivityLogic(caller, strEmail, strActivityId, svValue);
        fl.doLogic();
    }
}
