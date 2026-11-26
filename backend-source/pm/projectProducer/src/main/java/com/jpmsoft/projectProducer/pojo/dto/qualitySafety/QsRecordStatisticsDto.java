package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import lombok.Data;

@Data
public class QsRecordStatisticsDto {

    //问题数量
    private Integer questionNum;

    //其中重大问题数量
    private Integer importantQuestionNum;

    //整改数
    private Integer rectificationNum;

    //重大问题整改数
    private Integer importantRectificationNum;

    //问题整改率
    private String rectificationRate;

    //重大问题整改率
    private String importantRectificationRate;

}
