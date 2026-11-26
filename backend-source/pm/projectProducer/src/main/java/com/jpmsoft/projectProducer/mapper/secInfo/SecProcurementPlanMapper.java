package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecProcurementPlanDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProcurementPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecProcurementPlanMapper extends BaseMapper<SecProcurementPlan> {

    SecProcurementPlan getSecProcurementPlanByName(@Param("pId")String pId,@Param("planName") String planName);


    SecProcurementPlanDto getSecProcurementPlanById(@Param("ppId") String ppId);


    List<SecProcurementPlanDto> getSecProcurementPlanList(@Param("page") IPage<SecProcurementPlanDto> page,@Param("params") Map<String, Object> params);


    List<SecProcurementPlanDto> getSecProcurementPlanByPid(@Param("pId") String pId);



}
