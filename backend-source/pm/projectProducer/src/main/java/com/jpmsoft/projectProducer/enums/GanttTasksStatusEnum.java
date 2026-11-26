package com.jpmsoft.projectProducer.enums;

/**
 * 进度计划 任务状态
 */
public enum GanttTasksStatusEnum {

    Cancel("已取消", -1),
    UnStart("未开始", 0),
    Normal("正常/办理中", 1),
    Complete("完成", 2),
    Temporary("临期", 3),
    Overdue("逾期", 4),
    After_Overdue("逾期后完成", 5),
    Suspend("暂停", 6);

    private String name;
    private Integer index;

    GanttTasksStatusEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (GanttTasksStatusEnum c : GanttTasksStatusEnum.values()) {
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
