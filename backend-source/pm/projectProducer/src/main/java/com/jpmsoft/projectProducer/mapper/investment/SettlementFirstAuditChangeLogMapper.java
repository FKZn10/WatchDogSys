package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditChangeLogModel;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.SettlementFirstAuditModel;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementFirstAuditChangeLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SettlementFirstAuditChangeLogMapper extends BaseMapper<SettlementFirstAuditChangeLog> {
    List<SettlementFirstAuditChangeLogModel> getSettlementFirstAuditChangeLogs(@Param("id")String id);

    List<SettlementFirstAuditModel> getSettlementFirstAuditChangeLogList(@Param("params")Map<String, Object> query,
                                                                         @Param("page") IPage<SettlementFirstAuditModel> page);

    SettlementFirstAuditChangeLogModel selectModelById(@Param("id")String id);
}
