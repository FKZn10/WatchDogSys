package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConTaskDelegationDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConTaskDelegation;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/5 13:57
 */
public interface IConTaskDelegationService extends IService<ConTaskDelegation> {

    BaseResponse createOrUpdateConTaskDelegation(ConTaskDelegationDto request);

    BaseResponse getConTaskDelegationList(ConTaskDelegationDto request, String uId);

    BaseResponse getConTaskDelegationById(String id);

    BaseResponse getConTaskDelegationByConId(String conId);
}
