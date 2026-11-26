package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAllocationDtlDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAllocationDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:06
 */
public interface FundAllocationDtlMapper extends BaseMapper<FundAllocationDtl> {

   List<FundAllocationDtlDTO> getFundAllocationDtlList(@Param("conId") String conId);

    List<FundAllocationDtl> getiFundActionDtlPayList(@Param("payIds") List<String> payIds);
}
