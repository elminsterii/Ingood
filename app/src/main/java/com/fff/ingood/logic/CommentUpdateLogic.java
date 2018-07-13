package com.fff.ingood.logic;

import com.fff.ingood.data.Comment;
import com.fff.ingood.task.wrapper.CommentUpdateTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class CommentUpdateLogic extends Logic implements CommentUpdateTaskWrapper.CommentUpdateTaskWrapperCallback {

    public interface CommentUpdateLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnUpdateCommentSuccess();
    }

    private CommentUpdateLogicCaller mCaller;
    private Comment mComment;

    CommentUpdateLogic(CommentUpdateLogicCaller caller, Comment comment) {
        super(caller);
        mCaller = caller;
        mComment = comment;
    }

    @Override
    protected void doLogic() {
        CommentUpdateTaskWrapper task = new CommentUpdateTaskWrapper(this);
        task.execute(mComment);
    }

    @Override
    public void onUpdateSuccess() {
        mCaller.returnUpdateCommentSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onUpdateFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
