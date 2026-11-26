package com.jpmsoft.projectProducer.mapper.actualMeasurement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.ActualMeasurement.DrawingItemResponse;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.DrawingItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface DrawingItemMapper extends BaseMapper<DrawingItem> {


    List<DrawingItemResponse> getDrawingItemList(@Param("page") IPage<DrawingItemResponse> page, @Param("params")  Map<String, Object> params);

}
