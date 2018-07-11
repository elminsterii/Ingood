package com.fff.ingood.logic;

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
    private String m_strIgActivityId;
    private String m_strPublisherEmail;
    private String m_strPublisherPassword;

    IgActivityDeleteLogic(IgActivityDeleteLogicCaller caller
            , String strIgActivityId
            , String strPublisherEmail
            , String strPublisherPassword) {
        super(caller);
        mCaller = caller;
        m_strIgActivityId = strIgActivityId;
        m_strPublisherEmail = strPublisherEmail;
        m_strPublisherPassword = strPublisherPassword;
    }

    @Override
    protected void doLogic() {
        IgActivityDeleteTaskWrapper task = new IgActivityDeleteTaskWrapper(this);
        task.execute(m_strIgActivityId, m_strPublisherEmail, m_strPublisherPassword);
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
