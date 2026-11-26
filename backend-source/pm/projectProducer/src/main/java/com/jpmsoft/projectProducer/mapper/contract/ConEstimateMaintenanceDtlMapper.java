package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateMaintenanceDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayWitnessDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimateMaintenanceDtl;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayWitness;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: shanzhi
 * @date: 2024/05/21 10:05
 */
public interface ConEstimateMaintenanceDtlMapper extends BaseMapper<ConEstimateMaintenanceDtl> {

    List<ConEstimateMaintenanceDtlModel> getConEstimateMaintenanceDtlByCemId(@Param("cemid") String cemid);
}
