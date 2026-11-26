package com.jpmsoft.projectProducer.pojo.vo.qualitySafety;

import lombok.Data;

@Data
public class QualitySafetyStatisticFowYWVO {

    private String qstId;

    private String qstName;

    private String qstPath;

    private Integer count;

    private String severityLevelName;
    private Integer qsFinish;
    private String qsFinishRateStr;
}