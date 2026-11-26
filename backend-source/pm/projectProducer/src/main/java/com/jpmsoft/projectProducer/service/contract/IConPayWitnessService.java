package com.jpmsoft.projectProducer.service.contract;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayWitnessDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayWitness;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/30 17:31
 */
public interface IConPayWitnessService extends IService<ConPayWitness> {
    void createOrUpdate(List<ConPayWitnessDTO> data, String payId);

    void deleteByPayId(String payId);

    List<ConPayWitnessDTO> getConPayWitnessByPayId(String payId);

    BaseResponse getConPayWitnessByOrderNo(String orderNo);

    BaseResponse getConPayWitnessByConId(String conId);
}
