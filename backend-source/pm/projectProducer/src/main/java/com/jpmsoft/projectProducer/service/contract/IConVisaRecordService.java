package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConVisaRecordRequest;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConVisaRecord;


public interface IConVisaRecordService extends IService<ConVisaRecord> {


    BaseResponse createOrUpdateConVisaRecord(ConVisaRecordRequest request);

    BaseResponse getConVisaRecordList(ConVisaRecordRequest request, String uId);

    BaseResponse getConVisaRecordByVrId(String vrId);
}
