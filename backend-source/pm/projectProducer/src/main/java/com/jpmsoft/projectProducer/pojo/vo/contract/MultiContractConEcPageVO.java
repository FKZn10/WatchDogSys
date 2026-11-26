package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class MultiContractConEcPageVO {

    // 主键id
    private String uuid;

    // 项目id
    private String projectId;

    // 变更/签证类型，1变更，2签证
    private String ecChangeType;

    // 原因
    private String reason;

    // 内容
    private String content;

    // 备注
    private String remark;

    // 是否需要概算部门审批，0否，1是
    private Integer needEstimateDepartmentApproval;

    // 审批文件关联id
    private String approvalFileRelationId;

    // 变更依据、技术方案、变更设计文件关联id
    private String changeBasisAndTechSolutionAndChangeDesignFileRelationId;

    // 变更组价文件关联id
    private String changeGroupFileRelationId;

    // 签证组价文件关联id
    private String visaGroupFileRelationId;

    // 审批状态
    private Integer state;

    // 审批时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date stateTime;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    // 创建人id
    private String createUid;

    // 创建人名称
    private String createUname;

    // 项目名称
    private String projectName;

}
