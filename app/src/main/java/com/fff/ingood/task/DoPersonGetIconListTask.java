package com.fff.ingood.task;

import android.app.Activity;
import android.graphics.Bitmap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoPersonGetIconListTask<Object> extends HttpPostAbstractTask<Object>{
    public DoPersonGetIconListTask(Activity activity, AsyncResponder<String> responder) {
        super(activity,responder);
    }
    @Override
    protected String access(Activity activity, Object info) {
        {
            BufferedReader reader = null;
            StringBuilder stringBuilder;
            String account ;
            URL url;
            account = (String)info;

            try {
                url = new URL(String.valueOf(HttpProxy.HTTP_POST_API_PERSON_ACCESS_ICON) + "/"+account+"/" );
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Charset", "UTF_8");
                connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
                connection.setReadTimeout(10000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.connect();

                    //response body
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    stringBuilder = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        stringBuilder.append(line + "\n");
                    }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}