package com.fff.ingood.logic;

import com.fff.ingood.task.wrapper.CommentCreateTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class CommentCreateLogic extends Logic implements CommentCreateTaskWrapper.CommentCreateTaskWrapperCallback {

    public interface CommentCreateLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnCreateCommentSuccess(String strId);
    }

    private CommentCreateLogicCaller mCaller;
    private String m_strPublisherEmail;
    private String m_strPublisherName;
    private String m_strIgActivityId;
    private String m_strCommentContent;

    CommentCreateLogic(CommentCreateLogicCaller caller
            , String strPublisherEmail
            , String strPublisherName
            , String strIgActivityId
            , String strCommentContent) {
        super(caller);
        mCaller = caller;
        m_strPublisherEmail = strPublisherEmail;
        m_strPublisherName = strPublisherName;
        m_strIgActivityId = strIgActivityId;
        m_strCommentContent = strCommentContent;
    }

    @Override
    protected void doLogic() {
        CommentCreateTaskWrapper loginWrapper = new CommentCreateTaskWrapper(this);
        loginWrapper.execute(m_strPublisherEmail, m_strPublisherName, m_strIgActivityId, m_strCommentContent);
    }

    @Override
    public void onCreateSuccess(String strId) {
        mCaller.returnCreateCommentSuccess(strId);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onCreateFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
