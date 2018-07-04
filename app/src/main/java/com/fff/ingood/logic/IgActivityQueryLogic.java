package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.wrapper.IgActivityQueryIdByTaskWrapper;
import com.fff.ingood.task.wrapper.IgActivityQueryTaskWrapper;
import com.fff.ingood.tools.StringTool;

import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class IgActivityQueryLogic extends Logic implements
        IgActivityQueryIdByTaskWrapper.IgActivityQueryIdByTaskWrapperCallback
        , IgActivityQueryTaskWrapper.IgActivityQueryTaskWrapperCallback {

    public interface IgActivityQueryLogicCaller extends Logic.LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnIgActivities(List<IgActivity> lsActivities);
        //void returnActivitiesImages(List<Image> lsActivitiesIds);
        void returnIgActivitiesIds(String strActivitiesIds);
    }

    private IgActivityQueryLogicCaller mCaller;
    private IgActivity mActivityCondition;
    private String m_strIds;

    IgActivityQueryLogic(IgActivityQueryLogicCaller caller, IgActivity activity) {
        super(caller);
        mCaller = caller;
        mActivityCondition = activity;
    }

    IgActivityQueryLogic(IgActivityQueryLogicCaller caller, String strIds) {
        super(caller);
        mCaller = caller;
        m_strIds = strIds;
    }

    @Override
    protected void doLogic() {
        if(StringTool.checkStringNotNull(m_strIds)) {
            //query activities data by ids.
            IgActivityQueryTaskWrapper task = new IgActivityQueryTaskWrapper(this);
            task.execute(m_strIds);
        } else if(mActivityCondition != null) {
            //take activities ids by conditions.
            IgActivityQueryIdByTaskWrapper task = new IgActivityQueryIdByTaskWrapper(this);
            task.execute(mActivityCondition);
        } else {
            //clear activity list in Homepage.
            mCaller.returnIgActivities(new ArrayList<IgActivity>());
        }
    }

    @Override
    public void onQueryIgActivitiesIdsSuccess(String strIds) {
        mCaller.returnIgActivitiesIds(strIds);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onQueryIgActivitiesIdsFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }

    @Override
    public void onQueryIgActivitiesSuccess(List<IgActivity> lsActivities) {
        mCaller.returnIgActivities(lsActivities);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onQueryIgActivitiesFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
