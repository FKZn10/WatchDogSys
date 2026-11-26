package com.jpmsoft.common.model.request.ConstructionProcedure;

import com.jpmsoft.common.model.BaseFileRequest;

import java.math.BigDecimal;
import java.util.Date;
public class SupervisionUnitWonRequest extends BaseFileRequest {
    private String uuid;

    private String productId;

    private String productName;

    private String constructionControlUnit;

    private BigDecimal winningAmount;

    private String remarks;

    private String createId;

    private String approveStatus;

    private String createName;

    private Date createTime;

    private String fileRelationId;

    private String deleteState;

    private String rteFileIds;


    private Date approveTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getConstructionControlUnit() {
        return constructionControlUnit;
    }

    public void setConstructionControlUnit(String constructionControlUnit) {
        this.constructionControlUnit = constructionControlUnit;
    }

    public BigDecimal getWinningAmount() {
        return winningAmount;
    }

    public void setWinningAmount(BigDecimal winningAmount) {
        this.winningAmount = winningAmount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getFileRelationId() {
        return fileRelationId;
    }

    @Override
    public void setFileRelationId(String fileRelationId) {
        this.fileRelationId = fileRelationId;
    }

    public String getDeleteState() {
        return deleteState;
    }

    public void setDeleteState(String deleteState) {
        this.deleteState = deleteState;
    }

    public String getRteFileIds() {
        return rteFileIds;
    }

    public void setRteFileIds(String rteFileIds) {
        this.rteFileIds = rteFileIds;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }
}
