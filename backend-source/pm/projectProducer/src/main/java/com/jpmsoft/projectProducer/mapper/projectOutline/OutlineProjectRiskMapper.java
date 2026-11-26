package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectRiskRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectRiskResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectRisk;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/10
 */
@Mapper
public interface OutlineProjectRiskMapper extends BaseMapper<OutlineProjectRisk> {
    /**
     * 根据项目管理大纲体系id查询风险指标
     *
     * @param oplIds
     * @return
     */
    List<OutlineProjectRiskResponse> getOutlineProjectRiskByOplIds(@Param("oplIds") List<String> oplIds);

    @Select("select max(Opr_Sort) from Outline_Project_Risk where Opl_Id=#{oplId}")
    Integer selectSort(@Param("oplId") String oplId);

    List<OutlineProjectRiskResponse> getOutlineProjectRiskPage(@Param("page") Page<OutlineProjectRisk> page, @Param("params") OutlineProjectRiskRequest request);

    OutlineProjectRiskResponse getOutlineProjectRiskById(String id);

    List<OutlineProjectRiskResponse> getProjectRiskLedgerByOpmIds(@Param("opmIds") List<String> opmIds);
}
