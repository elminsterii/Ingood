package com.fff.ingood.task;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.fff.ingood.data.Person;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoPersonGetIconTask<Object> extends HttpPostAbstractTask<Object>{
    public DoPersonGetIconTask(Activity activity, AsyncResponder<String> responder) {
        super(activity,responder);
    }
    @Override

    protected String access(Activity activity, Object info){
        byte[] imageByte = null;
        {
            BufferedReader reader = null;
            StringBuilder stringBuilder;
            String account = ((Person)info).getEmail();

            try {

                URL downloadUrl = new URL(String.valueOf(HttpProxy.HTTP_GET_API_PERSON_ACCESS_ICON) + "/"+ account + "/icon01.jpg");
                HttpURLConnection connection = (HttpURLConnection) downloadUrl.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Charset", "UTF_8");
                connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
                connection.setReadTimeout(10000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.connect();


                    /*response body
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    stringBuilder = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        stringBuilder.append(line + "\n");
                    }*/

            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}