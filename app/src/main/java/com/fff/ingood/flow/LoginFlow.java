package com.fff.ingood.flow;

import android.graphics.Bitmap;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.LoginActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.logic.PersonIconComboLogic_PersonMainIconDownload;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonLoginLogic;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_FILE_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_UNKNOWN_ERROR_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_NEVER_LOGIN;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class LoginFlow extends Flow implements PersonLoginLogic.PersonLoginLogicCaller
        , PersonIconComboLogic_PersonMainIconDownload.PersonMainIconDownloadLogicCaller {

    private Person mPersonCondition;

    LoginFlow(FlowLogicCaller caller, Person person) {
        super(caller);
        mPersonCondition = person;
    }

    LoginFlow(FlowLogicCaller caller) {
        super(caller);
    }

    @Override
    protected FLOW doLogic() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonLogin(this, mPersonCondition);

        return FLOW.FL_LOGIN;
    }

    private void downloadPersonIcon(String strEmail) {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonMainIconDownload(this, strEmail);
    }

    @Override
    public void returnPersonMainIcon(Bitmap bmPersonIcon) {
        if(bmPersonIcon != null)
            PersonManager.getInstance().setPersonIcon(bmPersonIcon);

        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_HOME, HomeActivity.class);
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if (iStatusCode != null) {
            if (iStatusCode.equals(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT))
                mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_HOME, HomeActivity.class);
            else if(iStatusCode.equals(STATUS_CODE_NEVER_LOGIN))
                mCaller.returnFlow(iStatusCode, FLOW.FL_LOGIN, LoginActivity.class);
            else if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
                mCaller.returnFlow(iStatusCode, FLOW.FL_LOGIN, LoginActivity.class);
        } else
            mCaller.returnFlow(STATUS_CODE_FAIL_UNKNOWN_ERROR_INT, FLOW.FL_LOGIN, LoginActivity.class);
    }

    @Override
    public void returnLoginPerson(Person person) {
        downloadPersonIcon(person.getEmail());
    }
}
