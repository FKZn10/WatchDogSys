package com.jpmsoft.common.model.request.Subcontract;


import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 分包合同备案
 */
public class SubcontractRecordRequest extends BaseFileRequest {

    private String scId;
    private String scTitle;
    private String scName;
    private String scCode;
    private BigDecimal ScMoney;
    private String scMode;
    private String scFp;
    private String scSp;
    private String scSpDeputy;
    private String scSpDelegator;
    private String scSpDelegatorSsn;
    private Date scDate;
    private String scResident;
    private String scResidentTel;
    private String scResidentSsn;
    private BigDecimal scColPaid;
    private String scColAccount;
    private String scColAccountHolder;
    private String scSelectMethod;
    private String scParticipationBid;
    private String scBidMethod;
    private String scBidExpert;
    private String scCreateUid;
    private String scCreateUname;
    private Date scCreateTime;
    private Integer scState;
    private Date scStateTime;
    private String scType;
    private String pId;
    private String conId;
    private String scSup;
    private String scMasterConId;
    private String scSupIsChangeMoney;
    private Integer scSupChangeMonType;
    private BigDecimal scSupChangeMoney;
    private String scSupIsChangeDuration;
    private Integer scSupChangeDurType;
    private Integer scSupChangeDuration;

    private String conName;
    private String pName;
    private List<String> notScConId;


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

    public String getScType() {
        return scType;
    }

    public void setScType(String scType) {
        this.scType = scType;
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId;
    }

    public String getScTitle() {
        return scTitle;
    }

    public void setScTitle(String scTitle) {
        this.scTitle = scTitle;
    }

    public String getScName() {
        return scName;
    }

    public void setScName(String scName) {
        this.scName = scName;
    }

    public String getScCode() {
        return scCode;
    }

    public void setScCode(String scCode) {
        this.scCode = scCode;
    }

    public BigDecimal getScMoney() {
        return ScMoney;
    }

    public void setScMoney(BigDecimal scMoney) {
        ScMoney = scMoney;
    }

    public String getScMode() {
        return scMode;
    }

    public void setScMode(String scMode) {
        this.scMode = scMode;
    }

    public String getScFp() {
        return scFp;
    }

    public void setScFp(String scFp) {
        this.scFp = scFp;
    }

    public String getScSp() {
        return scSp;
    }

    public void setScSp(String scSp) {
        this.scSp = scSp;
    }

    public String getScSpDeputy() {
        return scSpDeputy;
    }

    public void setScSpDeputy(String scSpDeputy) {
        this.scSpDeputy = scSpDeputy;
    }

    public String getScSpDelegator() {
        return scSpDelegator;
    }

    public void setScSpDelegator(String scSpDelegator) {
        this.scSpDelegator = scSpDelegator;
    }

    public String getScSpDelegatorSsn() {
        return scSpDelegatorSsn;
    }

    public void setScSpDelegatorSsn(String scSpDelegatorSsn) {
        this.scSpDelegatorSsn = scSpDelegatorSsn;
    }

    public Date getScDate() {
        return scDate;
    }

    public void setScDate(Date scDate) {
        this.scDate = scDate;
    }

    public String getScResident() {
        return scResident;
    }

    public void setScResident(String scResident) {
        this.scResident = scResident;
    }

    public String getScResidentTel() {
        return scResidentTel;
    }

    public void setScResidentTel(String scResidentTel) {
        this.scResidentTel = scResidentTel;
    }

    public String getScResidentSsn() {
        return scResidentSsn;
    }

    public void setScResidentSsn(String scResidentSsn) {
        this.scResidentSsn = scResidentSsn;
    }

    public BigDecimal getScColPaid() {
        return scColPaid;
    }

    public void setScColPaid(BigDecimal scColPaid) {
        this.scColPaid = scColPaid;
    }

    public String getScColAccount() {
        return scColAccount;
    }

    public void setScColAccount(String scColAccount) {
        this.scColAccount = scColAccount;
    }

    public String getScColAccountHolder() {
        return scColAccountHolder;
    }

    public void setScColAccountHolder(String scColAccountHolder) {
        this.scColAccountHolder = scColAccountHolder;
    }

    public String getScSelectMethod() {
        return scSelectMethod;
    }

    public void setScSelectMethod(String scSelectMethod) {
        this.scSelectMethod = scSelectMethod;
    }

    public String getScParticipationBid() {
        return scParticipationBid;
    }

    public void setScParticipationBid(String scParticipationBid) {
        this.scParticipationBid = scParticipationBid;
    }

    public String getScBidMethod() {
        return scBidMethod;
    }

    public void setScBidMethod(String scBidMethod) {
        this.scBidMethod = scBidMethod;
    }

    public String getScBidExpert() {
        return scBidExpert;
    }

    public void setScBidExpert(String scBidExpert) {
        this.scBidExpert = scBidExpert;
    }

    public String getScCreateUid() {
        return scCreateUid;
    }

    public void setScCreateUid(String scCreateUid) {
        this.scCreateUid = scCreateUid;
    }

    public String getScCreateUname() {
        return scCreateUname;
    }

    public void setScCreateUname(String scCreateUname) {
        this.scCreateUname = scCreateUname;
    }

    public Date getScCreateTime() {
        return scCreateTime;
    }

    public void setScCreateTime(Date scCreateTime) {
        this.scCreateTime = scCreateTime;
    }

    public Integer getScState() {
        return scState;
    }

    public void setScState(Integer scState) {
        this.scState = scState;
    }

    public Date getScStateTime() {
        return scStateTime;
    }

    public void setScStateTime(Date scStateTime) {
        this.scStateTime = scStateTime;
    }

    public String getScSup() {
        return scSup;
    }

    public void setScSup(String scSup) {
        this.scSup = scSup;
    }

    public String getScMasterConId() {
        return scMasterConId;
    }

    public void setScMasterConId(String scMasterConId) {
        this.scMasterConId = scMasterConId;
    }

    public String getScSupIsChangeMoney() {
        return scSupIsChangeMoney;
    }

    public void setScSupIsChangeMoney(String scSupIsChangeMoney) {
        this.scSupIsChangeMoney = scSupIsChangeMoney;
    }

    public Integer getScSupChangeMonType() {
        return scSupChangeMonType;
    }

    public void setScSupChangeMonType(Integer scSupChangeMonType) {
        this.scSupChangeMonType = scSupChangeMonType;
    }

    public BigDecimal getScSupChangeMoney() {
        return scSupChangeMoney;
    }

    public void setScSupChangeMoney(BigDecimal scSupChangeMoney) {
        this.scSupChangeMoney = scSupChangeMoney;
    }

    public String getScSupIsChangeDuration() {
        return scSupIsChangeDuration;
    }

    public void setScSupIsChangeDuration(String scSupIsChangeDuration) {
        this.scSupIsChangeDuration = scSupIsChangeDuration;
    }

    public Integer getScSupChangeDurType() {
        return scSupChangeDurType;
    }

    public void setScSupChangeDurType(Integer scSupChangeDurType) {
        this.scSupChangeDurType = scSupChangeDurType;
    }

    public Integer getScSupChangeDuration() {
        return scSupChangeDuration;
    }

    public void setScSupChangeDuration(Integer scSupChangeDuration) {
        this.scSupChangeDuration = scSupChangeDuration;
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

    public List<String> getNotScConId() {
        return notScConId;
    }

    public void setNotScConId(List<String> notScConId) {
        this.notScConId = notScConId;
    }
}
