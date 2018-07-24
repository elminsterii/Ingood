package com.fff.ingood.tools;

import com.fff.ingood.data.IgActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ElminsterII on 2018/7/11.
 */
public class TimeHelper {
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

    public static String gmtToLocalTime(String strGMT) {
        String strDatePattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDatePattern, Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

        SimpleDateFormat outputFormat = new SimpleDateFormat(strDatePattern, Locale.getDefault());
        outputFormat.setTimeZone(TimeZone.getDefault());

        Date date = null;
        try {
            date = dateFormat.parse(strGMT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputFormat.format(date);
    }

    public static String getCurTime() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        Date dateCurrent = calendar.getTime();

        String strDatePattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDatePattern, Locale.getDefault());

        return dateFormat.format(dateCurrent);
    }

    public static String getTimeByDaysBasedCurrent(int iDays) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.add(Calendar.DATE, iDays);
        Date date = calendar.getTime();

        String strDatePattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat dateFormat = new SimpleDateFormat(strDatePattern, Locale.getDefault());

        return dateFormat.format(date);
    }
}
