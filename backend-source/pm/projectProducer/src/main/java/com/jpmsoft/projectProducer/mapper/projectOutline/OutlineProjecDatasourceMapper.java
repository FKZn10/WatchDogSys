package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjecDatasourceRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjecDatasourceResponse;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjecDatasource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/6/10
 */
@Mapper
public interface OutlineProjecDatasourceMapper extends BaseMapper<OutlineProjecDatasource> {
    List<OutlineProjecDatasourceResponse> getOutlineProjecDatasourceByOplIds(@Param("oplIds") List<String> oplIds);

    OutlineProjecDatasourceResponse getProjectDatasourceById(String id);

    List<OutlineProjecDatasourceResponse> getProjectDatasourcePage(@Param("page") Page<OutlineProjecDatasourceResponse> page, @Param("params") OutlineProjecDatasourceRequest request);

    OutlineProjecDatasourceResponse getProjectDatasourceByOplIdAndOdId(@Param("oplId") String oplId, @Param("odId") String odId);
}
