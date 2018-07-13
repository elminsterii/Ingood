package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.wrapper.PersonUpdateTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class PersonUpdateLogic extends Logic implements PersonUpdateTaskWrapper.PersonUpdateTaskWrapperCallback {

    public interface PersonUpdateLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnUpdatePersonSuccess();
    }

    private PersonUpdateLogicCaller mCaller;
    private Person mPerson;

    PersonUpdateLogic(PersonUpdateLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPerson = person;
    }

    @Override
    protected void doLogic() {
        PersonUpdateTaskWrapper task = new PersonUpdateTaskWrapper(this);
        task.execute(mPerson);
    }

    @Override
    public void onUpdateSuccess() {
        mCaller.returnUpdatePersonSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onUpdateFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
