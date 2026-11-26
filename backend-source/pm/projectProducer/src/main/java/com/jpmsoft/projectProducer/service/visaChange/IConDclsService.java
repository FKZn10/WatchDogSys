package com.jpmsoft.projectProducer.service.visaChange;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDclsDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDcls;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/7 8:40
 */
public interface IConDclsService extends IService<ConDcls> {

    BaseResponse createOrUpdateConDcls(ConDclsDTO request);

    BaseResponse getConDclsList(ConDclsDTO request, String uid);

    BaseResponse getConDclsById(String id);
}
