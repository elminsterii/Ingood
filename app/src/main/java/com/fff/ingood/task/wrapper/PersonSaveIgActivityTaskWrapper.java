package com.fff.ingood.task.wrapper;

import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.PersonSaveIgActivityTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class PersonSaveIgActivityTaskWrapper {

    public enum SAVE_ACT_VALUE {SV_CANCEL_SAVE, SV_SAVE}

    public interface PersonSaveIgActivityTaskWrapperCallback {
        void onSaveIgActivitySuccess();
        void onSaveIgActivityFailure(Integer iStatusCode);
    }

    private PersonSaveIgActivityTask task;
    private PersonSaveIgActivityTaskWrapperCallback mCb;

    public PersonSaveIgActivityTaskWrapper(PersonSaveIgActivityTaskWrapperCallback cb) {
        mCb = cb;
        task = new PersonSaveIgActivityTask(new AsyncResponder<Integer, Void>() {
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
                mCb.onSaveIgActivitySuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onSaveIgActivityFailure(iStatusCode);
            }
        });
    }

    public void execute(String strEmail, String strActivityId, SAVE_ACT_VALUE svValue) {
        final String TAG_PERSON_EMAIL = "email";
        final String TAG_ACTIVITY_ID = "activityid";
        final String TAG_IS_SAVE = "issave";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_PERSON_EMAIL, strEmail);
        jsonObject.addProperty(TAG_ACTIVITY_ID, strActivityId);
        jsonObject.addProperty(TAG_IS_SAVE, (svValue == SAVE_ACT_VALUE.SV_CANCEL_SAVE ? "0" : "1"));

        task.execute(jsonObject);
    }
}