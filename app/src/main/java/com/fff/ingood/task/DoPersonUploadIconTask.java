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

public class DoPersonUploadIconTask<Object> extends HttpPostAbstractTask<Object>{
    public DoPersonUploadIconTask(Activity activity, AsyncResponder<String> responder) {
        super(activity,responder);
    }
    @Override
    protected String access(Activity activity, Object info) {
        {
            BufferedReader reader = null;
            StringBuilder stringBuilder;
            String list[] =null;
            URL uploadUrl;

            list = ((String)info).split("&");
            File file = new File((list[1]));

            try {
                uploadUrl = new URL(String.valueOf(HttpProxy.HTTP_POST_API_PERSON_ACCESS_ICON) + "/"+list[0]  + "/icon02.jpg");
                HttpURLConnection connection = (HttpURLConnection) uploadUrl.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestProperty("Charset", "UTF_8");
                connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
                connection.setReadTimeout(10000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.connect();

                if (file != null) {

                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    FileInputStream is = new FileInputStream(file);
                    int read = 0;
                    byte[] bytes = new byte[1024000];
                    while ((read = is.read(bytes)) != -1) {
                        out.write(bytes, 0, read);
                    }
                    is.close();
                    out.flush();
                    out.close();

                    //response body
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    stringBuilder = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        stringBuilder.append(line + "\n");
                    }
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return null;
    }
}