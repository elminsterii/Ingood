package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.wrapper.IgActivityDeleteTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class IgActivityDeleteLogic extends Logic implements IgActivityDeleteTaskWrapper.IgActivityDeleteTaskWrapperCallback {

    public interface IgActivityDeleteLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnDeleteIgActivitySuccess();
    }

    private IgActivityDeleteLogicCaller mCaller;
    private IgActivity mIgActivity;

    IgActivityDeleteLogic(IgActivityDeleteLogicCaller caller, IgActivity igActivity) {
        super(caller);
        mCaller = caller;
        mIgActivity = igActivity;
    }

    @Override
    protected void doLogic() {
        IgActivityDeleteTaskWrapper task = new IgActivityDeleteTaskWrapper(this);
        task.execute(mIgActivity);
    }

    @Override
    public void onDeleteIgActivitiesIdsSuccess() {
        mCaller.returnDeleteIgActivitySuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onDeleteIgActivitiesIdsFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
