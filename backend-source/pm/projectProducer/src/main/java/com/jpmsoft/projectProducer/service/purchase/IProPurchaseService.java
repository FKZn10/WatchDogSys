package com.jpmsoft.projectProducer.service.purchase;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.purchase.ProPurchaseDto;
import com.jpmsoft.projectProducer.pojo.entity.purchase.ProPurchase;

public interface IProPurchaseService extends IService<ProPurchase> {
    BaseResponse createOrUpdatePropurchase(ProPurchaseDto request);

    BaseResponse createPropurchase(ProPurchaseDto request);

    BaseResponse updatePropurchase(ProPurchaseDto request);

    BaseResponse getPropurchaseById(String id);

    BaseResponse getPropurchaseList(ProPurchaseDto request);
}
