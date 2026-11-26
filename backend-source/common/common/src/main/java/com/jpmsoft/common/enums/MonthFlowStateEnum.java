package com.jpmsoft.common.enums;

public enum MonthFlowStateEnum {
    NOT_SUBMITTED("未提交", "01"),
    SAVED("保存", "02"),
    REJECTED_BY_COMPANY_PLAN_ADMIN("被企业计划管理员驳回", "03"),
    PENDING_COMPANY_PLAN_ADMIN_APPROVAL("企业计划管理员待审批", "04"),
    REJECTED_BY_GROUP_ENGINEERING_OFFICER("被集团工程部经办人驳回", "05"),
    PENDING_GROUP_ENGINEERING_OFFICER_APPROVAL("集团工程部经办人待审批", "06"),
    REJECTED_BY_GROUP_PLAN_ADMIN("被集团计划管理员驳回", "07"),
    PENDING_GROUP_PLAN_ADMIN_APPROVAL("集团计划管理员待审批", "08"),
    COMPLETED("完结", "09");

    private String title;
    private String value;

    MonthFlowStateEnum(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public static String getTitle(String value) {
        for (MonthFlowStateEnum c : MonthFlowStateEnum.values()) {
            if (c.getValue().equals(value)) {
                return c.title;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
