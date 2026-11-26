package com.jpmsoft.projectProducer.mapper.audit;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Audit.AuditBudgetModel;
import com.jpmsoft.projectProducer.pojo.entity.audit.AuditBudget;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface AuditBudgetMapper extends BaseMapper<AuditBudget> {

    List<AuditBudgetModel> getAuditBudgetList(@Param("page") IPage<AuditBudgetModel> page, @Param("params") Map<String, Object> params);


    AuditBudgetModel getAuditBudgetById(@Param("abId") String abId);

    List<AuditBudgetModel> getAuditBudgetBySecId(@Param("secId") String secId);

}
