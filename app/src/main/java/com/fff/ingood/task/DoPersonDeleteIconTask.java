package com.fff.ingood.task;

import android.app.Activity;
import android.graphics.Bitmap;

import com.fff.ingood.data.Person;
import com.fff.ingood.tools.JsonUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DoPersonDeleteIconTask <Object> extends HttpPostAbstractTask<Object> {
    public DoPersonDeleteIconTask(Activity activity, AsyncResponder<String> responder) {
        super(activity,responder);
    }
    @Override
    protected String access(Activity activity, Object info) {
        {
            boolean result = false;
            URL url;
            BufferedReader reader = null;
            StringBuilder stringBuilder;
            String jsonString;
            if(info instanceof String){
                jsonString = (String)info;
            }
            else if(info instanceof Person){
                jsonString = new Gson().toJson(info, Person.class);
            }
            else {
                jsonString = JsonUtils.createJsonString(info);
            }
            try
            {
                // create the HttpURLConnection
                url = new URL(String.valueOf(HttpProxy.HTTP_DELETE_API_PERSON_ICON));
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // 使用甚麼方法做連線
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
                connection.setReadTimeout(10000);
                connection.setDoInput(true);                                                        //允許輸入流，即允許下載
                connection.setDoOutput(true);                                                       //允許輸出流，即允許上傳
                connection.setUseCaches(false);                                                     //設置是否使用緩存
                connection.connect();

                // output body
                OutputStream os = connection.getOutputStream();
                DataOutputStream writer = new DataOutputStream(os);
                writer.write(jsonString.getBytes());
                writer.flush();
                writer.close();
                os.close();

                //response body
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                stringBuilder = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    stringBuilder.append(line + "\n");
                }
                return stringBuilder.toString();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                // close the reader; this can throw an exception too, so
                // wrap it in another try/catch block.
                if (reader != null)
                {
                    try
                    {
                        reader.close();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return  null;
        }
    }
}
