package com.fff.ingood.task2;


import android.os.AsyncTask;

import com.fff.ingood.tools.JsonUtils;

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

public class HttpProxy {

    public interface AsyncResponse {
        void processFinish(boolean output);
    }
    private static final String SERVER_URL = "https://hangouttw.appspot.com";

    public static final String HTTP_POST_API_REGISTER = SERVER_URL + "/register";
    public static final String HTTP_POST_API_UNREGISTER = SERVER_URL + "/unregister";
    public static final String HTTP_POST_API_LOGIN = SERVER_URL + "/login";
    public static final String HTTP_POST_API_LOGOUT = SERVER_URL + "/logout";
    public static final String HTTP_POST_API_VERIFY_EMAIL = SERVER_URL + "/verifyemail";
    public static final String HTTP_POST_API_PERSON_QUERY = SERVER_URL + "/queryperson";
    public static final String HTTP_POST_API_PERSON_UPDATE = SERVER_URL + "/updateperson";
    public static final String HTTP_POST_API_ACTIVITY_CREATE = SERVER_URL + "/createactivity";
    public static final String HTTP_POST_API_ACTIVITY_DELETE = SERVER_URL + "/deleteactivity";
    public static final String HTTP_POST_API_ACTIVITY_QUERY = SERVER_URL + "/queryactivity";
    public static final String HTTP_POST_API_ACTIVITY_UPDATE = SERVER_URL + "/updateactivity";
    public static final String HTTP_POST_API_ACTIVITY_QUERY_BY_ATTR = SERVER_URL + "/queryactivityidby";

    public static final int HTTP_POST_TIMEOUT = 10;
    public static final int HTTP_GET_TIMEOUT = 15;

    public static class HttpGetTask extends AsyncTask<String,Void,String> {

        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public String doInBackground(String... arg0) {
            BufferedReader reader = null;
            StringBuilder stringBuilder;

            try {
                URL url = new URL(arg0[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(HTTP_GET_TIMEOUT*1000);
                connection.connect();


                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                stringBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null)
                    stringBuilder.append(line).append("\n");

                return stringBuilder.toString();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    public static class HttpPostTask extends AsyncTask<Object, Void, String> {
        AsyncResponse delegate;

        public HttpPostTask(AsyncResponse delegate){
            this.delegate = delegate;
        }
        @Override
        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        public String doInBackground(Object... arg0) {
            BufferedReader reader = null;
            StringBuilder stringBuilder;
            String jsonString = JsonUtils.createJsonString(arg0[0]);

            try {
                URL url = new URL(String.valueOf(arg0[1]));

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(HTTP_POST_TIMEOUT*1000);
                connection.setReadTimeout(10000);
                connection.setDoInput(true);                                                        //允許輸入流，即允許下載
                connection.setDoOutput(true);                                                       //允許輸出流，即允許上傳
                connection.setUseCaches(false);                                                     //設置是否使用緩存
                connection.setRequestProperty("Content-Length", String.valueOf(jsonString.length()));                              //設定內容長度
                connection.connect();

                OutputStream os = connection.getOutputStream();
                DataOutputStream writer = new DataOutputStream(os);
                writer.writeBytes(jsonString);
                writer.flush();
                writer.close();
                os.close();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                stringBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null)
                    stringBuilder.append(line).append("\n");

                return stringBuilder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        public void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result.contains("200 OK "))
                delegate.processFinish(true);
            else
                delegate.processFinish(false);
        }
    }
}


