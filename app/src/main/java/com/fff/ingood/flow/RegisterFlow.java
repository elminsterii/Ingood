package com.fff.ingood.flow;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.RegistrationFragmentActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonLoginLogic;
import com.fff.ingood.logic.PersonRegisterLogic;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class RegisterFlow extends Flow implements
        PersonRegisterLogic.PersonRegisterLogicCaller
        , PersonLoginLogic.PersonLoginLogicCaller {

    private Person mPersonNew;

    RegisterFlow(Flow.FlowLogicCaller caller, Person personNew) {
        super(caller);
        mPersonNew = personNew;
    }

    @Override
    protected FLOW doLogic() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonRegister(this, mPersonNew);

        return FLOW.FL_REGISTER;
    }

    @Override
    public void onRegisterSuccess() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonLogin(this, mPersonNew);
    }

    @Override
    public void returnLoginPerson(Person person) {
        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_HOME, HomeActivity.class);
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
            mCaller.returnFlow(iStatusCode, FLOW.FL_REGISTER, RegistrationFragmentActivity.class);
    }
}
