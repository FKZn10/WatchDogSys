package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeTempRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineNodeTempResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineNodeTemp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OutlineNodeTempMapper extends BaseMapper<OutlineNodeTemp> {
    @Select("select  max(Odt_Sort) from Outline_Node_Temp where Osl_Id=#{oslId}")
    Integer selectSort(@Param("oslId") String oslId);

    @Delete("delete from Outline_Node_Temp where Osl_Id=#{oslId}")
    Integer deleteByOslId(@Param("oslId") String oslId);

    @Delete("delete from Outline_Node_Temp lr where lr.Osl_Id in (select sl.id from Outline_System_Lib sl where sl.osm_id=#{osmId})")
    Integer deleteByOsmId(@Param("osmId") String osmId);

    List<OutlineNodeTempResponse> getOutlineNodeTempList(@Param("page") Page<OutlineNodeTemp> page, @Param("params") OutlineNodeTempRequest request);

    /**
     * 体系模板-大纲体系ID获取体系模板-目录节点
     *
     * @param osmId
     * @return
     */
    List<OutlineNodeTempResponse> getAllOutlineNodeTempByOsmId(@Param("osmId") String osmId);
}
