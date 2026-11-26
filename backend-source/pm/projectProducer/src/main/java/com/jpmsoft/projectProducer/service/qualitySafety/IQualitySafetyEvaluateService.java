package com.jpmsoft.projectProducer.service.qualitySafety;

import com.jpmsoft.projectProducer.pojo.request.evaluate.qualitySafety.QualitySafetyEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;

public interface IQualitySafetyEvaluateService {
    IndicatorDataResponse getQualitySafetyQuestionNumSG(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumSGImportant(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumJL(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumJLImportant(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumSGJL(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumSGJLSame(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameImportant(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumYZ(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumYZImportant(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumYZSGSame(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameImportant(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumYZJLSame(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameImportant(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumZG(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionNumZGImportant(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionZCRate(QualitySafetyEvaluateRequest request);

    IndicatorDataResponse getQualitySafetyQuestionImportantRate(QualitySafetyEvaluateRequest request);
}
