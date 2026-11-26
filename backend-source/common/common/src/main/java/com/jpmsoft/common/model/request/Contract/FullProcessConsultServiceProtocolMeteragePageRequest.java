package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.annotations.ClassInfo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class FullProcessConsultServiceProtocolMeteragePageRequest {

    private Integer pageNum;

    private Integer pageSize;

    @ClassInfo(title = "项目名称", type = "string", value = "projectNameList", dbValue = "projectName", no = 1)
    private List<String> projectNameList;

    @ClassInfo(title = "合同名称", type = "string", value = "contractNameList", dbValue = "contractName", no = 2)
    private List<String> contractNameList;

    // 合同金额
    @ClassInfo(title = "合同金额", type = "money", value = "contractAmountList", dbValue = "contractAmount",no = 3)
    private List<BigDecimal> contractAmountList;

    // 本期计划付款
    @ClassInfo(title = "本期计划付款", type = "money", value = "thisStagePlanPayAmountList", dbValue = "thisStagePlanPayAmount",no = 4)
    private List<BigDecimal> thisStagePlanPayAmountList;

    // 本期应付款
    @ClassInfo(title = "本期应付款", type = "money", value = "shouldPayAmountTotalList", dbValue = "shouldPayAmountTotal",no = 5)
    private List<BigDecimal> shouldPayAmountTotalList;

    // 累计合同支付比例
    @ClassInfo(title = "累计合同支付比例", type = "number", value = "totalPayRate", dbValue = "totalPayRate",no = 6)
    private BigDecimal totalPayRate;

    @ClassInfo(title = "流程状态",type="dictionary",value = "stateList",dictionary = "JPMsoft_LCZT",dbValue = "state",no = 7)
    private List<Integer> stateList;

    private String contractName;

    private String projectName;

    private List<String> projectIds;

    @ClassInfo(title = "项目编码", type = "string", value = "projectCodeList", dbValue = "projectCode")
    private List<String> projectCodeList;

    @ClassInfo(title = "所属区域", type = "config", value = "projectRegion", dictionary = "projectArea", dbValue = "projectRegion")
    private String projectRegion;

    @ClassInfo(title = "项目经度(X)", type = "number", value = "projectLongitude", dbValue = "projectLongitude")
    private Float projectLongitude;
    @ClassInfo(title = "项目纬度(Y)", type = "number", value = "projectLatitude", dbValue = "projectLatitude")
    private Float projectLatitude;
    @ClassInfo(title = "项目概况", type = "string", value = "projectOverviewList", dbValue = "projectOverview")
    private List<String> projectOverviewList;

//    @ClassInfo(title = "项目管理单位", type = "organization", value = "projectCompetentDepartment", dbValue = "projectConstructionUnit", no = 9)
    private String projectConstructionUnit;

    @ClassInfo(title = "批复投资估算", type = "money", value = "projectApproveBudgetList", dbValue = "projectApproveBudget")
    private List<BigDecimal> projectApproveBudgetList;
    @ClassInfo(title = "批复文号", type = "string", value = "projectApproveBudgetCodeList", dbValue = "projectApproveBudgetCode")
    private List<String> projectApproveBudgetCodeList;

    @ClassInfo(title = "批复日期", type = "date", value = "projectApproveBudgetDate", dbValue = "projectApproveBudgetDate")
    private Date projectApproveBudgetDate;

    @ClassInfo(title = "创建人", type = "string", value = "createUnameList", dbValue = "createUname", no = 10)
    private List<String> createUnameList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ClassInfo(title = "创建时间", type = "date", value = "createTime", dbValue = "createTime", no = 11)
    private Date createTime;

    @ClassInfo(title = "建设模式",type="dictionary",value = "projectBuildModelList",dictionary = "JPMsoft_JSMS",dbValue = "projectBuildModel",no = 3)
    private List<String> projectBuildModelList;

    @ClassInfo(title = "立项形式",type="dictionary",value = "projectFormTypeList",dictionary = "JPMsoft_LXXS",dbValue = "projectFormType")
    private List<String> projectFormTypeList;

    @ClassInfo(title = "项目代码",type="string",value = "projectNumberList",dbValue = "projectNumber")
    private List<String> projectNumberList;

    @ClassInfo(title = "批复投资估算",type="money",value = "approvalInvestmentAmountList",dbValue = "approvalInvestmentAmount")
    private List<BigDecimal> approvalInvestmentAmountList;

//    @ClassInfo(title = "可研申报单位",type="company",value = "canYanApplyUnitList",dbValue = "canYanApplyUnit")
    private List<String> canYanApplyUnitList;

//    @ClassInfo(title = "可研审批单位",type="company",value = "canYanApprovalUnitList",dbValue = "canYanApprovalUnit")
    private List<String> canYanApprovalUnitList;

    @ClassInfo(title = "备案证号",type="string",value = "recordNumList",dbValue = "recordNum")
    private List<String> recordNumList;

    @ClassInfo(title = "备案日期",type="date",value = "recordDate",dbValue = "recordDate")
    private Date recordDate;

    @ClassInfo(title = "备案总投资",type="money",value = "recordAmountList",dbValue = "recordAmount")
    private List<BigDecimal> recordAmountList;

//    @ClassInfo(title = "项目法人单位",type="company",value = "projectLegalUnitList",dbValue = "projectLegalUnit")
    private List<String> projectLegalUnitList;

//    @ClassInfo(title = "备案证核发单位",type="company",value = "recordIssueUnitList",dbValue = "recordIssueUnit")
    private List<String> recordIssueUnitList;

    @ClassInfo(title = "立项日期",type="date",value = "projectApplyDate",dbValue = "projectApplyDate")
    private Date projectApplyDate;

    @ClassInfo(title = "建设年限",type="string",value = "projectBuildYearList",dbValue = "projectBuildYear")
    private List<String> projectBuildYearList;

    @ClassInfo(title = "投资估算",type="money",value = "projectApprovalAmountList",dbValue = "projectApprovalAmount")
    private List<BigDecimal> projectApprovalAmountList;
    //建设形式
    @ClassInfo(title = "建设形式",type="dictionary",value = "projectBuildTypeList",dictionary = "JPMsoft_JSXS",dbValue = "projectBuildType")
    private List<String> projectBuildTypeList;
    //专业分类
    @ClassInfo(title = "专业分类",type="dictionary",value = "projectProfessionalTypeList",dictionary = "JPMsoft_XMLB",dbValue = "projectProfessionalType")
    private List<String> projectProfessionalTypeList;
    //移交接收单位
//    @ClassInfo(title = "移交接收单位",type="company",value = "projectTransferUnitList",dbValue = "projectTransferUnit")
    private List<String> projectTransferUnitList;
    //项目经理
//    @ClassInfo(title = "项目经理",type="person",value = "projectProjectManagerList",dbValue = "projectProjectManager")
    private List<String> projectProjectManagerList;
    //项目分管领导
//    @ClassInfo(title = "项目分管领导",type="person",value = "projectProjectLeaderList",dbValue = "projectProjectLeader")
    private List<String> projectProjectLeaderList;
//    @ClassInfo(title = "管理单位所属部门",type="department",value = "projectDepartment",dbValue = "projectDepartment")
    private String projectDepartment;
    //工程部经办人
//    @ClassInfo(title = "工程部经办人",type="person",value = "projectEngineerList",dbValue = "projectEngineer")
    private List<String>  projectEngineerList;
    //工程部分管领导
//    @ClassInfo(title = "工程部分管领导",type="person",value = "projectEngineerLeaderList",dbValue = "projectEngineerLeader")
    private List<String>  projectEngineerLeaderList;

}
