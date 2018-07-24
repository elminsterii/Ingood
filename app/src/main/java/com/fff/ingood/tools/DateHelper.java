package com.fff.ingood.tools;

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
public class DateHelper {

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
