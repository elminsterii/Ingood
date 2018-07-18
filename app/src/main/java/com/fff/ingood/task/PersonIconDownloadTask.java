package com.fff.ingood.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.net.URL;

public class PersonIconDownloadTask extends HttpStreamTask<String, Integer, Bitmap> {

    public PersonIconDownloadTask(AsyncHttpStreamResponder<Integer, Bitmap> responder) {
        super(responder);
    }

    @Override
    protected Bitmap access(String strEmailAndIcon) {

        Bitmap bitmap;
        String strURL = String.valueOf(HttpProxy.HTTP_GET_API_PERSON_ICON_ACCESS);
        strURL += "/";
        strURL += strEmailAndIcon;

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