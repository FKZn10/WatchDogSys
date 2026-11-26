package com.jpmsoft.common.enums;

/**
 * 项目阶段
 */
@Deprecated
public enum GlsTypeEnum {

    Advance_Charge("预付款保函", "01"),
    Performance("履约保函", "02");

    private String name;
    private String key;

    GlsTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (GlsTypeEnum c : GlsTypeEnum.values()) {
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
