package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.ActivityAttendTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class ActivityAttendLogic extends Logic implements ActivityAttendTaskWrapper.ActivityAttendTaskWrapperCallback {

    public interface ActivityAttendLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnAttendSuccess();
    }

    private ActivityAttendLogicCaller mCaller;
    private String m_strId;
    private String m_strEmail;
    private String m_strPassword;
    private String m_strActivityId;
    private ActivityAttendTaskWrapper.ATTEND_VALUE m_avAttend;

    ActivityAttendLogic(ActivityAttendLogicCaller caller, String strId, String strEmail, String strPassword, String strActivityId
            , ActivityAttendTaskWrapper.ATTEND_VALUE avAttend) {
        super(caller);
        mCaller = caller;
        m_strId = strId;
        m_strEmail = strEmail;
        m_strPassword = strPassword;
        m_strActivityId = strActivityId;
        m_avAttend = avAttend;
    }

    @Override
    protected void doLogic() {
        ActivityAttendTaskWrapper task = new ActivityAttendTaskWrapper(this);
        task.execute(m_strId, m_strEmail, m_strPassword, m_strActivityId, m_avAttend);
    }

    @Override
    public void onAttendSuccess() {
        mCaller.returnAttendSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onAttendFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
