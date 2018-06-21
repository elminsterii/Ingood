package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.wrapper.PersonCheckExistTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonCheckExistLogic extends Logic implements PersonCheckExistTaskWrapper.PersonCheckExistTaskWrapperCallback {

    public interface PersonCheckExistLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void onPersonNotExist();
    }

    private PersonCheckExistLogicCaller mCaller;
    private Person mPerson;

    PersonCheckExistLogic(PersonCheckExistLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPerson = person;
    }

    @Override
    protected void doLogic() {
        PersonCheckExistTaskWrapper checkExistWrapper = new PersonCheckExistTaskWrapper(this);
        checkExistWrapper.execute(mPerson);
    }

    @Override
    public void onPersonNotExist() {
        mCaller.onPersonNotExist();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onCheckFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
