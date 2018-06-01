package com.fff.ingood.flow;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.activity.RegisterInterestPageActivity;
import com.fff.ingood.activity.RegisterLocationPageActivity;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class FlowManager {

    private static Logger m_logger;
    private static FlowManager m_instance = null;
    private FlowLogic.FLOW m_curFlow;

    private FlowManager() {
    }

    private void initialize() {
        m_logger = Logger.getLogger(FlowManager.class.getName());
    }

    public static FlowManager getInstance() {
        if(m_instance == null) {
            m_instance = new FlowManager();
            m_instance.initialize();
        }
        return m_instance;
    }

    public FlowLogic.FLOW getCurFlow() {
        return m_curFlow;
    }

    public Class<?> goLoginFlow() {
        Class<?> clsFlow = null;
        FlowLogic fl = new LoginFlowLogic();

        switch(fl.decideFlow()) {
            case FLOW_LOGIN :
                clsFlow = LoginActivity.class;
                m_curFlow = FlowLogic.FLOW.FLOW_LOGIN;
                break;
            case FLOW_HOME :
                clsFlow = HomeActivity.class;
                m_curFlow = FlowLogic.FLOW.FLOW_HOME;
                break;
            case FLOW_UNKNOWN :
                m_curFlow = FlowLogic.FLOW.FLOW_UNKNOWN;
                break;
        }

        m_logger.log(Level.INFO, "going to" + m_curFlow);
        return clsFlow;
    }

    public Class<?> goRegisterFlow() {
        Class<?> clsFlow = null;
        FlowLogic fl = new RegisterFlowLogic();

        switch(fl.decideFlow()) {
            case FLOW_REGISTER_LOCATION:
                clsFlow = RegisterLocationPageActivity.class;
                m_curFlow = FlowLogic.FLOW.FLOW_REGISTER_LOCATION;
                break;
            case FLOW_REGISTER_INTERESTS:
                clsFlow = RegisterInterestPageActivity.class;
                m_curFlow = FlowLogic.FLOW.FLOW_REGISTER_INTERESTS;
                break;
            case FLOW_HOME :
                clsFlow = HomeActivity.class;
                m_curFlow = FlowLogic.FLOW.FLOW_HOME;
                break;

        }

        m_logger.log(Level.INFO, "going to" + m_curFlow);
        return clsFlow;
    }
}
