package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.DoActivityQueryIdByTask;
import com.fff.ingood.task.DoActivityQueryTask;
import com.fff.ingood.tools.StringTool;

import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class ActivityLogic extends Logic {

    public interface ActivityLogicCaller extends Logic.LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnActivities(List<IgActivity> lsActivities);
        //void returnActivitiesImages(List<Image> lsActivitiesIds);
        void returnActivitiesIds(String strActivitiesIds);
    }

    private ActivityLogicCaller mCaller;
    private IgActivity mActivityCondition;
    private String m_strIds;

    ActivityLogic(ActivityLogicCaller caller, IgActivity activity) {
        super(caller);
        mCaller = caller;
        mActivityCondition = activity;
    }

    ActivityLogic(ActivityLogicCaller caller, String strIds) {
        super(caller);
        mCaller = caller;
        m_strIds = strIds;
    }

    @Override
    protected void doLogic() {
        if(StringTool.checkStringNotNull(m_strIds)) {
            //query activities data by ids.
            DoActivityQueryTask task = new DoActivityQueryTask(new AsyncResponder<List<IgActivity>>() {
                @Override
                public void onSuccess(List<IgActivity> lsActivities) {
                    mCaller.returnActivities(lsActivities);
                }

                @Override
                public void onFailure() {
                    mCaller.returnStatus(STATUS_CODE_NWK_FAIL_INT);
                }
            });
            task.execute(m_strIds);
        } else if(mActivityCondition != null) {
            //take activities ids by conditions.
            DoActivityQueryIdByTask task = new DoActivityQueryIdByTask(new AsyncResponder<String>() {
                @Override
                public void onSuccess(String strActivitiesIds) {
                    mCaller.returnActivitiesIds(strActivitiesIds);
                }

                @Override
                public void onFailure() {
                    mCaller.returnStatus(STATUS_CODE_NWK_FAIL_INT);
                }
            });
            task.execute(mActivityCondition);
        } else {
            //clear activity list in Homepage.
            mCaller.returnActivities(new ArrayList<IgActivity>());
        }
    }
}
