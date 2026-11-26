package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Plan.GanttTasksOverdueDTO;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttTasksOverdueMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksOverdue;
import com.jpmsoft.projectProducer.pojo.response.OverdueTaskResponse;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttTasksOverdueService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Administrator
 * @description 针对表【gantt_tasks_overdue】的数据库操作Service实现
 * @createDate 2025-04-19 14:57:41
 */
@Service
public class GanttTasksOverdueServiceImpl extends FileImpl<GanttTasksOverdueMapper, GanttTasksOverdue, GanttTasksOverdueDTO>
        implements GanttTasksOverdueService {

    @Autowired
    private IGanttTasksService tasksService;
    @Autowired
    private IGanttPlanService planService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGanttPlan(GanttTasksOverdueDTO overdue) {
        GanttTasksOverdue entity = new GanttTasksOverdue();
        createOrUpdateFileEntity(entity, overdue);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public List<OverdueTaskResponse> getGanttTasksOverdueByTaskId(String taskId) {
        return baseMapper.getGanttTasksOverdueByTaskId(taskId);
    }

    @Override
    public BaseResponse getGanttTasksOverdueByTaskIdByPage(int page, int size, String taskId) {
        IPage<OverdueTaskResponse> iPage = new Page<>(page, size);
        List<OverdueTaskResponse> ganttTasksOverdueByTaskId = baseMapper.getGanttTasksOverdueByTaskId(iPage, taskId);
        long total = iPage.getTotal();
        return BaseResponse.ok(ganttTasksOverdueByTaskId, (int) total);
    }

    @Override
    public BaseResponse getGanttTaskOverdueById(String id) {
        return BaseResponse.ok(baseMapper.getGanttTasksOverdueById(id));
    }

    @Override
    public BaseResponse existInApprovalTaskOverdue(String taskId) {
        LambdaQueryWrapper<GanttTasksOverdue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GanttTasksOverdue::getTaskId, taskId);
        wrapper.ne(GanttTasksOverdue::getState, ApprovalStatusEnum.Invalid.getIndex());
        wrapper.ne(GanttTasksOverdue::getState, ApprovalStatusEnum.Already_Over.getIndex());
        return BaseResponse.ok(baseMapper.exists(wrapper));
    }


}




