package com.jpmsoft.projectProducer.mapper.riskWarn;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.riskWarn.RiskPreventionMeasureDTO;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskPreventionMeasure;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 11:30
 */
public interface RiskPreventionMeasureMapper extends BaseMapper<RiskPreventionMeasure> {

    @Select("select max(Rpm_Sort) from Risk_Prevention_Measure where Rpm_Parent_Id=#{parentId}")
    Integer getMaxSore(@Param("parentId") String parentId);

    @Select("select a.*,(select count(*) from Risk_Template_Relation where Rpm_Id=a.id) as \"relationCount\" from Risk_Prevention_Measure a where a.Te_Id=#{teId} order by a.Rpm_Sort asc")
    List<RiskPreventionMeasureDTO> getAllWithTeId(@Param("teId")  String teId);
    @Select("select a.*,(select count(*) from Risk_Template_Relation where Rpm_Id=a.id) as \"relationCount\" from Risk_Prevention_Measure a order by a.Rpm_Sort asc")
    List<RiskPreventionMeasureDTO> getAll();
}

