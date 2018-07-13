package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.Comment;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.CommentQueryTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class CommentQueryTaskWrapper {

    public interface CommentQueryTaskWrapperCallback {
        void onQueryCommentsSuccess(List<Comment> lsComments);
        void onQueryCommentsFailure(Integer iStatusCode);
    }

    private CommentQueryTask task;
    private CommentQueryTaskWrapperCallback mCb;

    public CommentQueryTaskWrapper(CommentQueryTaskWrapperCallback cb) {
        mCb = cb;
        task = new CommentQueryTask(new AsyncResponder<Integer, List<Comment>>() {
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
                        setData(ParserUtils.getCommentsByJson(strJsonResponse));
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
            public void onSuccess(List<Comment> lsComments) {
                mCb.onQueryCommentsSuccess(lsComments);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onQueryCommentsFailure(iStatusCode);
            }
        });
    }

    public void execute(String strIds) {
        final String TAG_COMMENT_IDS = "ids";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_COMMENT_IDS, strIds);

        task.execute(jsonObject);
    }
}
