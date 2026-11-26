package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.jpmsoft.common.model.response.Contract.ConEcResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class MultiContractConEcDetailVO {

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
    private String changeAndTechSolutionAndChangeDesignFileRelationId;

    // 变更组价文件关联id
    private String changeGroupFileRelationId;

    // 签证组价文件关联id
    private String visaGroupFileRelationId;

    // 其他附件关联id
    private String otherFileRelationId;

    // 审批状态
    private Integer state;

    // 审批时间
    private Date stateTime;

    // 创建时间
    private Date createTime;

    // 创建人id
    private String createUid;

    // 创建人名称
    private String createUname;

    // 项目名称
    private String projectName;

    // 变更签证列表
    private List<ConEcResponse> conEcResponseList;

    // 工程变更类型，1一般工程变更，2重大工程变更
    private Integer engineerChangeType;

    // 申报金额
    private BigDecimal applyMoney;

    // 累计申报金额
    private BigDecimal applyMoneyTotal;
}
