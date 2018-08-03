package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.task.wrapper.IgActivityAttendTaskWrapper;
import com.fff.ingood.task.wrapper.IgActivityDeemTaskWrapper;

import java.util.List;

/**
 * Created by ElminsterII on 2018/5/27.
 */
public class IgActivityLogicExecutor {

    public void doCreateIgActivity(IgActivityCreateLogic.IgActivityCreateLogicCaller caller, IgActivity activity) {
        Logic fl = new IgActivityCreateLogic(caller, activity);
        fl.doLogic();
    }

    public void doDeleteIgActivity(IgActivityDeleteLogic.IgActivityDeleteLogicCaller caller, String strIgActivityId
            , String strPublisherEmail
            , String strPublisherPassword) {
        Logic fl = new IgActivityDeleteLogic(caller, strIgActivityId, strPublisherEmail, strPublisherPassword);
        fl.doLogic();
    }

    public void doUpdateIgActivity(IgActivityUpdateLogic.IgActivityUpdateLogicCaller caller, IgActivity activity) {
        Logic fl = new IgActivityUpdateLogic(caller, activity);
        fl.doLogic();
    }

    public void doRepublishIgActivity(IgActivityRepublishLogic.IgActivityRepublishLogicCaller caller, IgActivity activity) {
        Logic fl = new IgActivityRepublishLogic(caller, activity);
        fl.doLogic();
    }

    public void doSearchIgActivitiesIds(IgActivityQueryLogic.IgActivityQueryLogicCaller caller, IgActivity activity) {
        Logic fl = new IgActivityQueryLogic(caller, activity);
        fl.doLogic();
    }

    public void doGetIgActivitiesData(IgActivityQueryLogic.IgActivityQueryLogicCaller caller, String strIds) {
        Logic fl = new IgActivityQueryLogic(caller, strIds);
        fl.doLogic();
    }

    public void doDeemIgActivity(IgActivityDeemLogic.IgActivityDeemLogicCaller caller, String strEmail, String strPassword, String strActivityId
            , IgActivityDeemTaskWrapper.DEEM_VALUE dvDeem, boolean bIsDeemRollBack) {
        Logic fl = new IgActivityDeemLogic(caller, strEmail, strPassword, strActivityId, dvDeem, bIsDeemRollBack);
        fl.doLogic();
    }

    public void doAttendIgActivity(IgActivityAttendLogic.IgActivityAttendLogicCaller caller, String strId, String strEmail
            , String strPassword, String strActivityId, IgActivityAttendTaskWrapper.ATTEND_VALUE avAttend) {
        Logic fl = new IgActivityAttendLogic(caller, strId, strEmail, strPassword, strActivityId, avAttend);
        fl.doLogic();
    }

    public void doIgActivityImageGetList(IgActivityImageGetListLogic.IgActivityImageGetListLogicCaller caller, String strIgActivityId) {
        Logic fl = new IgActivityImageGetListLogic(caller, strIgActivityId);
        fl.doLogic();
    }

    public void doIgActivityImageDelete(IgActivityImageDeleteLogic.IgActivityImageDeleteLogicCaller caller
            , String strEmail, String strPassword, String strIgActivityId, List<String> lsImagesName) {
        Logic fl = new IgActivityImageDeleteLogic(caller, strEmail, strPassword, strIgActivityId, lsImagesName);
        fl.doLogic();
    }

    public void doIgActivityImageDownload(IgActivityImageDownloadLogic.IgActivityImageDownloadLogicCaller caller, String strImagesName) {
        Logic fl = new IgActivityImageDownloadLogic(caller, strImagesName);
        fl.doLogic();
    }

    public void doIgActivityImageUpload(IgActivityImageUploadLogic.IgActivityImageUploadLogicCaller caller
             , String strIgActivityId, String strImagesName, Bitmap bmUploadImage) {
        Logic fl = new IgActivityImageUploadLogic(caller, strIgActivityId, strImagesName, bmUploadImage);
        fl.doLogic();
    }

    public void doIgActivityImagesUploadAll(IgActivityImagesUploadLogic.IgActivityImagesUploadLogicCaller caller
            , String strIgActivityId, List<Bitmap> lsUploadImage) {
        Logic fl = new IgActivityImagesUploadLogic(caller, strIgActivityId, lsUploadImage);
        fl.doLogic();
    }

    public void doIgActivityImagesDownloadAll(IgActivityImageComboLogic_IgActivityImagesDownload.IgActivityImagesDownloadLogicCaller caller
            , String strIgActivityId) {
        Logic fl = new IgActivityImageComboLogic_IgActivityImagesDownload(caller, strIgActivityId);
        fl.doLogic();
    }

    public void doIgActivityMainImageDownload(IgActivityImageComboLogic_IgActivityMainImageDownload.IgActivityMainImageDownloadLogicCaller caller
            , String strIgActivityId) {
        Logic fl = new IgActivityImageComboLogic_IgActivityMainImageDownload(caller, strIgActivityId);
        fl.doLogic();
    }

    public void doIgActivityMainImageDownload(IgActivityImageComboLogic_IgActivityMainImageDownload.IgActivityMainImageDownloadLogicCaller caller
            , String strIgActivityId
            , String strTag) {
        Logic fl = new IgActivityImageComboLogic_IgActivityMainImageDownload(caller, strIgActivityId, strTag);
        fl.doLogic();
    }
}
