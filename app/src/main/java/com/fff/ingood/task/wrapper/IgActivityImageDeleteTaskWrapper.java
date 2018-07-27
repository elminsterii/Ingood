package com.fff.ingood.task.wrapper;

import com.fff.ingood.task.AsyncHttpRequestResponder;
import com.fff.ingood.task.IgActivityImageDeleteTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;
import com.google.gson.JsonObject;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class IgActivityImageDeleteTaskWrapper {

    public interface IgActivityImageDeleteTaskWrapperCallback {
        void onDeleteIgActivityImageSuccess();
        void onDeleteIgActivityImageFailure(Integer iStatusCode);
    }

    private IgActivityImageDeleteTask task;
    private IgActivityImageDeleteTaskWrapperCallback mCb;

    public IgActivityImageDeleteTaskWrapper(IgActivityImageDeleteTaskWrapperCallback cb) {
        mCb = cb;
        task = new IgActivityImageDeleteTask(new AsyncHttpRequestResponder<Integer, Void>() {
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
                mCb.onDeleteIgActivityImageSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onDeleteIgActivityImageFailure(iStatusCode);
            }
        });
    }

    public void execute(String strEmail, String strPassword, String strIgActivityId, List<String> lsImagesName) {
        final String TAG_EMAIL = "email";
        final String TAG_PASSWORD = "userpassword";
        final String TAG_IGACTIVITY_ID = "id";
        final String TAG_IMAGES = "images";

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(TAG_EMAIL, strEmail);
        jsonObject.addProperty(TAG_PASSWORD, strPassword);
        jsonObject.addProperty(TAG_IGACTIVITY_ID, strIgActivityId);

        if(lsImagesName != null && lsImagesName.size() > 0) {
            String strImagesName = StringTool.listStringToString(lsImagesName, ',');
            jsonObject.addProperty(TAG_IMAGES, strImagesName);
        }
        task.execute(jsonObject);
    }
}