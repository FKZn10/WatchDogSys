package com.jpmsoft.projectProducer.utils;

import com.jpmsoft.common.utils.ValidateUtil;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
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
    final static public SimpleDateFormat MMddYYYY_HHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 将字符串按照指定格式转换为日期对象
     *
     * @param dateStr 需要转换的时间字符串
     * @param format  日期格式，例如"yyyy-MM-dd"或"yyyy-MM-dd HH:mm:ss"
     * @return 成功转换后返回对应的Date对象，如果转换失败返回null
     * @throws ParseException 如果字符串无法解析为有效日期时抛出异常
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
     * 操作时间 增加小时
     *
     * @param date 时间
     * @param hour 增加的小时
     * @return
     */
    public static Date DateChangeHour(Date date, Integer hour) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.HOUR, hour);
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
        if (smdate == null || bdate == null) return null;
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
        if (ValidateUtil.isEmpty(str)) return null;
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

    public static Integer getAge(String idNum) {
        //非身份找无法计算
        try {
            if (ValidateUtil.isEmpty(idNum) || idNum.length() != 18) return null;
            String birthday = idNum.substring(6, 14);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date birthDate = sdf.parse(birthday);
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthDate);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            Calendar now = Calendar.getInstance();
            int nowYear = now.get(Calendar.YEAR);
            int nowMonth = now.get(Calendar.MONTH);
            int nowDay = now.get(Calendar.DAY_OF_MONTH);
            int age = nowYear - year;
            if (nowMonth < month || (nowMonth == month && nowDay < day)) {
                age--;
            }
            return age;
        } catch (ParseException ex) {
        }
        return null;
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

    /**
     * 比较 a是否大于b(日期 天)
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean dayCompare(Date a, Date b) {
        if (a == null || b == null) return false;
        return daysBetween(b,a)>0;
//        String aDay = Date2String(a, "yyyy-MM-dd");
//        String bDay = Date2String(b, "yyyy-MM-dd");
//        String[] as = aDay.split("-");
//        String[] bs = bDay.split("-");
//        for (int i = 0; i < 3; i++) {
//            if (Integer.parseInt(as[i]) > Integer.parseInt(bs[i])) {
//                System.out.println(Integer.parseInt(as[i])+" > "+Integer.parseInt(bs[i]));
//                return true;
//            }
//        }
//        return false;
    }


    public static String firstDayOfMonth(String fmt) {
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 使用TemporalAdjusters获取月初日期
        LocalDate firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(fmt);
        return firstDayOfMonth.format(formatter);
    }
}
