package com.fff.ingood.logic;

import android.graphics.Bitmap;

import com.fff.ingood.task.wrapper.PersonIconDownloadTaskWrapper;

import static com.fff.ingood.global.ServerResponse.STATUS_CODE_FAIL_FILE_NOT_FOUND_INT;
import static com.fff.ingood.global.ServerResponse.STATUS_CODE_SUCCESS_INT;

/**
 * Created by ElminsterII on 2018/6/8.3
 */
public class PersonIconDownloadLogic extends Logic implements PersonIconDownloadTaskWrapper.PersonIconDownloadTaskWrapperCallback {

    public interface PersonIconDownloadLogicCaller extends LogicCaller {
        void returnPersonIcon(Bitmap bmPersonIcon);
        void returnStatus(Integer iStatusCode);
    }

    private PersonIconDownloadLogicCaller mCaller;
    private String m_strIconName;

    PersonIconDownloadLogic(PersonIconDownloadLogicCaller caller, String strIconName) {
        super(caller);
        mCaller = caller;
        m_strIconName = strIconName;
    }

    @Override
    protected void doLogic() {
        PersonIconDownloadTaskWrapper task = new PersonIconDownloadTaskWrapper(this);
        task.execute(m_strIconName);
    }

    @Override
    public void onPersonIconDownloadSuccess(Bitmap bitmap) {
        if(bitmap != null) {
            mCaller.returnPersonIcon(bitmap);
            mCaller.returnStatus(STATUS_CODE_SUCCESS_INT);
        } else {
            mCaller.returnStatus(STATUS_CODE_FAIL_FILE_NOT_FOUND_INT);
        }
    }

    @Override
    public void onPersonIconDownloadFailure(Integer iStatusCode) {
        mCaller.returnStatus(iStatusCode);
    }
}
