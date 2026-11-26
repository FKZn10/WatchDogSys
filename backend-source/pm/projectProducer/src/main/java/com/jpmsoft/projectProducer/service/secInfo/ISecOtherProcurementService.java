package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOtherProcurementModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOtherProcurement;

public interface ISecOtherProcurementService extends IService<SecOtherProcurement> {

    BaseResponse createOrUpdateSecOtherProcurement(SecOtherProcurementModel request);

    BaseResponse getSecOtherProcurementList(SecOtherProcurementModel request);

    BaseResponse getSecOtherProcurementById(String sopId);
}
