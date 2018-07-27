package com.fff.ingood.task.wrapper;

import android.graphics.Bitmap;

import com.fff.ingood.task.AsyncHttpRequestResponder;
import com.fff.ingood.task.IgActivityImageUploadTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class IgActivityImageUploadTaskWrapper {

    public interface IgActivityImageUploadTaskWrapperCallback {
        void onIgActivityImageUploadSuccess();
        void onIgActivityImageUploadFailure(Integer iStatusCode);
    }

    private IgActivityImageUploadTask task;
    private IgActivityImageUploadTaskWrapperCallback mCb;

    public IgActivityImageUploadTaskWrapper(IgActivityImageUploadTaskWrapperCallback cb, Bitmap bmUpload) {
        mCb = cb;
        task = new IgActivityImageUploadTask(new AsyncHttpRequestResponder<Integer, Void>() {
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
                mCb.onIgActivityImageUploadSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onIgActivityImageUploadFailure(iStatusCode);
            }
        }, bmUpload);
    }

    public void execute(String strActivityIdAndImageName) {
        task.execute(strActivityIdAndImageName);
    }
}