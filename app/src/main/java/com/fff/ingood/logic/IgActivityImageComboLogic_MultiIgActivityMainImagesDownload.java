package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_FILE_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class IgActivityImageComboLogic_MultiIgActivityMainImagesDownload extends Logic implements
        IgActivityImageComboLogic_IgActivityMainImageDownload.IgActivityMainImageDownloadLogicCaller{

    public interface MultiIgActivityMainImagesDownloadLogicCaller extends LogicCaller {
        void returnIgActivityMainImages(Bitmap[] arrIgActivityMainImages, String strTag);
        void returnStatus(Integer iStatusCode);
    }

    private MultiIgActivityMainImagesDownloadLogicCaller mCaller;
    private String[] m_arrIgActivitiesId;
    private Bitmap[] m_arrIgActivitiesImage;

    private int m_curIndex;

    IgActivityImageComboLogic_MultiIgActivityMainImagesDownload(MultiIgActivityMainImagesDownloadLogicCaller caller, String strIgActivitiesId) {
        super(caller);
        initialize(caller, strIgActivitiesId);
    }

    IgActivityImageComboLogic_MultiIgActivityMainImagesDownload(MultiIgActivityMainImagesDownloadLogicCaller caller
            , String strIgActivitiesId, String strTag) {
        super(caller, strTag);
        initialize(caller, strIgActivitiesId);
    }

    private void initialize(MultiIgActivityMainImagesDownloadLogicCaller caller, String strIgActivitiesId) {
        mCaller = caller;

        if(StringTool.checkStringNotNull(strIgActivitiesId)) {
            m_arrIgActivitiesId = strIgActivitiesId.split(",");
            m_arrIgActivitiesImage = new Bitmap[m_arrIgActivitiesId.length];
        }
        m_curIndex = 0;
    }

    @Override
    protected void doLogic() {
        queryNextIgActivityMainImage();
    }

    @Override
    public void returnIgActivityMainImage(Bitmap bmIgActivityImage) {
        if(m_arrIgActivitiesImage == null)
            return;

        m_arrIgActivitiesImage[(m_curIndex-1)] = bmIgActivityImage;
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        if(!queryNextIgActivityMainImage()) {
            mCaller.returnIgActivityMainImages(m_arrIgActivitiesImage, getTag());
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        } else {
            mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
        }
    }

    private boolean queryNextIgActivityMainImage() {
        if(m_arrIgActivitiesId == null
                || m_curIndex >= m_arrIgActivitiesId.length)
            return false;

        IgActivityImageComboLogic_IgActivityMainImageDownload logic = new IgActivityImageComboLogic_IgActivityMainImageDownload(this, m_arrIgActivitiesId[m_curIndex]);
        logic.doLogic();
        m_curIndex++;
        return true;
    }
}
