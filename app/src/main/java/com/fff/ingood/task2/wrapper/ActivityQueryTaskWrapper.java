package com.fff.ingood.task2.wrapper;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task2.ActivityQueryTask;
import com.fff.ingood.task2.AsyncResponder;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class ActivityQueryTaskWrapper {

    public interface ActivityQueryTaskWrapperCallback {
        void onQueryActivitiesSuccess(List<IgActivity> lsActivities);
        void onQueryActivitiesFailure(Integer iStatusCode);
    }

    private ActivityQueryTask task;
    private ActivityQueryTaskWrapperCallback mCb;

    public ActivityQueryTaskWrapper(ActivityQueryTaskWrapperCallback cb) {
        mCb = cb;
        task = new ActivityQueryTask(new AsyncResponder<Integer, List<IgActivity>>() {
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
                        setData(ParserUtils.getActivitiesByJson(strJsonResponse));
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
            public void onSuccess(List<IgActivity> lsActivities) {
                mCb.onQueryActivitiesSuccess(lsActivities);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onQueryActivitiesFailure(iStatusCode);
            }
        });
    }

    public void execute(String strIds) {
        task.execute(strIds);
    }
}
