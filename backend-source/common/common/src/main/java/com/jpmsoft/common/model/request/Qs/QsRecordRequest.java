package com.jpmsoft.common.model.request.Qs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 质量安全检查记录
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QsRecordRequest extends BaseFileRequest {

    private String qsrId;
    private String qstId;
    private String pId;
    private String conId;
    private String qsrType;
    private String qsrPosition;
    private String qsrDes;
    private String qsrProblemNature;

    /**
     * 整改要求
     */
    private String qsrRectifyRequire;
    /**
     * 检查时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date qsrTime;
    /**
     * 问题数量
     */
    private Integer qsrQuestionNum;
    /**
     * 要求整改时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date qsrRectifyRequireTime;
    /**
     * 实际整改时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date qsrRectifyActualTime;
    /**
     * 整改责任人ID
     */
    private String qsrRectifyUid;
    /**
     * 整改责任人姓名
     */
    private String qsrRectifyUame;
    /**
     * 整改督办人ID
     */
    private String qsrSuperviseUid;
    /**
     * 整改督办人姓名
     */
    private String qsrSuperviseUname;
    /**
     * 是否短信通知
     * 0：否
     * 1：是
     */
    private Integer qsrShortMessage;

    /**
     * 整改前照片
     */
    private String qsrPic;
    private String qsrRectifyPic;
    private String qsrCreateUid;
    private String qsrCreateUname;
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date qsrCreateTime;
    //修改人
    private String qsrUpdateUid;
    //修改时间
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date qsrUpdateTime;
    /**
     * 审批状态
     */
    private Integer qsrState;
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date qsrStateTime;
    /**
     * 类型
     * 0：质量
     * 1：安全
     */
    private Integer qsrCategory;
    /**
     * 实际整改人（文本输入）
     */
    private String qsrRectifyRealUname;
    /**
     * 整改回复
     */
    private String qsrRectifyReply;
    /**
     * 整改完成时间
     */
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date qsrRectifyFinishTime;
    /**
     * 巡查对象
     */
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
     * 整改状态 JPMsoft_ZGZT
     * 待整改	01
     * 整改中	02
     * 整改完成	03
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


    private String qsrPicIds;
    private String qsrRectifyPicIds;
    private String conName;
    private String pName;

    /**
     * 责任人单位ID（台账筛选）
     */
    private String responsibleUnitId;
    /**
     * 责任人单位名称（台账筛选）
     */
    private String responsibleUnitName;

    private String pConstructionUnit;
    private String pCompetentDepartment;

    /**
     * bim模型构件编码
     */
    private List<String> bimModelComponentCodes;
    private List<QsRecordDutyDTO> qsRecordDutys;

    /**
     * 业务表id: 风险评价表id
     */
    private String bizId;

    // 任务id
    private String tdcId;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getQsrRectifyActualTime() {
        return qsrRectifyActualTime;
    }

    public void setQsrRectifyActualTime(Date qsrRectifyActualTime) {
        this.qsrRectifyActualTime = qsrRectifyActualTime;
    }

    public String getTdcId() {
        return tdcId;
    }

    public void setTdcId(String tdcId) {
        this.tdcId = tdcId;
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

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpConstructionUnit() {
        return pConstructionUnit;
    }

    public void setpConstructionUnit(String pConstructionUnit) {
        this.pConstructionUnit = pConstructionUnit;
    }

    public String getpCompetentDepartment() {
        return pCompetentDepartment;
    }

    public void setpCompetentDepartment(String pCompetentDepartment) {
        this.pCompetentDepartment = pCompetentDepartment;
    }

    public List<String> getBimModelComponentCodes() {
        return bimModelComponentCodes;
    }

    public void setBimModelComponentCodes(List<String> bimModelComponentCodes) {
        this.bimModelComponentCodes = bimModelComponentCodes;
    }

    public List<QsRecordDutyDTO> getQsRecordDutys() {
        return qsRecordDutys;
    }

    public void setQsRecordDutys(List<QsRecordDutyDTO> qsRecordDutys) {
        this.qsRecordDutys = qsRecordDutys;
    }
}
