package com.fff.ingood.task.wrapper;

import com.fff.ingood.task.AsyncHttpRequestResponder;
import com.fff.ingood.task.PersonIconGetListTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_PERSON_ICONS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

public class PersonIconGetListTaskWrapper {

    public interface PersonIconGetListTaskWrapperCallback {
        void onGetIconListSuccess(String strIconsName);
        void onGetIconListFailure(Integer iStatusCode);
    }

    private PersonIconGetListTask task;
    private PersonIconGetListTaskWrapperCallback mCb;

    public PersonIconGetListTaskWrapper(PersonIconGetListTaskWrapperCallback cb) {
        mCb = cb;
        task = new PersonIconGetListTask(new AsyncHttpRequestResponder<Integer, String>() {
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
                        setData(ParserUtils.getStringByTag(TAG_SERVER_RESPONSE_PERSON_ICONS, strJsonResponse));
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
            public void onSuccess(String strIconsName) {
                mCb.onGetIconListSuccess(strIconsName);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onGetIconListFailure(iStatusCode);
            }
        });
    }

    public void execute(String strEmailOrId) {
        task.execute(strEmailOrId);
    }
}