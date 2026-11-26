package com.jpmsoft.projectProducer.enums;

/**
 * 核销状态
 */
public enum VerifyStateEnum {

    Not_Started("申请中", 0),
    Started("未核销", 1),
    Already("核销成功", 2),
    RollBack("核销退回", 3),
    Stop("已停止", 4);

    private String name;
    private Integer index;

    VerifyStateEnum(String name,Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (VerifyStateEnum c : VerifyStateEnum.values()) {
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
