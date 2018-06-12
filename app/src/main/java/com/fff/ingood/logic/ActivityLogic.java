package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.wrapper.ActivityQueryIdByTaskWrapper;
import com.fff.ingood.task.wrapper.ActivityQueryTaskWrapper;
import com.fff.ingood.tools.StringTool;

import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class ActivityLogic extends Logic implements
        ActivityQueryIdByTaskWrapper.ActivityQueryIdByTaskWrapperCallback
        , ActivityQueryTaskWrapper.ActivityQueryTaskWrapperCallback {

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
            ActivityQueryTaskWrapper task = new ActivityQueryTaskWrapper(this);
            task.execute(m_strIds);
        } else if(mActivityCondition != null) {
            //take activities ids by conditions.
            ActivityQueryIdByTaskWrapper task = new ActivityQueryIdByTaskWrapper(this);
            task.execute(mActivityCondition);
        } else {
            //clear activity list in Homepage.
            mCaller.returnActivities(new ArrayList<IgActivity>());
        }
    }

    @Override
    public void onQueryActivitiesIdsSuccess(String strIds) {
        mCaller.returnActivitiesIds(strIds);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onQueryActivitiesIdsFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }

    @Override
    public void onQueryActivitiesSuccess(List<IgActivity> lsActivities) {
        mCaller.returnActivities(lsActivities);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onQueryActivitiesFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
