package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.IgActivityCreateTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class IgActivityCreateTaskWrapper {

    public interface IgActivityCreateTaskWrapperCallback {
        void onCreateIgActivitySuccess(String strId);
        void onCreateIgActivityFailure(Integer iStatusCode);
    }

    private IgActivityCreateTask task;
    private IgActivityCreateTaskWrapperCallback mCb;

    public IgActivityCreateTaskWrapper(IgActivityCreateTaskWrapperCallback cb) {
        mCb = cb;
        task = new IgActivityCreateTask(new AsyncResponder<Integer, String>() {
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
                mCb.onCreateIgActivitySuccess(strId);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onCreateIgActivityFailure(iStatusCode);
            }
        });
    }

    public void execute(IgActivity activity) {
        task.execute(activity);
    }
}
