package com.fff.ingood.task;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class IgActivityOfferTookTask extends HttpRequestTask<JsonObject, Integer, Void> {

    public IgActivityOfferTookTask(AsyncHttpRequestResponder<Integer, Void> responder) {
        super(responder);
    }

    @Override
    protected String access(JsonObject jsonObj) {
        HttpURLConnection connection = null;

        try {
            URL url = new URL(String.valueOf(HttpProxy.HTTP_POST_API_ACTIVITY_OFFER_TOOK));

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
            connection.setReadTimeout(10000);
            connection.setDoOutput(true);
            connection.setUseCaches(false);

            OutputStream os = connection.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);

            writer.write(jsonObj.toString().getBytes());
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
