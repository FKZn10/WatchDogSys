package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOtherProcurementModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOtherProcurement;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecOtherProcurementMapper extends BaseMapper<SecOtherProcurement> {

    List<SecOtherProcurementModel> getSecOtherProcurementList(@Param("params") Map<String, Object> params);

    Integer getSecOtherProcurementListCount(@Param("params") Map<String, Object> params);

    SecOtherProcurementModel getSecOtherProcurementById(@Param("sopId") String sopId);
}
