package com.jpmsoft.projectProducer.pojo.dto.ganttPlan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 进度执行临期时间配置
 *
 * @author tianhelin
 * @date 2025/5/28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleExpirationTimeDTO {
    private Integer milestone;
    private Integer firstLevel;
    private Integer secondLevel;
}
