package com.jpmsoft.common.model.third;

/**
 * 代办信息
 */
public class AgencyModel {

    private String taskId;
    private String registerCode = "3002";
    /**
     * 待办标题
     */
    private String title;
    /**
     * 待办发起人
     */
    private String thirdSenderId;
    /**
     * 待办发起人姓名
     */
    private String senderName;
    /**
     * 待办接收人
     * 账号
     */
    private String thirdReceiverId;
    /**
     * 接收人唯一标识
     * 账号
     */
    private String noneBindingReceiver;
    /**
     * 发送人唯一标识
     * 账号
     */
    private String noneBindingSender;
    /**
     * 流程创建人唯一标识
     */
    private String fileCreator;
    /**
     * 待办创建时间
     * 代办发送时间
     */
    private String creationDate;
    /**
     * 状态
     */
    private String state;
    /**
     * 处理后状态：0/1/2/3同意已办/不同意已办/取消/驳回
     */
    private String subState;
    /**
     *
     */
    private String content;
    private String url;
    private String h5url;
    private String appParam;
    private String tpId;
    private String actName;
    private String flowName;
    private String entityId;
    private String tCode;


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThirdSenderId() {
        return thirdSenderId;
    }

    public void setThirdSenderId(String thirdSenderId) {
        this.thirdSenderId = thirdSenderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getThirdReceiverId() {
        return thirdReceiverId;
    }

    public void setThirdReceiverId(String thirdReceiverId) {
        this.thirdReceiverId = thirdReceiverId;
    }

    public String getNoneBindingReceiver() {
        return noneBindingReceiver;
    }

    public void setNoneBindingReceiver(String noneBindingReceiver) {
        this.noneBindingReceiver = noneBindingReceiver;
    }

    public String getNoneBindingSender() {
        return noneBindingSender;
    }

    public String getFileCreator() {
        return fileCreator;
    }

    public void setFileCreator(String fileCreator) {
        this.fileCreator = fileCreator;
    }

    public void setNoneBindingSender(String noneBindingSender) {
        this.noneBindingSender = noneBindingSender;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubState() {
        return subState;
    }

    public void setSubState(String subState) {
        this.subState = subState;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getH5url() {
        return h5url;
    }

    public void setH5url(String h5url) {
        this.h5url = h5url;
    }

    public String getAppParam() {
        return appParam;
    }

    public void setAppParam(String appParam) {
        this.appParam = appParam;
    }

    public String getTpId() {
        return tpId;
    }

    public void setTpId(String tpId) {
        this.tpId = tpId;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }


    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }
}
