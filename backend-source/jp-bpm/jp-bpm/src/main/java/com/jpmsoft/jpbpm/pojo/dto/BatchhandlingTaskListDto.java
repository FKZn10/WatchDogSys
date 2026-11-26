package com.jpmsoft.jpbpm.pojo.dto;

public class BatchhandlingTaskListDto {
    public String fileId;//需要生成任务的文档id
    public String processed;//下一步骤操作人
    public String opinion;//处理意见
    public String uId;//当前操作人
    public String operator;//当前操作人

    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
