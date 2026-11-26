package com.jpmsoft.projectProducer.pojo.dto.contract;

import com.jpmsoft.common.model.ProjectCommonRes;
import com.jpmsoft.projectProducer.pojo.vo.fund.PaymentReceiptVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MarketInvestmentPaymentDto extends ProjectCommonRes implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    private String id;

    /** 支付表 ID */
    private String payId;

    /** 合同 ID */
    private String conId;

    /** 合同名称 */
    private String conName;

    /** 是否合同 (0-否, 1-是) */
    private Integer mipIsCon;

    /** 合同编号/关联 ID */
    private String mipConId;

    /** 状态 */
    private Integer state;

    /** 状态时间 */
    private Date stateTime;

    /** 创建人 ID */
    private String createUid;

    /** 创建人姓名 */
    private String createUname;

    /** 创建时间 */
    private Date createTime;

    /** 施工单位 */
    private String mipConstructionUnit;

    /** 资金来源 */
    private String mipSourceFunds;

    /** 上次投资额 */
    private BigDecimal mipInvestmentLast;

    /** 批准贷款额 */
    private BigDecimal mipApproveLoan;

    /** 累计释放额 */
    private BigDecimal mipCumulativeRelease;

    /** 合同金额 */
    private BigDecimal mipConMoney;

    /** 收款人 */
    private String mipConsignee;

    /** 开户行 */
    private String mipBank;

    /** 银行账号 */
    private String mipAccountNumber;

    /** 本次申请金额 */
    private BigDecimal mipAppliedAmount;

    /** 进度款说明 */
    private String mipProgressFunds;

    /** 总投资额 */
    private BigDecimal mipInvestmentTotal;

    /** 编号/代码 */
    private String mipCode;

    private String markType;

    private String payType;

    private Date payApplicationDate;

    private String payCreateUid;

    private String payCreateUname;

    public String getPayCreateUid() {
        return payCreateUid;
    }

    public void setPayCreateUid(String payCreateUid) {
        this.payCreateUid = payCreateUid;
    }

    public String getPayCreateUname() {
        return payCreateUname;
    }

    public void setPayCreateUname(String payCreateUname) {
        this.payCreateUname = payCreateUname;
    }

    public Date getPayApplicationDate() {
        return payApplicationDate;
    }

    public void setPayApplicationDate(Date payApplicationDate) {
        this.payApplicationDate = payApplicationDate;
    }

    public String getMarkType() {
        return markType;
    }

    public void setMarkType(String markType) {
        this.markType = markType;
    }


    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
    }

    public Integer getMipIsCon() {
        return mipIsCon;
    }

    public void setMipIsCon(Integer mipIsCon) {
        this.mipIsCon = mipIsCon;
    }

    public String getMipConId() {
        return mipConId;
    }

    public void setMipConId(String mipConId) {
        this.mipConId = mipConId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getStateTime() {
        return stateTime;
    }

    public void setStateTime(Date stateTime) {
        this.stateTime = stateTime;
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

    public String getMipConstructionUnit() {
        return mipConstructionUnit;
    }

    public void setMipConstructionUnit(String mipConstructionUnit) {
        this.mipConstructionUnit = mipConstructionUnit;
    }

    public String getMipSourceFunds() {
        return mipSourceFunds;
    }

    public void setMipSourceFunds(String mipSourceFunds) {
        this.mipSourceFunds = mipSourceFunds;
    }

    public BigDecimal getMipInvestmentLast() {
        return mipInvestmentLast;
    }

    public void setMipInvestmentLast(BigDecimal mipInvestmentLast) {
        this.mipInvestmentLast = mipInvestmentLast;
    }

    public BigDecimal getMipApproveLoan() {
        return mipApproveLoan;
    }

    public void setMipApproveLoan(BigDecimal mipApproveLoan) {
        this.mipApproveLoan = mipApproveLoan;
    }

    public BigDecimal getMipCumulativeRelease() {
        return mipCumulativeRelease;
    }

    public void setMipCumulativeRelease(BigDecimal mipCumulativeRelease) {
        this.mipCumulativeRelease = mipCumulativeRelease;
    }

    public BigDecimal getMipConMoney() {
        return mipConMoney;
    }

    public void setMipConMoney(BigDecimal mipConMoney) {
        this.mipConMoney = mipConMoney;
    }

    public String getMipConsignee() {
        return mipConsignee;
    }

    public void setMipConsignee(String mipConsignee) {
        this.mipConsignee = mipConsignee;
    }

    public String getMipBank() {
        return mipBank;
    }

    public void setMipBank(String mipBank) {
        this.mipBank = mipBank;
    }

    public String getMipAccountNumber() {
        return mipAccountNumber;
    }

    public void setMipAccountNumber(String mipAccountNumber) {
        this.mipAccountNumber = mipAccountNumber;
    }

    public BigDecimal getMipAppliedAmount() {
        return mipAppliedAmount;
    }

    public void setMipAppliedAmount(BigDecimal mipAppliedAmount) {
        this.mipAppliedAmount = mipAppliedAmount;
    }

    public String getMipProgressFunds() {
        return mipProgressFunds;
    }

    public void setMipProgressFunds(String mipProgressFunds) {
        this.mipProgressFunds = mipProgressFunds;
    }

    public BigDecimal getMipInvestmentTotal() {
        return mipInvestmentTotal;
    }

    public void setMipInvestmentTotal(BigDecimal mipInvestmentTotal) {
        this.mipInvestmentTotal = mipInvestmentTotal;
    }

    public String getMipCode() {
        return mipCode;
    }

    public void setMipCode(String mipCode) {
        this.mipCode = mipCode;
    }
}
