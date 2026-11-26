package com.jpmsoft.custom.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 时间日期工具
 * created by:Aaron
 * created time:2021-06-11
 */
public class DateUtil {



    /**
     * 将日期格式转为yyyy-MM-dd格式的日期
     *
     * @param date
     * @return
     */
    final static public SimpleDateFormat MMddYYYY_HHmmss = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    /**
     * 时间戳转指定格式时间字符串
     *
     * @param timestamp 时间戳（毫秒）
     * @param format    时间格式
     * @return 格式化后的时间字符串
     */
    public static String timestampToString(long timestamp, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(timestamp));
    }

    /**
     * 字符串转时间
     *
     * @param dateStr 时间字符串
     * @param format  格式
     * @return
     */
    public static Date String2Date(String dateStr, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 操作天数 增加天数
     *
     * @param date   时间
     * @param dayNum 增加的天数
     * @return
     */
    public static Date DateChangeDay(Date date, Integer dayNum) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DATE, dayNum);
        return rightNow.getTime();
    }

    /**
     * 天数计算（整数）
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static Integer daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            Long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            Long time2 = cal.getTimeInMillis();
            Long between_days = (time2 - time1) / (1000 * 3600 * 24);
            return between_days.intValue();
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * 天数计算(保留两位小数)
     *
     * @param small 小的时间
     * @param big   大的时间
     * @return
     */
    public static BigDecimal daysCalculation(Date small, Date big) {
        try {
            Integer datePoor = getDatePoor(small, big);
            BigDecimal b = new BigDecimal((double) datePoor / (24 * 60 * 60 * 1000));
            BigDecimal day = b.setScale(2, BigDecimal.ROUND_HALF_UP);//第一个参数是保留小数的位数
            return day;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<String> getBeforeSevenDay() {
        String[] arr = new String[7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = null;
        for (int i = 0; i < 7; i++) {
            c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -i);
            arr[6 - i] = sdf.format(c.getTime());
        }
        return Arrays.asList(arr);
    }

    /**
     * 获取时间中的年份
     *
     * @param time
     * @return
     */
    public static Integer getTimeYear(Date time) {
        if (time == null) return null;
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        int y = c.get(Calendar.YEAR);
        return y;
    }

    /**
     * 格式化时间格式
     *
     * @param date
     * @return
     */
    static public Date dateFormatToDate(Date date) {
        String timeString = MMddYYYY_HHmmss.format(date);
        return str2date(timeString);
    }

    public static Date str2date(String str) {
        Date result = null;
        try {
            Date udate = MMddYYYY_HHmmss.parse(str);
            result = new java.sql.Date(udate.getTime());
            return result;
        } catch (Exception e) {
            System.out.println("DateUtil.str2date(str) Error:e = " + e);
            return null;
        }
    }

    public static String Date2String(Date time, String format) {
        if (time == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
    }

    public static Integer getDatePoor(Date startDate, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        Long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        Long mymint = diff / 1000 / 60;
        int intValue = mymint.intValue();
        return intValue;
    }
}
