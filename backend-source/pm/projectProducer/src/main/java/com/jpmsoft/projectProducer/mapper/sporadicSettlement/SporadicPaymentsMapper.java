package com.jpmsoft.projectProducer.mapper.sporadicSettlement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Maintenance.MaintenanceFinanceResponse;
import com.jpmsoft.common.model.response.Maintenance.SporadicPaymentsResponse;
import com.jpmsoft.projectProducer.pojo.entity.SporadicSetttlement.SporadicPayments;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SporadicPaymentsMapper extends BaseMapper<SporadicPayments> {

    List<SporadicPaymentsResponse> getSporadicPaymentsList(@Param("page") IPage<SporadicPaymentsResponse> page,
                                                           @Param("params") Map<String, Object> query);

    List<MaintenanceFinanceResponse> getMfCodesBySettlementIds(@Param("list") List<String> sporadicSettlementIds);

    String queryAreaManagerIds(@Param("list") List<String> mrIdList,@Param("actCode") String actCode);

    List<MaintenanceRecord> getModifymrRepairState(@Param("id") String id);

    String getSporadicPaymentsBySeId(String seId);
}
