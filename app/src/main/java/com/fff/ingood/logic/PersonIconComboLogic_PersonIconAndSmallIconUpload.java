package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.tools.ImageHelper;

import static com.fff.ingood.global.GlobalProperty.PERSON_ICON_SMALL_HEIGHT;
import static com.fff.ingood.global.GlobalProperty.PERSON_ICON_SMALL_WIDTH;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class PersonIconComboLogic_PersonIconAndSmallIconUpload extends Logic implements PersonIconUploadLogic.PersonIconUploadLogicCaller {

    public interface PersonIconAndSmallIconUploadLogicCaller extends LogicCaller {
        void returnUploadIconAndSmallIconSuccess();
        void returnStatus(Integer iStatusCode);
    }

    private PersonIconAndSmallIconUploadLogicCaller mCaller;
    private String m_strEmail;
    private String m_strIconName;
    private String m_strIconSmallName;
    private Bitmap m_bmUploadIcon;

    private static final String TAG_UPLOAD_ICON = "upload_icon";
    private static final String TAG_UPLOAD_SMALL_ICON = "upload_small_icon";

    PersonIconComboLogic_PersonIconAndSmallIconUpload(PersonIconAndSmallIconUploadLogicCaller caller, String strEmail
            , String strIconName, String strIconSmallName, Bitmap bmUploadIcon) {
        super(caller);
        mCaller = caller;
        m_strEmail = strEmail;
        m_strIconName = strIconName;
        m_strIconSmallName = strIconSmallName;
        m_bmUploadIcon = bmUploadIcon;
    }

    @Override
    protected void doLogic() {
        PersonLogicExecutor executor = new PersonLogicExecutor();
        executor.doPersonIconUpload(this, m_strEmail, m_strIconName, m_bmUploadIcon, TAG_UPLOAD_ICON);
    }

    @Override
    public void returnUploadPersonIconSuccess(String strTag) {
        switch(strTag) {
            case TAG_UPLOAD_ICON :
                Bitmap bmSmallIcon = ImageHelper.resizeBitmap(m_bmUploadIcon, PERSON_ICON_SMALL_WIDTH, PERSON_ICON_SMALL_HEIGHT);
                PersonLogicExecutor executor = new PersonLogicExecutor();
                executor.doPersonIconUpload(this, m_strEmail, m_strIconSmallName, bmSmallIcon, TAG_UPLOAD_SMALL_ICON);
                break;
            case TAG_UPLOAD_SMALL_ICON :
                mCaller.returnUploadIconAndSmallIconSuccess();
                mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
                break;
        }
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
