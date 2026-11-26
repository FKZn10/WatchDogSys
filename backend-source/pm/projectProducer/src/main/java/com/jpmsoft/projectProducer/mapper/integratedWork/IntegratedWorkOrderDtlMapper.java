package com.jpmsoft.projectProducer.mapper.integratedWork;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.integratedWork.IntegratedWorkOrderDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.integratedWork.IntegratedWorkOrderDtl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IntegratedWorkOrderDtlMapper extends BaseMapper<IntegratedWorkOrderDtl> {
    List<IntegratedWorkOrderDtlDTO> getLastWeekOrders(@Param("conConstructionId") String conConstructionId, @Param("conSupervisionId") String conSupervisionId, @Param("weeklyStartDate") String weeklyStartDate, @Param("id") String id);
}
