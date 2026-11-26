package com.jpmsoft.fileUpload.enums;

/**
 * 自动归档附件名称配置规则
 *
 * @Author: 李文涛 aaron
 * @date: 2024/8/29 14:44
 */
public enum AfnrTypeEnum {

    CONST("常量", "01"),
    PROJECT("项目", "02"),
    CONTRACT("合同", "03"),
    SECINFO("标段", "04"),
    CURRENT("当前表单信息", "05"),
    LINKED("链表信息", "06"),
    SQL("SQL", "07"),
    Unknown("未知类型", "9999");

    private String name;
    private String key;

    AfnrTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (AfnrTypeEnum c : AfnrTypeEnum.values()) {
            if (c.getkey().equals(key)) {
                return c.name;
            }
        }
        return null;
    }

    public static AfnrTypeEnum getEnum(String key) {
        for (AfnrTypeEnum c : AfnrTypeEnum.values()) {
            if (c.getkey().equals(key)) {
                return c;
            }
        }
        return AfnrTypeEnum.Unknown;
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
