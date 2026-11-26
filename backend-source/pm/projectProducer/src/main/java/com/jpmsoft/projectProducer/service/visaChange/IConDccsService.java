package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDccsDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDccs;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:49
 */
public interface IConDccsService extends IService<ConDccs> {

    BaseResponse createOrUpdateConDccs(ConDccsDTO request);

    BaseResponse getConDccsList(ConDccsDTO request, String uid);

    BaseResponse getConDccsById(String id);
}
