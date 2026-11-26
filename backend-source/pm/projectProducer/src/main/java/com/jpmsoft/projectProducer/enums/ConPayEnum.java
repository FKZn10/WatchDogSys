package com.jpmsoft.projectProducer.enums;

public enum ConPayEnum {

    // 预付款
    Advance("预付款", "01"),
    // 进度款
    JD("进度款", "02"),
    // 结算款
    JG("结算款", "03"),
    // 质保金
    ZB("质保金", "04");


    private String name;

    private String key;

    ConPayEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (ConPayEnum c : ConPayEnum.values()) {
            if (c.getKey().equals(key)) {
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
