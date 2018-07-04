package com.fff.ingood.task.wrapper;

import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.CommentCreateTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class CommentCreateTaskWrapper {

    public interface CommentCreateTaskWrapperCallback {
        void onCommentSuccess();
        void onCommentFailure(Integer iStatusCode);
    }

    private CommentCreateTask task;
    private CommentCreateTaskWrapperCallback mCb;

    public CommentCreateTaskWrapper(CommentCreateTaskWrapperCallback cb) {
        mCb = cb;
        task = new CommentCreateTask(new AsyncResponder<Integer, Void>() {
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
            public void onSuccess(Void aVoid) {
                mCb.onCommentSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onCommentFailure(iStatusCode);
            }
        });
    }

    public void execute(String strEmail, String strDisplayName, String strActivityId, String strContent) {
        final String TAG_PUBLISHER_EMAIL = "publisheremail";
        final String TAG_DISPLAY_NAME = "displayname";
        final String TAG_ACTIVITY_ID = "activityid";
        final String TAG_COMMENT_CONTENT = "content";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_PUBLISHER_EMAIL, strEmail);
        jsonObject.addProperty(TAG_DISPLAY_NAME, strDisplayName);
        jsonObject.addProperty(TAG_ACTIVITY_ID, strActivityId);
        jsonObject.addProperty(TAG_COMMENT_CONTENT, strContent);

        task.execute(jsonObject);
    }
}