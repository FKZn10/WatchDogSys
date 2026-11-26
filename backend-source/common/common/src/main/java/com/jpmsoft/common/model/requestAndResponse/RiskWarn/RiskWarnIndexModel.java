package com.jpmsoft.common.model.requestAndResponse.RiskWarn;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.Date;

/**
 * 风险预警指标
 */
public class RiskWarnIndexModel extends BaseModeRequest {

    private String rwiId;
    private String rwiCode;
    private String rwiType;
    private String rwiName;
    private String rwiDesc;
    private String rwiProblem;
    private String rwiCalculationRule;
    private String rwiValue;
    private String rwiBasis;
    private String rwiLevel;
    private String rwiCreateUid;
    private String rwiCreateUname;
    private Date rwiCreateTime;
    private Integer rwiState;
    private String dId;
    private String rwiPersonUid;
    private String rwiPersonUname;
    private Integer rwiProjectLeader;
    private String rwiBusinessInforUrl;
    private String rwiScreeningConditions;
    private String rwiQuerySql;
    private String rwiCcPost;
    private String dName;
    private String rwiDbType;
    private String rwiCcPostFilter;
    private String rwiCcSmsType;
    private String rwiCcSmsTemplate;
    /** 数据快照 SQL：查询命中预警后的明细数据，用于线索数据快照（columns+rows） */
    private String rwiSnapshotSql;
    /** 责任部门领导 ID（配置后线索直接下发至该领导核实，跳过纪检下发） */
    private String rwiLeaderId;
    /** 责任部门领导姓名 */
    private String rwiLeaderName;
    /** 是否推送到纪检：01 否 / 02 是，空视为 02 */
    private String rwiPushToJw;

    public String getRwiSnapshotSql() {
        return rwiSnapshotSql;
    }

    public void setRwiSnapshotSql(String rwiSnapshotSql) {
        this.rwiSnapshotSql = rwiSnapshotSql;
    }

    public String getRwiLeaderId() {
        return rwiLeaderId;
    }

    public void setRwiLeaderId(String rwiLeaderId) {
        this.rwiLeaderId = rwiLeaderId;
    }

    public String getRwiLeaderName() {
        return rwiLeaderName;
    }

    public void setRwiLeaderName(String rwiLeaderName) {
        this.rwiLeaderName = rwiLeaderName;
    }

    public String getRwiPushToJw() {
        return rwiPushToJw;
    }

    public void setRwiPushToJw(String rwiPushToJw) {
        this.rwiPushToJw = rwiPushToJw;
    }

    public String getRwiId() {
        return rwiId;
    }

    public void setRwiId(String rwiId) {
        this.rwiId = rwiId;
    }

    public String getRwiCode() {
        return rwiCode;
    }

    public void setRwiCode(String rwiCode) {
        this.rwiCode = rwiCode;
    }

    public String getRwiType() {
        return rwiType;
    }

    public void setRwiType(String rwiType) {
        this.rwiType = rwiType;
    }

    public String getRwiName() {
        return rwiName;
    }

    public void setRwiName(String rwiName) {
        this.rwiName = rwiName;
    }

    public String getRwiDesc() {
        return rwiDesc;
    }

    public void setRwiDesc(String rwiDesc) {
        this.rwiDesc = rwiDesc;
    }

    public String getRwiProblem() {
        return rwiProblem;
    }

    public void setRwiProblem(String rwiProblem) {
        this.rwiProblem = rwiProblem;
    }

    public String getRwiCalculationRule() {
        return rwiCalculationRule;
    }

    public void setRwiCalculationRule(String rwiCalculationRule) {
        this.rwiCalculationRule = rwiCalculationRule;
    }

    public String getRwiValue() {
        return rwiValue;
    }

    public void setRwiValue(String rwiValue) {
        this.rwiValue = rwiValue;
    }

    public String getRwiBasis() {
        return rwiBasis;
    }

    public void setRwiBasis(String rwiBasis) {
        this.rwiBasis = rwiBasis;
    }

    public String getRwiLevel() {
        return rwiLevel;
    }

    public void setRwiLevel(String rwiLevel) {
        this.rwiLevel = rwiLevel;
    }

    public String getRwiCreateUid() {
        return rwiCreateUid;
    }

    public void setRwiCreateUid(String rwiCreateUid) {
        this.rwiCreateUid = rwiCreateUid;
    }

    public String getRwiCreateUname() {
        return rwiCreateUname;
    }

    public void setRwiCreateUname(String rwiCreateUname) {
        this.rwiCreateUname = rwiCreateUname;
    }

    public Date getRwiCreateTime() {
        return rwiCreateTime;
    }

    public void setRwiCreateTime(Date rwiCreateTime) {
        this.rwiCreateTime = rwiCreateTime;
    }

    public Integer getRwiState() {
        return rwiState;
    }

    public void setRwiState(Integer rwiState) {
        this.rwiState = rwiState;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getRwiPersonUid() {
        return rwiPersonUid;
    }

    public void setRwiPersonUid(String rwiPersonUid) {
        this.rwiPersonUid = rwiPersonUid;
    }

    public String getRwiPersonUname() {
        return rwiPersonUname;
    }

    public void setRwiPersonUname(String rwiPersonUname) {
        this.rwiPersonUname = rwiPersonUname;
    }

    public Integer getRwiProjectLeader() {
        return rwiProjectLeader;
    }

    public void setRwiProjectLeader(Integer rwiProjectLeader) {
        this.rwiProjectLeader = rwiProjectLeader;
    }

    public String getRwiBusinessInforUrl() {
        return rwiBusinessInforUrl;
    }

    public void setRwiBusinessInforUrl(String rwiBusinessInforUrl) {
        this.rwiBusinessInforUrl = rwiBusinessInforUrl;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }


    public String getRwiScreeningConditions() {
        return rwiScreeningConditions;
    }

    public void setRwiScreeningConditions(String rwiScreeningConditions) {
        this.rwiScreeningConditions = rwiScreeningConditions;
    }

    public String getRwiQuerySql() {
        return rwiQuerySql;
    }

    public void setRwiQuerySql(String rwiQuerySql) {
        this.rwiQuerySql = rwiQuerySql;
    }

    public String getRwiCcPost() {
        return rwiCcPost;
    }

    public void setRwiCcPost(String rwiCcPost) {
        this.rwiCcPost = rwiCcPost;
    }

    public String getRwiDbType() {
        return rwiDbType;
    }

    public void setRwiDbType(String rwiDbType) {
        this.rwiDbType = rwiDbType;
    }

    public String getRwiCcPostFilter() {
        return rwiCcPostFilter;
    }

    public void setRwiCcPostFilter(String rwiCcPostFilter) {
        this.rwiCcPostFilter = rwiCcPostFilter;
    }

    public String getRwiCcSmsType() {
        return rwiCcSmsType;
    }

    public void setRwiCcSmsType(String rwiCcSmsType) {
        this.rwiCcSmsType = rwiCcSmsType;
    }

    public String getRwiCcSmsTemplate() {
        return rwiCcSmsTemplate;
    }

    public void setRwiCcSmsTemplate(String rwiCcSmsTemplate) {
        this.rwiCcSmsTemplate = rwiCcSmsTemplate;
    }
}
