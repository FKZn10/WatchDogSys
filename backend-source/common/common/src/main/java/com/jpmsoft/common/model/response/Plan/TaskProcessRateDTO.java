package com.jpmsoft.common.model.response.Plan;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/6/5 14:34
 */
@Data
public class TaskProcessRateDTO implements Serializable {

    private Float rate;
    private Date completeDate;
    private String taskId;
}
