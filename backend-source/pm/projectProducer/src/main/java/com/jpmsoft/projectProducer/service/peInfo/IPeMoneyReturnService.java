package com.jpmsoft.projectProducer.service.peInfo;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.peinfo.PeMoneyReturnDTO;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMoneyReturn;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/25 18:45
 */
public interface IPeMoneyReturnService extends IService<PeMoneyReturn> {

    BaseResponse createOrUpdatePeMoneyReturn(PeMoneyReturnDTO request);

    BaseResponse getPeMoneyReturnList(PeMoneyReturnDTO request,String uid);

    BaseResponse getPeMoneyReturnById(String id);


    BaseResponse getPeMoneySumByConId(String conId);
}
