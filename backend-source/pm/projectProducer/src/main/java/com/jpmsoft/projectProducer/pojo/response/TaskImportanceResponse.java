package com.jpmsoft.projectProducer.pojo.response;

import lombok.Data;


@Data
public class TaskImportanceResponse {
    private String pName; // 项目名
    private int lqCount;  // 临期任务数
    private int yqCount;  // 逾期任务数
    private String lqTaskImportance; // 临期任务的Task_Importance
    private String yqTaskImportance; // 逾期任务的Task_Importance
}
