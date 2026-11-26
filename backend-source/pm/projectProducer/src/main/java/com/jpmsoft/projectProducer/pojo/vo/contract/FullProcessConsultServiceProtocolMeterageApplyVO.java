package com.jpmsoft.projectProducer.pojo.vo.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FullProcessConsultServiceProtocolMeterageApplyVO {

    private String id;

    // 项目id
    private String projectId;

    private String projectName;

    // 合同id
    private String contractId;

    private String contractName;

    // 合同金额
    private BigDecimal contractAmount;

    // 本期计划付款
    private BigDecimal thisStagePlanPayAmount;

    // 本期应付款
    private BigDecimal shouldPayAmountTotal;

    // 累计合同支付比例
    private BigDecimal totalPayRate;

    //是否含有合同金额明细；没金额明细说明没走通用合同；
    private boolean conMoneyDtlFlag;

    // 流程状态
    private Integer state;

    // 审批时间
    private Date stateTime;

    // 创建人id
    private String createUid;

    // 创建人名称
    private String createUname;

    // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;

    // 填报日期
    private Date fillDate;

    
    private String projectCode;

    
    private String projectRegion;

    
    private Float projectLongitude;
    
    private Float projectLatitude;
    
    private String projectOverview;

    
    private String projectConstructionUnit;

    
    private BigDecimal projectApproveBudget;
    
    private String projectApproveBudgetCode;

    
    private Date projectApproveBudgetDate;
    

    
    private String projectBuildModel;

    
    private String projectFormType;

    
    private String projectNumber;

    
    private BigDecimal approvalInvestmentAmount;

    
    private String canYanApplyUnit;

    
    private String canYanApprovalUnit;

    
    private String recordNum;

    
    private Date recordDate;

    
    private BigDecimal recordAmount;

    
    private String projectLegalUnit;

    
    private String recordIssueUnit;

    
    private Date projectApplyDate;

    
    private String projectBuildYear;

    
    private BigDecimal projectApprovalAmount;
    //建设形式
    
    private String projectBuildType;
    //专业分类
    
    private String projectProfessionalType;
    //移交接收单位
    
    private String projectTransferUnit;
    //项目经理
    
    private String projectProjectManager;
    //项目分管领导
    
    private String projectProjectLeader;
    
    private String projectDepartment;
    //工程部经办人
    
    private String  projectEngineer;
    //工程部分管领导
    
    private String  projectEngineerLeader;

}
