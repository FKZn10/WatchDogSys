package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConContractChangeDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractChange;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/18 13:43
 */
public interface IConContractChangeService extends IService<ConContractChange> {


    BaseResponse createOrUpdateConContractChange(ConContractChangeDTO request);

    BaseResponse getContractChangeList(ConContractChangeDTO request, String uId);

    BaseResponse getContractChangeById(String id);

    BaseResponse approve2RefreshContract(String id);

    boolean havingProcessContractChange(String conId,String id);
}
