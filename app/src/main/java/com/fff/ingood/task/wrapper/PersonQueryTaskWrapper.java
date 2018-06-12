package com.fff.ingood.task.wrapper;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.AsyncResponder;
import com.fff.ingood.task.PersonQueryTask;
import com.fff.ingood.tools.ParserUtils;
import com.fff.ingood.tools.StringTool;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NWK_FAIL_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_PARSING_ERROR;
import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_STATUS_CODE;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class PersonQueryTaskWrapper {

    public interface PersonQueryTaskWrapperCallback {
        void onSuccess(List<Person> lsPersons);
        void onFailure(Integer iStatusCode);
    }

    private PersonQueryTask task;
    private PersonQueryTaskWrapperCallback mCb;

    public PersonQueryTaskWrapper(PersonQueryTaskWrapperCallback cb) {
        mCb = cb;
        task = new PersonQueryTask(new AsyncResponder<Integer, List<Person>>() {
            @Override
            public boolean parseResponse(String strJsonResponse) {
                if(!StringTool.checkStringNotNull(strJsonResponse)) {
                    setStatus(STATUS_CODE_NWK_FAIL_INT);
                    return false;
                }

                String strStatusCode = ParserUtils.getStringByTag(TAG_SERVER_RESPONSE_STATUS_CODE, strJsonResponse);

                if(StringTool.checkStringNotNull(strStatusCode)) {
                    setStatus(Integer.parseInt(strStatusCode));
                    setData(ParserUtils.getPersonsByJson(strJsonResponse));
                    return true;
                } else {
                    setStatus(STATUS_CODE_PARSING_ERROR);
                    return false;
                }
            }

            @Override
            public void onSuccess(List<Person> lsPersons) {
                mCb.onSuccess(lsPersons);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onFailure(iStatusCode);
            }
        });
    }

    public void execute(String strIds) {
        task.execute(strIds);
    }
}
