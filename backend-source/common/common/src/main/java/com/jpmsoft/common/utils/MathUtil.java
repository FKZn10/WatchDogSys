package com.jpmsoft.common.utils;

import java.math.BigDecimal;

@Deprecated
public class MathUtil {

    /**
     * Decimal乘法
     *
     * @param douValue
     * @param intValue
     * @return
     */
    public static BigDecimal DecimalMultiplicationInt(BigDecimal douValue, Integer intValue) {
        if (douValue == null)
            return new BigDecimal(0);
        BigDecimal b1 = new BigDecimal(intValue);
        return douValue.multiply(b1);
    }

    /**
     * Decimal除法
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal DecimalDivisionInt(BigDecimal a, int b) {
        if (a == null || b == 0)
            return new BigDecimal(0);
        if (a.compareTo(BigDecimal.ZERO) == 0)
            return new BigDecimal(0);
        BigDecimal b2 = new BigDecimal(b);
        return a.divide(b2);
    }

    /**
     * a除b 保留三位小数
     * 默认向上补1
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal DecimalDivisionInt(Object a, Object b) {
        if (a == null || b == null)
            return new BigDecimal(0);
        BigDecimal anum = new BigDecimal(a.toString());
        BigDecimal bnum = new BigDecimal(b.toString());
        if (anum.compareTo(BigDecimal.ZERO) == 0 || bnum.compareTo(BigDecimal.ZERO) == 0)
            return new BigDecimal(0);
        return new BigDecimal(a.toString()).divide(bnum, 3, BigDecimal.ROUND_UP);
    }


    public static Float FloatDivisionInt(Float f, int b) {
        if (f == null || f == 0F) return 0F;
        BigDecimal a = new BigDecimal(String.valueOf(f));
        if (a == null || b == 0)
            return 0F;
        if (a.compareTo(BigDecimal.ZERO) == 0)
            return 0F;
        BigDecimal b2 = new BigDecimal(b);
        return Float.valueOf(a.divide(b2).toString());
    }
}
