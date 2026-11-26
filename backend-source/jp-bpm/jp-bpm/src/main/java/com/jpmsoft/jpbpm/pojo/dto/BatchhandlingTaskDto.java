package com.jpmsoft.jpbpm.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;

import java.util.List;

public class BatchhandlingTaskDto  extends BaseModeRequest {
    public String tCode;//文档模模板编码
    public List<BatchhandlingTaskListDto> sendList;//需要生成代办信息
    public String teId;//租户id，没有传All
    public String currentNode;//当前节点，startNode：任务生成在发起人节点，流程状态为0，createNode：流程启动，任务生成在发起人之后的节点，流程状态为4；其他，流程正常审批，流程状态为1
    public String onlineConditions;//是否需要线上条件，流程图设置了扭转流转条件需要设置true，其他则设置false
    public String notGenerateTask;
    public String gettCode() {
        return tCode;
    }

    public void settCode(String tCode) {
        this.tCode = tCode;
    }

    @Override
    public String getTeId() {
        return teId;
    }

    @Override
    public void setTeId(String teId) {
        this.teId = teId;
    }

    public List<BatchhandlingTaskListDto> getSendList() {
        return sendList;
    }

    public void setSendList(List<BatchhandlingTaskListDto> sendList) {
        this.sendList = sendList;
    }

    public String getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }

    public String getOnlineConditions() {
        return onlineConditions;
    }

    public void setOnlineConditions(String onlineConditions) {
        this.onlineConditions = onlineConditions;
    }

    public String getNotGenerateTask() {
        return notGenerateTask;
    }

    public void setNotGenerateTask(String notGenerateTask) {
        this.notGenerateTask = notGenerateTask;
    }
}


