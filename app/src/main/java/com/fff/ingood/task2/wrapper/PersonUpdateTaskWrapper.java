package com.fff.ingood.task2.wrapper;

import com.fff.ingood.data.Person;
import com.fff.ingood.task2.AsyncResponder;
import com.fff.ingood.task2.PersonUpdateTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class PersonUpdateTaskWrapper {

    public interface PersonUpdateTaskWrapperCallback {
        void onUpdateSuccess();
        void onUpdateFailure(Integer iStatusCode);
    }

    private PersonUpdateTask task;
    private PersonUpdateTaskWrapperCallback mCb;

    public PersonUpdateTaskWrapper(PersonUpdateTaskWrapperCallback cb) {
        mCb = cb;
        task = new PersonUpdateTask(new AsyncResponder<Integer, Void>() {
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
                mCb.onUpdateSuccess();
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onUpdateFailure(iStatusCode);
            }
        });
    }

    public void execute(Person person) {
        task.execute(person);
    }
}
