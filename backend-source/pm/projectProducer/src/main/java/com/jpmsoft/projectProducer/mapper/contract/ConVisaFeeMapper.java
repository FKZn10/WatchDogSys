package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Contract.ConVisaFeeResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConVisaFee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConVisaFeeMapper extends BaseMapper<ConVisaFee> {

    List<ConVisaFeeResponse> getConVisaFeeList(@Param("page") IPage<ConVisaFeeResponse> page, @Param("params") Map<String, Object> params);


    ConVisaFeeResponse getConVisaFeeByVfId(@Param("vfId") String vfId, @Param("conWhere") Integer conWhere);
}
