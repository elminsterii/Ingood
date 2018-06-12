package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class ActivityLogicExecutor {

    public void doSearchActivitiesIds(ActivityLogic.ActivityLogicCaller caller, IgActivity activity) {
        Logic fl = new ActivityLogic(caller, activity);
        fl.doLogic();
    }

    public void doGetActivitiesData(ActivityLogic.ActivityLogicCaller caller, String strIds) {
        Logic fl = new ActivityLogic(caller, strIds);
        fl.doLogic();
    }
}
