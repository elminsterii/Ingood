package com.fff.ingood.tools;

import com.fff.ingood.data.Comment;
import com.fff.ingood.data.IgActivity;
import com.fff.ingood.data.Person;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yoie7 on 2018/5/16.
 */

public class ParserUtils {

    public static String getStringByTag(String tag, String Body){
        String result = null;

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(Body);

        if(jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for(int i = 0; i < jsonArray.size(); i++){
                if(jsonArray.get(i).getAsJsonObject().has(tag))
                    result = jsonArray.get(i).getAsJsonObject().get(tag).getAsString();

            }
        } else {
            result = jsonElement.getAsJsonObject().get(tag).getAsString();
        }

        return result;
    }

    public static int getIntByTag(String tag, String Body){
        int result = -1;

        try {
            final JSONObject obj = new JSONObject(Body);
            result =  obj.getInt(tag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Person getPersonByJson(String Body){
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(Body);
        Gson gson = new Gson();
        if(jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            return gson.fromJson(jsonArray.get(1).toString(), Person.class);
        }
        else
            return null;
    }

    public static List<Comment> getCommentsByJson(String Body){
        List<Comment> result = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(Body);
        JsonArray jsonArray;
        Gson gson = new Gson();
        if(jsonElement.isJsonArray()) {
            jsonArray = jsonElement.getAsJsonArray();

            for(int i = 1; i < jsonArray.size(); i++){
                result.add(gson.fromJson(jsonArray.get(i).toString(), Comment.class));
            }
            return result;
        }
        else
            return null;
    }

    public static List<IgActivity> getActivitiesByJson(String Body){
        List<IgActivity> result = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(Body);
        JsonArray jsonArray;
        Gson gson = new Gson();
        if(jsonElement.isJsonArray()) {
            jsonArray = jsonElement.getAsJsonArray();

            for(int i = 1; i < jsonArray.size(); i++){
                result.add(gson.fromJson(jsonArray.get(i).toString(), IgActivity.class));
            }
            return result;
        }
        else
            return null;
    }

    public static List<Person> getPersonsByJson(String Body){
        List<Person> result = new ArrayList<>();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(Body);
        Gson gson = new Gson();
        if(jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            for(int i = 1; i < jsonArray.size(); i++){
                result.add(gson.fromJson(jsonArray.get(i).toString(), Person.class));
            }
            return result;
        }
        else
            return null;
    }

    public static String listStringToString(List<String> lsString, char splitChar) {
        if(lsString == null || lsString.isEmpty())
            return "";

        StringBuilder strBuilder = new StringBuilder();
        for(String str : lsString) {
            if(str != null && !str.isEmpty())
                strBuilder.append(str).append(splitChar);
        }

        if(strBuilder.length() > 0)
            strBuilder.deleteCharAt(strBuilder.length()-1);

        return strBuilder.toString();
    }

    public static String arrayStringToString(String[] arrString, char splitChar) {
        if(arrString == null || arrString.length <= 0)
            return "";

        StringBuilder strBuilder = new StringBuilder();
        for(String str : arrString) {
            if(str != null && !str.isEmpty())
                strBuilder.append(str).append(splitChar);
        }

        if(strBuilder.length() > 0)
            strBuilder.deleteCharAt(strBuilder.length()-1);

        return strBuilder.toString();
    }
}