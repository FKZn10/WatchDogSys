package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyDtlRequest;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeEnterApplyDtl;

import java.util.List;

public interface IMeEnterApplyDtlService extends IService<MeEnterApplyDtl> {

    BaseResponse createOrUpdateMeEnterApplyDtl(MeEnterApplyDtlRequest request);

    void createOrUpdateMeEnterApplyDtls(List<MeEnterApplyDtlRequest> applyDtls, String eaId);

    void updateMeEnterApplyDtlsByBath(List<MeEnterApplyDtlRequest> applyDtls);

    BaseResponse getMeEnterApplyDtlList(MeEnterApplyDtlRequest request, String uId);

    boolean existWithMetId(String metId);
}
