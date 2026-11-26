package com.jpmsoft.userProducer.enums;

public enum ThirdPartyAppIdEnum {

    BJ_ICBC("北京工行", "BJ-ICBC"),
    JS_YINBAO("江苏银宝", "JS-YINBAO"),
    SX_PM("苏相", "SX-PM"),
    PM_TEL("对接使用手机号识别用户", "PM_TEL"),
    PM_ACT("对接使用账户识别用户", "PM_ACT"),
    PM_CODE("对接使用用户编码识别用户", "PM_CODE");

    private String name;
    private String key;

    ThirdPartyAppIdEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (ThirdPartyAppIdEnum c : ThirdPartyAppIdEnum.values()) {
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
