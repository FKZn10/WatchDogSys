package com.jpmsoft.projectProducer.mapper.integrity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.integrity.FunctionalIntegrityWarning;
import com.jpmsoft.projectProducer.pojo.vo.integrity.IntegrityWarningChildVo;
import com.jpmsoft.projectProducer.pojo.vo.integrity.IntegrityWarningPositionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 功能预警流程记录
 */
@Mapper
public interface FunctionalIntegrityWarningMapper extends BaseMapper<FunctionalIntegrityWarning> {
    List<IntegrityWarningChildVo> getIntegrityWarningLedger(@Param("params") Map<String, Object> query);

    List<IntegrityWarningPositionVo> getIntegrityWarningPositionLedger(@Param("params") Map<String, Object> query);
}
