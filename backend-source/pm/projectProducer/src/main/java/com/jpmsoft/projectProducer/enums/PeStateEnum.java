package com.jpmsoft.projectProducer.enums;

/**
 * 上会状态
 */
public enum PeStateEnum {

    NOT("未上会", 0),
    Propose("提请上会", 1),
    Aldeay("已经上会", 2);

    private String name;
    private Integer index;

    PeStateEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (PeStateEnum c : PeStateEnum.values()) {
            if (c.getIndex() == index) {
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

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
