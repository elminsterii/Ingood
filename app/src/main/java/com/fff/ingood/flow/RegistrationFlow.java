package com.fff.ingood.flow;

import android.graphics.Bitmap;

import com.fff.ingood.activity.HomeActivity;
import com.fff.ingood.activity.RegistrationFragmentActivity;
import com.fff.ingood.data.Person;
import com.fff.ingood.global.GlobalProperty;
import com.fff.ingood.global.PersonManager;
import com.fff.ingood.logic.PersonIconUploadLogic;
import com.fff.ingood.logic.PersonLogicExecutor;
import com.fff.ingood.logic.PersonLoginLogic;
import com.fff.ingood.logic.PersonRegisterLogic;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class RegistrationFlow extends Flow implements
        PersonRegisterLogic.PersonRegisterLogicCaller
        , PersonLoginLogic.PersonLoginLogicCaller
        , PersonIconUploadLogic.PersonIconUploadLogicCaller {

    private Person mPersonNew;
    private Bitmap m_bmPersonIcon;

    RegistrationFlow(Flow.FlowLogicCaller caller, Person personNew, Bitmap bmPersonIcon) {
        super(caller);
        mPersonNew = personNew;
        m_bmPersonIcon = bmPersonIcon;
    }

    RegistrationFlow(Flow.FlowLogicCaller caller, Person personNew) {
        super(caller);
        mPersonNew = personNew;
    }

    private void uploadPersonIcon(Bitmap bmIcon, String strEmail, String strIconName) {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonIconUpload(this, strEmail, strIconName, bmIcon);
    }

    @Override
    protected FLOW doLogic() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonRegister(this, mPersonNew);

        return FLOW.FL_REGISTRATION;
    }

    @Override
    public void onRegisterSuccess() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonLogin(this, mPersonNew);

        if(m_bmPersonIcon != null)
            uploadPersonIcon(m_bmPersonIcon, mPersonNew.getEmail(), GlobalProperty.ARRAY_PERSON_ICON_NAMES[0]);
    }

    @Override
    public void returnLoginPerson(Person person) {
        mCaller.returnFlow(STATUS_CODE_SUCCESS_INT, FLOW.FL_HOME, HomeActivity.class);
    }

    @Override
    public void returnUploadPersonIconSuccess() {
        PersonManager.getInstance().setPersonIcon(m_bmPersonIcon);
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(!iStatusCode.equals(STATUS_CODE_SUCCESS_INT))
            mCaller.returnFlow(iStatusCode, FLOW.FL_REGISTRATION, RegistrationFragmentActivity.class);
    }
}
