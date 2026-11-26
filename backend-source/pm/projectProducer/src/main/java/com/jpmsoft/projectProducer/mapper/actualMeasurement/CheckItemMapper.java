package com.jpmsoft.projectProducer.mapper.actualMeasurement;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.jpmsoft.common.model.response.ActualMeasurement.CheckItemResponse;
import com.jpmsoft.projectProducer.pojo.entity.actualMeasurement.CheckItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface CheckItemMapper extends BaseMapper<CheckItem> {


    List<CheckItemResponse> selectAll();

    void refreshFileAnexState(@Param("fileIds") List<String> fileIds);

    List<CheckItemResponse> getCheckItemList(@Param("teId") String teId);

}
