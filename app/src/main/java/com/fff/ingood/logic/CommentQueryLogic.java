package com.fff.ingood.logic;

import com.fff.ingood.data.Comment;
import com.fff.ingood.task.wrapper.CommentQueryIdByTaskWrapper;
import com.fff.ingood.task.wrapper.CommentQueryTaskWrapper;
import com.fff.ingood.tools.StringTool;

import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.
 */
public class CommentQueryLogic extends Logic implements
        CommentQueryIdByTaskWrapper.CommentQueryIdByTaskWrapperCallback
        , CommentQueryTaskWrapper.CommentQueryTaskWrapperCallback {

    public interface CommentQueryLogicCaller extends LogicCaller {
        void returnStatus(Integer iStatusCode);
        void returnComments(List<Comment> lsComments);
        void returnCommentsIds(String strCommentsIds);
    }

    private CommentQueryLogicCaller mCaller;
    private Comment mCommentCondition;
    private String m_strIds;

    CommentQueryLogic(CommentQueryLogicCaller caller, Comment comment) {
        super(caller);
        mCaller = caller;
        mCommentCondition = comment;
    }

    CommentQueryLogic(CommentQueryLogicCaller caller, String strIds) {
        super(caller);
        mCaller = caller;
        m_strIds = strIds;
    }

    @Override
    protected void doLogic() {
        if(StringTool.checkStringNotNull(m_strIds)) {
            //query comments data by ids.
            CommentQueryTaskWrapper task = new CommentQueryTaskWrapper(this);
            task.execute(m_strIds);
        } else if(mCommentCondition != null) {
            //take comments ids by conditions.
            CommentQueryIdByTaskWrapper task = new CommentQueryIdByTaskWrapper(this);
            task.execute(mCommentCondition);
        } else {
            //clear comment list.
            mCaller.returnComments(new ArrayList<Comment>());
        }
    }

    @Override
    public void onQueryCommentsIdsSuccess(String strIds) {
        mCaller.returnCommentsIds(strIds);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onQueryCommentsIdsFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }

    @Override
    public void onQueryCommentsSuccess(List<Comment> lsComments) {
        mCaller.returnComments(lsComments);
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onQueryCommentsFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
