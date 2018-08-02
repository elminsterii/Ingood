package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.wrapper.IgActivityRepublishTaskWrapper;
import com.fff.ingood.task.wrapper.IgActivityUpdateTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class IgActivityRepublishLogic extends Logic implements IgActivityRepublishTaskWrapper.IgActivityRepublishTaskWrapperCallback {

    public interface IgActivityRepublishLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnRepublishIgActivitySuccess();
    }

    private IgActivityRepublishLogicCaller mCaller;
    private IgActivity mIgActivity;

    IgActivityRepublishLogic(IgActivityRepublishLogicCaller caller, IgActivity igActivity) {
        super(caller);
        mCaller = caller;
        mIgActivity = igActivity;
    }

    @Override
    protected void doLogic() {
        IgActivityRepublishTaskWrapper task = new IgActivityRepublishTaskWrapper(this);
        task.execute(mIgActivity);
    }

    @Override
    public void onRepublishIgActivitySuccess() {
        mCaller.returnRepublishIgActivitySuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onRepublishIgActivityFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
