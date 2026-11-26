package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectDeviceCoordinate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PreProjectDeviceCoordinateMapper extends BaseMapper<PreProjectDeviceCoordinate> {

    List<PreProjectDeviceCoordinate> getList(@Param("params") Map<String, Object> params);

    Page<PreProjectDeviceCoordinate> getPageDistinctProject(Page<Object> page);
}
