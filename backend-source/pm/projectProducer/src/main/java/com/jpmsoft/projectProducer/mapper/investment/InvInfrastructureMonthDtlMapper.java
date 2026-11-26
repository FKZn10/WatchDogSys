package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvInfrastructureMonthDtl;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvInfrastructureMonthDtlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:05
 */
public interface InvInfrastructureMonthDtlMapper extends BaseMapper<InvInfrastructureMonthDtl> {

    List<InvInfrastructureMonthDtlVo> getInvInfrastructureMonthDtlList(@Param("page") IPage<InvInfrastructureMonthDtlVo> page, @Param("params") Map<String, Object> params);

}
