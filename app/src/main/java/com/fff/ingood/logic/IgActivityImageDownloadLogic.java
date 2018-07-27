package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.task.wrapper.IgActivityImageDownloadTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_FILE_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class IgActivityImageDownloadLogic extends Logic
        implements IgActivityImageDownloadTaskWrapper.IgActivityDownloadTaskWrapperCallback {

    public interface IgActivityImageDownloadLogicCaller extends LogicCaller {
        void returnIgActivityImage(Bitmap bmIgActivityImage);
        void returnStatus(Integer iStatusCode);
    }

    private IgActivityImageDownloadLogicCaller mCaller;
    private String m_strImageName;

    IgActivityImageDownloadLogic(IgActivityImageDownloadLogicCaller caller, String strImageName) {
        super(caller);
        mCaller = caller;
        m_strImageName = strImageName;
    }

    @Override
    protected void doLogic() {
        IgActivityImageDownloadTaskWrapper task = new IgActivityImageDownloadTaskWrapper(this);
        task.execute(m_strImageName);
    }

    @Override
    public void onIgActivityImageDownloadSuccess(Bitmap bitmap) {
        if(bitmap != null) {
            mCaller.returnIgActivityImage(bitmap);
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        } else {
            mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
        }
    }

    @Override
    public void onIgActivityImageDownloadFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
