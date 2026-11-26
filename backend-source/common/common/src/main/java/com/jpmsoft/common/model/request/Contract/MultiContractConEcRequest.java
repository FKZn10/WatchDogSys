package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class MultiContractConEcRequest extends BaseFileRequest {

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
    private String approvalFileRelationIds;

    // 变更依据、技术方案、变更设计文件关联id
    private String changeAndTechSolutionAndChangeDesignFileRelationId;
    private String changeAndTechSolutionAndChangeDesignFileRelationIds;

    // 变更组价文件关联id
    private String changeGroupFileRelationId;
    private String changeGroupFileRelationIds;

    // 签证组价文件关联id
    private String visaGroupFileRelationId;
    private String visaGroupFileRelationIds;

    // 其他附件关联id
    private String otherFileRelationId;
    private String otherFileRelationIds;

    // 审批状态
    private Integer state;

    // 审批时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date stateTime;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    // 创建人id
    private String createUid;

    // 创建人名称
    private String createUname;

    // 变更签证列表
    private List<ConEcRequest> conEcRequestList;

    // 工程变更类型
    private Integer engineerChangeType;

    // 申报金额
    private BigDecimal applyMoney;

    // 累计申报金额
    private BigDecimal applyMoneyTotal;

    // 文件标题
    private String fileTitle;

}
