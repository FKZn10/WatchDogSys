package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeWitnessInspectionRequest;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeWitnessInspection;

public interface IMeWitnessInspectionService extends IService<MeWitnessInspection> {

    BaseResponse createOrUpdateMeWitnessInspection(MeWitnessInspectionRequest request);

    BaseResponse updateMeWitnessInspectionDtl(MeWitnessInspectionRequest request);

    BaseResponse return2InitWitnessInspectionDtl(String wiId);

    BaseResponse getMeWitnessInspectionList(MeWitnessInspectionRequest request, String uId);

    BaseResponse getMeWitnessInspectionByWiId(String wiId);

    BaseResponse getMeWitnessInspectionByConId(String conId);

    BaseResponse getMeWitnessInspectionByOrderNo(String wiOrderNo);
}
