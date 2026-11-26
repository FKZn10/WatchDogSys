package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyRequest;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterApply;

public interface IMeEnterApplyService extends IService<MeEnterApply> {

    BaseResponse createOrUpdateMeEnterApply(MeEnterApplyRequest request);

    BaseResponse getMeEnterApplyList(MeEnterApplyRequest request, String uId);

    BaseResponse getMeEnterApplyByEaId(String eaId);

    MeEnterApply getMeEnterApplyPojoByEaId(String eaId);
}
