package com.jpmsoft.projectProducer.service.materialsEquipment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandLibraryApplyDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandLibraryApply;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:06
 */
public interface IMeBrandLibraryApplyService extends IService<MeBrandLibraryApply> {

    BaseResponse createOrUpdateMeBrandLibraryApply(MeBrandLibraryApplyDto request);

    BaseResponse getMeBrandLibraryApplyList(MeBrandLibraryApplyDto request, String uId);

    BaseResponse getMeBrandLibraryApplyById(String id);
}
