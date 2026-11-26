package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandRecommendDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandRecommend;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:46
 */
public interface IMeBrandRecommendService extends IService<MeBrandRecommend> {

    BaseResponse createOrUpdateMeBrandRecommend(MeBrandRecommendDto request);

    BaseResponse getMeBrandRecommendList(MeBrandRecommendDto request, String uId);

    BaseResponse getMeBrandRecommendById(String id);
}
