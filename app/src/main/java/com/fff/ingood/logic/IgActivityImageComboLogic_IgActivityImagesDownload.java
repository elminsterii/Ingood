package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.task.wrapper.IgActivityImageDownloadTaskWrapper;
import com.fff.ingood.task.wrapper.IgActivityImageGetListTaskWrapper;
import com.fff.ingood.tools.StringTool;

import java.util.ArrayList;
import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_FILE_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class IgActivityImageComboLogic_IgActivityImagesDownload extends Logic implements
        IgActivityImageDownloadTaskWrapper.IgActivityImageDownloadTaskWrapperCallback
        , IgActivityImageGetListTaskWrapper.IgActivityImageGetListTaskWrapperCallback {

    public interface IgActivityImagesDownloadLogicCaller extends LogicCaller {
        void returnIgActivityImages(List<Bitmap> bmIgActivityImages);
        void returnStatus(Integer iStatusCode);
    }

    private IgActivityImagesDownloadLogicCaller mCaller;
    private String m_strIgActivityId;

    private List<String> m_lsIgActivityImagesName;
    private List<Bitmap> m_lsIgActivityImages;

    IgActivityImageComboLogic_IgActivityImagesDownload(IgActivityImagesDownloadLogicCaller caller, String strIgActivityId) {
        super(caller);
        mCaller = caller;
        m_strIgActivityId = strIgActivityId;
    }

    @Override
    protected void doLogic() {
        IgActivityImageGetListTaskWrapper task = new IgActivityImageGetListTaskWrapper(this);
        task.execute(m_strIgActivityId);
    }

    @Override
    public void onIgActivityImageDownloadSuccess(Bitmap bmIgActivityImage) {
        m_lsIgActivityImages.add(bmIgActivityImage);

        if(m_lsIgActivityImages.size() >= m_lsIgActivityImagesName.size()) {
            mCaller.returnIgActivityImages(m_lsIgActivityImages);
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        }
    }

    @Override
    public void onIgActivityImageDownloadFailure(Integer iStatusCode) {
        mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
    }

    @Override
    public void onGetIgActivitiesImageListSuccess(String strIgActivityImagesName) {
        m_lsIgActivityImagesName = StringTool.arrayStringToListString(strIgActivityImagesName.split(","));
        if(m_lsIgActivityImagesName.size() > 0) {
            m_lsIgActivityImages = new ArrayList<>();

            for(String strImageName : m_lsIgActivityImagesName) {
                IgActivityImageDownloadTaskWrapper task = new IgActivityImageDownloadTaskWrapper(this);
                task.execute(strImageName);
            }
        }
    }

    @Override
    public void onGetIgActivitiesImageListFailure(Integer iStatusCode) {
        mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
    }
}
