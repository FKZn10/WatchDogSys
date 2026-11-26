package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.Plan.GanttTasksResponse;
import com.jpmsoft.fileUpload.pojo.entity.GanttTasks;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GanttTasksMapper extends BaseMapper<GanttTasks> {

    List<GanttTasksResponse> exportGanttTaskFeedback(@Param("planId") String planId);

}
