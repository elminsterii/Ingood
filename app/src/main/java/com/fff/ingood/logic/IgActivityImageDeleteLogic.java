package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.IgActivityImageDeleteTaskWrapper;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class IgActivityImageDeleteLogic extends Logic
        implements IgActivityImageDeleteTaskWrapper.IgActivityImageDeleteTaskWrapperCallback {

    public interface IgActivityImageDeleteLogicCaller extends LogicCaller {
        void returnIgActivityImageDeleteSuccess();
        void returnStatus(Integer iStatusCode);
    }

    private IgActivityImageDeleteLogicCaller mCaller;
    private String m_strEmail;
    private String m_strPassword;
    private String m_strIgActivityId;
    private List<String> m_lsImagesName;

    IgActivityImageDeleteLogic(IgActivityImageDeleteLogicCaller caller
            , String strEmail, String strPassword, String strIgActivityId, List<String> lsImagesName) {
        super(caller);
        mCaller = caller;
        m_strEmail = strEmail;
        m_strPassword = strPassword;
        m_strIgActivityId = strIgActivityId;
        m_lsImagesName = lsImagesName;
    }

    @Override
    protected void doLogic() {
        IgActivityImageDeleteTaskWrapper task = new IgActivityImageDeleteTaskWrapper(this);
        task.execute(m_strEmail, m_strPassword, m_strIgActivityId, m_lsImagesName);
    }

    @Override
    public void onDeleteIgActivityImageSuccess() {
        mCaller.returnIgActivityImageDeleteSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onDeleteIgActivityImageFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
