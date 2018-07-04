package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.IgActivityDeemTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class IgActivityDeemLogic extends Logic implements IgActivityDeemTaskWrapper.IgActivityDeemTaskWrapperCallback {

    public interface IgActivityDeemLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnDeemSuccess();
    }

    private IgActivityDeemLogicCaller mCaller;
    private String m_strEmail;
    private String m_strPassword;
    private String m_strActivityId;
    private IgActivityDeemTaskWrapper.DEEM_VALUE m_dvDeem;
    private boolean m_bDeemRollBack;

    IgActivityDeemLogic(IgActivityDeemLogicCaller caller, String strEmail, String strPassword, String strActivityId
            , IgActivityDeemTaskWrapper.DEEM_VALUE dvDeem, boolean bIsDeemRollBack) {
        super(caller);
        mCaller = caller;
        m_strEmail = strEmail;
        m_strPassword = strPassword;
        m_strActivityId = strActivityId;
        m_dvDeem = dvDeem;
        m_bDeemRollBack = bIsDeemRollBack;
    }

    @Override
    protected void doLogic() {
        IgActivityDeemTaskWrapper task = new IgActivityDeemTaskWrapper(this);
        task.execute(m_strEmail, m_strPassword, m_strActivityId, m_dvDeem, m_bDeemRollBack);
    }

    @Override
    public void onDeemSuccess() {
        mCaller.returnDeemSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onDeemFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
