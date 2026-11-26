package com.jpmsoft.projectConsumer.pojo.dto.ganttPlan;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/26 10:07
 */
@Data
public class MilestoneSubmitDTO implements Serializable {

    private String taskId;
    private String finishTime;
    private String tCode;

    private String gtpScenePhotos;
    private String gtpLiveVideo;
    private String gtpSignatureConfirm;
    private String fileRelationId;
}
