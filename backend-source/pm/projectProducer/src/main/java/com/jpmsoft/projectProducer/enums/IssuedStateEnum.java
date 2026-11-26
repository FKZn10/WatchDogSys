package com.jpmsoft.projectProducer.enums;

/**
 * 开具状态
 */
public enum IssuedStateEnum {

    Not_Started("申请中", 0),
    Started("未开具", 1),
    Already("已开具", 2),
    Invalid("已作废", 3),
    RollBack("已回收", 4),
    Stop("已停止", 5),
    Ingored("无需开具", 6);

    private String name;
    private Integer index;

    IssuedStateEnum(String name,Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (IssuedStateEnum c : IssuedStateEnum.values()) {
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
