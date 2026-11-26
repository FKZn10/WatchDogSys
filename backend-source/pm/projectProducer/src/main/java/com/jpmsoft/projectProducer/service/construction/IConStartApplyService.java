package com.jpmsoft.projectProducer.service.construction;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Construction.ConStartApplyModel;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConStartApply;

public interface IConStartApplyService extends IService<ConStartApply> {

    BaseResponse createOrUpdateConStartApply(ConStartApplyModel request);

    BaseResponse getConStartApplyList(ConStartApplyModel request, String uId);

    BaseResponse getConStartApplyById(String saId);

    BaseResponse syncDataToContract(String saId);
}
