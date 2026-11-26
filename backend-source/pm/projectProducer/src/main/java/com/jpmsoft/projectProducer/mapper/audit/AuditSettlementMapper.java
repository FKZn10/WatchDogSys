package com.jpmsoft.projectProducer.mapper.audit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditSettlementModel;
import com.jpmsoft.projectProducer.pojo.entity.audit.AuditSettlement;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface AuditSettlementMapper extends BaseMapper<AuditSettlement> {

    List<AuditSettlementModel> getAuditSettlementList(@Param("page") IPage<AuditSettlementModel> page, @Param("params") Map<String, Object> params);


    AuditSettlementModel getAuditSettlementById(@Param("asId") String asId);

    AuditSettlementModel getAuditSettlementConId(@Param("conId") String conId);


    AuditSettlementModel getApproveAuditSettlementByConId(@Param("conId")  String conId);
}
