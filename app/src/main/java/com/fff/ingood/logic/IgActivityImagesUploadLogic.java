package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.task.wrapper.IgActivityImageUploadTaskWrapper;

import java.util.List;

import static com.fff.ingood.global.GlobalProperty.ARRAY_IGACTIVITY_IMAGE_NAMES;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class IgActivityImagesUploadLogic extends Logic
        implements IgActivityImageUploadTaskWrapper.IgActivityImageUploadTaskWrapperCallback {

    public interface IgActivityImagesUploadLogicCaller extends LogicCaller {
        void returnUploadIgActivityImagesSuccess(int iUploadCount);
        void returnStatus(Integer iStatusCode);
    }

    private IgActivityImagesUploadLogicCaller mCaller;
    private String m_strIgActivityId;
    private List<Bitmap> m_lsUploadImage;
    private int m_curIndex;

    IgActivityImagesUploadLogic(IgActivityImagesUploadLogicCaller caller, String strIgActivityId, List<Bitmap> lsUploadImage) {
        super(caller);
        mCaller = caller;
        m_strIgActivityId = strIgActivityId;
        m_lsUploadImage = lsUploadImage;
        m_curIndex = 0;
    }

    @Override
    protected void doLogic() {
        if(m_lsUploadImage != null && m_lsUploadImage.size() > 0) {
            uploadImage(m_lsUploadImage.get(m_curIndex), m_strIgActivityId, ARRAY_IGACTIVITY_IMAGE_NAMES[m_curIndex]);
            m_curIndex++;
        }
    }

    @Override
    public void onIgActivityImageUploadSuccess() {
        if(m_curIndex < m_lsUploadImage.size()) {
            uploadImage(m_lsUploadImage.get(m_curIndex), m_strIgActivityId, ARRAY_IGACTIVITY_IMAGE_NAMES[m_curIndex]);
            m_curIndex++;
        } else {
            mCaller.returnUploadIgActivityImagesSuccess(m_curIndex++);
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        }
    }

    @Override
    public void onIgActivityImageUploadFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }

    private void uploadImage(Bitmap bmImage, String strIgActivityId, String strImageName) {
        IgActivityImageUploadTaskWrapper task = new IgActivityImageUploadTaskWrapper(this, bmImage);
        task.execute(strIgActivityId, strImageName);
    }
}
