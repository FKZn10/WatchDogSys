package com.jpmsoft.common.model.requestAndResponse.SafeResponsibility;

import lombok.Data;

import java.util.Date;

/**
 * 安全责任体系变更台账数据传输对象。
 */
@Data
public class SafeResponsibilityChangeLogDTO {
    private String id;
    private String systemId;
    private String targetType;
    private String targetId;
    private String targetName;
    private String operationType;
    private String beforeContent;
    private String afterContent;
    private String changeDescription;
    private String operatorUid;
    private String operatorUname;
    private Date operationTime;
    private String batchNo;
}
