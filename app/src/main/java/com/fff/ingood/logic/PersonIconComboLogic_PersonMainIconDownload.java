package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.task.wrapper.PersonIconDownloadTaskWrapper;
import com.fff.ingood.task.wrapper.PersonIconGetListTaskWrapper;
import com.fff.ingood.tools.StringTool;

import java.util.List;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_FILE_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class PersonIconComboLogic_PersonMainIconDownload extends Logic implements
        PersonIconDownloadTaskWrapper.PersonIconDownloadTaskWrapperCallback
        , PersonIconGetListTaskWrapper.PersonIconGetListTaskWrapperCallback {

    public interface PersonMainIconDownloadLogicCaller extends LogicCaller {
        void returnPersonMainIcon(Bitmap bmPersonIcon);
        void returnStatus(Integer iStatusCode);
    }

    private PersonMainIconDownloadLogicCaller mCaller;
    private String m_strEmailOrId;

    PersonIconComboLogic_PersonMainIconDownload(PersonMainIconDownloadLogicCaller caller, String strEmailOrId) {
        super(caller);
        mCaller = caller;
        m_strEmailOrId = strEmailOrId;
    }

    @Override
    protected void doLogic() {
        PersonIconGetListTaskWrapper task = new PersonIconGetListTaskWrapper(this);
        task.execute(m_strEmailOrId);
    }

    @Override
    public void onPersonIconDownloadSuccess(Bitmap bitmap) {
        if(bitmap != null) {
            mCaller.returnPersonMainIcon(bitmap);
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        } else {
            mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
        }
    }

    @Override
    public void onPersonIconDownloadFailure(Integer iStatusCode) {
        //do nothing
    }

    @Override
    public void onGetIconListSuccess(String strIconsName) {
        List<String> lsIconsName = StringTool.arrayStringToListString(strIconsName.split(","));
        if(lsIconsName.size() > 0) {
            PersonIconDownloadTaskWrapper task = new PersonIconDownloadTaskWrapper(this);
            task.execute(lsIconsName.get(0));
        }
    }

    @Override
    public void onGetIconListFailure(Integer iStatusCode) {
        //do nothing
    }
}
