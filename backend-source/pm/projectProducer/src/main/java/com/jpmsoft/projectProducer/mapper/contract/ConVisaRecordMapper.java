package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Contract.ConVisaRecordResponse;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConVisaRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ConVisaRecordMapper extends BaseMapper<ConVisaRecord> {

    List<ConVisaRecordResponse> getConVisaRecordList(@Param("page") IPage<ConVisaRecordResponse> page, @Param("params") Map<String, Object> params);


    ConVisaRecordResponse getConVisaRecordByVrId(@Param("vrId") String vrId);

}
