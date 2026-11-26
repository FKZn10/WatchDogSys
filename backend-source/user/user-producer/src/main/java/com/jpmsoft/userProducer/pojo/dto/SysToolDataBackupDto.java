package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SysToolDataBackupDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = 639855264820008765L;
    public String id;
    public Date stdbTime;
    public String stdbTitle;
    public Integer stdbTableCount;
    public Integer stdbDataCount;
    public String stdbOperator;
    public String stdbOperatorId;
    public Boolean stdbBackupStatus;
    public Integer stdbDeleteStatus;
    public Boolean stdReductionStatus;
    public String stdbTableCode;
    public String stdDeleteUid;
    public String stdDeleteUname;
    public String stdReductionUid;
    public String stdReductionUname;
    public Integer serialNumber;
    public Integer deletedState;
    public Integer restoreState;
    public Date restoreTime;
    private String start;
    private String end;
    private List<String> ids;
    private String type;
    private String dataId;
    private String endType;
    private  Integer num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStdbTime() {
        return stdbTime;
    }

    public void setStdbTime(Date stdbTime) {
        this.stdbTime = stdbTime;
    }

    public String getStdbTitle() {
        return stdbTitle;
    }

    public void setStdbTitle(String stdbTitle) {
        this.stdbTitle = stdbTitle;
    }

    public Integer getStdbTableCount() {
        return stdbTableCount;
    }

    public void setStdbTableCount(Integer stdbTableCount) {
        this.stdbTableCount = stdbTableCount;
    }

    public Integer getStdbDataCount() {
        return stdbDataCount;
    }

    public void setStdbDataCount(Integer stdb_DataCount) {
        this.stdbDataCount = stdb_DataCount;
    }

    public String getStdbOperator() {
        return stdbOperator;
    }

    public void setStdbOperator(String stdbOperator) {
        this.stdbOperator = stdbOperator;
    }

    public String getStdbOperatorId() {
        return stdbOperatorId;
    }

    public void setStdbOperatorId(String stdbOperatorId) {
        this.stdbOperatorId = stdbOperatorId;
    }

    public Boolean getStdbBackupStatus() {
        return stdbBackupStatus;
    }

    public void setStdbBackupStatus(Boolean stdbBackupStatus) {
        this.stdbBackupStatus = stdbBackupStatus;
    }

    public Integer getStdbDeleteStatus() {
        return stdbDeleteStatus;
    }

    public void setStdbDeleteStatus(Integer stdbDeleteStatus) {
        this.stdbDeleteStatus = stdbDeleteStatus;
    }

    public String getStdbTableCode() {
        return stdbTableCode;
    }

    public void setStdbTableCode(String stdbTableCode) {
        this.stdbTableCode = stdbTableCode;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getDeletedState() {
        return deletedState;
    }

    public void setDeletedState(Integer deletedState) {
        this.deletedState = deletedState;
    }

    public Integer getRestoreState() {
        return restoreState;
    }

    public void setRestoreState(Integer restoreState) {
        this.restoreState = restoreState;
    }

    public Date getRestoreTime() {
        return restoreTime;
    }

    public void setRestoreTime(Date restoreTime) {
        this.restoreTime = restoreTime;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getEndType() {
        return endType;
    }

    public void setEndType(String endType) {
        this.endType = endType;
    }

    public Boolean getStdReductionStatus() {
        return stdReductionStatus;
    }

    public void setStdReductionStatus(Boolean stdReductionStatus) {
        this.stdReductionStatus = stdReductionStatus;
    }

    public String getStdDeleteUid() {
        return stdDeleteUid;
    }

    public void setStdDeleteUid(String stdDeleteUid) {
        this.stdDeleteUid = stdDeleteUid;
    }

    public String getStdDeleteUname() {
        return stdDeleteUname;
    }

    public void setStdDeleteUname(String stdDeleteUname) {
        this.stdDeleteUname = stdDeleteUname;
    }

    public String getStdReductionUid() {
        return stdReductionUid;
    }

    public void setStdReductionUid(String stdReductionUid) {
        this.stdReductionUid = stdReductionUid;
    }

    public String getStdReductionUname() {
        return stdReductionUname;
    }

    public void setStdReductionUname(String stdReductionUname) {
        this.stdReductionUname = stdReductionUname;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
