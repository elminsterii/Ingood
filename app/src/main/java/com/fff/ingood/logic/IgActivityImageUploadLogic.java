package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.task.wrapper.IgActivityImageUploadTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class IgActivityImageUploadLogic extends Logic
        implements IgActivityImageUploadTaskWrapper.IgActivityImageUploadTaskWrapperCallback {

    public interface IgActivityImageUploadLogicCaller extends LogicCaller {
        void returnUploadIgActivityImageSuccess();
        void returnStatus(Integer iStatusCode);
    }

    private IgActivityImageUploadLogicCaller mCaller;
    private String m_strImageName;
    private Bitmap m_bmUploadImage;

    IgActivityImageUploadLogic(IgActivityImageUploadLogicCaller caller, String strImageName, Bitmap bmUploadImage) {
        super(caller);
        mCaller = caller;
        m_strImageName = strImageName;
        m_bmUploadImage = bmUploadImage;
    }

    @Override
    protected void doLogic() {
        IgActivityImageUploadTaskWrapper task = new IgActivityImageUploadTaskWrapper(this, m_bmUploadImage);
        task.execute(m_strImageName);
    }

    @Override
    public void onIgActivityImageUploadSuccess() {
        mCaller.returnUploadIgActivityImageSuccess();
        mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
    }

    @Override
    public void onIgActivityImageUploadFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
