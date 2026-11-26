package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.DesignChange.ConEnBudgetResponse;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnBudget;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface ConEnBudgetMapper extends BaseMapper<ConEnBudget> {

    List<ConEnBudgetResponse> getConEnBudgetList(@Param("params") Map<String, Object> params);

    Integer getConEnBudgetListCount(@Param("params") Map<String, Object> params);

    ConEnBudgetResponse getConEnBudgetById(@Param("enbId") String enbId);
    @Select("select sum(Enb_Estimate_Amt) from Con_En_Budget where Enb_State=7 and Con_Id=#{conId}")
    BigDecimal getNotContainAmtByConId(@Param("conId") String conId);
}
