package com.fff.ingood.task2.wrapper;

import com.fff.ingood.data.Person;
import com.fff.ingood.task2.AsyncResponder;
import com.fff.ingood.task2.PersonLoginTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */
public class PersonLoginTaskWrapper {

    public interface PersonLoginTaskWrapperCallback {
        void onLoginSuccess(Person person);
        void onLoginFailure(Integer iStatusCode);
    }

    private PersonLoginTask task;
    private PersonLoginTaskWrapperCallback mCb;

    public PersonLoginTaskWrapper(PersonLoginTaskWrapperCallback cb) {
        mCb = cb;
        task = new PersonLoginTask(new AsyncResponder<Integer, Person>() {
            @Override
            public boolean makeOutput(String strJsonResponse) {
                if(!StringTool.checkStringNotNull(strJsonResponse)) {
                    setStatus(STATUS_CODE_NWK_FAIL_INT);
                    return false;
                }

                String strStatusCode = ParserUtils.getStringByTag(TAG_SERVER_RESPONSE_STATUS_CODE, strJsonResponse);

                if(StringTool.checkStringNotNull(strStatusCode)) {
                    if (strStatusCode.equals(STATUS_CODE_SUCCESS)) {
                        setStatus(Integer.parseInt(strStatusCode));
                        setData(ParserUtils.getPersonByJson(strJsonResponse));
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
            public void onSuccess(Person person) {
                mCb.onLoginSuccess(person);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onLoginFailure(iStatusCode);
            }
        });
    }

    public void execute(Person person) {
        task.execute(person);
    }
}
