package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.wrapper.PersonTempPasswordTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonTempPasswordLogic extends Logic implements PersonTempPasswordTaskWrapper.PersonTempPasswordTaskWrapperCallback {


    public interface PersonTempPasswordLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void onPersonSetTempPassword();
    }

    private PersonTempPasswordLogic.PersonTempPasswordLogicCaller mCaller;
    private Person mPerson;

    PersonTempPasswordLogic(PersonTempPasswordLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPerson = person;
    }

    @Override
    protected void doLogic() {
        PersonTempPasswordTaskWrapper tempPasswordWrapper = new PersonTempPasswordTaskWrapper(this);
        tempPasswordWrapper.execute(mPerson);
    }

    @Override
    public void onPersonSetTempPassword() {
        mCaller.onPersonSetTempPassword();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onPersonSetTempPasswordFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }

}
