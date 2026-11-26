package com.jpmsoft.projectProducer.mapper.visaChange;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConSvBudgetDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvBudget;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/19 10:35
 */
public interface ConSvBudgetMapper extends BaseMapper<ConSvBudget> {

    List<ConSvBudgetDTO> getConSvBudgetList(@Param("page") IPage<ConSvBudgetDTO> page,
                                            @Param("params") Map<String, Object> params);


    ConSvBudgetDTO getConSvBudgetById(@Param("id") String id);


    @Select("select sum(Csb_Estimate_Amt) from Con_Sv_Budget where State=7 and Con_Id=#{conId}")
    BigDecimal getNotContainAmtByConId(@Param("conId") String conId);
}
