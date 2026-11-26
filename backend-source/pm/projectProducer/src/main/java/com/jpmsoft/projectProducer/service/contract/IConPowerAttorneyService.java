package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPowerAttorneyDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPowerAttorney;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/7 9:49
 */
public interface IConPowerAttorneyService extends IService<ConPowerAttorney> {

    BaseResponse createOrUpdateConPowerAttorney(ConPowerAttorneyDTO request);

    BaseResponse getConPowerAttorneyList(ConPowerAttorneyDTO request, String uid);

    BaseResponse getConPowerAttorneyById(String id);

    BaseResponse contractApprove2Create(String conId);

}
