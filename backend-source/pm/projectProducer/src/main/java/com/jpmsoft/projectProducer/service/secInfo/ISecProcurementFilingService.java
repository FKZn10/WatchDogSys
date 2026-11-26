package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecProcurementFilingModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementFiling;

public interface ISecProcurementFilingService extends IService<SecProcurementFiling> {

    BaseResponse createOrUpdateSecProcurementFiling(SecProcurementFilingModel request);

    BaseResponse getSecProcurementFilingList(SecProcurementFilingModel request, String uId);

    BaseResponse getSecProcurementFilingById(String pfId);

    BaseResponse getSecProcurementFilingLastByPid(String pId);

    /**
     * 审批通过生成标段信息
     *
     * @param pfId
     * @return
     */
    BaseResponse approve2GenerateSecInfo(String pfId);
}
