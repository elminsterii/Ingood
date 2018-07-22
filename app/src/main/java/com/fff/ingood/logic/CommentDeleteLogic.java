package com.fff.ingood.logic;

import com.fff.ingood.data.Comment;
import com.fff.ingood.task.wrapper.CommentDeleteTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class CommentDeleteLogic extends Logic implements CommentDeleteTaskWrapper.CommentDeleteTaskWrapperCallback {

    public interface CommentDeleteLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnDeleteCommentSuccess();
    }

    private CommentDeleteLogicCaller mCaller;
    private Comment mComment;

    CommentDeleteLogic(CommentDeleteLogicCaller caller, Comment comment) {
        super(caller);
        mCaller = caller;
        mComment = comment;
    }

    @Override
    protected void doLogic() {
        CommentDeleteTaskWrapper task = new CommentDeleteTaskWrapper(this);
        task.execute(mComment);
    }

    @Override
    public void onDeleteSuccess() {
        mCaller.returnDeleteCommentSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onDeleteFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
