package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayWitnessDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayWitness;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/30 17:31
 */
public interface ConPayWitnessMapper extends BaseMapper<ConPayWitness> {

    List<ConPayWitnessDTO> getConPayWitnessByPayId(@Param("payId") String payId);
    List<ConPayWitnessDTO> getConPayWitnessByConId(@Param("conId") String conId);
    List<ConPayWitnessDTO> getConPayWitnessByOrderNo(@Param("orderNo") String orderNo);
}
