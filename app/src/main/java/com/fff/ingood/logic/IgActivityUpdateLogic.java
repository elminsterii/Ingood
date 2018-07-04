package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.wrapper.IgActivityUpdateTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class IgActivityUpdateLogic extends Logic implements IgActivityUpdateTaskWrapper.IgActivityUpdateTaskWrapperCallback {

    public interface IgActivityUpdateLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnUpdateIgActivitySuccess();
    }

    private IgActivityUpdateLogicCaller mCaller;
    private IgActivity mIgActivity;

    IgActivityUpdateLogic(IgActivityUpdateLogicCaller caller, IgActivity igActivity) {
        super(caller);
        mCaller = caller;
        mIgActivity = igActivity;
    }

    @Override
    protected void doLogic() {
        IgActivityUpdateTaskWrapper task = new IgActivityUpdateTaskWrapper(this);
        task.execute(mIgActivity);
    }

    @Override
    public void onUpdateIgActivitiesIdsSuccess() {
        mCaller.returnUpdateIgActivitySuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onUpdateIgActivitiesIdsFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
