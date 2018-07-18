package com.fff.ingood.task.wrapper;

import android.graphics.Bitmap;

import com.fff.ingood.task.AsyncHttpStreamResponder;
import com.fff.ingood.task.PersonIconDownloadTask;

public class PersonIconDownloadTaskWrapper {

    public interface PersonIconDownloadTaskWrapperCallback {
        void onPersonIconDownloadSuccess(Bitmap bitmap);
        void onPersonIconDownloadFailure(Integer iStatusCode);
    }

    private PersonIconDownloadTask task;
    private PersonIconDownloadTaskWrapperCallback mCb;

    public PersonIconDownloadTaskWrapper(PersonIconDownloadTaskWrapperCallback cb) {
        mCb = cb;
        task = new PersonIconDownloadTask(new AsyncHttpStreamResponder<Integer, Bitmap>() {
            @Override
            public void onSuccess(Bitmap bmPersonIcon) {
                mCb.onPersonIconDownloadSuccess(bmPersonIcon);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onPersonIconDownloadFailure(iStatusCode);
            }
        });
    }

    public void execute(String strIconName) {
        task.execute(strIconName);
    }
}