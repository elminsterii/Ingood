package com.fff.ingood.flow;

import com.fff.ingood.activity.LoginAccountActivity;
import com.fff.ingood.activity.RegistrationFragmentActivity;
import com.fff.ingood.data.Person;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class FlowManager {
    private Flow.FLOW mCurFlow;

    private static FlowManager m_instance = null;

    private FlowManager() {
    }

    private void initialize() {
    }

    public static FlowManager getInstance() {
        if(m_instance == null) {
            m_instance = new FlowManager();
            m_instance.initialize();
        }
        return m_instance;
    }

    public void setCurFlow(Flow.FLOW flow) {
        mCurFlow = flow;
    }

    public void goLoginFlow(Flow.FlowLogicCaller caller) {
        Flow fl = new LoginFlow(caller);
        mCurFlow = fl.doLogic();
    }

    public void goLoginFlow(Flow.FlowLogicCaller caller, Person person) {
        Flow fl = new LoginFlow(caller, person);
        mCurFlow = fl.doLogic();
    }

    public void goLogoutFlow(Flow.FlowLogicCaller caller) {
        Flow fl = new LogoutFlow(caller);
        mCurFlow = fl.doLogic();
    }

    public void goLoginAccountFlow(Flow.FlowLogicCaller caller) {
        caller.returnFlow(STATUS_CODE_SUCCESS_INT, Flow.FLOW.FL_LOGIN, LoginAccountActivity.class);
        mCurFlow = Flow.FLOW.FL_LOGIN;
    }

    public void goRegistrationFlow(Flow.FlowLogicCaller caller) {
        caller.returnFlow(STATUS_CODE_SUCCESS_INT, Flow.FLOW.FL_REGISTRATION, RegistrationFragmentActivity.class);
        mCurFlow = Flow.FLOW.FL_REGISTRATION;
    }

    public void endRegistrationFlow(Flow.FlowLogicCaller caller, Person personNew) {
        Flow fl = new RegistrationFlow(caller, personNew);
        mCurFlow = fl.doLogic();
    }
}
