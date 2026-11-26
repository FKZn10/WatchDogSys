package com.jpmsoft.projectProducer.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang.time.DateUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/8/4 15:02
 * @Description:
 * @Version: 1.0
 */
public class DateFormatUtil {

    public static final String FORMAT_PATTERN_OF_DAY = "yyyy-MM-dd";

    public static final String FORMAT_PATTERN_OF_MONTH = "yyyy-MM";

    public static final String FORMAT_PATTERN_SINGLE_MONTH = "MM";

    public static final String FORMAT_PATTERN_OF_MONTH_1 = "yyyy/MM";

    public static final String FORMAT_PATTERN_OF_MONTH_CHINESE = "yyyy年MM月";

    public static final String FORMAT_PATTERN_OF_SECOND = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMAT_PATTERN_OF_DAY_1 = "yyyy/MM/dd";

    public static final String FORMAT_PATTERN_OF_SECOND_1 = "yyyy/MM/dd HH:mm:ss";

    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Date stringToDate(String date, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static List<String> getDatesBetween(String start, String end){
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
     * @param year
     * @param month
     * @return
     */
    public static int getMaxDayOfMonth(int year, int month){
        //获取当前时间
        Calendar cal = Calendar.getInstance();
        //下面可以设置月份，注：月份设置要减1，所以设置1月就是1-1，设置2月就是2-1，如此类推
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        int maxDay=cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    /**
     * 获取月份的最大天数
     * @param date yyyy-mm
     * @return
     */
    public static int getMaxDayOfMonth(String date){
        return getMaxDayOfMonth(Integer.parseInt(date.substring(0, date.lastIndexOf("-"))),Integer.parseInt(date.substring(date.lastIndexOf("-") + 1)));
    }

    /**
     * 未来N天，不包含今天
     * @return
     */
    public static String getDays(int count, String format){

        Date date = DateUtils.addDays(new Date(), count);
        String endDate = DateFormatUtil.dateToString(date, format);

        return endDate;
    }

    /**
     * yyyy-mm 转 2020年9月11日
     * @return
     */
    public static String dateToCHINESE(String date, String format){

        return DateFormatUtil.dateToString(DateFormatUtil.stringToDate(date,DateFormatUtil.FORMAT_PATTERN_OF_MONTH), format);
    }

    /**
     * 返回两个字符型日期范围内所有key数据。例如 "2023/06"、"2023/07"
     * @param startDateStr
     * @param endDateStr
     * @param outputPatter     需要输出的格式  "yyyy/MM"：需要返回的key格式。需要和传入的日期格式一致
     * @return
     */
    public static List<String> dateRangeKeyList(String startDateStr,String endDateStr,String outputPatter){
        String inputPatter="yyyy-MM-dd";
        if(startDateStr.indexOf("/")>-1){
            inputPatter="yyyy/MM/dd";
        }
        if(StrUtil.isBlank(outputPatter)){
            outputPatter="yyyy/MM";
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(inputPatter);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(outputPatter);

        LocalDate startDate = YearMonth.parse(startDateStr, inputFormatter).atDay(1);
        LocalDate endDate = YearMonth.parse(endDateStr, inputFormatter).atEndOfMonth();
        List<String> keyList = new ArrayList<>();

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            String key = currentDate.format(outputFormatter);
            keyList.add(key);
            currentDate = currentDate.plusMonths(1);
        }
        return keyList;
    }

    /**
     * 当月天数，以当前日期为准
     * @return
     */
    public static int getDaysOfCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        return days;
    }

    /**
     * 获取起止时间之间的月份，包含开始和结束时间
     * @param start
     * @param end
     * @param format
     * @return
     */
    public static List<String> getMonthsBetween(Date start, Date end, String format) {
        Set<String> result = new TreeSet<>();

        result.add(DateFormatUtil.dateToString(start, format));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (calendar.getTime().before(end)) {
            calendar.add(Calendar.MONTH, 1);
            String month = DateFormatUtil.dateToString(calendar.getTime(), format);
            result.add(month);
        }
        result.add(DateFormatUtil.dateToString(end, format));
        return new ArrayList<>(result);
    }

    public static long getYears(String startDateStr, String endDateStr){
        LocalDate startDate = LocalDate.parse(startDateStr);
        LocalDate endDate = LocalDate.parse(endDateStr);
        return ChronoUnit.YEARS.between(startDate, endDate);
    }


}
