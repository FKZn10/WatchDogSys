package com.jpmsoft.gmgws.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.gmgws.pojo.entity.ContractStatistics;
import com.jpmsoft.gmgws.pojo.vo.ContractStatisticsVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface ContractStatisticsMapper  extends BaseMapper<ContractStatistics> {

    IPage<ContractStatisticsVo> getContractStatisticsList(@Param("page") IPage<ContractStatisticsVo> page, @Param("params") Map<String, Object> params);
}
