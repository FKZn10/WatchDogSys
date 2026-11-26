package com.jpmsoft.jputils.utils;

import org.apache.commons.lang.time.DateUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/8/4 15:02
 * @Description:
 * @Version: 1.0
 */
public class DateFormatUtil {

    public static final String FORMAT_PATTERN_OF_DAY = "yyyy-MM-dd";

    public static final String FORMAT_PATTERN_OF_DAY_2 = "yyyy年MM月dd日";

    public static final String FORMAT_PATTERN_OF_DAY_3 = "MM月dd日";

    public static final String FORMAT_PATTERN_OF_MONTH = "yyyy-MM";

    public static final String FORMAT_PATTERN_OF_YEAR = "yyyy";

    public static final String FORMAT_PATTERN_OF_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_PATTERN_OF_DAY_1 = "yyyy/MM/dd";

    public static final String FORMAT_PATTERN_OF_SECOND_1 = "yyyy/MM/dd HH:mm:ss";

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date stringToDate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static List<String> getDatesBetween(String start, String end) {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat sdf1 = new SimpleDateFormat(FORMAT_PATTERN_OF_SECOND);
        Date dateStart = null;
        try {
            dateStart = sdf1.parse(start);
        } catch (ParseException e) {
            return dates;
        }
        Instant instant = dateStart.toInstant();
        LocalDate startDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        Date dateEnd = null;
        try {
            dateEnd = sdf1.parse(end);
        } catch (ParseException e) {
            return dates;
        }
        Instant instant1 = dateEnd.toInstant();
        LocalDate endDate = instant1.atZone(ZoneId.systemDefault()).toLocalDate();

        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN_OF_DAY);

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        for (int i = 0; i <= numOfDaysBetween; i++) {
            LocalDate date = startDate.plusDays(i);
            Date date1 = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
            dates.add(sdf.format(date1));
        }
        return dates;
    }

    public static List<String> getDatesMonth(String month) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(FORMAT_PATTERN_OF_SECOND);
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN_OF_MONTH);
        List<String> datesBetween = new ArrayList<>();
        Date date = null;
        try {
            date = sdf.parse(month);
        } catch (ParseException e) {
            return datesBetween;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = calendar.getTime();

        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        Date lastDayOfMonth = calendar.getTime();

        datesBetween = getDatesBetween(sdf1.format(firstDayOfMonth), sdf1.format(lastDayOfMonth));
        return datesBetween;
    }

    public static String dateDiff(long diff) {
        long nd = 24 * 60 * 60;//一天的秒数
        long nh = 60 * 60;//一小时的秒数
        long nm = 60;//一分钟的秒数
        long day = diff / nd;//计算差多少天
//        long hour = diff % nd / nh;//计算差多少小时
        float num = (float) (diff % nd) / nh;

        DecimalFormat df = new DecimalFormat("0.0");

        String hour = df.format(num);
//        long min = diff % nd % nh / nm;//计算差多少分钟
//        long sec = diff % nd % nh % nm ;//计算差多少秒//输出结果
//        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
        return day + "天" + hour + "小时";
    }

    public static Float countHours(long diff) {
        //一小时的毫秒数
        long nh = 60 * 60;
        float num = (float) (diff) / nh;
        DecimalFormat df = new DecimalFormat("0.0");
        String hour = df.format(num);
        return Float.valueOf(hour);
    }

    /**
     * 获取月份的最大天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMaxDayOfMonth(int year, int month) {
        //获取当前时间
        Calendar cal = Calendar.getInstance();
        //下面可以设置月份，注：月份设置要减1，所以设置1月就是1-1，设置2月就是2-1，如此类推
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    /**
     * 未来N天，不包含今天
     *
     * @return
     */
    public static String getDays(int count) {

        Date date = DateUtils.addDays(new Date(), count);
        String endDate = DateFormatUtil.dateToString(date, DateFormatUtil.FORMAT_PATTERN_OF_DAY);

        return endDate;
    }

    public static String getDayOfWeek(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        // 解析字符串为LocalDate
        LocalDate date = LocalDate.parse(dateStr, formatter);

        // 获取这个日期是周几
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        // 使用中文地区设置来获取星期几的中文表示（这里以简体中文为例）
        String dayOfWeekInChinese = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.SIMPLIFIED_CHINESE);
        return dayOfWeekInChinese;
    }
}
