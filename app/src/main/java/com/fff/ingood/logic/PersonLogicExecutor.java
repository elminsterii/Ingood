package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.data.Person;
import com.fff.ingood.task.wrapper.PersonSaveIgActivityTaskWrapper;

import java.util.List;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class PersonLogicExecutor {

    public void doPersonLogin(PersonLoginLogic.PersonLoginLogicCaller caller, Person person) {
        Logic fl = new PersonLoginLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonLogout(PersonLogoutLogic.PersonLogoutLogicCaller caller) {
        Logic fl = new PersonLogoutLogic(caller);
        fl.doLogic();
    }

    public void doPersonRegister(PersonRegisterLogic.PersonRegisterLogicCaller caller, Person personNew) {
        Logic fl = new PersonRegisterLogic(caller, personNew);
        fl.doLogic();
    }

    public void doPersonUnregister(PersonUnregisterLogic.PersonUnregisterLogicCaller caller, Person person) {
        Logic fl = new PersonUnregisterLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonUpdate(PersonUpdateLogic.PersonUpdateLogicCaller caller, Person person) {
        Logic fl = new PersonUpdateLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonVerify(PersonVerifyLogic.PersonVerifyLogicCaller caller, Person person) {
        Logic fl = new PersonVerifyLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonCheckExist(PersonCheckExistLogic.PersonCheckExistLogicCaller caller, Person person) {
        Logic fl = new PersonCheckExistLogic(caller, person);
        fl.doLogic();
    }

    public void doPersonQuery(PersonQueryLogic.PersonQueryLogicCaller caller, String strPersonPrimaryKey, boolean bByEmail) {
        Logic fl = new PersonQueryLogic(caller, strPersonPrimaryKey, bByEmail);
        fl.doLogic();
    }

    public void doSaveIgActivity(PersonSaveIgActivityLogic.PersonSaveIgActivityLogicCaller caller, String strEmail, String strActivityId
            , PersonSaveIgActivityTaskWrapper.SAVE_ACT_VALUE svValue) {
        Logic fl = new PersonSaveIgActivityLogic(caller, strEmail, strActivityId, svValue);
        fl.doLogic();
    }

    public void doPersonIconsGetList(PersonIconGetListLogic.PersonIconGetListLogicCaller caller, String strEmailOrId) {
        Logic fl = new PersonIconGetListLogic(caller, strEmailOrId);
        fl.doLogic();
    }

    public void doPersonIconsDelete(PersonIconDeleteLogic.PersonIconDeleteLogicCaller caller
            , String strEmail, String strPassword, List<String> lsIconsName) {
        Logic fl = new PersonIconDeleteLogic(caller, strEmail, strPassword, lsIconsName);
        fl.doLogic();
    }

    public void doPersonIconDownload(PersonIconDownloadLogic.PersonIconDownloadLogicCaller caller, String strIconName) {
        Logic fl = new PersonIconDownloadLogic(caller, strIconName);
        fl.doLogic();
    }

    public void doPersonIconUpload(PersonIconUploadLogic.PersonIconUploadLogicCaller caller, String strIconName, Bitmap bmUploadIcon) {
        Logic fl = new PersonIconUploadLogic(caller, strIconName, bmUploadIcon);
        fl.doLogic();
    }

    public void doPersonMainIconDownload(PersonIconComboLogic_PersonMainIconDownload.PersonMainIconDownloadLogicCaller caller, String strEmail) {
        Logic fl = new PersonIconComboLogic_PersonMainIconDownload(caller, strEmail);
        fl.doLogic();
    }

    public void doMultiPersonMainIconsDownload(PersonIconComboLogic_MultiPersonMainIconsDownload.MultiPersonMainIconsDownloadLogicCaller caller, String strEmailsOrIds) {
        Logic fl = new PersonIconComboLogic_MultiPersonMainIconsDownload(caller, strEmailsOrIds);
        fl.doLogic();
    }

    public void doMultiPersonMainIconsDownload(PersonIconComboLogic_MultiPersonMainIconsDownload.MultiPersonMainIconsDownloadLogicCaller caller
            , String strEmailsOrIds
            , String strTag) {
        Logic fl = new PersonIconComboLogic_MultiPersonMainIconsDownload(caller, strEmailsOrIds, strTag);
        fl.doLogic();
    }
}
