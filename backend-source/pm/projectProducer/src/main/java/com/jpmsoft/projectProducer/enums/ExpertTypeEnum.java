package com.jpmsoft.projectProducer.enums;

/**
 * 专家类别
 */
public enum ExpertTypeEnum {

    Technology("技术类专家", "01"),
    Economics("经济类专家", "02");

    private String name;
    private String key;

    ExpertTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (ExpertTypeEnum c : ExpertTypeEnum.values()) {
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
