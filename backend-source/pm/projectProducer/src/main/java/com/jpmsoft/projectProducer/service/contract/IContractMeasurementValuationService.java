package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ContractMeasurementContractPageRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ContractMeasurementValuationModel;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractMeasurementValuation;

/**
 * 合同计量计价低代码表单 Service
 */
public interface IContractMeasurementValuationService extends IService<ContractMeasurementValuation> {
    BaseResponse createOrUpdateContractMeasurementValuation(ContractMeasurementValuationModel request, String token);

    BaseResponse getContractMeasurementValuationById(String id, String token);

    BaseResponse getContractMeasurementValuationList(ContractMeasurementValuationModel request, String token);

    BaseResponse getContractMeasurementValuationClassInfo(ContractMeasurementValuationModel request, String token);

    BaseResponse getFinishedConsultAndInternalAgencyContractListByPId(
            ContractMeasurementContractPageRequest request, String token);
}
