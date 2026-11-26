package com.jpmsoft.projectProducer.mapper.construction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.DesignChange.ConDcBudgetResponse;
import com.jpmsoft.projectProducer.pojo.entity.construction.ConDcBudget;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public interface ConDcBudgetMapper extends BaseMapper<ConDcBudget> {
    List<ConDcBudgetResponse> getConDcBudgetList(@Param("page") IPage<ConDcBudgetResponse> page, @Param("params") Map<String, Object> params);


    ConDcBudgetResponse getConDcBudgetByDcbId(@Param("dcbId") String dcbId);


    @Select("select sum(Dcb_Estimate_Amt) from Con_Dc_Budget where Dcb_State=7 and Con_Id=#{conId}")
    BigDecimal getNotContainAmtByConId(@Param("conId") String conId);
}
