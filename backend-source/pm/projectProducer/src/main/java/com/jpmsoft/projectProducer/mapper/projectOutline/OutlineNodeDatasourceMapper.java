package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeDatasourceRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineDatasourceTempResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineNodeDatasourceResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineDatasource;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineNodeDatasource;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author tianhelin
 * @date 2025/6/10
 */
@Mapper
public interface OutlineNodeDatasourceMapper extends BaseMapper<OutlineNodeDatasource> {
    @Delete("delete from Outline_Node_Datasource where Osl_Id=#{oslId}")
    Integer deleteByOslId(@Param("oslId") String oslId);

    @Delete("delete from Outline_Node_Datasource lr where lr.Osl_Id in (select sl.id from Outline_System_Lib sl where sl.osm_id=#{osmId})")
    Integer deleteByOsmId(@Param("osmId") String osmId);

    List<OutlineNodeDatasourceResponse> getOutlineNodeDatasourceList(@Param("page") IPage<OutlineNodeDatasourceResponse> page, @Param("params") OutlineNodeDatasourceRequest request);

    List<OutlineNodeDatasourceResponse> getAllOutlineNodeDatasourceByOslIds(@Param("oslIds") List<String> oslIds);

    List<Map<String, Object>> executeSqlQuery(@Param("sql") String sql);

    List<OutlineDatasource> getAllOutlineDatasource();

    /**
     * 根据数据源ID获取对应的TCodes
     *
     * @param odIds
     * @return
     */
    List<OutlineDatasourceTempResponse> getTCodesByOdIds(@Param("odIds") List<String> odIds);
}
