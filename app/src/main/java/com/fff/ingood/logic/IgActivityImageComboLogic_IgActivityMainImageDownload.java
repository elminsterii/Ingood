package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.task.wrapper.IgActivityImageDownloadTaskWrapper;
import com.fff.ingood.task.wrapper.IgActivityImageGetListTaskWrapper;
import com.fff.ingood.tools.StringTool;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_FILE_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class IgActivityImageComboLogic_IgActivityMainImageDownload extends Logic implements
        IgActivityImageDownloadTaskWrapper.IgActivityImageDownloadTaskWrapperCallback
        , IgActivityImageGetListTaskWrapper.IgActivityImageGetListTaskWrapperCallback {

    public interface IgActivityMainImageDownloadLogicCaller extends LogicCaller {
        void returnIgActivityMainImage(Bitmap bmIgActivityImage, String strTag);
        void returnStatus(Integer iStatusCode);
    }

    private IgActivityMainImageDownloadLogicCaller mCaller;
    private String m_strIgActivityId;

    IgActivityImageComboLogic_IgActivityMainImageDownload(IgActivityMainImageDownloadLogicCaller caller, String strIgActivityId) {
        super(caller);
        mCaller = caller;
        m_strIgActivityId = strIgActivityId;
    }

    IgActivityImageComboLogic_IgActivityMainImageDownload(IgActivityMainImageDownloadLogicCaller caller, String strIgActivityId, String strTag) {
        super(caller, strTag);
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
        if(bmIgActivityImage != null) {
            mCaller.returnIgActivityMainImage(bmIgActivityImage, getTag());
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        } else {
            mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
        }
    }

    @Override
    public void onIgActivityImageDownloadFailure(Integer iStatusCode) {
        mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
    }

    @Override
    public void onGetIgActivitiesImageListSuccess(String strIgActivityImagesName) {
        List<String> lsImagesName = StringTool.arrayStringToListString(strIgActivityImagesName.split(","));
        if(lsImagesName.size() > 0) {
            IgActivityImageDownloadTaskWrapper task = new IgActivityImageDownloadTaskWrapper(this);
            task.execute(lsImagesName.get(0));
        }
    }

    @Override
    public void onGetIgActivitiesImageListFailure(Integer iStatusCode) {
        mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
    }
}
