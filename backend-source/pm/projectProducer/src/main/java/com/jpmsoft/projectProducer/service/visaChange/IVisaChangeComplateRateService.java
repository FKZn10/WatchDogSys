package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDccsCompanyDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDccsCompany;
import com.jpmsoft.projectProducer.pojo.request.evaluate.BaseEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;

import java.util.List;

public interface IVisaChangeComplateRateService {

    IndicatorDataResponse getVisaChangeCompleteRate(BaseEvaluateRequest request);

    IndicatorDataResponse getVisaChangePotentialMoneyRate(BaseEvaluateRequest request);

    IndicatorDataResponse getVisaChange(BaseEvaluateRequest request);
    IndicatorDataResponse getVisaChangeComplete(BaseEvaluateRequest request);

    IndicatorDataResponse getVisaChangePotentialMoney(BaseEvaluateRequest request);
}
