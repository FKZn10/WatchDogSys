package com.jpmsoft.projectProducer.pojo.request.thirdDock;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/5/22 11:17
 * @Description:
 * @Version: 1.0
 */
@Data
public class WorkFlowRequest implements Serializable {

    private static final long serialVersionUID = 4057506543553724717L;

    private String requestName;

    private String requestId;

    private String workflowId;

    /**
     * 云上建工用户id
     */
    private String thirdUserId;

    private List<MainDataRequest> mainData;

    //业务id
    private String bizId;

    //业务模块类型
    private String bizType;

    private String remark="请领导审批。";

}
