package com.jpmsoft.common.enums;

/**
 * 审批流程状态
 */
public enum ApprovalStatusEnum {

    Not_Started("未发起流程", 0),
    Under_Approval("流程审批中", 1),
    Returned_Initiator("流程退回发起人", 2),
    Invalid("流程停止", 3),
    Started("流程已启动", 4),
    Waiting("流程等待中", 5),
    Suspend("流程暂停中", 6),
    Already_Over("流程结束", 7),
    Process_Change("流程改变中", 8),
    Returned("流程已退回", 9);

    private String name;
    private Integer index;

    ApprovalStatusEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (ApprovalStatusEnum c : ApprovalStatusEnum.values()) {
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
