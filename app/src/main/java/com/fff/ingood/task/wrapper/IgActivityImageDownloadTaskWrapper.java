package com.fff.ingood.task.wrapper;

import android.graphics.Bitmap;

import com.fff.ingood.task.AsyncHttpDownloadResponder;
import com.fff.ingood.task.IgActivityImageDownloadTask;


public class IgActivityImageDownloadTaskWrapper {

    public interface IgActivityImageDownloadTaskWrapperCallback {
        void onIgActivityImageDownloadSuccess(Bitmap bmIgActivityImage);
        void onIgActivityImageDownloadFailure(Integer iStatusCode);
    }

    private IgActivityImageDownloadTask task;
    private IgActivityImageDownloadTaskWrapperCallback mCb;

    public IgActivityImageDownloadTaskWrapper(IgActivityImageDownloadTaskWrapperCallback cb) {
        mCb = cb;
        task = new IgActivityImageDownloadTask(new AsyncHttpDownloadResponder<Integer, Bitmap>() {
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