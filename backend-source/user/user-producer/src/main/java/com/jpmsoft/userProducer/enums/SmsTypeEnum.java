package com.jpmsoft.userProducer.enums;

/**
 * 短信类型
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/6 10:19
 */
public enum SmsTypeEnum {

    Code("验证码短信", "01"),
    sms("通知短信", "02");

    private String name;
    private String key;

    SmsTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static SmsTypeEnum getEnum(String key) {
        for (SmsTypeEnum c : SmsTypeEnum.values()) {
            if (c.getkey().equals(key)) {
                return c;
            }
        }
        return null;
    }

    public static String getName(String key) {
        for (SmsTypeEnum c : SmsTypeEnum.values()) {
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
