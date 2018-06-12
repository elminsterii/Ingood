package com.fff.ingood.flow;

import com.fff.ingood.data.Person;

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

    public void goRegisterFlow(Flow.FlowLogicCaller caller) {
        Flow fl = new RegisterFlow(caller, mCurFlow);
        mCurFlow = fl.doLogic();
    }

    public void goRegisterPersonFlow(Flow.FlowLogicCaller caller, Person person) {
        Flow fl = new RegisterPersonFlow(caller, person);
        mCurFlow = fl.doLogic();
    }
}
