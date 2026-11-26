package com.jpmsoft.projectProducer.mapper.sporadicSettlement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceFinanceResponse;
import com.jpmsoft.common.model.response.Maintenance.SporadicSettlementResponse;
import com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement.SporadicSettlement;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SporadicSettlementMapper extends BaseMapper<SporadicSettlement> {
    List<SporadicSettlementResponse> getSporadicSettlementeList(@Param("page") IPage<SporadicSettlementResponse> page,@Param("params") Map<String, Object> params);

    int checkSporadicSettlement(@Param("mrIds") List<String> mrIds,@Param("seId") String seId);

    List<MaintenanceFinanceResponse> getMaintenanceFinanceByMrIds(@Param("mrIds") List<String> mrIds);
}
