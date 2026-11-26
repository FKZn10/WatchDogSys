package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.projectOutline.OutlineLibRiskRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineLibRiskResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineLibRisk;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/10
 */
@Mapper
public interface OutlineLibRiskMapper extends BaseMapper<OutlineLibRisk> {
    /**
     * 根据体系库id获取体系库风险
     *
     * @param oslIds
     * @return
     */
    List<OutlineLibRiskResponse> getOutLineLibRiskByOslIds(@Param("oslIds") List<String> oslIds);

    @Select("select max(Olr_Sort) from Outline_Lib_Risk where Osl_Id=#{oslId}")
    Integer selectSort(@Param("oslId") String oslId);

    @Delete("delete from Outline_Lib_Risk where Osl_Id=#{oslId}")
    Integer deleteByOslId(@Param("oslId") String oslId);

    @Delete("delete from Outline_Lib_Risk lr where lr.Osl_Id in (select sl.id from Outline_System_Lib sl where sl.osm_id=#{osmId})")
    Integer deleteByOsmId(@Param("osmId") String osmId);

    List<OutlineLibRiskResponse> getOutlineLibRiskPage(@Param("page") Page<OutlineLibRiskResponse> page, @Param("params") OutlineLibRiskRequest request);
}
