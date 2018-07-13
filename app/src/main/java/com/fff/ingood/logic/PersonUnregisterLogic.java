package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.wrapper.PersonUnregisterTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class PersonUnregisterLogic extends Logic implements PersonUnregisterTaskWrapper.PersonUnregisterTaskWrapperCallback {

    public interface PersonUnregisterLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnUnregisterPersonSuccess();
    }

    private PersonUnregisterLogicCaller mCaller;
    private Person mPerson;

    PersonUnregisterLogic(PersonUnregisterLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPerson = person;
    }

    @Override
    protected void doLogic() {
        PersonUnregisterTaskWrapper task = new PersonUnregisterTaskWrapper(this);
        task.execute(mPerson);
    }

    @Override
    public void onUnregisterSuccess() {
        mCaller.returnUnregisterPersonSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onUnregisterFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
