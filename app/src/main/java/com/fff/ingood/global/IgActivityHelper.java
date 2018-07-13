package com.fff.ingood.global;

import com.fff.ingood.data.IgActivity;
import com.fff.ingood.tools.StringTool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ElminsterII on 2018/6/25.
 */
public class IgActivityHelper {
    public static String makeDateStringByIgActivity(IgActivity activity) {
        if(activity == null)
            return "";

        String strOriginPattern = "yyyy-MM-dd HH:mm:ss";
        String strNewPattern = "yyyy-MM-dd(EEE) HH:mm";

        DateFormat dateOriginFormat = new SimpleDateFormat(strOriginPattern, Locale.getDefault());
        DateFormat dateNewFormat = new SimpleDateFormat(strNewPattern, Locale.getDefault());

        String strTimeBegin = activity.getDateBegin();
        String strTimeEnd = activity.getDateEnd();

        if(!StringTool.checkStringNotNull(strTimeBegin)
                || !StringTool.checkStringNotNull(strTimeEnd))
            return "";

        try {
            Date dateBegin = dateOriginFormat.parse(strTimeBegin);
            strTimeBegin = dateNewFormat.format(dateBegin);

            Date dateEnd = dateOriginFormat.parse(strTimeEnd);
            strTimeEnd = dateNewFormat.format(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return strTimeBegin + " ~ " + strTimeEnd;
    }

    public static String makeIgActivityDateStringByUI(String strUIDate) {
        if(!StringTool.checkStringNotNull(strUIDate))
            return "";

        String bRes = "";

        String strOriginPattern = "yyyy-MM-dd HH:mm";
        String strNewPattern = "yyyy-MM-dd HH:mm:ss";

        DateFormat dateOriginFormat = new SimpleDateFormat(strOriginPattern, Locale.getDefault());
        DateFormat dateNewFormat = new SimpleDateFormat(strNewPattern, Locale.getDefault());

        try {
            Date date = dateOriginFormat.parse(strUIDate);
            bRes = dateNewFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return bRes;
    }
}
