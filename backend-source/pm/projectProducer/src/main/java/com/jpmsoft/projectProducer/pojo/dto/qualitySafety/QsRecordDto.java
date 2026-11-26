package com.jpmsoft.projectProducer.pojo.dto.qualitySafety;

import lombok.Data;

import java.io.Serializable;

@Data
public class QsRecordDto extends QsPlanTaskDto implements Serializable {

    /**
     * Qs_Type表主键id。
     */
    private String parentQstId;

    // Qs_Type表id。
    private String qstId;

}
