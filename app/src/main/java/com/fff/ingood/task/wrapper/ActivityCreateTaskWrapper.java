package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.ActivityCreateTask;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class ActivityCreateTaskWrapper {

    public interface ActivityCreateTaskWrapperCallback {
        void onCreateActivitySuccess(String strId);
        void onCreateActivityFailure(Integer iStatusCode);
    }

    private ActivityCreateTask task;
    private ActivityCreateTaskWrapper.ActivityCreateTaskWrapperCallback mCb;

    public ActivityCreateTaskWrapper(ActivityCreateTaskWrapper.ActivityCreateTaskWrapperCallback cb) {
        mCb = cb;
        task = new ActivityCreateTask(new AsyncResponder<Integer, String>() {
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
                        setData(ParserUtils.getActivitiesByJson(strJsonResponse).get(0).getId());
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
                mCb.onCreateActivitySuccess(strId);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onCreateActivityFailure(iStatusCode);
            }
        });
    }

    public void execute(IgActivity activity) {
        task.execute(activity);
    }
}
