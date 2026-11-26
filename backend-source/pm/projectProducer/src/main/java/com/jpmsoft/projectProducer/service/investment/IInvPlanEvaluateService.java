package com.jpmsoft.projectProducer.service.investment;

import com.jpmsoft.projectProducer.pojo.request.evaluate.investment.InvPlanEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;

public interface IInvPlanEvaluateService {
    IndicatorDataResponse getInvPlanAnnualCompletion(InvPlanEvaluateRequest request);

    IndicatorDataResponse getInvPlanMonthCompletion(InvPlanEvaluateRequest request);

    IndicatorDataResponse getInvPlanAnnualInvestment(InvPlanEvaluateRequest request);

    IndicatorDataResponse getInvPlanMonthlyInvestment(InvPlanEvaluateRequest request);

    IndicatorDataResponse getInvPlanMonthlyPlanInvestment(InvPlanEvaluateRequest request);

    IndicatorDataResponse getInvPlanCumulativeInvestment(InvPlanEvaluateRequest request);
}