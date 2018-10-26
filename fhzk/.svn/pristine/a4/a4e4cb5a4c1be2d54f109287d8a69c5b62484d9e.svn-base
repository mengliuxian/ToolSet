package com.flym.fhzk.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Morphine on 2017/3/25.
 */

public class DateFormatUtil {
    /**
     * 格式(yyyy-MM-dd)
     */
    public static final String DATE_PATTERN_1 = "yyyy-MM-dd";
    /**
     * 格式(yyyy-MM)
     */
    public static final String DATE_PATTERN_2 = "yyyy-MM";

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[4];
    }

    /**
     * 根据指定的格式对日期进行格式化处理
     *
     * @param date 日期
     * @return 日期
     */
    public static String formatMonth(Date date) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
        df.applyPattern("yyyy-MM");
        return df.format(date);
    }

    public static long simpleDateFormat(String str) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = date.getTime();
        return time;
    }

    public static String int2MMss(int i) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        return simpleDateFormat.format(i);
    }


    public static int getCurHoursAndMinutes() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        String format = dateFormat.format(System.currentTimeMillis());
        return Integer.parseInt(format);
    }


    /**
     * 根据指定的格式对日期进行格式化处理
     *
     * @param date 日期
     * @return 日期
     */
    public static String formatDate(Date date) {
        if (null == date) {
            return "";
        }

        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
        df.applyPattern("yyyy-MM-dd");
        return df.format(date);
    }


    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }


    /**
     * 根据指定的格式对日期进行格式化处理
     *
     * @param date   日期
     * @param format 格式
     * @return 日期
     */
    public static String formatDate(Date date, String format) {
        if (null == date) {
            return null;
        }

        SimpleDateFormat df = (SimpleDateFormat) DateFormat.getDateInstance();
        df.applyPattern(format);
        return df.format(date);
    }

    /**
     * 获取本周开始与结束日期
     *
     * @return [0]=本周开始日期，[1]=本周结束日期
     */
    public static String[] getCurWeekDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_YEAR, d);
        String beginDate = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR, +6);
        String endDate = sdf.format(cal.getTime());
        return new String[]{beginDate, endDate};
    }

    /**
     * 获取当前时间上周的开始与结束时间
     *
     * @return string[0]为上周开始日期，string[1]为上周结束日期
     */
    public static String[] getLastWeekDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int d = 0;
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            d = -6;
        } else {
            d = 2 - cal.get(Calendar.DAY_OF_WEEK);
        }
        cal.add(Calendar.DAY_OF_YEAR, d - 7);
        String beginDate = sdf.format(cal.getTime());
        cal.add(Calendar.DAY_OF_YEAR, +6);
        String endDate = sdf.format(cal.getTime());
        return new String[]{beginDate, endDate};
    }

    /**
     * 获取当前月份的开始与结束日期
     *
     * @return string[0]为开始日期，[1]为结束日期
     */
    public static String[] getCurMonthDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        String beginDate = sdf.format(cal.getTime());

        int day = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //获取当月最大天数
        cal.set(Calendar.DAY_OF_MONTH, day);
        String endDate = sdf.format(cal.getTime());
        return new String[]{beginDate, endDate};
    }

    /**
     * 获取当前时间的前一个月的开始与结束日期
     *
     * @return string[0]为开始日期，[1]为结束日期
     */
    public static String[] getLastMonthDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.MONTH, -1);
        String beginDate = sdf.format(cal.getTime());

        int day1 = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //获取当月最大天数
        cal.set(Calendar.DAY_OF_MONTH, day1);
        String endDate = sdf.format(cal.getTime());
        return new String[]{beginDate, endDate};
    }

}
