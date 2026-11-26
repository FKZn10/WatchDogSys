package com.jpmsoft.common.enums;

import java.util.Objects;

public enum MaintenancelStatusEnum {
    Repair_Project_Application_Approval("修缮项目申报审批", "01"),
    Repair_Project_Application_Approval_Finish("修缮项目申报完成", "02"),
    Construction_Completion_Confirmation_Approval("施工完成确认审批", "03"),
    Construction_Completion_Confirmation_Finish("施工完成确认完成", "04"),
    Settlement_Submission_Approval("结算送审审批", "05"),
    Settlement_Submission_Finish("结算送审完成", "06"),
    Suspending_Payment_Approval("款项支付审批", "07"),
    Suspending_Payment_Finish("款项支付完成", "08"),
    Warranty_Payment_Approval("质保金支付审批", "09"),
    Warranty_Payment_Finish("质保金支付完成", "10");

    private String name;
    private String index;

    MaintenancelStatusEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(String index) {
        for (MaintenancelStatusEnum c : MaintenancelStatusEnum.values()) {
            if (Objects.equals(c.getIndex(), index)) {
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
