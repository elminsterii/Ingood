package com.fff.ingood.logic;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.wrapper.IgActivityCreateTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class IgActivityCreateLogic extends Logic implements IgActivityCreateTaskWrapper.IgActivityCreateTaskWrapperCallback {

    public interface IgActivityCreateLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void onCreateIgActivitySuccess(String strId);
    }

    private IgActivityCreateLogicCaller mCaller;
    private IgActivity mIgActivityNew;

    IgActivityCreateLogic(IgActivityCreateLogicCaller caller, IgActivity igActivityNew) {
        super(caller);
        mCaller = caller;
        mIgActivityNew = igActivityNew;
    }

    @Override
    protected void doLogic() {
        IgActivityCreateTaskWrapper loginWrapper = new IgActivityCreateTaskWrapper(this);
        loginWrapper.execute(mIgActivityNew);
    }

    @Override
    public void onCreateIgActivitySuccess(String strId) {
        mCaller.onCreateIgActivitySuccess(strId);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onCreateIgActivityFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
