package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.AsyncHttpRequestResponder;
import com.fff.ingood.task.PersonTempPasswordTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by chris on 2018/7/30.
 */

public class PersonTempPasswordTaskWrapper {

    public interface PersonTempPasswordTaskWrapperCallback {
        void onPersonTempPasswordSentSuccess();
        void onPersonTempPasswordSentFailure(Integer iStatusCode);
    }

    private PersonTempPasswordTask task;
    private PersonTempPasswordTaskWrapperCallback mCb;

    public PersonTempPasswordTaskWrapper(PersonTempPasswordTaskWrapperCallback cb) {
        mCb = cb;
        task = new PersonTempPasswordTask(new AsyncHttpRequestResponder<Integer, Void>() {
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
                mCb.onPersonTempPasswordSentSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onPersonTempPasswordSentFailure(iStatusCode);
            }
        });
    }

    public void execute(Person person) {
        task.execute(person);
    }
}
