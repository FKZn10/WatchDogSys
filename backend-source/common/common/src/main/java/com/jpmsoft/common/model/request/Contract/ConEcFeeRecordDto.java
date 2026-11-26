package com.jpmsoft.common.model.request.Contract;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;

public class ConEcFeeRecordDto  extends BaseFileRequest {

    private String id;


    private String meaId;

    private String mearCode;


    private String mearBoqdCode;


    private String mearBoqdUnit;

    private String mearPosition;


    private String mearPriceElement;

    private Float mearDeclareAmount;

    private String mearBoqdName;

    private String mearDraft;

    private String mearDraftSupervisor;

    private Integer mearAnnexNumber;

    private Integer mearAnnexPage;


    private Float mearFinalAmount;

    private String mearGauger;

    private String mearContractorPm;


    private String mearContractorId;


    private String createUid;


    private String createUname;


    private Date createTime;


    private Date stateTime;

    /**
     *
     */

    private Integer state;


    private String mearRemark;

    private String conId;

    private String pId;

    private Date mearVerbTime;


    private BigDecimal mearDeclaredTotal;
    private BigDecimal mearFinalTotal;

    /**
     * 累计工程量
     */
    private Float mearUpperAmount;
    private BigDecimal mearUpperTotal;
    private Float mearCumulativeAmount;
    private BigDecimal mearCumulativeTotal;
    private String boqdId;
    private Float mearRate;
    private String mearFormula;
    private Float mearFeesRatio;
    private Float mearOtherRatio;
    private Float mearTaxesRatio;

    /**
     * 序号
     */
    private Integer mearOrder;
    /**
     * 施工单位申报单价
     */
    private Float mearConPrice;

    /**
     * 施工单位申报本期总价
     */
    private BigDecimal mearConTotal;

    /**
     * 监理单位审核工程量
     */
    private Float mearSuperviseAmount;

    /**
     * 监理单位审核单价
     */
    private Float mearSupervisePrice;

    /**
     * 监理单位审核本期总价
     */
    private BigDecimal mearSuperviseTotal;

    /**
     * 全过程咨询单位审核工程量
     */
    private Float mearConsultAmount;

    /**
     * 全过程咨询单位审核单价
     */
    private Float mearConsultPrice;

    /**
     * 全过程咨询单位审核本期总价
     */
    private BigDecimal mearConsultTotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeaId() {
        return meaId;
    }

    public void setMeaId(String meaId) {
        this.meaId = meaId;
    }

    public String getMearCode() {
        return mearCode;
    }

    public void setMearCode(String mearCode) {
        this.mearCode = mearCode;
    }

    public String getMearBoqdCode() {
        return mearBoqdCode;
    }

    public void setMearBoqdCode(String mearBoqdCode) {
        this.mearBoqdCode = mearBoqdCode;
    }

    public String getMearBoqdUnit() {
        return mearBoqdUnit;
    }

    public void setMearBoqdUnit(String mearBoqdUnit) {
        this.mearBoqdUnit = mearBoqdUnit;
    }

    public String getMearPosition() {
        return mearPosition;
    }

    public void setMearPosition(String mearPosition) {
        this.mearPosition = mearPosition;
    }

    public String getMearPriceElement() {
        return mearPriceElement;
    }

    public void setMearPriceElement(String mearPriceElement) {
        this.mearPriceElement = mearPriceElement;
    }

    public Float getMearDeclareAmount() {
        return mearDeclareAmount;
    }

    public void setMearDeclareAmount(Float mearDeclareAmount) {
        this.mearDeclareAmount = mearDeclareAmount;
    }

    public String getMearBoqdName() {
        return mearBoqdName;
    }

    public void setMearBoqdName(String mearBoqdName) {
        this.mearBoqdName = mearBoqdName;
    }

    public String getMearDraft() {
        return mearDraft;
    }

    public void setMearDraft(String mearDraft) {
        this.mearDraft = mearDraft;
    }

    public String getMearDraftSupervisor() {
        return mearDraftSupervisor;
    }

    public void setMearDraftSupervisor(String mearDraftSupervisor) {
        this.mearDraftSupervisor = mearDraftSupervisor;
    }

    public Integer getMearAnnexNumber() {
        return mearAnnexNumber;
    }

    public void setMearAnnexNumber(Integer mearAnnexNumber) {
        this.mearAnnexNumber = mearAnnexNumber;
    }

    public Integer getMearAnnexPage() {
        return mearAnnexPage;
    }

    public void setMearAnnexPage(Integer mearAnnexPage) {
        this.mearAnnexPage = mearAnnexPage;
    }

    public Float getMearFinalAmount() {
        return mearFinalAmount;
    }

    public void setMearFinalAmount(Float mearFinalAmount) {
        this.mearFinalAmount = mearFinalAmount;
    }

    public String getMearGauger() {
        return mearGauger;
    }

    public void setMearGauger(String mearGauger) {
        this.mearGauger = mearGauger;
    }

    public String getMearContractorPm() {
        return mearContractorPm;
    }

    public void setMearContractorPm(String mearContractorPm) {
        this.mearContractorPm = mearContractorPm;
    }

    public String getMearContractorId() {
        return mearContractorId;
    }

    public void setMearContractorId(String mearContractorId) {
        this.mearContractorId = mearContractorId;
    }

    public String getCreateUid() {
        return createUid;
    }

    public void setCreateUid(String createUid) {
        this.createUid = createUid;
    }

    public String getCreateUname() {
        return createUname;
    }

    public void setCreateUname(String createUname) {
        this.createUname = createUname;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getMearRemark() {
        return mearRemark;
    }

    public void setMearRemark(String mearRemark) {
        this.mearRemark = mearRemark;
    }

    @Override
    public String getConId() {
        return conId;
    }

    @Override
    public void setConId(String conId) {
        this.conId = conId;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public Date getMearVerbTime() {
        return mearVerbTime;
    }

    public void setMearVerbTime(Date mearVerbTime) {
        this.mearVerbTime = mearVerbTime;
    }

    public BigDecimal getMearDeclaredTotal() {
        return mearDeclaredTotal;
    }

    public void setMearDeclaredTotal(BigDecimal mearDeclaredTotal) {
        this.mearDeclaredTotal = mearDeclaredTotal;
    }

    public BigDecimal getMearFinalTotal() {
        return mearFinalTotal;
    }

    public void setMearFinalTotal(BigDecimal mearFinalTotal) {
        this.mearFinalTotal = mearFinalTotal;
    }

    public Float getMearUpperAmount() {
        return mearUpperAmount;
    }

    public void setMearUpperAmount(Float mearUpperAmount) {
        this.mearUpperAmount = mearUpperAmount;
    }

    public BigDecimal getMearUpperTotal() {
        return mearUpperTotal;
    }

    public void setMearUpperTotal(BigDecimal mearUpperTotal) {
        this.mearUpperTotal = mearUpperTotal;
    }

    public Float getMearCumulativeAmount() {
        return mearCumulativeAmount;
    }

    public void setMearCumulativeAmount(Float mearCumulativeAmount) {
        this.mearCumulativeAmount = mearCumulativeAmount;
    }

    public BigDecimal getMearCumulativeTotal() {
        return mearCumulativeTotal;
    }

    public void setMearCumulativeTotal(BigDecimal mearCumulativeTotal) {
        this.mearCumulativeTotal = mearCumulativeTotal;
    }

    public String getBoqdId() {
        return boqdId;
    }

    public void setBoqdId(String boqdId) {
        this.boqdId = boqdId;
    }

    public Float getMearRate() {
        return mearRate;
    }

    public void setMearRate(Float mearRate) {
        this.mearRate = mearRate;
    }

    public String getMearFormula() {
        return mearFormula;
    }

    public void setMearFormula(String mearFormula) {
        this.mearFormula = mearFormula;
    }

    public Float getMearFeesRatio() {
        return mearFeesRatio;
    }

    public void setMearFeesRatio(Float mearFeesRatio) {
        this.mearFeesRatio = mearFeesRatio;
    }

    public Float getMearOtherRatio() {
        return mearOtherRatio;
    }

    public void setMearOtherRatio(Float mearOtherRatio) {
        this.mearOtherRatio = mearOtherRatio;
    }

    public Float getMearTaxesRatio() {
        return mearTaxesRatio;
    }

    public void setMearTaxesRatio(Float mearTaxesRatio) {
        this.mearTaxesRatio = mearTaxesRatio;
    }

    public Integer getMearOrder() {
        return mearOrder;
    }

    public void setMearOrder(Integer mearOrder) {
        this.mearOrder = mearOrder;
    }

    public Float getMearConPrice() {
        return mearConPrice;
    }

    public void setMearConPrice(Float mearConPrice) {
        this.mearConPrice = mearConPrice;
    }

    public BigDecimal getMearConTotal() {
        return mearConTotal;
    }

    public void setMearConTotal(BigDecimal mearConTotal) {
        this.mearConTotal = mearConTotal;
    }

    public Float getMearSuperviseAmount() {
        return mearSuperviseAmount;
    }

    public void setMearSuperviseAmount(Float mearSuperviseAmount) {
        this.mearSuperviseAmount = mearSuperviseAmount;
    }

    public Float getMearSupervisePrice() {
        return mearSupervisePrice;
    }

    public void setMearSupervisePrice(Float mearSupervisePrice) {
        this.mearSupervisePrice = mearSupervisePrice;
    }

    public BigDecimal getMearSuperviseTotal() {
        return mearSuperviseTotal;
    }

    public void setMearSuperviseTotal(BigDecimal mearSuperviseTotal) {
        this.mearSuperviseTotal = mearSuperviseTotal;
    }

    public Float getMearConsultAmount() {
        return mearConsultAmount;
    }

    public void setMearConsultAmount(Float mearConsultAmount) {
        this.mearConsultAmount = mearConsultAmount;
    }

    public Float getMearConsultPrice() {
        return mearConsultPrice;
    }

    public void setMearConsultPrice(Float mearConsultPrice) {
        this.mearConsultPrice = mearConsultPrice;
    }

    public BigDecimal getMearConsultTotal() {
        return mearConsultTotal;
    }

    public void setMearConsultTotal(BigDecimal mearConsultTotal) {
        this.mearConsultTotal = mearConsultTotal;
    }
}
