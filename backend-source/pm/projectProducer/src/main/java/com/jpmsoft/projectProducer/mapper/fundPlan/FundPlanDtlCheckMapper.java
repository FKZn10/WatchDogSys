package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlanDtlCheck;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundPlanDtlCheckVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:03
 */
public interface FundPlanDtlCheckMapper extends BaseMapper<FundPlanDtlCheck> {

    List<FundPlanDtlCheckVo> getFundPlanDtlCheckList(@Param("page") IPage<FundPlanDtlCheckVo> page
            , @Param("params") Map<String, Object> params);

    FundPlanDtlCheckVo getFundPlanDtlCheckById(@Param("id") String id);
}
