package com.fff.ingood.task.wrapper;

import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.IgActivityDeemTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class IgActivityDeemTaskWrapper {

    public enum DEEM_VALUE {DV_BAD, DV_GOOD}

    public interface IgActivityDeemTaskWrapperCallback {
        void onDeemSuccess();
        void onDeemFailure(Integer iStatusCode);
    }

    private IgActivityDeemTask task;
    private IgActivityDeemTaskWrapperCallback mCb;

    public IgActivityDeemTaskWrapper(IgActivityDeemTaskWrapperCallback cb) {
        mCb = cb;
        task = new IgActivityDeemTask(new AsyncResponder<Integer, Void>() {
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
                mCb.onDeemSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onDeemFailure(iStatusCode);
            }
        });
    }

    public void execute(String strEmail, String strPassword, String strActivityId, DEEM_VALUE dvDeem, boolean bIsRollBack) {
        final String TAG_PERSON_EMAIL = "email";
        final String TAG_PERSON_PASSWORD = "userpassword";
        final String TAG_ACTIVITY_ID = "activityid";
        final String TAG_ACTIVITY_DEEM = "deem";
        final String TAG_ACTIVITY_DEEMRB = "deemrb";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_PERSON_EMAIL, strEmail);
        jsonObject.addProperty(TAG_PERSON_PASSWORD, strPassword);
        jsonObject.addProperty(TAG_ACTIVITY_ID, strActivityId);
        jsonObject.addProperty(TAG_ACTIVITY_DEEM, (dvDeem == DEEM_VALUE.DV_BAD ? "0" : "1"));
        jsonObject.addProperty(TAG_ACTIVITY_DEEMRB, (bIsRollBack ? "1" : "0"));

        task.execute(jsonObject);
    }
}