package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.IgActivityOfferTookTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class IgActivityOfferTookLogic extends Logic implements IgActivityOfferTookTaskWrapper.IgActivityOfferTookTaskWrapperCallback {

    public interface IgActivityOfferTookLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnOfferTookIgActivitySuccess();
    }

    private IgActivityOfferTookLogicCaller mCaller;
    private String m_strEmail;
    private String m_strPassword;
    private String m_strActivityId;

    IgActivityOfferTookLogic(IgActivityOfferTookLogicCaller caller, String strEmail, String strPassword, String strActivityId) {
        super(caller);
        mCaller = caller;
        m_strEmail = strEmail;
        m_strPassword = strPassword;
        m_strActivityId = strActivityId;
    }

    @Override
    protected void doLogic() {
        IgActivityOfferTookTaskWrapper task = new IgActivityOfferTookTaskWrapper(this);
        task.execute(m_strEmail, m_strPassword, m_strActivityId);
    }

    @Override
    public void onOfferTookSuccess() {
        mCaller.returnOfferTookIgActivitySuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onOfferTookFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
