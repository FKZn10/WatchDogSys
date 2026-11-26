package com.jpmsoft.projectProducer.service.zdyxy;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionCompletionConfirmModel;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.ConstructionCompletionConfirm;

public interface ConstructionCompletionConfirmService extends IService<ConstructionCompletionConfirm> {
    BaseResponse saveOrUpdateData(ConstructionCompletionConfirmModel model);

    BaseResponse approvalData(ConstructionCompletionConfirmModel request);

    BaseResponse getList(ConstructionCompletionConfirmModel request, String token);

    BaseResponse getDetail(String id);
}
