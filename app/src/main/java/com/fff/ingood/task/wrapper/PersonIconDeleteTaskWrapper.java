package com.fff.ingood.task.wrapper;

import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.PersonIconDeleteTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class PersonIconDeleteTaskWrapper {

    public interface PersonIconDeleteTaskWrapperCallback {
        void onDeleteIconSuccess();
        void onDeleteIconFailure(Integer iStatusCode);
    }

    private PersonIconDeleteTask task;
    private PersonIconDeleteTaskWrapperCallback mCb;

    public PersonIconDeleteTaskWrapper(PersonIconDeleteTaskWrapperCallback cb) {
        mCb = cb;
        task = new PersonIconDeleteTask(new AsyncResponder<Integer, Void>() {
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
                mCb.onDeleteIconSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onDeleteIconFailure(iStatusCode);
            }
        });
    }

    public void execute(String strEmail, String strPassword, List<String> lsIconsName) {
        final String TAG_EMAIL = "email";
        final String TAG_PASSWORD = "userpassword";
        final String TAG_ICONS = "icons";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_EMAIL, strEmail);
        jsonObject.addProperty(TAG_PASSWORD, strPassword);

        if(lsIconsName != null && lsIconsName.size() > 0) {
            String strIconsName = StringTool.listStringToString(lsIconsName, ',');
            jsonObject.addProperty(TAG_ICONS, strIconsName);
        }

        task.execute(jsonObject);
    }
}