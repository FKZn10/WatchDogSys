package com.jpmsoft.projectProducer.pojo.dto.ganttPlan;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * @Author: 李翔
 * @date: 2025/5/26 17:33
 */
@Data
public class MonthlyReportDTO implements Serializable {
    private String year;
    private String month;
}
