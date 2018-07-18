package com.fff.ingood.task;

import android.graphics.Bitmap;

import com.fff.ingood.tools.ImageHelper;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PersonIconUploadTask extends HttpStreamTask<String, Integer, String> {

    private Bitmap m_bmUpload;

    public PersonIconUploadTask(AsyncHttpStreamResponder<Integer, String> responder, Bitmap bmUpload) {
        super(responder);
        m_bmUpload = bmUpload;
    }

    @Override
    protected String access(String strEmailAndIcon) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(String.valueOf(HttpProxy.HTTP_GET_API_PERSON_ICON_ACCESS) + "/" + strEmailAndIcon);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
            connection.setReadTimeout(10000);
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);

            writer.write(ImageHelper.bitmapToByteArray(m_bmUpload));
            writer.flush();
            writer.close();
            os.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line).append("\n");
            reader.close();

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(connection != null)
                connection.disconnect();
        }
        return  null;
    }
}