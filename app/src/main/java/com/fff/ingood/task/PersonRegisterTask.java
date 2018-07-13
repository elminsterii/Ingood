package com.fff.ingood.task;

import com.fff.ingood.data.Person;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ElminsterII on 2018/6/11.
 */

public class PersonRegisterTask extends HttpPostAccessTask<Person, Integer, Void> {

    public PersonRegisterTask(AsyncResponder<Integer, Void> responder) {
        super(responder);
    }

    @Override
    protected String access(Person info) {
        BufferedReader reader = null;
        String jsonString;
        jsonString = new Gson().toJson(info, Person.class);

        try {
            URL url = new URL(String.valueOf(HttpProxy.HTTP_POST_API_REGISTER));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(HttpProxy.HTTP_POST_TIMEOUT*1000);
            connection.setReadTimeout(10000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.connect();

            OutputStream os = connection.getOutputStream();
            DataOutputStream writer = new DataOutputStream(os);
            writer.write(jsonString.getBytes());
            writer.flush();
            writer.close();
            os.close();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();

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
