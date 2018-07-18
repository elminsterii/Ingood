package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.AsyncHttpRequestResponder;
import com.fff.ingood.task.IgActivityQueryTask;
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

public class IgActivityQueryTaskWrapper {

    public interface IgActivityQueryTaskWrapperCallback {
        void onQueryIgActivitiesSuccess(List<IgActivity> lsActivities);
        void onQueryIgActivitiesFailure(Integer iStatusCode);
    }

    private IgActivityQueryTask task;
    private IgActivityQueryTaskWrapperCallback mCb;

    public IgActivityQueryTaskWrapper(IgActivityQueryTaskWrapperCallback cb) {
        mCb = cb;
        task = new IgActivityQueryTask(new AsyncHttpRequestResponder<Integer, List<IgActivity>>() {
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
                mCb.onQueryIgActivitiesSuccess(lsActivities);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onQueryIgActivitiesFailure(iStatusCode);
            }
        });
    }

    public void execute(String strIds) {
        final String TAG_ACTIVITY_IDS = "ids";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_ACTIVITY_IDS, strIds);

        task.execute(jsonObject);
    }
}
