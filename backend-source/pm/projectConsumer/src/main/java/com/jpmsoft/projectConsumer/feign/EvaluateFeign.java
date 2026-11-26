package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.projectConsumer.request.BaseEvaluateRequest;
import com.jpmsoft.projectConsumer.request.investment.InvPlanEvaluateRequest;
import com.jpmsoft.projectConsumer.request.qualitySafety.QualitySafetyEvaluateRequest;
import com.jpmsoft.projectConsumer.response.IndicatorDataResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
@Component
@FeignClient(name = "project-producer", contextId = "evaluate")
public interface EvaluateFeign {
    @PostMapping("/v1/dataEvaluate/InvPlanEvaluate/getInvPlanAnnualInvestment")
    IndicatorDataResponse getInvPlanAnnualInvestment(InvPlanEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/InvPlanEvaluate/getInvPlanMonthlyInvestment")
    IndicatorDataResponse getInvPlanMonthlyInvestment(InvPlanEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/InvPlanEvaluate/getInvPlanMonthlyPlanInvestment")
    IndicatorDataResponse getInvPlanMonthlyPlanInvestment(InvPlanEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/InvPlanEvaluate/getInvPlanCumulativeInvestment")
    IndicatorDataResponse getInvPlanCumulativeInvestment(InvPlanEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/InvPlanEvaluate/getInvPlanAnnualCompletion")
    IndicatorDataResponse getInvPlanAnnualCompletion(InvPlanEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/InvPlanEvaluate/getInvPlanMonthCompletion")
    IndicatorDataResponse getInvPlanMonthCompletion(InvPlanEvaluateRequest request);


    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSG")
    IndicatorDataResponse getQualitySafetyQuestionNumSG(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSGImportant")
    IndicatorDataResponse getQualitySafetyQuestionNumSGImportant(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumJL")
    IndicatorDataResponse getQualitySafetyQuestionNumJL(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumJLImportant")
    IndicatorDataResponse getQualitySafetyQuestionNumJLImportant(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSGJL")
    IndicatorDataResponse getQualitySafetyQuestionNumSGJL(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSGJLSame")
    IndicatorDataResponse getQualitySafetyQuestionNumSGJLSame(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSGJLSameImportant")
    IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameImportant(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZ")
    IndicatorDataResponse getQualitySafetyQuestionNumYZ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZImportant")
    IndicatorDataResponse getQualitySafetyQuestionNumYZImportant(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZSGSame")
    IndicatorDataResponse getQualitySafetyQuestionNumYZSGSame(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZSGSameImportant")
    IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameImportant(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZJLSame")
    IndicatorDataResponse getQualitySafetyQuestionNumYZJLSame(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZJLSameImportant")
    IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameImportant(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumZG")
    IndicatorDataResponse getQualitySafetyQuestionNumZG(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumZGImportant")
    IndicatorDataResponse getQualitySafetyQuestionNumZGImportant(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionZCRate")
    IndicatorDataResponse getQualitySafetyQuestionZCRate(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionImportantRate")
    IndicatorDataResponse getQualitySafetyQuestionImportantRate(QualitySafetyEvaluateRequest request);



    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSGAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumSGAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSGImportantAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumSGImportantAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumJLAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumJLAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumJLImportantAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumJLImportantAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSGJLAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumSGJLAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSGJLSameAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumSGJLSameImportantAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameImportantAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumYZAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZImportantAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumYZImportantAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZSGSameAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZSGSameImportantAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameImportantAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZJLSameAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumYZJLSameImportantAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameImportantAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumZGAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumZGAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionNumZGImportantAQ")
    IndicatorDataResponse getQualitySafetyQuestionNumZGImportantAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionZCRateAQ")
    IndicatorDataResponse getQualitySafetyQuestionZCRateAQ(QualitySafetyEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/QualitySafetyEvaluate/getQualitySafetyQuestionImportantRateAQ")
    IndicatorDataResponse getQualitySafetyQuestionImportantRateAQ(QualitySafetyEvaluateRequest request);




    @PostMapping("/v1/dataEvaluate/ContractEvaluate/getContractScMoney")
    IndicatorDataResponse getContractScMoney(BaseEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/ContractEvaluate/getConPayTotalScMoney")
    IndicatorDataResponse getConPayTotalScMoney(BaseEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/VisaChangeEvaluate/getVisaChange")
    IndicatorDataResponse getVisaChange(BaseEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/VisaChangeEvaluate/getVisaChangeComplete")
    IndicatorDataResponse getVisaChangeComplete(BaseEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/VisaChangeEvaluate/getVisaChangePotentialMoney")
    IndicatorDataResponse getVisaChangePotentialMoney(BaseEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/VisaChangeEvaluate/getVisaChangeCompleteRate")
    IndicatorDataResponse getVisaChangeCompleteRate(BaseEvaluateRequest request);
    @PostMapping("/v1/dataEvaluate/VisaChangeEvaluate/getVisaChangePotentialMoneyRate")
    IndicatorDataResponse getVisaChangePotentialMoneyRate(BaseEvaluateRequest request);

}
