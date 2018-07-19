package com.fff.ingood.task.wrapper;

import android.graphics.Bitmap;

import com.fff.ingood.task.AsyncHttpStreamResponder;
import com.fff.ingood.task.IgActivityImageDownloadTask;


public class IgActivityImageDownloadTaskWrapper {

    public interface IgAcitivityDownloadTaskWrapperCallback {
        void onIgActivityImageDownloadSuccess(Bitmap bitmap);
        void onIgActivityImageDownloadFailure(Integer iStatusCode);
    }

    private IgActivityImageDownloadTask task;
    private IgAcitivityDownloadTaskWrapperCallback mCb;

    public IgActivityImageDownloadTaskWrapper(IgAcitivityDownloadTaskWrapperCallback cb) {
        mCb = cb;
        task = new IgActivityImageDownloadTask(new AsyncHttpStreamResponder<Integer, Bitmap>() {
            @Override
            public void onSuccess(Bitmap bmPersonIcon) {
                mCb.onIgActivityImageDownloadSuccess(bmPersonIcon);
            }

            @Override
            public void onFailure(Integer iStatusCode) {
                mCb.onIgActivityImageDownloadFailure(iStatusCode);
            }
        });
    }

    public void execute(String strIconName) {
        task.execute(strIconName);
    }
}