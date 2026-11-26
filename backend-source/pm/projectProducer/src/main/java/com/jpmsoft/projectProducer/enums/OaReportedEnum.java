package com.jpmsoft.projectProducer.enums;

/**
 * 报备类型
 */
public enum OaReportedEnum {

    Filing("报备", "01"),
    Change("变更", "02");

    private String name;
    private String key;

    OaReportedEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (OaReportedEnum c : OaReportedEnum.values()) {
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
