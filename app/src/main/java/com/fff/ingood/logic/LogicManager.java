package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LogicManager {

    private static LogicManager m_instance = null;

    private LogicManager() {
    }

    private void initialize() {
    }

    public static LogicManager getInstance() {
        if(m_instance == null) {
            m_instance = new LogicManager();
            m_instance.initialize();
        }
        return m_instance;
    }

    public void doSearchActivitiesIds(ActivityLogic.ActivityLogicCaller caller, IgActivity activity) {
        Logic fl = new ActivityLogic(caller, activity);
        fl.doLogic();
    }

    public void doGetActivitiesData(ActivityLogic.ActivityLogicCaller caller, String strIds) {
        Logic fl = new ActivityLogic(caller, strIds);
        fl.doLogic();
    }
}
