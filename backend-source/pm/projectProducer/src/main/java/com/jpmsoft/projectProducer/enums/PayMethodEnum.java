package com.jpmsoft.projectProducer.enums;

/**
 * 支付方式
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/25 9:47
 */
public enum PayMethodEnum {

    NL("按比例", "01"),
    JA("按金额", "02");

    private String name;
    private String key;

    PayMethodEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (PayMethodEnum c : PayMethodEnum.values()) {
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
