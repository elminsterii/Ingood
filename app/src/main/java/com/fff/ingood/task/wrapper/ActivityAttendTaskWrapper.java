package com.fff.ingood.task.wrapper;

import com.fff.ingood.task.ActivityAttendTask;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class ActivityAttendTaskWrapper {

    public enum ATTEND_VALUE {AV_CANCEL_ATTEND, AV_ATTEND}

    public interface ActivityAttendTaskWrapperCallback {
        void onAttendSuccess();
        void onAttendFailure(Integer iStatusCode);
    }

    private ActivityAttendTask task;
    private ActivityAttendTaskWrapperCallback mCb;

    public ActivityAttendTaskWrapper(ActivityAttendTaskWrapperCallback cb) {
        mCb = cb;
        task = new ActivityAttendTask(new AsyncResponder<Integer, Void>() {
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
                mCb.onAttendSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onAttendFailure(iStatusCode);
            }
        });
    }

    public void execute(String strId, String strEmail, String strPassword, String strActivityId, ATTEND_VALUE avAttend) {
        final String TAG_PERSON_ID = "id";
        final String TAG_PERSON_EMAIL = "email";
        final String TAG_PERSON_PASSWORD = "userpassword";
        final String TAG_ACTIVITY_ID = "activityid";
        final String TAG_ACTIVITY_ATTEND = "attend";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_PERSON_ID, strId);
        jsonObject.addProperty(TAG_PERSON_EMAIL, strEmail);
        jsonObject.addProperty(TAG_PERSON_PASSWORD, strPassword);
        jsonObject.addProperty(TAG_ACTIVITY_ID, strActivityId);
        jsonObject.addProperty(TAG_ACTIVITY_ATTEND, (avAttend == ATTEND_VALUE.AV_CANCEL_ATTEND ? "0" : "1"));

        task.execute(jsonObject);
    }
}