package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConDurationAdjust;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConDurationAdjustVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 10:28
 */
public interface ConDurationAdjustMapper extends BaseMapper<ConDurationAdjust> {

    List<ConDurationAdjustVo> getConDurationAdjustList(@Param("page") IPage<ConDurationAdjustVo> page,
                                                       @Param("params") Map<String, Object> params);

    BigDecimal getConDurationAdjustConMoneySum(@Param("params") Map<String, Object> params);

    ConDurationAdjustVo getConDurationAdjustById(@Param("id") String id);
}
