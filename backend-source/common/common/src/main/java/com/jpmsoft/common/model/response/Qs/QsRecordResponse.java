package com.jpmsoft.common.model.response.Qs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 质量安全检查记录
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QsRecordResponse extends ProjectCommonRes {

    private static final long serialVersionUID = 7634814600131078477L;
    private String qsrId;
    private String qstId;
    private String pId;
    private String conId;
    private String qsrType;
    private String qsrPosition;
    private String qsrDes;
    private String qsrProblemNature;
    private String qsrRectifyRequire;
    private Date qsrTime;
    /**
     * 问题数量
     */
    private Integer qsrQuestionNum;
    private Date qsrRectifyRequireTime;
    /**
     * 实际整改时间
     */
    private Date qsrRectifyActualTime;
    private String qsrRectifyUid;
    private String qsrRectifyUame;
    private String rectifyUname;
    private String qsrSuperviseUid;
    private String qsrSuperviseUname;
    private Integer qsrShortMessage;
    private String qsrPic;
    private String qsrRectifyPic;
    private String qsrCreateUid;
    private String qsrCreateUname;
    private Date qsrCreateTime;
    private Date createTime;
    //修改人
    private String qsrUpdateUid;
    //修改时间
    private Date qsrUpdateTime;
    private Integer qsrState;
    private Date qsrStateTime;
    private Integer qsrCategory;
    private String qsrRectifyRealUname;
    private String qsrRectifyReply;
    private Date qsrRectifyFinishTime;
    private String qsrPatrolObject;
    /**
     * 巡检任务ID
     */
    private String ptId;
    /**
     * 问题类型（监理或施工）
     */
    private String qsrProblemType;
    /**
     * 责任单位
     */
    private String qsrDutyCpy;
    /**
     * 整改状态
     */
    private String qsrRectifyStatus;
    /**
     * 隐患等级
     */
    private String qsrHazardLevel;
    private String qsrLocation;
    private String qsrReasonType;
    private String qsrFormation;
    private String qsrCid;
    private String qsrDid;
    private String qsrCnature;
    private String qsrFzCid;
    private Boolean qsrHf;
    private Boolean qsrHfHg;
    private String qsrXdUid;
    private String qsrCname;
    private String qsrFzCname;
    private String qsrXdUname;

    private String qsrPicIds;
    private String qsrRectifyPicIds;
    private String conName;
    private String conCode;
    private BigDecimal conMoneyTotal;
    private String qstName;
    private String qstCode;
    private String qstItem;

    /**
     * 是否影响功能和安全使用的重大质量问题  0：否  1：是
     */
    private Integer qstIsImportant;
    /**
     * 全路径
     */
    private String qstPath;
    private String qsrDutyCpyName;

    /**
     * 责任人所属单位名称
     */
    private String responsibleUnitName;
    /**
     * 问题分类：质量问题或安全问题
     */
    private String problemCategory;
    /**
     * 问题总数
     */
    private Integer problemCount;
    /**
     * 已整改数量
     */
    private Integer rectifiedCount;
    /**
     * 整改率，单位：%
     */
    private BigDecimal rectificationRate;

    private String qrdCpyNames;
    private String qrdUserNames;
    private List<QsRecordDutyDTO> qsRecordDutys;

    /**
     * 业务表id: 风险评价表id
     */
    private String bizId;

    @JsonProperty("ptCode")
    private String ptCode;

    public String getPtCode() {
        return ptCode;
    }

    public void setPtCode(String ptCode) {
        this.ptCode = ptCode;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public String getQsrId() {
        return qsrId;
    }

    public void setQsrId(String qsrId) {
        this.qsrId = qsrId;
    }

    public String getQstId() {
        return qstId;
    }

    public void setQstId(String qstId) {
        this.qstId = qstId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getQsrType() {
        return qsrType;
    }

    public void setQsrType(String qsrType) {
        this.qsrType = qsrType;
    }

    public String getQsrPosition() {
        return qsrPosition;
    }

    public void setQsrPosition(String qsrPosition) {
        this.qsrPosition = qsrPosition;
    }

    public String getQsrDes() {
        return qsrDes;
    }

    public void setQsrDes(String qsrDes) {
        this.qsrDes = qsrDes;
    }

    public String getQsrProblemNature() {
        return qsrProblemNature;
    }

    public void setQsrProblemNature(String qsrProblemNature) {
        this.qsrProblemNature = qsrProblemNature;
    }

    public String getQsrRectifyRequire() {
        return qsrRectifyRequire;
    }

    public void setQsrRectifyRequire(String qsrRectifyRequire) {
        this.qsrRectifyRequire = qsrRectifyRequire;
    }

    public Date getQsrTime() {
        return qsrTime;
    }

    public void setQsrTime(Date qsrTime) {
        this.qsrTime = qsrTime;
    }

    public Integer getQsrQuestionNum() {
        return qsrQuestionNum;
    }

    public void setQsrQuestionNum(Integer qsrQuestionNum) {
        this.qsrQuestionNum = qsrQuestionNum;
    }

    public Date getQsrRectifyRequireTime() {
        return qsrRectifyRequireTime;
    }

    public void setQsrRectifyRequireTime(Date qsrRectifyRequireTime) {
        this.qsrRectifyRequireTime = qsrRectifyRequireTime;
    }

    public String getQsrRectifyUid() {
        return qsrRectifyUid;
    }

    public void setQsrRectifyUid(String qsrRectifyUid) {
        this.qsrRectifyUid = qsrRectifyUid;
    }

    public String getQsrRectifyUame() {
        return qsrRectifyUame;
    }

    public void setQsrRectifyUame(String qsrRectifyUame) {
        this.qsrRectifyUame = qsrRectifyUame;
    }

    public String getRectifyUname() {
        return rectifyUname;
    }

    public void setRectifyUname(String rectifyUname) {
        this.rectifyUname = rectifyUname;
    }

    public String getQsrSuperviseUid() {
        return qsrSuperviseUid;
    }

    public void setQsrSuperviseUid(String qsrSuperviseUid) {
        this.qsrSuperviseUid = qsrSuperviseUid;
    }

    public String getQsrSuperviseUname() {
        return qsrSuperviseUname;
    }

    public void setQsrSuperviseUname(String qsrSuperviseUname) {
        this.qsrSuperviseUname = qsrSuperviseUname;
    }

    public Integer getQsrShortMessage() {
        return qsrShortMessage;
    }

    public void setQsrShortMessage(Integer qsrShortMessage) {
        this.qsrShortMessage = qsrShortMessage;
    }

    public String getQsrPic() {
        return qsrPic;
    }

    public void setQsrPic(String qsrPic) {
        this.qsrPic = qsrPic;
    }

    public String getQsrRectifyPic() {
        return qsrRectifyPic;
    }

    public void setQsrRectifyPic(String qsrRectifyPic) {
        this.qsrRectifyPic = qsrRectifyPic;
    }

    public String getQsrCreateUid() {
        return qsrCreateUid;
    }

    public void setQsrCreateUid(String qsrCreateUid) {
        this.qsrCreateUid = qsrCreateUid;
    }

    public String getQsrCreateUname() {
        return qsrCreateUname;
    }

    public void setQsrCreateUname(String qsrCreateUname) {
        this.qsrCreateUname = qsrCreateUname;
    }

    public Date getQsrCreateTime() {
        return qsrCreateTime;
    }

    public void setQsrCreateTime(Date qsrCreateTime) {
        this.qsrCreateTime = qsrCreateTime;
    }

    public String getQsrUpdateUid() {
        return qsrUpdateUid;
    }

    public void setQsrUpdateUid(String qsrUpdateUid) {
        this.qsrUpdateUid = qsrUpdateUid;
    }

    public Date getQsrUpdateTime() {
        return qsrUpdateTime;
    }

    public void setQsrUpdateTime(Date qsrUpdateTime) {
        this.qsrUpdateTime = qsrUpdateTime;
    }

    public Integer getQsrState() {
        return qsrState;
    }

    public void setQsrState(Integer qsrState) {
        this.qsrState = qsrState;
    }

    public Date getQsrStateTime() {
        return qsrStateTime;
    }

    public void setQsrStateTime(Date qsrStateTime) {
        this.qsrStateTime = qsrStateTime;
    }

    public Integer getQsrCategory() {
        return qsrCategory;
    }

    public void setQsrCategory(Integer qsrCategory) {
        this.qsrCategory = qsrCategory;
    }

    public String getQsrRectifyRealUname() {
        return qsrRectifyRealUname;
    }

    public void setQsrRectifyRealUname(String qsrRectifyRealUname) {
        this.qsrRectifyRealUname = qsrRectifyRealUname;
    }

    public String getQsrRectifyReply() {
        return qsrRectifyReply;
    }

    public void setQsrRectifyReply(String qsrRectifyReply) {
        this.qsrRectifyReply = qsrRectifyReply;
    }

    public Date getQsrRectifyFinishTime() {
        return qsrRectifyFinishTime;
    }

    public void setQsrRectifyFinishTime(Date qsrRectifyFinishTime) {
        this.qsrRectifyFinishTime = qsrRectifyFinishTime;
    }

    public String getQsrPatrolObject() {
        return qsrPatrolObject;
    }

    public void setQsrPatrolObject(String qsrPatrolObject) {
        this.qsrPatrolObject = qsrPatrolObject;
    }

    public String getPtId() {
        return ptId;
    }

    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    public String getQsrProblemType() {
        return qsrProblemType;
    }

    public void setQsrProblemType(String qsrProblemType) {
        this.qsrProblemType = qsrProblemType;
    }

    public String getQsrDutyCpy() {
        return qsrDutyCpy;
    }

    public void setQsrDutyCpy(String qsrDutyCpy) {
        this.qsrDutyCpy = qsrDutyCpy;
    }

    public String getQsrRectifyStatus() {
        return qsrRectifyStatus;
    }

    public void setQsrRectifyStatus(String qsrRectifyStatus) {
        this.qsrRectifyStatus = qsrRectifyStatus;
    }

    public String getQsrHazardLevel() {
        return qsrHazardLevel;
    }

    public void setQsrHazardLevel(String qsrHazardLevel) {
        this.qsrHazardLevel = qsrHazardLevel;
    }

    public String getQsrLocation() {
        return qsrLocation;
    }

    public void setQsrLocation(String qsrLocation) {
        this.qsrLocation = qsrLocation;
    }

    public String getQsrReasonType() {
        return qsrReasonType;
    }

    public void setQsrReasonType(String qsrReasonType) {
        this.qsrReasonType = qsrReasonType;
    }

    public String getQsrFormation() {
        return qsrFormation;
    }

    public void setQsrFormation(String qsrFormation) {
        this.qsrFormation = qsrFormation;
    }

    public String getQsrCid() {
        return qsrCid;
    }

    public void setQsrCid(String qsrCid) {
        this.qsrCid = qsrCid;
    }

    public String getQsrDid() {
        return qsrDid;
    }

    public void setQsrDid(String qsrDid) {
        this.qsrDid = qsrDid;
    }

    public String getQsrCnature() {
        return qsrCnature;
    }

    public void setQsrCnature(String qsrCnature) {
        this.qsrCnature = qsrCnature;
    }

    public String getQsrFzCid() {
        return qsrFzCid;
    }

    public void setQsrFzCid(String qsrFzCid) {
        this.qsrFzCid = qsrFzCid;
    }

    public Boolean getQsrHf() {
        return qsrHf;
    }

    public void setQsrHf(Boolean qsrHf) {
        this.qsrHf = qsrHf;
    }

    public Boolean getQsrHfHg() {
        return qsrHfHg;
    }

    public void setQsrHfHg(Boolean qsrHfHg) {
        this.qsrHfHg = qsrHfHg;
    }

    public String getQsrXdUid() {
        return qsrXdUid;
    }

    public void setQsrXdUid(String qsrXdUid) {
        this.qsrXdUid = qsrXdUid;
    }

    public String getQsrCname() {
        return qsrCname;
    }

    public void setQsrCname(String qsrCname) {
        this.qsrCname = qsrCname;
    }

    public String getQsrFzCname() {
        return qsrFzCname;
    }

    public void setQsrFzCname(String qsrFzCname) {
        this.qsrFzCname = qsrFzCname;
    }

    public String getQsrXdUname() {
        return qsrXdUname;
    }

    public void setQsrXdUname(String qsrXdUname) {
        this.qsrXdUname = qsrXdUname;
    }

    public String getQsrPicIds() {
        return qsrPicIds;
    }

    public void setQsrPicIds(String qsrPicIds) {
        this.qsrPicIds = qsrPicIds;
    }

    public String getQsrRectifyPicIds() {
        return qsrRectifyPicIds;
    }

    public void setQsrRectifyPicIds(String qsrRectifyPicIds) {
        this.qsrRectifyPicIds = qsrRectifyPicIds;
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

    public BigDecimal getConMoneyTotal() {
        return conMoneyTotal;
    }

    public void setConMoneyTotal(BigDecimal conMoneyTotal) {
        this.conMoneyTotal = conMoneyTotal;
    }


    public String getQstName() {
        return qstName;
    }

    public void setQstName(String qstName) {
        this.qstName = qstName;
    }

    public String getQstCode() {
        return qstCode;
    }

    public void setQstCode(String qstCode) {
        this.qstCode = qstCode;
    }

    public String getQstItem() {
        return qstItem;
    }

    public void setQstItem(String qstItem) {
        this.qstItem = qstItem;
    }

    public String getQsrDutyCpyName() {
        return qsrDutyCpyName;
    }

    public void setQsrDutyCpyName(String qsrDutyCpyName) {
        this.qsrDutyCpyName = qsrDutyCpyName;
    }

    public String getQrdCpyNames() {
        return qrdCpyNames;
    }

    public void setQrdCpyNames(String qrdCpyNames) {
        this.qrdCpyNames = qrdCpyNames;
    }

    public String getQrdUserNames() {
        return qrdUserNames;
    }

    public void setQrdUserNames(String qrdUserNames) {
        this.qrdUserNames = qrdUserNames;
    }

    public List<QsRecordDutyDTO> getQsRecordDutys() {
        return qsRecordDutys;
    }

    public void setQsRecordDutys(List<QsRecordDutyDTO> qsRecordDutys) {
        this.qsRecordDutys = qsRecordDutys;
    }

    public Integer getQstIsImportant() {
        return qstIsImportant;
    }

    public void setQstIsImportant(Integer qstIsImportant) {
        this.qstIsImportant = qstIsImportant;
    }

    public String getQstPath() {
        return qstPath;
    }

    public void setQstPath(String qstPath) {
        this.qstPath = qstPath;
    }
}
