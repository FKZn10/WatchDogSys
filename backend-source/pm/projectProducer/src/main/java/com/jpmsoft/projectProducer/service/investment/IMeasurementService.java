package com.jpmsoft.projectProducer.service.investment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.investment.FundPlanModifyDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.FundPlanModify;
import io.micrometer.core.instrument.Measurement;

public interface IMeasurementService {

    BaseResponse getCalculationTenderPriceBySecId(String pId, String secId);
}
