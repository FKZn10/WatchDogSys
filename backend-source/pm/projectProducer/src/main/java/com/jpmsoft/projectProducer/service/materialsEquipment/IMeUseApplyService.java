package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeUseApplyReuqest;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeUseApply;

public interface IMeUseApplyService extends IService<MeUseApply> {

    BaseResponse createOrUpdateMeUseApply(MeUseApplyReuqest request);

    BaseResponse getMeUseApplyList(MeUseApplyReuqest request, String uId);

    BaseResponse getMeUseApplyByUaId(String uaId);
}
