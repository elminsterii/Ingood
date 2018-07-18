package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.AsyncHttpRequestResponder;
import com.fff.ingood.task.IgActivityImageGetListTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_ACTIVITY_IMAGES;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class IgActivityImageGetListTaskWrapper {

    public interface IgActivityImageGetListTaskWrapperCallback {
        void onGetIgActivitiesImageListSuccess(String s);
        void onGetIgActivitiesImageListFailure(Integer iStatusCode);
    }

    private IgActivityImageGetListTask task;
    private IgActivityImageGetListTaskWrapperCallback mCb;

    public IgActivityImageGetListTaskWrapper(IgActivityImageGetListTaskWrapperCallback cb) {
        mCb = cb;
        task = new IgActivityImageGetListTask(new AsyncHttpRequestResponder<Integer, String>() {
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
                        setData(ParserUtils.getStringByTag(TAG_SERVER_RESPONSE_ACTIVITY_IMAGES, strJsonResponse));
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
            public void onSuccess(String s) {
                mCb.onGetIgActivitiesImageListSuccess(s);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onGetIgActivitiesImageListFailure(iStatusCode);
            }
        });
    }

    public void execute(IgActivity strIgActivityId ) {

        task.execute(strIgActivityId);
    }
}