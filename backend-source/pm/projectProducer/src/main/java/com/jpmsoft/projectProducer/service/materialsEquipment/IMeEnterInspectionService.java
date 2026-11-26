package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeEnterInspectionRequest;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterInspection;

public interface IMeEnterInspectionService extends IService<MeEnterInspection> {

    BaseResponse createOrUpdateMeEnterInspection(MeEnterInspectionRequest request);

    BaseResponse getMeEnterInspectionList(MeEnterInspectionRequest request, String uId);

    BaseResponse getMeEnterInspectionByEiId(String eiId);
}
