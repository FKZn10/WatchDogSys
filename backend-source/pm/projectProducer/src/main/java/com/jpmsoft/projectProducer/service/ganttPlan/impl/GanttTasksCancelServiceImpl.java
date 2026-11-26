package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Plan.GanttTasksCancelDTO;
import com.jpmsoft.common.model.response.Plan.GanttTasksCancelResponse;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttTasksCancelMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasks;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasksCancel;
import com.jpmsoft.projectProducer.service.ganttPlan.GanttTasksCancelService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 * @description 针对表【gantt_tasks_cancel(甘特图任务取消记录表)】的数据库操作Service实现
 * @createDate 2025-04-19 19:19:55
 */
@Service
public class GanttTasksCancelServiceImpl extends FileImpl<GanttTasksCancelMapper, GanttTasksCancel, GanttTasksCancelDTO>
        implements GanttTasksCancelService {

    @Autowired
    private IGanttTasksService tasksService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGanttCannel(GanttTasksCancelDTO ganttTasksCancelDTO) {
        GanttTasksCancel entity = new GanttTasksCancel();
        createOrUpdateFileEntity(entity, ganttTasksCancelDTO);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public List<GanttTasksCancelResponse> getGanttTasksCannelByTaskId(String taskId) {
        return baseMapper.getGanttTasksCancelResponseByTaskId(taskId);
    }

    @Override
    public BaseResponse getGanttTasksCannelByTaskIdByPage(int page, int size, String taskId) {
        IPage<GanttTasksCancelResponse> iPage = new Page<>(page, size);
        List<GanttTasksCancelResponse> entity = baseMapper.getGanttTasksCancelResponseByTaskId(iPage, taskId);
        long total = iPage.getTotal();
        return BaseResponse.ok(entity, (int) total);
    }

    @Override
    public BaseResponse rewriteTask(String taskId) {
        List<GanttTasksCancel> list = lambdaQuery()
                .eq(GanttTasksCancel::getTaskId, taskId)
                .ne(GanttTasksCancel::getState, ApprovalStatusEnum.Invalid.getIndex())
                .orderByDesc(GanttTasksCancel::getCreateTime) // 数据库层排序
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return BaseResponse.fail("该任务没有取消记录");
        } else {
            GanttTasksCancel ganttTasksCancelDTO = list.get(0);
            Integer state = ganttTasksCancelDTO.getState();
            if (state.equals(ApprovalStatusEnum.Already_Over.getIndex())) {
                GanttTasks task = tasksService.lambdaQuery().eq(GanttTasks::getTaskId, taskId).one();
                task.setTaskState(ApprovalStatusEnum.Not_Started.getIndex());
                tasksService.saveOrUpdate(task);
                return BaseResponse.ok(task);
            } else {
                return BaseResponse.fail("GanttTasksCancel的状态不是Cannel，不能回写task");
            }
        }
    }

    @Override
    public BaseResponse getGanttCannelById(String id) {
        GanttTasksCancelResponse entity = baseMapper.getGanttCannelById(id);
        if (Objects.isNull(entity))
            return BaseResponse.fail("未找到");
        return BaseResponse.ok(entity);
    }

    @Override
    public BaseResponse existApproveGanttCancel(String taskId) {
        LambdaQueryWrapper<GanttTasksCancel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GanttTasksCancel::getTaskId, taskId);
        wrapper.ne(GanttTasksCancel::getState, ApprovalStatusEnum.Invalid.getIndex());
        return BaseResponse.ok(baseMapper.exists(wrapper));
    }
}




