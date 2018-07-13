package com.fff.ingood.logic;

import com.fff.ingood.data.Comment;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class CommentLogicExecutor {

    public void doCreateComment(CommentCreateLogic.CommentCreateLogicCaller caller
            , String strPublisherEmail, String strPublisherName, String strIgActivityId, String strCommentContent) {
        Logic fl = new CommentCreateLogic(caller, strPublisherEmail, strPublisherName, strIgActivityId, strCommentContent);
        fl.doLogic();
    }

    public void doDeleteComment(CommentDeleteLogic.CommentDeleteLogicCaller caller, Comment comment) {
        Logic fl = new CommentDeleteLogic(caller, comment);
        fl.doLogic();
    }

    public void doUpdateComment(CommentUpdateLogic.CommentUpdateLogicCaller caller, Comment comment) {
        Logic fl = new CommentUpdateLogic(caller, comment);
        fl.doLogic();
    }

    public void doSearchCommentsIds(CommentQueryLogic.CommentQueryLogicCaller caller, Comment comment) {
        Logic fl = new CommentQueryLogic(caller, comment);
        fl.doLogic();
    }

    public void doSearchComments(CommentQueryLogic.CommentQueryLogicCaller caller, String strIds) {
        Logic fl = new CommentQueryLogic(caller, strIds);
        fl.doLogic();
    }
}
