package com.jpmsoft.fileUpload.utils;

import com.jpmsoft.common.utils.ValidateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
        if (ValidateUtil.isEmpty(dateStr)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
            return date;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String Date2String(Date time, String format) {
        if (time == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(time);
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
     * 计算两个日期之间相差的天数
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
     * 获取当前季度
     *
     * @return
     */
    public static Integer getCurrentQuarter() {
        Calendar cal = Calendar.getInstance();
        int m = cal.get(Calendar.MONTH) + 1;
        return m % 3 == 0 ? m / 3 : m / 3 + 1;
    }

}

