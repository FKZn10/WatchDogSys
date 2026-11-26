package com.jpmsoft.projectProducer.service.subcontract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Subcontract.SubcontractRecordRequest;
import com.jpmsoft.projectProducer.pojo.entity.subcontract.SubcontractRecord;

import java.util.List;

public interface ISubcontractRecordService extends IService<SubcontractRecord> {

    BaseResponse createOrUpdateSubcontractRecord(SubcontractRecordRequest request);

    BaseResponse getSubcontractRecordList(SubcontractRecordRequest request, String uId);

    BaseResponse getSubcontractRecordByScId(String scId);

    BaseResponse getSubcontractRecordByScIds(List<String> scIds);

    BaseResponse getSubcontractRecordByScMasterConId(String scMasterConId);
}
