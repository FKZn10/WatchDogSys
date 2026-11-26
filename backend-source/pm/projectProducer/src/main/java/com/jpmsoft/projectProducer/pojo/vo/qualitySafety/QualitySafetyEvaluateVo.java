package com.jpmsoft.projectProducer.pojo.vo.qualitySafety;

import lombok.Data;

@Data
public class QualitySafetyEvaluateVo {
    private Integer questionNumSG;
    private Integer questionNumSGImportant;
    private Integer questionNumJL;
    private Integer questionNumJLImportant;
    private Integer questionNumSGJL;
    private Integer questionNumSGJLSame;
    private Integer questionNumSGJLSameImportant;
    private Integer questionNumYZ;
    private Integer questionNumYZImportant;
    private Integer questionNumYZSGSame;
    private Integer questionNumYZSGSameImportant;
    private Integer questionNumYZJLSame;
    private Integer questionNumYZJLSameImportant;
    private Integer questionNumZG;
    private Integer questionNumZGImportant;
    private Double questionZCRate;
    private Double questionImportantRate;
}
