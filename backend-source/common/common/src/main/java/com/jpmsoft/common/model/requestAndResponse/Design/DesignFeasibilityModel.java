package com.jpmsoft.common.model.requestAndResponse.Design;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 可研报告
 */
public class DesignFeasibilityModel extends ProjectCommonRes {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String dfId;
    //可研报告名称
    private String dfName;
    //项目id
    private String pId;
    //投资估算
    private BigDecimal dfInvestment;
    //备注
    private String dfRemark;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String dfCreateUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String dfCreateUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date dfCreateTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer dfState;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date dfStateTime;
    //标段id
    private String secId;
    //编制单位
    private String dfUnit;
    //项目负责人
    private String dfPm;
    //可研范围
    private String dfScope;
    //开始时间
    private Date dfStart;
    //结束时间
    private Date dfEnd;
    //合同Id
    private String conId;
    //编制单位联系方式
    private String dfUnitTel;
    //批复时间
    private Date dfApprovalTime;
    /**
     *云上建工审批状态： 0-被驳回； 1-审批中； 3-审批通过
     */
    private String thirdStatus;

    /**
     * 云上建工审批意见
     */
    private String thirdApprovalComment;

    /**
     *
     */
    private String thirdRequestId;

    /**
     * 是否盖公司章
     */
    private Boolean companySealFlag;

    /**
     * 印章类别  0-公章，1-技术专用章
     *
     */
    private String sealCategory;

    /**
     * 专业审核 ： 0、道路 1、电气 2、房建 3、给排水 4、工程造价 5、公交规划 6、轨道交通 7、景观绿化 8、交通工程 9、交通规划
     * 10、交通综治 11、结构 12、路基路面 13、路线交叉 14、桥梁涵洞 15、燃气 16、水土保持 17、隧道 18、岩土 19、智能交通
     */
    private String professionalReview;

    /**
     * 项目类别：0、A类，1、B类，3、C类
     */
    private String projectCategory;

    /**
     * 事由
     */
    private String reason;

    /**
     * 云上建工用户id
     */
    private String thirdUserId;

    private String conName;
    private String conCode;
    private String secName;
    private String dfUnitName;


    private List<String> dfCreateTimes;
    //单位简称
    private String dfUnitAbbName;

    public String getDfId() {
        return dfId;
    }

    public void setDfId(String dfId) {
        this.dfId = dfId;
    }

    public String getDfName() {
        return dfName;
    }

    public void setDfName(String dfName) {
        this.dfName = dfName;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public BigDecimal getDfInvestment() {
        return dfInvestment;
    }

    public void setDfInvestment(BigDecimal dfInvestment) {
        this.dfInvestment = dfInvestment;
    }

    public String getDfRemark() {
        return dfRemark;
    }

    public void setDfRemark(String dfRemark) {
        this.dfRemark = dfRemark;
    }

    public String getDfCreateUid() {
        return dfCreateUid;
    }

    public void setDfCreateUid(String dfCreateUid) {
        this.dfCreateUid = dfCreateUid;
    }

    public String getDfCreateUname() {
        return dfCreateUname;
    }

    public void setDfCreateUname(String dfCreateUname) {
        this.dfCreateUname = dfCreateUname;
    }

    public Date getDfCreateTime() {
        return dfCreateTime;
    }

    public void setDfCreateTime(Date dfCreateTime) {
        this.dfCreateTime = dfCreateTime;
    }

    public Integer getDfState() {
        return dfState;
    }

    public void setDfState(Integer dfState) {
        this.dfState = dfState;
    }

    public Date getDfStateTime() {
        return dfStateTime;
    }

    public void setDfStateTime(Date dfStateTime) {
        this.dfStateTime = dfStateTime;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getDfUnit() {
        return dfUnit;
    }

    public void setDfUnit(String dfUnit) {
        this.dfUnit = dfUnit;
    }

    public String getDfPm() {
        return dfPm;
    }

    public void setDfPm(String dfPm) {
        this.dfPm = dfPm;
    }

    public String getDfScope() {
        return dfScope;
    }

    public void setDfScope(String dfScope) {
        this.dfScope = dfScope;
    }

    public Date getDfStart() {
        return dfStart;
    }

    public void setDfStart(Date dfStart) {
        this.dfStart = dfStart;
    }

    public Date getDfEnd() {
        return dfEnd;
    }

    public void setDfEnd(Date dfEnd) {
        this.dfEnd = dfEnd;
    }

    @Override
    public String getConId() {
        return conId;
    }

    @Override
    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getDfUnitTel() {
        return dfUnitTel;
    }

    public void setDfUnitTel(String dfUnitTel) {
        this.dfUnitTel = dfUnitTel;
    }

    public Date getDfApprovalTime() {
        return dfApprovalTime;
    }

    public void setDfApprovalTime(Date dfApprovalTime) {
        this.dfApprovalTime = dfApprovalTime;
    }

    public String getThirdStatus() {
        return thirdStatus;
    }

    public void setThirdStatus(String thirdStatus) {
        this.thirdStatus = thirdStatus;
    }

    public String getThirdApprovalComment() {
        return thirdApprovalComment;
    }

    public void setThirdApprovalComment(String thirdApprovalComment) {
        this.thirdApprovalComment = thirdApprovalComment;
    }

    public String getThirdRequestId() {
        return thirdRequestId;
    }

    public void setThirdRequestId(String thirdRequestId) {
        this.thirdRequestId = thirdRequestId;
    }

    public Boolean getCompanySealFlag() {
        return companySealFlag;
    }

    public void setCompanySealFlag(Boolean companySealFlag) {
        this.companySealFlag = companySealFlag;
    }

    public String getSealCategory() {
        return sealCategory;
    }

    public void setSealCategory(String sealCategory) {
        this.sealCategory = sealCategory;
    }

    public String getProfessionalReview() {
        return professionalReview;
    }

    public void setProfessionalReview(String professionalReview) {
        this.professionalReview = professionalReview;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getDfUnitName() {
        return dfUnitName;
    }

    public void setDfUnitName(String dfUnitName) {
        this.dfUnitName = dfUnitName;
    }

    public List<String> getDfCreateTimes() {
        return dfCreateTimes;
    }

    public void setDfCreateTimes(List<String> dfCreateTimes) {
        this.dfCreateTimes = dfCreateTimes;
    }

    public String getDfUnitAbbName() {
        return dfUnitAbbName;
    }

    public void setDfUnitAbbName(String dfUnitAbbName) {
        this.dfUnitAbbName = dfUnitAbbName;
    }
}
