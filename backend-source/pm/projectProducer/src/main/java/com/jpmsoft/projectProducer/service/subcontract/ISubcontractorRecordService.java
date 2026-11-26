package com.jpmsoft.projectProducer.service.subcontract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Subcontract.SubcontractorRecordRequest;
import com.jpmsoft.projectProducer.pojo.entity.subcontract.SubcontractorRecord;

public interface ISubcontractorRecordService extends IService<SubcontractorRecord> {

    BaseResponse createOrUpdateSubcontractorRecord(SubcontractorRecordRequest request);

    BaseResponse getSubcontractorRecordList(SubcontractorRecordRequest request);

    BaseResponse getSubcontractorRecordBySrId(String srId);

    BaseResponse checkCreditCodeIsExist(String crditCode);

    BaseResponse syncSubcontractorRecordComapny(String srId);
}
