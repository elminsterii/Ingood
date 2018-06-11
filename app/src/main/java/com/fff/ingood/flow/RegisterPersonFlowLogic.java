package com.fff.ingood.flow;

import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.activity.RegisterPrimaryPageActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.task2.wrapper.PersonRegisterTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class RegisterPersonFlowLogic extends FlowLogic implements PersonRegisterTaskWrapper.PersonRegisterTaskWrapperCallback{

    private Person mPerson;

    RegisterPersonFlowLogic(FlowLogic.FlowLogicCaller caller, Person person) {
        super(caller);
        mPerson = person;
    }

    @Override
    protected FLOW doLogic() {
        PersonRegisterTaskWrapper task = new PersonRegisterTaskWrapper(this);
        task.execute(mPerson);

        return FLOW.FL_REGISTER_INTEREST;
    }

    @Override
    public void onRegisterSuccess() {
        PreferenceManager.getInstance().setRegisterSuccess(true);
        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_LOGIN, LoginActivity.class);
    }

    @Override
    public void onRegisterFailure(Integer iStatusCode) {
        mCaller.returnFlow(iStatusCode, FLOW.FL_REGISTER_PRIMARY, RegisterPrimaryPageActivity.class);
    }
}
