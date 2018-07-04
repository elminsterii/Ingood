package com.fff.ingood.logic;

import com.fff.ingood.data.Comment;
import com.fff.ingood.task.wrapper.CommentCreateTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class CommentCreateLogic extends Logic implements CommentCreateTaskWrapper.CommentCreateTaskWrapperCallback {

    public interface CommentCreateLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void onCreateIgActivitySuccess(String strId);
    }

    private CommentCreateLogicCaller mCaller;
    private Comment mCommentNew;

    CommentCreateLogic(CommentCreateLogicCaller caller, Comment igCommentNew) {
        super(caller);
        mCaller = caller;
        mCommentNew = igCommentNew;
    }

    @Override
    protected void doLogic() {
        CommentCreateTaskWrapper loginWrapper = new CommentCreateTaskWrapper(this);
        loginWrapper.execute(mCommentNew);
    }

    @Override
    public void onCreateSuccess(String strId) {
        mCaller.onCreateIgActivitySuccess(strId);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onCreateFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
