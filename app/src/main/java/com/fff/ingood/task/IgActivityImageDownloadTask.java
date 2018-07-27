package com.fff.ingood.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;

public class IgActivityImageDownloadTask extends HttpDownloadTask<String, Integer, Bitmap> {

    public IgActivityImageDownloadTask(AsyncHttpDownloadResponder<Integer, Bitmap> responder) {
        super(responder);
    }

    @Override
    protected Bitmap access(String strIgActivityIdAndIconName) {

        Bitmap bitmap;
        String strURL = String.valueOf(HttpProxy.HTTP_API_ACTIVITY_IMAGE_ACCESS);
        strURL += "/";
        strURL += strIgActivityIdAndIconName;

        try {
            URL url = new URL(strURL);
            InputStream input = url.openStream();
            bitmap = BitmapFactory.decodeStream(input);
            input.close();

            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}