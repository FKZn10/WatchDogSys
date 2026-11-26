package com.jpmsoft.projectProducer.enums;

/**
 * 合同进度类型
 *
 * @Author: 李文涛 aaron
 * @date: 2024/9/25 9:59
 */
public enum CppConJdEnum {

    RATE("按百分比", "01"),
    JD("按进度节点", "02"),
    JL("按计量", "03");

    private String name;
    private String key;

    CppConJdEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (CppConJdEnum c : CppConJdEnum.values()) {
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
