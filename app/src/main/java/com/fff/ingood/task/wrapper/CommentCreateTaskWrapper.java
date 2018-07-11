package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.Comment;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.CommentCreateTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class CommentCreateTaskWrapper {

    public interface CommentCreateTaskWrapperCallback {
        void onCreateSuccess(String strId);
        void onCreateFailure(Integer iStatusCode);
    }

    private CommentCreateTask task;
    private CommentCreateTaskWrapperCallback mCb;

    public CommentCreateTaskWrapper(CommentCreateTaskWrapperCallback cb) {
        mCb = cb;
        task = new CommentCreateTask(new AsyncResponder<Integer, String>() {
            @Override
            public boolean parseResponse(String strJsonResponse) {
                if(!StringTool.checkStringNotNull(strJsonResponse)) {
                    setStatus(STATUS_CODE_NWK_FAIL_INT);
                    return false;
                }

                String strStatusCode = ParserUtils.getStringByTag(TAG_SERVER_RESPONSE_STATUS_CODE, strJsonResponse);

                if(StringTool.checkStringNotNull(strStatusCode)) {
                    if (strStatusCode.equals(STATUS_CODE_SUCCESS)) {
                        setStatus(Integer.parseInt(strStatusCode));

                        List<Comment> lsComments = ParserUtils.getCommentsByJson(strJsonResponse);
                        if(lsComments != null && lsComments.size() > 0)
                            setData(lsComments.get(0).getId());
                        return true;
                    } else {
                        setStatus(Integer.parseInt(strStatusCode));
                    }
                } else {
                    setStatus(STATUS_CODE_PARSING_ERROR);
                }
                return false;
            }

            @Override
            public void onSuccess(String strId) {
                mCb.onCreateSuccess(strId);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onCreateFailure(iStatusCode);
            }
        });
    }

    public void execute(String strPublisherEmail, String strPublisherName, String strIgActivityId, String strCommentContent) {
        final String TAG_PUBLISHER_EMAIL = "publisheremail";
        final String TAG_PUBLISHER_NAME = "displayname";
        final String TAG_ACTIVITY_ID = "activityid";
        final String TAG_COMMENT_CONTENT = "content";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_PUBLISHER_EMAIL, strPublisherEmail);
        jsonObject.addProperty(TAG_PUBLISHER_NAME, strPublisherName);
        jsonObject.addProperty(TAG_ACTIVITY_ID, strIgActivityId);
        jsonObject.addProperty(TAG_COMMENT_CONTENT, strCommentContent);

        task.execute(jsonObject);
    }
}