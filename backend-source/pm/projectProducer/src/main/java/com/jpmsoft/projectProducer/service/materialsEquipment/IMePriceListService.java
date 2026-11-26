package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MePriceListDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MePriceList;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:33
 */
public interface IMePriceListService extends IService<MePriceList> {

    BaseResponse createOrUpdateMePriceList(MePriceListDto request);

    BaseResponse getMePriceListList(MePriceListDto request, String uId);

    BaseResponse getMePriceListById(String id);
}
