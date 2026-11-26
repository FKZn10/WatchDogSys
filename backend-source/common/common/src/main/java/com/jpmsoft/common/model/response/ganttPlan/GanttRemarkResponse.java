package com.jpmsoft.common.model.response.ganttPlan;

import lombok.Data;

import java.util.Date;

/**
 * @author tianhelin
 * @date 2025/5/26
 */
@Data
public class GanttRemarkResponse {
    private String id;
    private String gmsId;
    private String remark;
    private Date writeTime;
    private String createUser;
    private String createUserName;
}
