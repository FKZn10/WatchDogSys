package com.jpmsoft.custom.constants;

import com.jpmsoft.common.utils.ValidateUtil;

import java.util.Locale;

/**
 * 统计查询方法函数
 */
public enum TjQueryEnum {
    //求和
    SUM("SUM", "SUM"),
    //最大值
    MAX("MAX", "MAX"),
    //最小值
    MIN("MIN", "MIN");

    private String fun;
    private String key;

    TjQueryEnum(String fun, String key) {
        this.fun = fun;
        this.key = key;
    }

    public static String getFun(String key) {
        if (ValidateUtil.isEmpty(key)) return null;
        for (TjQueryEnum c : TjQueryEnum.values()) {
            if (c.getKey().equals(key.toUpperCase(Locale.ENGLISH))) {
                return c.fun;
            }
        }
        return null;
    }

    public String getFun() {
        return fun;
    }

    public void setFun(String fun) {
        this.fun = fun;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
