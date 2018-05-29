package com.fff.ingood.tools;

import com.fff.ingood.data.Activity;
import com.fff.ingood.data.Person;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Created by yoie7 on 2018/5/16.
 */

public class ParserUtils {

    private static Logger m_logger = Logger.getLogger(ParserUtils.class.getName());

    public static String getStringByTag(String tag, String Body){
        String result;

        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(Body);

        if(jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            result = jsonArray.get(0).getAsJsonObject().get(tag).getAsString();
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

    public static Person getPersonAttr(String Body){
        Person result = new Person();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(Body);
        JsonArray jsonArray = new JsonArray();
        Gson gson = new Gson();
        if(jsonElement.isJsonArray()) {
            jsonArray = jsonElement.getAsJsonArray();
            result = gson.fromJson(jsonArray.get(1).toString(), Person.class);
            return result;
        }
        else
            return null;
    }

    public static Activity getACtivityAttr(String Body){
        Activity result = new Activity();

        try {
            final JSONObject obj = new JSONObject(Body);
            Iterator x = obj.keys();
            while (x.hasNext()){
                String key = (String) x.next();
                if(key.contains(Activity.ATTRIBUTES_ACTIVITY_PUBLISHER_EMAIL))
                    result.setPublisherEmail(obj.getString(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_PUBLISHER_PASSWORD))
                    result.setPublisherPwd(obj.getString(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_DISPLAYNAME))
                    result.setName(obj.getString(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_LOCATION))
                    result.setLocation(obj.getString(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_DESCRIPTION))
                    result.setDescription(obj.getString(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_PUBLISH_BEGIN))
                    result.setPublisBegin(obj.getLong(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_PUBLISH_END))
                    result.setPublisEnd(obj.getLong(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_DATE_BEGIN))
                    result.setDateBegin(obj.getLong(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_DATE_END))
                    result.setDateEnd(obj.getLong(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_LARGE_ACTIVITY))
                    result.setLargeActivity(obj.getBoolean(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_EARLY_BIRD))
                    result.setEarlyBird(obj.getBoolean(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_ATTENDEES))
                    result.setAttendees(obj.getJSONArray(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_ATTENTION))
                    result.setAttention(obj.getInt(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_GOOD_ACTIVITY))
                    result.setGoodActivity(obj.getInt(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_TAGS))
                    result.setTags(obj.getJSONArray(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_ID))
                    result.setId(obj.getInt(key));
                else if(key.contains(Activity.ATTRIBUTES_ACTIVITY_STATUS))
                    result.setStatus(obj.getString(key));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String listStringToString(ArrayList<String> lsString, char splitChar) {
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
