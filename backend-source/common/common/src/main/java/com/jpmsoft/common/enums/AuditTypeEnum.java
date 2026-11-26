package com.jpmsoft.common.enums;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 21:45
 */
public enum AuditTypeEnum {

    add("新增", "01"),
    upd("更新", "02"),
    del("删除", "03");

    private String name;
    private String key;

    AuditTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (AuditTypeEnum c : AuditTypeEnum.values()) {
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
