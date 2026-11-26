package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAudit;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SettlementAuditMapper  extends BaseMapper<SettlementAudit> {
    List<Object> getprojectValueByCode(@Param("value") String dbValue);

    List<Object> getStateByCode(@Param("value") String dbValue);

    List<Object> getAuditContractNumByCode(@Param("value")String dbValue);

    List<Object> getAuditContractMoneyByCode(@Param("value")String dbValue);

    List<Object> getCreateUnameByCode(@Param("value")String dbValue);

    List<Object> getValueByCode(@Param("value") String dbValue);

    Page<SettlementAuditVo> querySettAuditList(@Param("page") Page<SettlementAudit> page, @Param("request")SettlementAuditVo request);
}
