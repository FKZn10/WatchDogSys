package com.jpmsoft.jpbpm.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 风险预警信息
 */
@Data
@TableName("Risk_Warn")
public class RiskWarn {

    @TableId(type = IdType.INPUT)
    private String rwId;
    private String rwiId;
    private String pId;
    private String secId;
    private String conId;
    private String rwCode;
    private String rwPmId;
    private Date rwTime;
    private String dId;
    private String rwContent;
    private String rwFrom;
    private String rwBusinessInforKey;
    private String rwFlowActName;
    private String rwFlowUid;
    private String rwFlowUname;
    private String rwFlowUphone;
    private String rwFlowCId;
    private Date rwFlowReceiveTime;
    private Date rwFlowApprovalTime;
    private Date rwFlowEtlTime;
    private String rwHandleUid;
    private String rwHandleUname;
    private Date rwHandleTime;
    private String rwHandleContent;
    /**
     * null或者0 未生成流程
     * 1 已生成流程
     */
    private Integer rwHaveFlow;
    private Integer rwState;
    private Date rwStateTime;
    private String teId;

    /** 数据快照列定义 JSON（前端 Ant Design 列配置，后端原样透传） */
    private String rwSnapshotColumns;

    /** 数据快照行数据 JSON（每行为平铺 key-value 对象，后端原样透传） */
    private String rwSnapshotRows;

    /** 数据快照总行数 */
    private Integer rwSnapshotTotalRows;

    public String getRwId() {
        return rwId;
    }

    public void setRwId(String rwId) {
        this.rwId = rwId;
    }

    public String getRwiId() {
        return rwiId;
    }

    public void setRwiId(String rwiId) {
        this.rwiId = rwiId;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getRwCode() {
        return rwCode;
    }

    public void setRwCode(String rwCode) {
        this.rwCode = rwCode;
    }

    public String getRwPmId() {
        return rwPmId;
    }

    public void setRwPmId(String rwPmId) {
        this.rwPmId = rwPmId;
    }

    public Date getRwTime() {
        return rwTime;
    }

    public void setRwTime(Date rwTime) {
        this.rwTime = rwTime;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getRwContent() {
        return rwContent;
    }

    public void setRwContent(String rwContent) {
        this.rwContent = rwContent;
    }

    public String getRwFrom() {
        return rwFrom;
    }

    public void setRwFrom(String rwFrom) {
        this.rwFrom = rwFrom;
    }

    public String getRwBusinessInforKey() {
        return rwBusinessInforKey;
    }

    public void setRwBusinessInforKey(String rwBusinessInforKey) {
        this.rwBusinessInforKey = rwBusinessInforKey;
    }

    public String getRwFlowActName() {
        return rwFlowActName;
    }

    public void setRwFlowActName(String rwFlowActName) {
        this.rwFlowActName = rwFlowActName;
    }

    public String getRwFlowUid() {
        return rwFlowUid;
    }

    public void setRwFlowUid(String rwFlowUid) {
        this.rwFlowUid = rwFlowUid;
    }

    public String getRwFlowUname() {
        return rwFlowUname;
    }

    public void setRwFlowUname(String rwFlowUname) {
        this.rwFlowUname = rwFlowUname;
    }

    public String getRwFlowUphone() {
        return rwFlowUphone;
    }

    public void setRwFlowUphone(String rwFlowUphone) {
        this.rwFlowUphone = rwFlowUphone;
    }

    public String getRwFlowCId() {
        return rwFlowCId;
    }

    public void setRwFlowCId(String rwFlowCId) {
        this.rwFlowCId = rwFlowCId;
    }

    public Date getRwFlowReceiveTime() {
        return rwFlowReceiveTime;
    }

    public void setRwFlowReceiveTime(Date rwFlowReceiveTime) {
        this.rwFlowReceiveTime = rwFlowReceiveTime;
    }

    public Date getRwFlowApprovalTime() {
        return rwFlowApprovalTime;
    }

    public void setRwFlowApprovalTime(Date rwFlowApprovalTime) {
        this.rwFlowApprovalTime = rwFlowApprovalTime;
    }

    public Date getRwFlowEtlTime() {
        return rwFlowEtlTime;
    }

    public void setRwFlowEtlTime(Date rwFlowEtlTime) {
        this.rwFlowEtlTime = rwFlowEtlTime;
    }

    public String getRwHandleUid() {
        return rwHandleUid;
    }

    public void setRwHandleUid(String rwHandleUid) {
        this.rwHandleUid = rwHandleUid;
    }

    public String getRwHandleUname() {
        return rwHandleUname;
    }

    public void setRwHandleUname(String rwHandleUname) {
        this.rwHandleUname = rwHandleUname;
    }

    public Date getRwHandleTime() {
        return rwHandleTime;
    }

    public void setRwHandleTime(Date rwHandleTime) {
        this.rwHandleTime = rwHandleTime;
    }

    public String getRwHandleContent() {
        return rwHandleContent;
    }

    public void setRwHandleContent(String rwHandleContent) {
        this.rwHandleContent = rwHandleContent;
    }

    public Integer getRwHaveFlow() {
        return rwHaveFlow;
    }

    public void setRwHaveFlow(Integer rwHaveFlow) {
        this.rwHaveFlow = rwHaveFlow;
    }

    public Integer getRwState() {
        return rwState;
    }

    public void setRwState(Integer rwState) {
        this.rwState = rwState;
    }

    public Date getRwStateTime() {
        return rwStateTime;
    }

    public void setRwStateTime(Date rwStateTime) {
        this.rwStateTime = rwStateTime;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    public String getRwSnapshotColumns() {
        return rwSnapshotColumns;
    }

    public void setRwSnapshotColumns(String rwSnapshotColumns) {
        this.rwSnapshotColumns = rwSnapshotColumns;
    }

    public String getRwSnapshotRows() {
        return rwSnapshotRows;
    }

    public void setRwSnapshotRows(String rwSnapshotRows) {
        this.rwSnapshotRows = rwSnapshotRows;
    }

    public Integer getRwSnapshotTotalRows() {
        return rwSnapshotTotalRows;
    }

    public void setRwSnapshotTotalRows(Integer rwSnapshotTotalRows) {
        this.rwSnapshotTotalRows = rwSnapshotTotalRows;
    }

}
