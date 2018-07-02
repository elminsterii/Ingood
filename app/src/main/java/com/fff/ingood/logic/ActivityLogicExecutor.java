package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.wrapper.ActivityDeemTaskWrapper;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class ActivityLogicExecutor {

    public void doSearchActivitiesIds(ActivityQueryLogic.ActivityQueryLogicCaller caller, IgActivity activity) {
        Logic fl = new ActivityQueryLogic(caller, activity);
        fl.doLogic();
    }

    public void doGetActivitiesData(ActivityQueryLogic.ActivityQueryLogicCaller caller, String strIds) {
        Logic fl = new ActivityQueryLogic(caller, strIds);
        fl.doLogic();
    }

    public void doDeemActivity(ActivityDeemLogic.ActivityDeemLogicCaller caller, String strEmail, String strPassword, String strActivityId
            , ActivityDeemTaskWrapper.DEEM_VALUE dvDeem, boolean bIsDeemRollBack) {
        Logic fl = new ActivityDeemLogic(caller, strEmail, strPassword, strActivityId, dvDeem, bIsDeemRollBack);
        fl.doLogic();
    }
}
