package com.jpmsoft.projectProducer.utils;

import com.jpmsoft.common.utils.ValidateUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

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


    public static BigDecimal decMultiply(BigDecimal a, BigDecimal b) {
        if (ValidateUtil.isNull(a) || ValidateUtil.isNull(b)) return BigDecimal.ZERO;
        return a.multiply(b);
    }

    public static BigDecimal decMultiplyFloat(BigDecimal a, Float b) {
        if (ValidateUtil.isNull(a) || ValidateUtil.isNull(b)) return BigDecimal.ZERO;
        BigDecimal f = new BigDecimal(String.valueOf(b));
        return a.multiply(f);
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
        return new BigDecimal(a.toString()).divide(bnum, 3, RoundingMode.UP);
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

    public static Float floatAddFloat(Float a, Float b) {
        if (a == null && b == null) return 0F;
        else if (a == null) return b;
        else if (b == null) return a;
        BigDecimal ba = new BigDecimal(a);
        BigDecimal bb = new BigDecimal(b);
        return ba.add(bb).floatValue();
    }

    public static Float floatDivideFloat(Float a, Float b) {
        if (a == null || b == null) return 0F;
        if (a.compareTo(0F) == 0 || b.compareTo(0F) == 0) return 0F;
        BigDecimal ba = new BigDecimal(a);
        BigDecimal bb = new BigDecimal(b);
        return ba.divide(bb).floatValue();
    }

    /**
     * a-b
     *
     * @param a
     * @param b
     * @return
     */
    public static Float floatSubtractFloat(Float a, Float b) {
        if (a == null || b == null) return 0F;
        if (b.compareTo(0F) == 0) return a;
        if (a.compareTo(0F) == 0)
            return new BigDecimal(b).multiply(new BigDecimal(-1)).floatValue();
        BigDecimal ba = new BigDecimal(a);
        BigDecimal bb = new BigDecimal(b);
        return ba.subtract(bb).floatValue();
    }


    public static Float floatDivideFloat(Float a, Float b, int scale, RoundingMode roundingMode) {
        if (a == null || b == null) return 0F;
        if (a.compareTo(0F) == 0 || b.compareTo(0F) == 0) return 0F;
        BigDecimal ba = new BigDecimal(a);
        BigDecimal bb = new BigDecimal(b);
        return ba.divide(bb, scale, roundingMode).floatValue();
    }

    public static BigDecimal DecimalXFloat(BigDecimal decValue, Float fValue) {
        if (decValue == null || fValue == null)
            return new BigDecimal(0);
        if (decValue.compareTo(BigDecimal.ZERO) == 0 || fValue == 0F)
            return new BigDecimal(0);
        BigDecimal b1 = new BigDecimal(fValue.toString());
        return decValue.multiply(b1);
    }

    /**
     * 除法 a/b
     * 默认保留两位小数
     * 向上取整 RoundingMode.HALF_UP
     *
     * @param a
     * @param b
     * @return
     */
    public static BigDecimal decimalDivide(BigDecimal a, BigDecimal b) {
        return decimalDivide(a, b, 2);
    }

    /**
     * 除法 a/b
     * 指定小数位 scale
     * 向上取整 RoundingMode.HALF_UP
     *
     * @param a
     * @param b
     * @param scale 保留几位小数
     * @return
     */
    public static BigDecimal decimalDivide(BigDecimal a, BigDecimal b, int scale) {
        return decimalDivide(a, b, scale, RoundingMode.HALF_UP);
    }

    /**
     * 除法 a/b
     * 指定小数位 scale
     * 指定取整方式 RoundingMode
     *
     * @param a
     * @param b
     * @param scale 保留几位小数
     * @return
     */
    public static BigDecimal decimalDivide(BigDecimal a, BigDecimal b, int scale, RoundingMode mode) {
        if (a == null || b == null) return BigDecimal.ZERO;
        if (a.compareTo(BigDecimal.ZERO) == 0 || b.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return a.divide(b, scale, mode);
    }

    /**
     * 生成随机整数
     *
     * @param min
     * @param max
     * @return
     */
    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public static BigDecimal decAdd(BigDecimal a, BigDecimal b) {
        if (ValidateUtil.isNull(a)) return b;
        if (ValidateUtil.isNull(b)) return a;
        return a.add(b);
    }

    /**
     * 减法
     *
     * @param a
     * @param b
     * @return a-b
     */
    public static BigDecimal decSubtract(BigDecimal a, BigDecimal b) {
        if (ValidateUtil.isNull(a) && ValidateUtil.isNull(b)) return BigDecimal.ZERO;
        if (ValidateUtil.isNull(a)) return b.multiply(new BigDecimal(-1));
        if (ValidateUtil.isNull(b)) return a;
        return a.subtract(b);
    }

}
