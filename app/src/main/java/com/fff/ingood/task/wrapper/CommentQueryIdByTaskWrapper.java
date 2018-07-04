package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.Comment;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.CommentQueryIdByTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_COMMON_IDS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class CommentQueryIdByTaskWrapper {

    public interface CommentQueryIdByTaskWrapperCallback {
        void onQueryCommentsIdsSuccess(String strIds);
        void onQueryCommentsIdsFailure(Integer iStatusCode);
    }

    private CommentQueryIdByTask task;
    private CommentQueryIdByTaskWrapperCallback mCb;

    public CommentQueryIdByTaskWrapper(CommentQueryIdByTaskWrapperCallback cb) {
        mCb = cb;
        task = new CommentQueryIdByTask(new AsyncResponder<Integer, String>() {
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
                        setData(ParserUtils.getStringByTag(TAG_SERVER_RESPONSE_COMMON_IDS, strJsonResponse));
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
            public void onSuccess(String strIds) {
                mCb.onQueryCommentsIdsSuccess(strIds);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onQueryCommentsIdsFailure(iStatusCode);
            }
        });
    }

    public void execute(Comment comment) {
        task.execute(comment);
    }
}
