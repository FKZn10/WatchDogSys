package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConDurationAdjustDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConDurationAdjust;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 10:29
 */
public interface IConDurationAdjustService extends IService<ConDurationAdjust> {
    BaseResponse createOrUpdateConDurationAdjust(ConDurationAdjustDto request);

    BaseResponse getConDurationAdjustList(ConDurationAdjustDto request, String uId);

    BaseResponse getConDurationAdjustById(String id);
}
