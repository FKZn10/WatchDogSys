package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.FilterObject;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditModel;
import com.jpmsoft.common.model.response.Contract.ConEcResponse;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementFirstAudit;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SettlementFirstAuditMapper extends BaseMapper<SettlementFirstAudit> {
    List<FilterObject> getCompanyValueByCode(@Param("value")String dbValue);

    List<FilterObject> getPersonValueByCode(@Param("value")String dbValue);

    List<Object> getValueByCode(@Param("value")String dbValue);

    List<SettlementFirstAuditModel> getSettlementFirstAuditList(@Param("params")Map<String, Object> query, @Param("page") IPage<SettlementFirstAuditModel> page);

    List<FilterObject> getProjectNameByCode(@Param("value")String dbValue);

    List<FilterObject> getContractNameByCode(@Param("value")String dbValue);

    SettlementFirstAuditModel selectModelById(@Param("id")String id);

    List<ConEcResponse> getContractInfo(@Param("contractId")String contractId);
}
