package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPayApplyFlowDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApplyFlow;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConPayApplyFlowVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ConPayApplyFlowMapper extends BaseMapper<ConPayApplyFlow> {
    List<ConPayApplyFlowDto> getConPayApplyFlowList(@Param("params") Map<String, Object> params);

    List<ConPayApplyFlowDto> getConPayApplyFlowByConId(@Param("conId") String conId);

    ConPayApplyFlowDto getConPayApplyFlowById(@Param("cpafId") String id);

    List<ConPayApplyFlow> getConPayApplyFlowSum(@Param("conId") String conId);

    IPage<ConPayApplyFlowVo> getConPayApplyFlowDataList(Page<ConPayApplyFlowVo> page, @Param("vo") ConPayApplyFlowVo vo);

    BigDecimal statsConPayApproval(@Param("vo") ConPayApplyFlowVo vo);

    Map<String,BigDecimal> statsPayApplyFlow(@Param("vo") ConPayApplyFlowVo vo);
}
