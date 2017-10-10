package com.nice.otis.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/5.
 */

public class DateUtils {
    private static SimpleDateFormat sf = null;
    public static final long ONE_MINUTE_MILLIONS = 60 * 1000;
    public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;
    public static final long ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS;
    /**
     * 获取短时间格式
     *
     * @return
     */
    public static String getShortTime(long millis) {
        Date date = new Date(millis);
        Date curDate = new Date();

        String str = "";
        long durTime = curDate.getTime() - date.getTime();

        int dayStatus = calculateDayStatus(date, new Date());

        if (durTime <= 10 * ONE_MINUTE_MILLIONS) {
            str = "刚刚";
        } else if (durTime < ONE_HOUR_MILLIONS) {
            str = durTime / ONE_MINUTE_MILLIONS + "分钟前";
        } else if (dayStatus == 0) {
            str = durTime / ONE_HOUR_MILLIONS + "小时前";
        } else if (dayStatus == -1) {
            str = "昨天" + DateFormat.format("HH:mm", date);
        } else if (isSameYear(date, curDate) && dayStatus < -1) {
            str = DateFormat.format("MM-dd", date).toString();
        } else {
            str = DateFormat.format("yyyy-MM", date).toString();
        }
        return str;
    }
    public static boolean isSameYear(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarYear = tarCalendar.get(Calendar.YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comYear = compareCalendar.get(Calendar.YEAR);

        return tarYear == comYear;
    }
    public static int calculateDayStatus(Date targetTime, Date compareTime) {
        Calendar tarCalendar = Calendar.getInstance();
        tarCalendar.setTime(targetTime);
        int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);

        Calendar compareCalendar = Calendar.getInstance();
        compareCalendar.setTime(compareTime);
        int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);

        return tarDayOfYear - comDayOfYear;
    }
    /**
     * 获取系统时间 格式为："yyyy/MM/dd "
     */
    public static String getCurrentDate() {
        Date d = new Date();
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateYToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyyMMdd");
        return sf.format(d);
    }

    public static String getDateToString(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }
    public static String getDateToStringMh(long time){
        Date d = new Date(time);
        sf = new SimpleDateFormat("MM-dd");
        return sf.format(d);
    }
    public static String getDateToStringText(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }
    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString2(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString3(long time) {
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日\u3000HH:mm");
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     */
    public static long getStringToDate(String time) {
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

//    public static String getTimestampString(long date) {
//        return DateUtils.getTimestampString(new Date(date));
//    }

    public static String getAgeBirthDate(String strBirthDate) {

        if ("".equals(strBirthDate) || strBirthDate == null) {
            return "";
        }
        Date format = new Date();
        try {
            format = new SimpleDateFormat("yyyy年MM月dd日").parse(strBirthDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // 读取当前日期
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);

        c.setTime(format);

        int yearBirthDate = c.get(Calendar.YEAR);
        int monthBirthDate = c.get(Calendar.MONTH) + 1;
        int dayBirthDate = c.get(Calendar.DATE);

        // 计算年龄
        int age = year - yearBirthDate - 1;
        if (monthBirthDate < month) {
            age++;
        } else if (monthBirthDate == month && dayBirthDate <= day) {
            age++;
        }
        return String.valueOf(age);
    }
}
