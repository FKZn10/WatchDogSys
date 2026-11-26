package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeQualityPriceDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeQualityPrice;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 13:50
 */
public interface IMeQualityPriceService extends IService<MeQualityPrice> {

    BaseResponse createOrUpdateMeQualityPrice(MeQualityPriceDto request);

    BaseResponse getMeQualityPriceList(MeQualityPriceDto request,String uid);

    BaseResponse getMeQualityPriceById(String id);
}
