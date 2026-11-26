package com.jpmsoft.projectProducer.mapper.riskWarn;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.riskWarn.RiskTemplateRelationDTO;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskTemplateRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 13:44
 */
public interface RiskTemplateRelationMapper extends BaseMapper<RiskTemplateRelation> {

    List<RiskTemplateRelationDTO> queryList(@Param("page") IPage<RiskTemplateRelationDTO> page, @Param("params") Map<String, Object> params);
}
