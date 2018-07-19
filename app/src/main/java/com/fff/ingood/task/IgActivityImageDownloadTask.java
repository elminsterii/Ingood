package com.fff.ingood.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;

public class IgActivityImageDownloadTask extends HttpStreamTask<String, Integer, Bitmap> {

    public IgActivityImageDownloadTask(AsyncHttpStreamResponder<Integer, Bitmap> responder) {
        super(responder);
    }

    @Override
    protected Bitmap access(String strActIdAndIcon) {

        Bitmap bitmap;
        String strURL = String.valueOf(HttpProxy.HTTP_GET_API_ACTIVITY_IMAGE);
        strURL += "/";
        strURL += strActIdAndIcon;

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