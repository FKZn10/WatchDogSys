package com.jpmsoft.common.enums;

@Deprecated
public enum FlowStatusEnum {

    Null("未发起流程", 0),
    Send("流程审批中", 1),
    SendBackStart("流程退回", 2),
    Stop("流程停止", 3),
    New("流程已启动", 4),
    Wait("流程等待中", 5),
    Pause("流程暂停中", 6),
    Finish("流程结束", 7),
    ChangeFlow("流程改变中", 8),
    SendBack("流程退回", 9);
    private String name;
    private Integer index;

    FlowStatusEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (FlowStatusEnum c : FlowStatusEnum.values()) {
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
