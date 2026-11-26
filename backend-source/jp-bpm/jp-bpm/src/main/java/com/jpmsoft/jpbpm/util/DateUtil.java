package com.jpmsoft.jpbpm.util;

import com.jpmsoft.common.utils.ValidateUtil;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
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
        if (ValidateUtil.isEmpty(dateStr)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date date = sdf.parse(dateStr);
            return date;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String DateToString(Date dateStr, String format) {
        if (ValidateUtil.isNull(dateStr)) return null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            String date = sdf.format(dateStr);
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
     * 操作小时 增加小时
     *
     * @param date    时间
     * @param hourNum 小时
     * @return
     */
    public static Date DateChangeHour(Date date, Integer hourNum) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.HOUR, hourNum);
        return rightNow.getTime();
    }

    public static Date DateChangeHour(Date date, Double hourNum) {
        Long times = date.getTime();
        Long nowTimes = times + (int) (1000 * 60 * 60 * hourNum);
        return new Date(nowTimes);
    }


    /**
     * 时间增加指定时分秒
     *
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date DateAddHMS(String date, Integer hour, Integer minute, Integer second) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date time = format.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            if (hour != null && hour != 0)
                cal.add(Calendar.HOUR, hour);
            if (minute != null && minute != 0)
                cal.add(Calendar.MINUTE, minute);
            if (second != null && second != 0)
                cal.add(Calendar.SECOND, second);
            return cal.getTime();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


    public static int TastProcessingtime(String datatime) {
        if (datatime == null || datatime.equals("")) {
            return 0;
        } else {
            if (datatime.matches("[+-]?[0-9]+(\\.[0-9]{1,4})?")) {
                //带小数
                return Double.valueOf(datatime).intValue();
            } else {
                return Integer.parseInt(datatime);
            }
        }
    }


    public static BigDecimal getDifferHour(String startDateTime, String endDateTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date parse1 = dateFormat.parse(startDateTime);
        Date parse2 = dateFormat.parse(endDateTime);
        long mills = parse2.getTime() - parse1.getTime();
        //转 BigDecimal
        BigDecimal fz = new BigDecimal(mills);
        //转 BigDecimal
        BigDecimal fm = new BigDecimal(60 * 60 * 1000);
        BigDecimal bwtHours = fz.divide(fm, 2, BigDecimal.ROUND_HALF_UP);
        return bwtHours;
    }

    //加秒
    public  static Date  dataAddSecond(Date datatime,Integer second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(datatime);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

}
