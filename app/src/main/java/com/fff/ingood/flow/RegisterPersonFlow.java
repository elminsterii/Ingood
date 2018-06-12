package com.fff.ingood.flow;

import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.activity.RegisterPrimaryPageActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonRegisterLogic;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class RegisterPersonFlow extends Flow implements PersonRegisterLogic.PersonRegisterLogicCaller {

    private Person mPersonNew;
    private boolean m_bIsRegisterSuccess;

    RegisterPersonFlow(Flow.FlowLogicCaller caller, Person personNew) {
        super(caller);
        mPersonNew = personNew;
    }

    @Override
    protected FLOW doLogic() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonRegister(this, mPersonNew);

        return FLOW.FL_REGISTER_PRIMARY;
    }

    @Override
    public void onRegisterSuccess() {
        m_bIsRegisterSuccess = true;
        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_LOGIN, LoginActivity.class);
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(!m_bIsRegisterSuccess)
            mCaller.returnFlow(iStatusCode, FLOW.FL_REGISTER_PRIMARY, RegisterPrimaryPageActivity.class);
    }
}
