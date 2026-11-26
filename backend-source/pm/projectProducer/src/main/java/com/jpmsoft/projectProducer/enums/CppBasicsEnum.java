package com.jpmsoft.projectProducer.enums;

/**
 * 合同支付计划
 * 支付基础
 * JPMsoft_CON_ZGJC
 */
public enum CppBasicsEnum {

    HTJ("合同价", "JPMsoft_ZFJC_HTJ"),
    MCJLZ("每次计量值", "JPMsoft_ZFJC_MCJLZ"),
    LJJLZ("累计计量值", "JPMsoft_ZFJC_LJJLZ"),
    JGJSJ("竣工决算价", "JPMsoft_ZFJC_JGJSJ"),
    CBSDJ("初步审定价", "JPMsoft_ZFJC_CBSDJ"),
    SJJ("审计价", "JPMsoft_ZFJC_SJJ"),
    Unknown("未知类型", "Unknown");

    private String name;
    private String key;

    CppBasicsEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }



    public static CppBasicsEnum getEnum(String key) {
        for (CppBasicsEnum c : CppBasicsEnum.values()) {
            if (c.getkey().equals(key)) {
                return c;
            }
        }
        return CppBasicsEnum.Unknown;
    }

    public static String getName(String key) {
        for (CppBasicsEnum c : CppBasicsEnum.values()) {
            if (c.getkey().equals(key)) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }
}
