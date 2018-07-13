package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.PersonVerifyTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class PersonVerifyTaskWrapper {

    public interface PersonVerifyTaskWrapperCallback {
        void onVerifyCodeIncoming(String strVerifyCode);
        void onFailure(Integer iStatusCode);
    }

    private PersonVerifyTask task;
    private PersonVerifyTaskWrapperCallback mCb;

    public PersonVerifyTaskWrapper(PersonVerifyTaskWrapperCallback cb) {
        mCb = cb;
        task = new PersonVerifyTask(new AsyncResponder<Integer, String>() {
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
                        Person person = ParserUtils.getPersonByJson(strJsonResponse);

                        if(person != null) {
                            setData(person.getVerifyCode());
                            return true;
                        } else {
                            setStatus(Integer.parseInt(strStatusCode));
                        }
                    } else {
                        setStatus(Integer.parseInt(strStatusCode));
                    }
                } else {
                    setStatus(STATUS_CODE_PARSING_ERROR);
                }
                return false;
            }

            @Override
            public void onSuccess(String strVerifyCode) {
                mCb.onVerifyCodeIncoming(strVerifyCode);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onFailure(iStatusCode);
            }
        });
    }

    public void execute(Person person) {
        task.execute(person);
    }
}
