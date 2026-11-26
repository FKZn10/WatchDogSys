package com.jpmsoft.userProducer.enums;

public enum SequenceTypeEnum {
    TIME("时间", "01"),
    UUID("UUID", "02"),
    SQL("SQL", "03"),
    CONST("常量", "04"),
    AUTO("自增值", "05"),
    CLASS("Class类", "06"),
    PROJECT("项目编码", "07"),
    CONTRACT("合同编码", "08"),
    SECINFO("标段信息", "09"),
    CURRENT("当前表单信息", "10"),
    LINKED("链表信息", "11"),
    INTERFACE("接口", "12");

    private String name;
    private String key;

    SequenceTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (SequenceTypeEnum c : SequenceTypeEnum.values()) {
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
