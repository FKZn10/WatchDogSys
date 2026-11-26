package com.jpmsoft.projectProducer.mapper.fundPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAppropriationDtl;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundAppropriationDtlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:29
 */
public interface FundAppropriationDtlMapper extends BaseMapper<FundAppropriationDtl> {

    List<FundAppropriationDtlVo> getFundAppropriationDtlList(@Param("page") IPage<FundAppropriationDtlVo> page, @Param("params") Map<String, Object> params);

}
