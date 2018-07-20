package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.tools.StringTool;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class PersonIconComboLogic_MultiPersonMainIconsDownload extends Logic implements
        PersonIconComboLogic_PersonMainIconDownload.PersonMainIconDownloadLogicCaller{

    public interface MultiPersonMainIconsDownloadLogicCaller extends LogicCaller {
        void returnPersonMainIcons(Bitmap[] arrPersonMainIcons, String strTag);
        void returnStatus(Integer iStatusCode);
    }

    private MultiPersonMainIconsDownloadLogicCaller mCaller;
    private String[] m_arrEmailsOrIds;
    private Bitmap[] m_arrPersonsIcons;

    private int m_curIndex;

    PersonIconComboLogic_MultiPersonMainIconsDownload(MultiPersonMainIconsDownloadLogicCaller caller, String strEmailsOrIds) {
        super(caller);
        initialize(caller, strEmailsOrIds);
    }

    PersonIconComboLogic_MultiPersonMainIconsDownload(MultiPersonMainIconsDownloadLogicCaller caller
            , String strEmailsOrIds, String strTag) {
        super(caller, strTag);
        initialize(caller, strEmailsOrIds);
    }

    private void initialize(MultiPersonMainIconsDownloadLogicCaller caller, String strEmailsOrIds) {
        mCaller = caller;

        if(StringTool.checkStringNotNull(strEmailsOrIds)) {
            m_arrEmailsOrIds = strEmailsOrIds.split(",");
            m_arrPersonsIcons = new Bitmap[m_arrEmailsOrIds.length];
        }
        m_curIndex = 0;
    }

    @Override
    protected void doLogic() {
        queryNextPersonMainIcon();
    }

    @Override
    public void returnPersonMainIcon(Bitmap bmPersonIcon) {
        if(m_arrPersonsIcons == null)
            return;

        m_arrPersonsIcons[(m_curIndex-1)] = bmPersonIcon;

        if(!queryNextPersonMainIcon()) {
            mCaller.returnPersonMainIcons(m_arrPersonsIcons, getTag());
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        }
    }

    @Override
    public void returnStatus(Integer iStatusCode) {
        //do nothing
    }

    private boolean queryNextPersonMainIcon() {
        if(m_arrEmailsOrIds == null
                || m_curIndex >= m_arrEmailsOrIds.length)
            return false;

        PersonIconComboLogic_PersonMainIconDownload logic = new PersonIconComboLogic_PersonMainIconDownload(this, m_arrEmailsOrIds[m_curIndex]);
        logic.doLogic();
        m_curIndex++;
        return true;
    }
}
