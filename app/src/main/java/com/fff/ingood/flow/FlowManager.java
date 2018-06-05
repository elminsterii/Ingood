package com.fff.ingood.flow;

import com.fff.ingood.data.Person;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class FlowManager {
    private FlowLogic.FLOW mCurFlow;

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

    public void setCurFlow(FlowLogic.FLOW flow) {
        mCurFlow = flow;
    }

    public void goLoginFlow(FlowLogic.FlowLogicCaller caller) {
        FlowLogic fl = new LoginFlowLogic(caller);
        mCurFlow = fl.doLogic();
    }

    public void goLoginFlow(FlowLogic.FlowLogicCaller caller, Person person) {
        FlowLogic fl = new LoginFlowLogic(caller, person);
        mCurFlow = fl.doLogic();
    }

    public void goLogoutFlow(FlowLogic.FlowLogicCaller caller) {
        FlowLogic fl = new LogoutFlowLogic(caller);
        mCurFlow = fl.doLogic();
    }

    public void goRegisterFlow(FlowLogic.FlowLogicCaller caller) {
        FlowLogic fl = new RegisterFlowLogic(caller, mCurFlow);
        mCurFlow = fl.doLogic();
    }

    public void goVerifyEmailFlow(VerifyEmailFlowLogic.VerifyEmailFlowLogicCaller caller, Person person) {
        FlowLogic fl = new VerifyEmailFlowLogic(caller, person);
        mCurFlow = fl.doLogic();
    }

    public void goRegisterPersonFlow(FlowLogic.FlowLogicCaller caller, Person person) {
        FlowLogic fl = new RegisterPersonFlowLogic(caller, person);
        mCurFlow = fl.doLogic();
    }
}
