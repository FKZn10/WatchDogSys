package com.jpmsoft.projectProducer.enums;

/**
 * 合同支付计划
 * 支付计划条件类型
 * JPMsoft_ZFJHTJLX
 */
public enum CppConditionTypeEnum {

    HTYFK("合同预付款", "JPMsoft_ZFJHTJLX_HTYFK"),
    HTJD("合同进度", "JPMsoft_ZFJHTJLX_GCJD"),
    HTJG("合同竣工", "JPMsoft_ZFJHTJLX_HTJG"),
    JGYS("竣工验收", "JPMsoft_ZFJHTJLX_JGYS"),
    JGJS("竣工决算", "JPMsoft_ZFJHTJLX_JGJS"),
    CBSJ("初步审计", "JPMsoft_ZFJHTJLX_CBSJ"),
    JSSJ("结算审计" , "JPMsoft_ZFJHTJLX_JSSJ"),
    WK("尾款", "JPMsoft_ZFJHTJLX_WK_RQ");

    private String name;
    private String key;

    CppConditionTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (CppConditionTypeEnum c : CppConditionTypeEnum.values()) {
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
