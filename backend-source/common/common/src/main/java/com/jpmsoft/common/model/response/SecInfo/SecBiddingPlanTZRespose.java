package com.jpmsoft.common.model.response.SecInfo;

import java.math.BigDecimal;
import java.util.Date;

public class SecBiddingPlanTZRespose {

    private String bpId;
    //计划名称
    private String bpName;
    //年度
    private Integer bpYear;
    //月度
    private Integer bpMonth;
    //备注
    private String bpRemark;
    private String bpCreateUid;
    private String bpCreateUname;
    private Date bpCreateTime;
    private Date stateTime;
    private Integer state;
    //招标人Id
    private String bpTenderee;
    //批复文件标题
    private String bpApprovalTitle;
    //批复编号
    private String bpApprovalNum;
    //批复时间
    private Date bpApprovalTime;
    //批复文件
    private String bpApprovalFile;
    private String teId;

    //招标开始时间
    private Date bpSecTime;
    //申请单位
    private String bpCid;
    //申请部门
    private String bpDid;
    //单位性质
    private String bpCnature;
    //部门性质
    private String bpDnature;
    //申请日期
    private Date bpTime;
    //招标内容
    private String bpSecContent;
    //估算价
    private BigDecimal bpGsAmt;
    //招标方式
    private String bpBiddingType;
    //招标完成时间
    private Date bpEndTime;
    //标段名称
    private String bpSecName;
    //是否工程招标
    private Boolean bpGczb;
    //项目规模
    private String bpProScale;
    //项目总投资（万元）
    private BigDecimal bpProAmt;
    //资金来源
    private String bpFundSource;
    //项目范围及工程概况
    private String bpScopeOverview;
    //说明
    private String bpSm;
    //概算金额
    private BigDecimal bpEsAmt;
    //委托类型（JPMsoft_WEITUOLX）
    private String bpEntrustmentType;
    /**
     * 集团是否为见证方 1：是 0：否
     */
    private Integer bpGroupWitness;
    /**
     * 项目投资控制情况
     */
    private String bpInvControlSituation;
    /**
     * 数据完整性签名
     */
    private String bpSign;

    //项目id
    private String pId;
    //项目编码
    private String pCode;
    //项目名称
    private String pName;
    //建设模式
    private String pBuildModel;
    //专业类别
    private String pType;
    //业主单位（主管单位/建设单位/使用单位）
    private String pUseUnit;
    //代建单位/实施单位
    private String pImpCompany;
    //全过程咨询/项目管理单位
    private String pCompetentDepartment;

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpName() {
        return bpName;
    }

    public void setBpName(String bpName) {
        this.bpName = bpName;
    }

    public Integer getBpYear() {
        return bpYear;
    }

    public void setBpYear(Integer bpYear) {
        this.bpYear = bpYear;
    }

    public Integer getBpMonth() {
        return bpMonth;
    }

    public void setBpMonth(Integer bpMonth) {
        this.bpMonth = bpMonth;
    }

    public String getBpRemark() {
        return bpRemark;
    }

    public void setBpRemark(String bpRemark) {
        this.bpRemark = bpRemark;
    }

    public String getBpCreateUid() {
        return bpCreateUid;
    }

    public void setBpCreateUid(String bpCreateUid) {
        this.bpCreateUid = bpCreateUid;
    }

    public String getBpCreateUname() {
        return bpCreateUname;
    }

    public void setBpCreateUname(String bpCreateUname) {
        this.bpCreateUname = bpCreateUname;
    }

    public Date getBpCreateTime() {
        return bpCreateTime;
    }

    public void setBpCreateTime(Date bpCreateTime) {
        this.bpCreateTime = bpCreateTime;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getBpTenderee() {
        return bpTenderee;
    }

    public void setBpTenderee(String bpTenderee) {
        this.bpTenderee = bpTenderee;
    }

    public String getBpApprovalTitle() {
        return bpApprovalTitle;
    }

    public void setBpApprovalTitle(String bpApprovalTitle) {
        this.bpApprovalTitle = bpApprovalTitle;
    }

    public String getBpApprovalNum() {
        return bpApprovalNum;
    }

    public void setBpApprovalNum(String bpApprovalNum) {
        this.bpApprovalNum = bpApprovalNum;
    }

    public Date getBpApprovalTime() {
        return bpApprovalTime;
    }

    public void setBpApprovalTime(Date bpApprovalTime) {
        this.bpApprovalTime = bpApprovalTime;
    }

    public String getBpApprovalFile() {
        return bpApprovalFile;
    }

    public void setBpApprovalFile(String bpApprovalFile) {
        this.bpApprovalFile = bpApprovalFile;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public Date getBpSecTime() {
        return bpSecTime;
    }

    public void setBpSecTime(Date bpSecTime) {
        this.bpSecTime = bpSecTime;
    }

    public String getBpCid() {
        return bpCid;
    }

    public void setBpCid(String bpCid) {
        this.bpCid = bpCid;
    }

    public String getBpDid() {
        return bpDid;
    }

    public void setBpDid(String bpDid) {
        this.bpDid = bpDid;
    }

    public String getBpCnature() {
        return bpCnature;
    }

    public void setBpCnature(String bpCnature) {
        this.bpCnature = bpCnature;
    }

    public String getBpDnature() {
        return bpDnature;
    }

    public void setBpDnature(String bpDnature) {
        this.bpDnature = bpDnature;
    }

    public Date getBpTime() {
        return bpTime;
    }

    public void setBpTime(Date bpTime) {
        this.bpTime = bpTime;
    }

    public String getBpSecContent() {
        return bpSecContent;
    }

    public void setBpSecContent(String bpSecContent) {
        this.bpSecContent = bpSecContent;
    }

    public BigDecimal getBpGsAmt() {
        return bpGsAmt;
    }

    public void setBpGsAmt(BigDecimal bpGsAmt) {
        this.bpGsAmt = bpGsAmt;
    }

    public String getBpBiddingType() {
        return bpBiddingType;
    }

    public void setBpBiddingType(String bpBiddingType) {
        this.bpBiddingType = bpBiddingType;
    }

    public Date getBpEndTime() {
        return bpEndTime;
    }

    public void setBpEndTime(Date bpEndTime) {
        this.bpEndTime = bpEndTime;
    }

    public String getBpSecName() {
        return bpSecName;
    }

    public void setBpSecName(String bpSecName) {
        this.bpSecName = bpSecName;
    }

    public Boolean getBpGczb() {
        return bpGczb;
    }

    public void setBpGczb(Boolean bpGczb) {
        this.bpGczb = bpGczb;
    }

    public String getBpProScale() {
        return bpProScale;
    }

    public void setBpProScale(String bpProScale) {
        this.bpProScale = bpProScale;
    }

    public BigDecimal getBpProAmt() {
        return bpProAmt;
    }

    public void setBpProAmt(BigDecimal bpProAmt) {
        this.bpProAmt = bpProAmt;
    }

    public String getBpFundSource() {
        return bpFundSource;
    }

    public void setBpFundSource(String bpFundSource) {
        this.bpFundSource = bpFundSource;
    }

    public String getBpScopeOverview() {
        return bpScopeOverview;
    }

    public void setBpScopeOverview(String bpScopeOverview) {
        this.bpScopeOverview = bpScopeOverview;
    }

    public String getBpSm() {
        return bpSm;
    }

    public void setBpSm(String bpSm) {
        this.bpSm = bpSm;
    }

    public BigDecimal getBpEsAmt() {
        return bpEsAmt;
    }

    public void setBpEsAmt(BigDecimal bpEsAmt) {
        this.bpEsAmt = bpEsAmt;
    }

    public String getBpEntrustmentType() {
        return bpEntrustmentType;
    }

    public void setBpEntrustmentType(String bpEntrustmentType) {
        this.bpEntrustmentType = bpEntrustmentType;
    }

    public Integer getBpGroupWitness() {
        return bpGroupWitness;
    }

    public void setBpGroupWitness(Integer bpGroupWitness) {
        this.bpGroupWitness = bpGroupWitness;
    }

    public String getBpInvControlSituation() {
        return bpInvControlSituation;
    }

    public void setBpInvControlSituation(String bpInvControlSituation) {
        this.bpInvControlSituation = bpInvControlSituation;
    }

    public String getBpSign() {
        return bpSign;
    }

    public void setBpSign(String bpSign) {
        this.bpSign = bpSign;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpBuildModel() {
        return pBuildModel;
    }

    public void setpBuildModel(String pBuildModel) {
        this.pBuildModel = pBuildModel;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getpUseUnit() {
        return pUseUnit;
    }

    public void setpUseUnit(String pUseUnit) {
        this.pUseUnit = pUseUnit;
    }

    public String getpImpCompany() {
        return pImpCompany;
    }

    public void setpImpCompany(String pImpCompany) {
        this.pImpCompany = pImpCompany;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }
}
