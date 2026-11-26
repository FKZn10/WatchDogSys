package com.jpmsoft.userProducer.enums;

/**
 */
public enum RegisterStateEnum {

    Pending("审核中", "01"),
    Approved("已通过", "02"),
    Rejected("未通过", "03");

    private String name;
    private String key;

    RegisterStateEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (RegisterStateEnum c : RegisterStateEnum.values()) {
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
