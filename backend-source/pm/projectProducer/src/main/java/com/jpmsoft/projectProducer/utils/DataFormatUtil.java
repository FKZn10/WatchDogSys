package com.jpmsoft.projectProducer.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2023/2/3 10:45
 * @Description:
 * @Version: 1.0
 */
public class DataFormatUtil {

    /**
     * 百分比，保留两位小数
     * @param x
     * @param y
     * @return
     */
    public static String getPercent(long x, long y) {
        if (y == 0) {
            return "0.00%";
        }
        double sum = (double)x/y;
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        return nt.format(sum);
    }

    /**
     * 百分比，保留两位小数
     * @param x
     * @param y
     * @return
     */
    public static String getPercentOfBigDecimal(BigDecimal x, BigDecimal y) {
        if (y.compareTo(BigDecimal.ZERO) == 0) {
            return "0.00%";
        }
        BigDecimal percentage = x.divide(y, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        return percentage.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "%";
    }

    public static boolean getResult(long x, long y ,double percent) {
        if (y == 0){
            return true;
        }
        BigDecimal a = new BigDecimal(x);
        BigDecimal b = new BigDecimal(y);
        BigDecimal divide = a.divide(b, 2, RoundingMode.HALF_UP);
        return divide.doubleValue() >= percent;
    }


}
