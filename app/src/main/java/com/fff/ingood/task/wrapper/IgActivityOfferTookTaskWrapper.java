package com.fff.ingood.task.wrapper;

import com.fff.ingood.task.AsyncHttpRequestResponder;
import com.fff.ingood.task.IgActivityOfferTookTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class IgActivityOfferTookTaskWrapper {

    public interface IgActivityOfferTookTaskWrapperCallback {
        void onOfferTookSuccess();
        void onOfferTookFailure(Integer iStatusCode);
    }

    private IgActivityOfferTookTask task;
    private IgActivityOfferTookTaskWrapperCallback mCb;

    public IgActivityOfferTookTaskWrapper(IgActivityOfferTookTaskWrapperCallback cb) {
        mCb = cb;
        task = new IgActivityOfferTookTask(new AsyncHttpRequestResponder<Integer, Void>() {
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
                mCb.onOfferTookSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onOfferTookFailure(iStatusCode);
            }
        });
    }

    public void execute(String strEmail, String strPassword, String strActivityId) {
        final String TAG_PERSON_EMAIL = "email";
        final String TAG_PERSON_PASSWORD = "userpassword";
        final String TAG_ACTIVITY_ID = "activityid";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_PERSON_EMAIL, strEmail);
        jsonObject.addProperty(TAG_PERSON_PASSWORD, strPassword);
        jsonObject.addProperty(TAG_ACTIVITY_ID, strActivityId);

        task.execute(jsonObject);
    }
}