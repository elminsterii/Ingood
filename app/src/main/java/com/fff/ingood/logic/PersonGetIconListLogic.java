package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.wrapper.PersonGetIconListTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class PersonGetIconListLogic extends Logic implements PersonGetIconListTaskWrapper.PersonGetIconListTaskWrapperCallback {

    public interface PersonGetIconListLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
    }

    private PersonGetIconListLogicCaller mCaller;
    private Person mPerson;

    PersonGetIconListLogic(PersonGetIconListLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPerson = person;
    }

    @Override
    protected void doLogic() {
        PersonGetIconListTaskWrapper task = new PersonGetIconListTaskWrapper(this);
        task.execute(mPerson);
    }

    @Override
    public void onGetIconListSuccess() {
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onGetIconListFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
