package com.fff.ingood.task2.wrapper;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task2.ActivityQueryIdByTask;
import com.fff.ingood.task2.AsyncResponder;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_ACTIVITIES_IDS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class ActivityQueryIdByTaskWrapper {

    public interface ActivityQueryIdByTaskWrapperCallback {
        void onQueryActivitiesIdsSuccess(String strIds);
        void onQueryActivitiesIdsFailure(Integer iStatusCode);
    }

    private ActivityQueryIdByTask task;
    private ActivityQueryIdByTaskWrapperCallback mCb;

    public ActivityQueryIdByTaskWrapper(ActivityQueryIdByTaskWrapperCallback cb) {
        mCb = cb;
        task = new ActivityQueryIdByTask(new AsyncResponder<Integer, String>() {
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
                        setData(ParserUtils.getStringByTag(TAG_SERVER_RESPONSE_ACTIVITIES_IDS, strJsonResponse));
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
                mCb.onQueryActivitiesIdsSuccess(strIds);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onQueryActivitiesIdsFailure(iStatusCode);
            }
        });
    }

    public void execute(IgActivity activity) {
        task.execute(activity);
    }
}
