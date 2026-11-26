package com.jpmsoft.projectProducer.pojo.dto.other;

import lombok.Data;

@Data
public class OuterRegisterActionUserDTO {

    /**
     * 外部用户注册发起人ID
     */
    private String outerCreateUid;
    private String outerCreateUname;
    /**
     * 外部用户注册审批人ID
     */
    private String outerApproveUid;
    private String outerApproveUname;
}
