package com.fff.ingood.flow;

import android.content.Context;
import android.content.Intent;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.LoginActivity;

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

    public Intent goLoginFlow(Context context) {
        Intent intent = null;

        if(context == null)
            return null;

        FlowLogic fl = new LoginFlowLogic();

        switch(fl.decideFlow()) {
            case FLOW_LOGIN :
                intent = new Intent(context, LoginActivity.class);
                m_curFlow = FlowLogic.FLOW.FLOW_LOGIN;
                break;
            case FLOW_HOME :
                intent = new Intent(context, HomeActivity.class);
                m_curFlow = FlowLogic.FLOW.FLOW_HOME;
                break;
            case FLOW_UNKNOWN :
                m_curFlow = FlowLogic.FLOW.FLOW_UNKNOWN;
                break;
        }

        m_logger.log(Level.INFO, "going to" + m_curFlow);
        return intent;
    }
}
