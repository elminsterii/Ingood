package com.fff.ingood.task2;

import com.fff.ingood.data.IgActivity;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static com.fff.ingood.global.ServerResponse.TAG_SERVER_RESPONSE_ACTIVITIES_IDS;

public class ActivityQueryTask extends HttpPostAccessTask<String, Integer, List<IgActivity>> {

    public ActivityQueryTask(AsyncResponder<Integer, List<IgActivity>> responder) {
        super(responder);
    }

    @Override
    protected String access(String info) {
        BufferedReader reader = null;
        StringBuilder stringBuilder;

        try {
            URL url = new URL(String.valueOf(HttpProxy.HTTP_POST_API_ACTIVITY_QUERY));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.connect();

            OutputStream os = connection.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(TAG_SERVER_RESPONSE_ACTIVITIES_IDS, info);

            writer.write(jsonObject.toString().getBytes());
            writer.flush();
            writer.close();
            os.close();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            stringBuilder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null)
                stringBuilder.append(line).append("\n");

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  null;
    }
}
