package com.jpmsoft.projectProducer.service.subcontract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Subcontract.SubcontractApplyRequest;
import com.jpmsoft.projectProducer.pojo.entity.subcontract.SubcontractApply;

public interface ISubcontractApplyService extends IService<SubcontractApply> {

    BaseResponse createOrUpdateSubcontractApply(SubcontractApplyRequest request);

    BaseResponse getSubcontractApplyList(SubcontractApplyRequest request);

    BaseResponse getSubcontractApplyBySaId(String saId);
}
