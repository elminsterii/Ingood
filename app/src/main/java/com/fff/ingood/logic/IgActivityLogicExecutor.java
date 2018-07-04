package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.wrapper.IgActivityAttendTaskWrapper;
import com.fff.ingood.task.wrapper.IgActivityDeemTaskWrapper;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class IgActivityLogicExecutor {

    public void doCreateIgActivity(IgActivityCreateLogic.IgActivityCreateLogicCaller caller, IgActivity activity) {
        Logic fl = new IgActivityCreateLogic(caller, activity);
        fl.doLogic();
    }

    public void doDeleteIgActivity(IgActivityDeleteLogic.IgActivityDeleteLogicCaller caller, IgActivity activity) {
        Logic fl = new IgActivityDeleteLogic(caller, activity);
        fl.doLogic();
    }

    public void doUpdatecomIgActivity(IgActivityUpdateLogic.IgActivityUpdateLogicCaller caller, IgActivity activity) {
        Logic fl = new IgActivityUpdateLogic(caller, activity);
        fl.doLogic();
    }

    public void doSearchIgActivitiesIds(IgActivityQueryLogic.IgActivityQueryLogicCaller caller, IgActivity activity) {
        Logic fl = new IgActivityQueryLogic(caller, activity);
        fl.doLogic();
    }

    public void doGetIgActivitiesData(IgActivityQueryLogic.IgActivityQueryLogicCaller caller, String strIds) {
        Logic fl = new IgActivityQueryLogic(caller, strIds);
        fl.doLogic();
    }

    public void doDeemIgActivity(IgActivityDeemLogic.IgActivityDeemLogicCaller caller, String strEmail, String strPassword, String strActivityId
            , IgActivityDeemTaskWrapper.DEEM_VALUE dvDeem, boolean bIsDeemRollBack) {
        Logic fl = new IgActivityDeemLogic(caller, strEmail, strPassword, strActivityId, dvDeem, bIsDeemRollBack);
        fl.doLogic();
    }

    public void doAttendIgActivity(IgActivityAttendLogic.IgActivityAttendLogicCaller caller, String strId, String strEmail
            , String strPassword, String strActivityId, IgActivityAttendTaskWrapper.ATTEND_VALUE avAttend) {
        Logic fl = new IgActivityAttendLogic(caller, strId, strEmail, strPassword, strActivityId, avAttend);
        fl.doLogic();
    }
}
