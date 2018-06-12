package com.fff.ingood.logic;

import com.fff.ingood.data.Person;
import com.fff.ingood.global.PreferenceManager;
import com.fff.ingood.task2.wrapper.PersonRegisterTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonRegisterLogic extends Logic implements PersonRegisterTaskWrapper.PersonRegisterTaskWrapperCallback {

    public interface PersonRegisterLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void onRegisterSuccess();
    }

    private PersonRegisterLogicCaller mCaller;
    private Person mPersonNew;

    PersonRegisterLogic(PersonRegisterLogicCaller caller, Person person) {
        super(caller);
        mCaller = caller;
        mPersonNew = person;
    }

    @Override
    protected void doLogic() {
        PersonRegisterTaskWrapper loginWrapper = new PersonRegisterTaskWrapper(this);
        loginWrapper.execute(mPersonNew);
    }

    @Override
    public void onRegisterSuccess() {
        PreferenceManager.getInstance().setRegisterSuccess(true);

        mCaller.onRegisterSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onRegisterFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
