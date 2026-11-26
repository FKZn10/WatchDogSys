package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlanDtl;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundPlanDtlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:04
 */
public interface FundPlanDtlMapper extends BaseMapper<FundPlanDtl> {

    List<FundPlanDtlVo> getFundPlanDtlList(@Param("page") IPage<FundPlanDtlVo> page, @Param("params") Map<String, Object> params);

    List<FundPlanDtlVo> getAllFundPlanDtlByFpId(@Param("fpId") String fpId);
}
