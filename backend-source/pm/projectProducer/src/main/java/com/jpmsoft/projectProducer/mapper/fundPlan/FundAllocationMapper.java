package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAllocationDTO;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAllocation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/10/8 10:19
 */
public interface FundAllocationMapper extends BaseMapper<FundAllocation> {


    List<FundAllocationDTO> getFundAllocationByDtlCon(@Param("faIds") List<String> faIds);
}
