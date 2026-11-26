package com.jpmsoft.projectProducer.mapper.actualMeasurement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.ActualMeasurement.DrawingManagementCatalogueResponse;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingManagementCatalogue;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DrawingManagementCatalogueMapper extends BaseMapper<DrawingManagementCatalogue> {


    List<DrawingManagementCatalogueResponse> getDrawingManagementCatalogueTreeByUser(@Param("page") IPage<DrawingManagementCatalogueResponse> page,
                                                                                     @Param("params") Map<String, Object> query);

    List<Map<String, Object>> getCatalogueStatisticsByUid(@Param("pId") String pId,
                                                          @Param("uId") String uId);
}
