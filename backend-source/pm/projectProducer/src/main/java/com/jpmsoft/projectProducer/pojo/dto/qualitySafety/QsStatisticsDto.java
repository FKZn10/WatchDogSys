package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;


import lombok.Data;

@Data
public class QsStatisticsDto {

    private String parentQstId;

    private String title;

    private String value;

    private QsRecordStatisticsDto qsRecordStatisticsDto;

}
