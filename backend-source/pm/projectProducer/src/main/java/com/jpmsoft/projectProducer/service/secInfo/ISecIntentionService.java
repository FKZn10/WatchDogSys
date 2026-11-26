package com.jpmsoft.projectProducer.service.secInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecIntentionDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecIntention;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/20 16:15
 */
public interface ISecIntentionService extends IService<SecIntention> {

    BaseResponse createOrUpdate(SecIntentionDTO request);

    BaseResponse getSecIntentionById(String id);
}
