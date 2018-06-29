package com.fff.ingood.flow;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonLoginLogic;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LoginFlow extends Flow implements PersonLoginLogic.PersonLoginLogicCaller {

    private Person mPersonCondition;
    private boolean m_bIsLoginSuccess;

    LoginFlow(FlowLogicCaller caller, Person person) {
        super(caller);
        m_bIsLoginSuccess = false;
        mPersonCondition = person;
    }

    LoginFlow(FlowLogicCaller caller) {
        super(caller);
    }

    @Override
    protected FLOW doLogic() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonLogin(this, mPersonCondition);

        return FLOW.FL_LOGIN;
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(!m_bIsLoginSuccess)
            mCaller.returnFlow(iStatusCode, FLOW.FL_LOGIN, LoginActivity.class);
        
    }

    @Override
    public void returnLoginPerson(Person person) {
        m_bIsLoginSuccess = true;
        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_HOME, HomeActivity.class);
    }
}
