package com.jpmsoft.projectProducer.enums;

public enum ApprovalProjectStateEnum {

    NotStarted("未启动", 0),
    PendingReview("待初审", 1),
    ToBeReview("待评审", 2),
    //已评审
    Reviewed("已评审", 3),
    //已入库
    Archived("已入库", 4),
    //退回
    Rejected("退回", 5);

    private String name;
    private Integer index;

    ApprovalProjectStateEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (DeliveryStatusEnum c : DeliveryStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.getName();
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
