package com.jpmsoft.common.model.response.Contract;


import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程联系单
 */

public class ConContactListResponse extends ProjectCommonRes {

    private String clId;
    private String conId;
    private String clCode;
    private String clType;
    private Integer clNeedCost;
    private BigDecimal clCost;
    private String clLocation;
    private String clDrawingNo;
    private String clTitle;
    private String clContent;
    private String clCreateUid;
    private String clCreateUname;
    private Date clCreateTime;
    private Integer clState;
    private Date clStateTime;
    private String clWithSupUnit;

    private String conName;
    private String pId;
    private String conCode;
    private String conMoneyTotal;
    private String conPartyBId;
    private String conPartyBName;
    private String conPartyBAbbName;

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
     * 云上建工用户id
     */
    private String thirdUserId;

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
     * 项目类别（0-A类，1-B类，3-C类）
     */
    private String projectCategory;

    /**
     * 事由
     */
    private String reason;

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

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
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

    public String getClId() {
        return clId;
    }

    public void setClId(String clId) {
        this.clId = clId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getClCode() {
        return clCode;
    }

    public void setClCode(String clCode) {
        this.clCode = clCode;
    }

    public String getClType() {
        return clType;
    }

    public void setClType(String clType) {
        this.clType = clType;
    }

    public Integer getClNeedCost() {
        return clNeedCost;
    }

    public void setClNeedCost(Integer clNeedCost) {
        this.clNeedCost = clNeedCost;
    }

    public BigDecimal getClCost() {
        return clCost;
    }

    public void setClCost(BigDecimal clCost) {
        this.clCost = clCost;
    }

    public String getClLocation() {
        return clLocation;
    }

    public void setClLocation(String clLocation) {
        this.clLocation = clLocation;
    }

    public String getClDrawingNo() {
        return clDrawingNo;
    }

    public void setClDrawingNo(String clDrawingNo) {
        this.clDrawingNo = clDrawingNo;
    }

    public String getClTitle() {
        return clTitle;
    }

    public void setClTitle(String clTitle) {
        this.clTitle = clTitle;
    }

    public String getClContent() {
        return clContent;
    }

    public void setClContent(String clContent) {
        this.clContent = clContent;
    }

    public String getClCreateUid() {
        return clCreateUid;
    }

    public void setClCreateUid(String clCreateUid) {
        this.clCreateUid = clCreateUid;
    }

    public String getClCreateUname() {
        return clCreateUname;
    }

    public void setClCreateUname(String clCreateUname) {
        this.clCreateUname = clCreateUname;
    }

    public Date getClCreateTime() {
        return clCreateTime;
    }

    public void setClCreateTime(Date clCreateTime) {
        this.clCreateTime = clCreateTime;
    }

    public Integer getClState() {
        return clState;
    }

    public void setClState(Integer clState) {
        this.clState = clState;
    }

    public Date getClStateTime() {
        return clStateTime;
    }

    public void setClStateTime(Date clStateTime) {
        this.clStateTime = clStateTime;
    }

    public String getClWithSupUnit() {
        return clWithSupUnit;
    }

    public void setClWithSupUnit(String clWithSupUnit) {
        this.clWithSupUnit = clWithSupUnit;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }


    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConCode() {
        return conCode;
    }

    public void setConCode(String conCode) {
        this.conCode = conCode;
    }

    public String getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(String conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }

    public String getConPartyBId() {
        return conPartyBId;
    }

    public void setConPartyBId(String conPartyBId) {
        this.conPartyBId = conPartyBId;
    }

    public String getConPartyBName() {
        return conPartyBName;
    }

    public void setConPartyBName(String conPartyBName) {
        this.conPartyBName = conPartyBName;
    }
    
    public String getConPartyBAbbName() {
        return conPartyBAbbName;
    }

    public void setConPartyBAbbName(String conPartyBAbbName) {
        this.conPartyBAbbName = conPartyBAbbName;
    }
}
