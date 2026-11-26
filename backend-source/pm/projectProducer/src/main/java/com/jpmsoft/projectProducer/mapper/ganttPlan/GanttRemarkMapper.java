package com.jpmsoft.projectProducer.mapper.ganttPlan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.ganttPlan.GanttRemarkRequest;
import com.jpmsoft.common.model.response.ganttPlan.GanttRemarkResponse;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttRemark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tianhelin
 * @date 2025/5/23
 */
@Mapper
public interface GanttRemarkMapper extends BaseMapper<GanttRemark> {
    /**
     * 获取任务备注列表
     *
     * @param page
     * @param request
     * @return
     */
    List<GanttRemarkResponse> getGanttRemarkList(@Param("page") Page<GanttRemarkRequest> page, @Param("params") GanttRemarkRequest request);
}
