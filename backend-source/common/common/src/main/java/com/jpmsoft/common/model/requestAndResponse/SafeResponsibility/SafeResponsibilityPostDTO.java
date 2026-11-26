package com.jpmsoft.common.model.requestAndResponse.SafeResponsibility;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 安全责任岗位数据传输对象。
 */
@Data
public class SafeResponsibilityPostDTO {
    private String id;
    private String systemId;
    private String levelId;
    private String postName;
    private String postDescription;
    private String dailyRequirement;
    private String controlStandard;
    private String assessmentBasis;
    private String accountabilityScenario;
    private Integer sortNo;
    private Boolean status;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    private List<SafeResponsibilityPostDutyDTO> dutyList;
}
