package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlan;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundPlanVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:04
 */
public interface FundPlanMapper extends BaseMapper<FundPlan> {

    List<FundPlanVo> getFundPlanList(@Param("page") IPage<FundPlanVo> page, @Param("params") Map<String, Object> params);

    FundPlanVo getFundPlanById(@Param("id") String id);


    Integer updateFinalInfo(@Param("id") String id);

    BigDecimal getDemand(@Param("id") String id, @Param("conId") String conId);

    @Select("select sum(Fp_Demand_Final) from Fund_Plan where P_Id=#{pId} and State=7 ")
    BigDecimal getProFpDemandFinalSum(@Param("pId")  String pId);
}
