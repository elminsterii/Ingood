package com.fff.ingood.task2.wrapper;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task2.ActivityDeleteTask;
import com.fff.ingood.task2.AsyncResponder;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class ActivityDeleteTaskWrapper {

    public interface ActivityDeleteTaskWrapperCallback {
        void onDeleteActivitiesIdsSuccess();
        void onDeleteActivitiesIdsFailure(Integer iStatusCode);
    }

    private ActivityDeleteTask task;
    private ActivityDeleteTaskWrapper.ActivityDeleteTaskWrapperCallback mCb;

    public ActivityDeleteTaskWrapper(ActivityDeleteTaskWrapper.ActivityDeleteTaskWrapperCallback cb) {
        mCb = cb;
        task = new ActivityDeleteTask(new AsyncResponder<Integer, Void>() {
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
                mCb.onDeleteActivitiesIdsSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onDeleteActivitiesIdsFailure(iStatusCode);
            }
        });
    }

    public void execute(IgActivity activity) {
        task.execute(activity);
    }
}