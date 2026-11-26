package com.jpmsoft.common.model.request.Plan;

import lombok.Data;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/4/8 11:23
 */
@Data
public class GantSectionTaskDTO {

    private String id;
    private String planId;
    private String taskId;
    private String gstSection;
    private String gstSource;
}
