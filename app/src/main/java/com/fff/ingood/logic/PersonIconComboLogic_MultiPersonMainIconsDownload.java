package com.fff.ingood.logic;

import android.graphics.Bitmap;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_FILE_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class PersonIconComboLogic_MultiPersonMainIconsDownload extends Logic implements
        PersonIconComboLogic_PersonMainIconDownload.PersonMainIconDownloadLogicCaller{

    public interface MultiPersonMainIconsDownloadLogicCaller extends LogicCaller {
        void returnPersonMainIcons(Bitmap[] arrPersonMainIcons);
        void returnStatus(Integer iStatusCode);
    }

    private MultiPersonMainIconsDownloadLogicCaller mCaller;
    private String[] m_arrEmailsOrIds;
    private Bitmap[] m_arrPersonsIcons;

    private int m_curIndex;

    PersonIconComboLogic_MultiPersonMainIconsDownload(MultiPersonMainIconsDownloadLogicCaller caller, String strEmailsOrIds) {
        super(caller);
        mCaller = caller;
        m_arrEmailsOrIds = strEmailsOrIds.split(",");
        m_arrPersonsIcons = new Bitmap[m_arrEmailsOrIds.length];
        m_curIndex = 0;
    }

    @Override
    protected void doLogic() {
        if(!queryNextPersonMainIcon())
            mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
    }

    @Override
    public void returnPersonMainIcon(Bitmap bmPersonIcon) {
        m_arrPersonsIcons[--m_curIndex] = bmPersonIcon;

        if(!queryNextPersonMainIcon()) {
            mCaller.returnPersonMainIcons(m_arrPersonsIcons);
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        }
    }

    @Override
    public void returnStatus(Integer iStatusCode) {

    }

    private boolean queryNextPersonMainIcon() {
        if(m_curIndex >= m_arrEmailsOrIds.length)
            return false;

        PersonIconComboLogic_PersonMainIconDownload logic = new PersonIconComboLogic_PersonMainIconDownload(this, m_arrEmailsOrIds[m_curIndex]);
        logic.doLogic();
        m_curIndex++;
        return true;
    }
}
