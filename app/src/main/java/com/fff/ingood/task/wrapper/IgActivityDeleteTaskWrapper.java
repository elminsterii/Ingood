package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.IgActivityDeleteTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class IgActivityDeleteTaskWrapper {

    public interface IgActivityDeleteTaskWrapperCallback {
        void onDeleteIgActivitiesIdsSuccess();
        void onDeleteIgActivitiesIdsFailure(Integer iStatusCode);
    }

    private IgActivityDeleteTask task;
    private IgActivityDeleteTaskWrapperCallback mCb;

    public IgActivityDeleteTaskWrapper(IgActivityDeleteTaskWrapperCallback cb) {
        mCb = cb;
        task = new IgActivityDeleteTask(new AsyncResponder<Integer, Void>() {
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
                mCb.onDeleteIgActivitiesIdsSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onDeleteIgActivitiesIdsFailure(iStatusCode);
            }
        });
    }

    public void execute(IgActivity activity) {
        task.execute(activity);
    }
}