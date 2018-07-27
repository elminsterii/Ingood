package com.fff.ingood.task;

import com.fff.ingood.data.IgActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class IgActivityImageGetListTask extends HttpRequestTask<IgActivity, Integer, String> {

    public IgActivityImageGetListTask(AsyncHttpRequestResponder<Integer, String> responder) {
        super(responder);
    }

    @Override
    protected String access(IgActivity activity) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(String.valueOf(HttpProxy.HTTP_API_ACTIVITY_IMAGE_ACCESS) + activity.getId());

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
            connection.setReadTimeout(10000);
            connection.setUseCaches(false);

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