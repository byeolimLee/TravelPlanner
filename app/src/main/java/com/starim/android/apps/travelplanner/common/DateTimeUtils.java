package com.starim.android.apps.travelplanner.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by starim on 14. 7. 4..
 */
public class DateTimeUtils {

    public static String convertFormattedTimeFromSystemTime(String timeFormat, long systemTime) {
        try {
            Date date = new Date(systemTime);
            SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
            return sdf.format(date);
        } catch (Throwable e) {
            return "";
        }
    }

    public static String createGlobalFormatTime() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR);
        int currentMinute = calendar.get(Calendar.MINUTE);
        int currentAmPm = calendar.get(Calendar.AM_PM);

        return createGlobalFormatTime(currentHour, currentMinute, currentAmPm);
    }

    public static String createGlobalFormatTime(int hour, int minute, int ampm) {
        int currentHour = hour;
        int currentMinute = minute;

        String hourString = String.valueOf(hour);
        if(currentHour >= 0 && currentHour < 10){
            hourString = "0" + currentHour;
        }

        String minuteString = String.valueOf(minute);
        if(currentMinute >= 0 && currentMinute < 10){
            minuteString = "0" + currentMinute;
        }

        //  Calendar.AM_PM returns 0 if it is AM and 1 for PM
        if (ampm == 0) {
            return String.format("%s.%s.%s", hourString, minuteString, "AM");
        } else {
            return String.format("%s.%s.%s", hourString, minuteString, "PM");
        }
    }

    public static String createGlobalFormatDate() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        return createGlobalFormatDate(currentYear, currentMonth, currentDay);
    }

    public static String createGlobalFormatDate(int year, int month, int day) {
        int currentMonth = month;
        int currentDay = day;

        currentMonth++;
        String monthString = String.valueOf(currentMonth);
        if(currentMonth >= 0 && currentMonth < 10){
            monthString = "0" + currentMonth;
        }

        String dayString = String.valueOf(currentDay);
        if(currentDay >= 0 && currentDay < 10){
            dayString = "0" + currentDay;
        }

        return String.format("%s.%s.%s", year, monthString, dayString);
    }
}
