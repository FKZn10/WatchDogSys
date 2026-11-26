package com.jpmsoft.gmgws.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间日期工具
 * created by:Aaron
 * created time:2021-06-11
 */
public class DateUtil {

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

    public static String Date2String(Date time, String format) {
        if (time == null) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            String date = sdf.format(time);
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
     * 时间增加秒
     *
     * @param date
     * @param second
     * @return
     */
    public static Date DateChangeSecond(Date date, Integer second) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.SECOND, second);
        return rightNow.getTime();
    }

    /**
     * 时间增加分钟
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date DateChangeMinute(Date date, Integer minute) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MINUTE, minute);
        return rightNow.getTime();
    }


    /**
     * 计算两个日期之间相差的天数
     */
    public static int daysBetweenDay(Date sendDate, Date startDate) {
        if (sendDate == null || startDate == null) return 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sendDate = sdf.parse(sdf.format(sendDate));
            startDate = sdf.parse(sdf.format(startDate));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(sendDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(startDate);
        long time2 = cal.getTimeInMillis();
        if (time2 < time1)
            return 0;
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算连个时间相差秒
     *
     * @param start
     * @param end
     * @return
     */
    public static long timeBetweenSecond(Date start, Date end) {
        if (start == null || end == null) return 0;
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        long time1 = cal.getTimeInMillis();
        cal.setTime(end);
        long time2 = cal.getTimeInMillis();
        if (time2 < time1)
            return 0;
        return (time2 - time1) / 1000L;
    }


    public static String stampToTime(String s) {
        try {
            String res;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(s);
            //将时间戳转换为时间
            Date date = new Date(lt);
            //将时间调整为yyyy-MM-dd HH:mm:ss时间样式
            res = simpleDateFormat.format(date);
            return res;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 比较 a是否大于b(日期 天)
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean dayCompare(Date a, Date b) {
        if (a == null || b == null) return false;
        String aDay = Date2String(a, "yyyy-MM-dd");
        String bDay = Date2String(b, "yyyy-MM-dd");
        String[] as = aDay.split("-");
        String[] bs = bDay.split("-");
        for (int i = 0; i < 3; i++) {
            if (Integer.parseInt(as[i]) < Integer.parseInt(bs[i]))
                return false;
        }
        return true;
    }
}
