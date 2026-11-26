package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 多合同变更/签证
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("multi_contract_con_ec")
public class MultiContractConEc {

    // 主键id
    @TableId(type = IdType.INPUT)
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

    // 工程变更类型，1一般工程变更，2重大工程变更
    private Integer engineerChangeType;

    // 申报金额
    private BigDecimal applyMoney;

    // 累计申报金额
    private BigDecimal applyMoneyTotal;

}
