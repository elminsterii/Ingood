package com.fff.ingood.task.wrapper;

import android.graphics.Bitmap;

import com.fff.ingood.task.AsyncHttpRequestResponder;
import com.fff.ingood.task.PersonIconUploadTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class PersonIconUploadTaskWrapper {

    public interface PersonIconUploadTaskWrapperCallback {
        void onPersonIconUploadSuccess();
        void onPersonIconUploadFailure(Integer iStatusCode);
    }

    private PersonIconUploadTask task;
    private PersonIconUploadTaskWrapperCallback mCb;

    public PersonIconUploadTaskWrapper(PersonIconUploadTaskWrapperCallback cb, Bitmap bmUpload) {
        mCb = cb;
        task = new PersonIconUploadTask(new AsyncHttpRequestResponder<Integer, Void>() {
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
                mCb.onPersonIconUploadSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onPersonIconUploadFailure(iStatusCode);
            }
        }, bmUpload);
    }

    public void execute(String strEmailAndIcon) {
        task.execute(strEmailAndIcon);
    }
}