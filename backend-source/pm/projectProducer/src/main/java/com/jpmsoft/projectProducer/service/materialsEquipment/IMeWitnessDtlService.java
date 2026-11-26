package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeWitnessDtlRequest;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeWitnessDtl;

import java.util.List;

public interface IMeWitnessDtlService extends IService<MeWitnessDtl> {

    void crateOrUpdateMeWitnessDtl(List<MeWitnessDtlRequest> datas, String wiId);

    void updateValueNull(String wiId);

    BaseResponse getMeWitnessDtlList(MeWitnessDtlRequest request, String uId);

    BaseResponse getMeWitnessDtlInspeList(MeWitnessDtlRequest request, String uId);
}
