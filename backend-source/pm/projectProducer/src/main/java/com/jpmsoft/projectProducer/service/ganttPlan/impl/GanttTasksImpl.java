package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.FilterObject;
import com.jpmsoft.common.model.export.request.GanttPlanExportRequest;
import com.jpmsoft.common.model.export.response.GanttTasksExportModel;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import com.jpmsoft.common.model.request.Plan.GantSectionTaskDTO;
import com.jpmsoft.common.model.request.Plan.GanttTasksRequest;
import com.jpmsoft.common.model.response.Plan.GanttLinksResponse;
import com.jpmsoft.common.model.response.Plan.GanttPlanResponse;
import com.jpmsoft.common.model.response.Plan.GanttTasksResponse;
import com.jpmsoft.common.model.response.Plan.TaskProcessRateDTO;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.GanttTasksStatusEnum;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpDtlMapper;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttTasksMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.GanttTasksCjDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MilestoneSubmitDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpRpr;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.*;
import com.jpmsoft.projectProducer.pojo.response.CjGanttTaskProcessResponse;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.ganttPlan.*;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GanttTasksImpl extends ServiceImpl<GanttTasksMapper, GanttTasks> implements IGanttTasksService {
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IGanttTasksProcessService ganttTasksProcessService;
    @Resource
    private IGanttLinksService ganttLinksService;
    @Resource
    private IGantSectionTaskService gantSectionTaskService;
    @Resource
    private IGanttPlanService ganttPlanService;
    @Resource
    private GanttTasksOverdueService ganttTasksOverdueService;
    @Resource
    private GanttTasksCancelService ganttTasksCancelService;
    @Resource
    private IGanttLinksTemplateService ganttLinksTemplateService;
    @Resource
    private IGanttPlanTemplateService ganttPlanTemplateService;
    @Resource
    private ProjectCpDtlMapper projectCpDtlMapper;


    @Override
    public List<String> createOrUpdateGanttTasks(List<GanttTasksRequest> datas, String planId, String action) {
        List<String> realDel = null;
        List<GanttTasksRequest> ganttTasks = new ArrayList<>();
        List<GanttTasksRequest> requests = datas.stream().filter(item -> item.getTaskSort() == null).collect(Collectors.toList());
        if (!requests.isEmpty()) {
            int i = 1;
            for (GanttTasksRequest data : datas) {
                data.setTaskSort(i);
                ganttTasks.add(data);
                i += 1;
            }
        } else {
            ganttTasks.addAll(datas);
        }
        if (action.equals("update")) {
            QueryWrapper<GanttTasks> wrapper = new QueryWrapper<>();
            wrapper.eq("Plan_Id", planId);
            List<GanttTasks> oldDatas = baseMapper.selectList(wrapper);
            //region 筛选出需要删除的、需要新增的、需要更新的集合
            //  oldDatas有 而ganttTasks内没有的 想要删除的
            List<GanttTasks> delList = oldDatas.stream()
                    .filter(item -> !ganttTasks.stream()
                            .map(GanttTasksRequest::getTaskId)
                            .collect(Collectors.toList())
                            .contains(item.getTaskId()))
                    .collect(Collectors.toList());

            // ganttTasks 有 而 oldDatas 内没有的 想要新增的
            List<GanttTasksRequest> addList = ganttTasks.stream()
                    .filter(item -> !oldDatas.stream()
                            .map(GanttTasks::getTaskId)
                            .collect(Collectors.toList())
                            .contains(item.getTaskId()))
                    .collect(Collectors.toList());

            // ganttTasks 有 而 oldDatas 内也有的 需要更新的
            List<GanttTasksRequest> updateList = ganttTasks.stream()
                    .filter(item -> oldDatas.stream()
                            .map(GanttTasks::getTaskId)
                            .collect(Collectors.toList())
                            .contains(item.getTaskId()))
                    .collect(Collectors.toList());
            //endregion
            //region 需要删除
            if (delList != null && delList.size() > 0) {
                //删除的要看看其下级是否有任务完成情况数据
                List<GanttTasksProcess> process = ganttTasksProcessService.getGanttTasksProcessByPlanId(planId);
                //有任务完成情况的taskId
                List<String> notToDel = process.stream().map(GanttTasksProcess::getTaskId).collect(Collectors.toList());
                //所有不可删除节点（有任务的taskId和其上级）
                List<String> allNotDel = new ArrayList<>();
                for (String s : notToDel) {
                    getAllNotDelIds(s, allNotDel, oldDatas);
                }
                //可以删除的ID
                realDel = new ArrayList<>();
                for (GanttTasks ganttTasksPojo : delList) {
                    if (!allNotDel.contains(ganttTasksPojo.getTaskId()))
                        realDel.add(ganttTasksPojo.getTaskId());
                }
                if (realDel.size() > 0)
                    baseMapper.deleteBatchIds(realDel);
            }
            //endregion
            //region 新增数据
            //新增的直接新增即可
            if (addList != null && addList.size() > 0) {
                List<GanttTasks> addEntitys = new ArrayList<>();
                addList.forEach(task -> {
                    GanttTasks entity = new GanttTasks();
                    BeanUtils.copyProperties(task, entity);
                    entity.setTaskProgress(0F);
                    entity.setTaskState(ValidateUtil.isNull(task.getTaskState()) ? 1 : task.getTaskState());
                    entity.setPlanId(planId);
                    addEntitys.add(entity);
                });
                if (addEntitys.size() > 0)
                    saveBatch(addEntitys);
            }
            //endregion
            //region 需要更新
            if (ValidateUtil.isNotEmpty(updateList)) {
                List<GanttTasks> upEntitys = new ArrayList<>();
                updateList.forEach(task -> {
                    GanttTasks entity = new GanttTasks();
                    BeanUtils.copyProperties(task, entity);
                    entity.setPlanId(planId);
                    entity.setTaskState(ValidateUtil.isNull(task.getTaskState()) ? 1 : task.getTaskState());
                    upEntitys.add(entity);
                });
                if (ValidateUtil.isNotEmpty(upEntitys))
                    updateBatchById(upEntitys);
            }
            //endregion
        } else {
            List<GanttTasks> entitys = new ArrayList<>();
            ganttTasks.forEach(task -> {
                GanttTasks entity = new GanttTasks();
                BeanUtils.copyProperties(task, entity);
                entity.setTaskProgress(0F);
                entity.setPlanId(planId);
                entity.setTaskState(ValidateUtil.isNull(task.getTaskState()) ? 1 : task.getTaskState());
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
        return realDel;
    }

    private void getAllNotDelIds(String s, List<String> allNotDel, List<GanttTasks> oldDatas) {
        if (!ValidateUtil.isEmpty(s) && !s.equals("0")) {
            allNotDel.add(s);
            GanttTasks curn = oldDatas.stream().filter(n -> n.getTaskId().equals(s)).findFirst().get();
            getAllNotDelIds(curn.getTaskParent(), allNotDel, oldDatas);
        }
    }

    @Override
    public GanttPlanResponse getGanttTasksTreeByPlanId(GanttPlanResponse plan) {
        List<GanttTasksResponse> datas = baseMapper.getAllTaskByPlanId(plan.getPlanId());
        List<Object> data = new ArrayList<>();
        List<GanttLinksResponse> links = ganttLinksService.getLinksByPlanId(plan.getPlanId());
        List<GantSectionTaskDTO> sectionTasks = gantSectionTaskService.getGantSectionTaskByPlanId(plan.getPlanId());
        if (datas.size() > 0) {
            List<GanttTasksResponse> roots = datas.stream().filter(n -> n.getTaskParent().equals("0")).collect(Collectors.toList());
            if (roots.size() > 0) {
                roots.forEach(root -> {
                    List<GanttTasksResponse> child = datas.stream().filter(n -> n.getTaskParent().equals(root.getTaskId())).collect(Collectors.toList());
                    List<GanttLinksResponse> lks = links.stream().filter(n -> n.getLinkTarget().equals(root.getTaskId())).collect(Collectors.toList());
                    root.setGanttLinks(lks);
                    root.setTaskLevel(1);
                    setGstSections(root, sectionTasks);
                    setChildNodes(child, datas, 1, links, sectionTasks);
                    root.setChildNodes(child);
                    data.add(root);
                });
            }
        }
        plan.setGanttTasks(data);
        plan.setGanttLinks(Arrays.asList(links.toArray()));
        return plan;
    }

    private void setGstSections(GanttTasksResponse task, List<GantSectionTaskDTO> sectionTasks) {
        if (!CollectionUtils.isEmpty(sectionTasks)) {
            List<GantSectionTaskDTO> sections = sectionTasks.stream().filter(f -> f.getTaskId().equals(task.getTaskId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(sections)) {
                task.setGstSections(sections.stream().map(GantSectionTaskDTO::getGstSection).collect(Collectors.toList()));
            }
        }
    }

    private void setChildNodes(List<GanttTasksResponse> roots, List<GanttTasksResponse> datas, int level, List<GanttLinksResponse> links, List<GantSectionTaskDTO> sectionTasks) {
        if (roots.size() > 0) {
            level += 1;
            final int currentLevel = level;
            roots.forEach(root -> {
                List<GanttTasksResponse> child = datas.stream().filter(n -> n.getTaskParent().equals(root.getTaskId())).collect(Collectors.toList());
                List<GanttLinksResponse> lks = links.stream().filter(n -> n.getLinkTarget().equals(root.getTaskId())).collect(Collectors.toList());
                root.setGanttLinks(lks);
                root.setTaskLevel(currentLevel);
                setGstSections(root, sectionTasks);
                setChildNodes(child, datas, currentLevel, links, sectionTasks);
                root.setChildNodes(child);
            });
        }
    }

    @Override
    public List<GanttTasks> getAllGanttTasksByPlanId(String planId) {
        QueryWrapper<GanttTasks> wrapper = new QueryWrapper<>();
        wrapper.eq("Plan_Id", planId);
        wrapper.orderByAsc("Task_Sort");
        List<GanttTasks> datas = baseMapper.selectList(wrapper);
        return datas;
    }

    @Override
    public List<GanttTasks> getCplGanttTasksByPlanId(String planId) {
        LambdaQueryWrapper<GanttTasks> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GanttTasks::getPlanId, planId);
        wrapper.isNotNull(GanttTasks::getCplId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<GanttTasks> getCplGanttTasksByPlanId(String planId, String taskType) {
        LambdaQueryWrapper<GanttTasks> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GanttTasks::getPlanId, planId);
        wrapper.eq(GanttTasks::getTaskType, taskType);
        wrapper.isNotNull(GanttTasks::getCplId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<GanttTasks> getGanttTasksByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return null;
        QueryWrapper<GanttTasks> wrapper = new QueryWrapper<>();
        if (ids.size() == 1) wrapper.eq("Task_Id", ids.get(0));
        else wrapper.in("Task_Id", ids);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public void updateParentTaskProgress(List<GanttTasks> datas, String taskParent, Set<GanttTasks> updates) {
        if (datas.size() < 1)
            return;
        if (taskParent.equals("0"))
            return;
        List<GanttTasks> sameNodes = datas.stream().filter(n -> n.getTaskParent().equals(taskParent)).collect(Collectors.toList());
        List<GanttTasks> parents = datas.stream().filter(n -> n.getTaskId().equals(taskParent)).collect(Collectors.toList());
        if (sameNodes.size() > 0) {
            List<GanttTasks> finishNodes = sameNodes.stream().filter(n -> n.getTaskProgress() == 1F).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(finishNodes)) {
                Float rate = (float) finishNodes.size() / (float) sameNodes.size();
                BigDecimal b = new BigDecimal(rate);
                Float realRate = b.setScale(2, RoundingMode.HALF_UP).floatValue();
                if (!CollectionUtils.isEmpty(parents)) {
                    GanttTasks parent = parents.get(0);
                    Integer index = finishNodes.indexOf(parent);
                    parent.setTaskProgress(realRate);
                    updates.add(parent);
                    finishNodes.set(index, parent);
                    if (realRate == 1F) {
                        updateParentTaskProgress(datas, parent.getTaskParent(), updates);
                    }
                }
            } else {
                GanttTasks parent = parents.get(0);
                parent.setTaskProgress(0F);
                updates.add(parent);
            }
        }
    }

    @Override
    public void process2RefreshGanttTask(TaskProcessRateDTO tpr) {
        List<GanttTasks> datas = baseMapper.getAllTaskByTaskId(tpr.getTaskId());
        List<GanttTasks> tasks = datas.stream().filter(n -> n.getTaskId().equals(tpr.getTaskId())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(tasks)) {
            List<GanttTasks> updates = new ArrayList<>();
            GanttTasks task = tasks.stream().findFirst().get();
            task.setTaskProgress(tpr.getRate());
            if (ValidateUtil.isNotNull(tpr.getRate()) && tpr.getRate().compareTo(100F) == 0) {
                task.setTaskFinishTime(tpr.getCompleteDate());
                updates.add(task);
                refreshParentProgress(datas, updates, task, null);
                if (!CollectionUtils.isEmpty(updates)) updateBatchById(updates);
            } else baseMapper.updateById(task);
        }
    }

    private void refreshParentProgress(List<GanttTasks> datas, List<GanttTasks> updates, GanttTasks task, Date time) {
        if (ValidateUtil.isEmpty(task.getTaskParent()) || task.getTaskParent().equals("0")) return;
        //同级节点 即父级的所有子集节点
        List<GanttTasks> levels = datas.stream().filter(n -> n.getTaskParent().equals(task.getTaskParent())).collect(Collectors.toList());
        //未完成的节点
        List<GanttTasks> unComplete = levels.stream().filter(n -> ValidateUtil.isNull(n.getTaskProgress()) || n.getTaskProgress().compareTo(100F) != 0).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(unComplete)) {
            List<GanttTasks> parents = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getTaskParent()) && n.getTaskId().equals(task.getTaskParent())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(parents)) {
                GanttTasks parent = parents.stream().findFirst().get();
                int dataIndex = datas.indexOf(parent);
                parent.setTaskProgress(100F);
                if (ValidateUtil.isNotNull(time))
                    parent.setTaskFinishTime(time);
                levels.stream().filter(n -> ValidateUtil.isNotNull(n.getTaskFinishTime())).max(Comparator.comparing(GanttTasks::getTaskFinishTime)).ifPresent(ganttTasks -> parent.setTaskFinishTime(ganttTasks.getTaskFinishTime()));
                levels.stream().filter(n -> ValidateUtil.isNotNull(n.getTaskBeginTime())).min(Comparator.comparing(GanttTasks::getTaskBeginTime)).ifPresent(ganttTasks -> parent.setTaskBeginTime(ganttTasks.getTaskBeginTime()));
                updates.add(parent);
                datas.set(dataIndex, parent);
                refreshParentProgress(datas, updates, parent, time);
            }
        }
    }

    @Override
    public BaseResponse GanttPlanStatisticsByOverdue(GanttTasksRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        if (request == null) request = new GanttTasksRequest();
        QueryMap<GanttTasksRequest> params = new QueryMap<>(request);
        params.put("planTypes").put("excludeTypes").put("pId").put("planType")
                .put(request.getPlanStartDates(), "planStartDateStart", "planStartDateEnd")
                .put(request.getPlanEndDates(), "planEndDateStart", "planEndDateEnd");
        List<GanttTasks> allTasks = baseMapper.getAllGanttTaskByPlanState(authSql, params.getQuery());
        Map<String, Integer> data = new HashMap<>();
        data.put("healths", 0);
        data.put("fastOverdue", 0);
        data.put("accomplishSchedule", 0);
        data.put("overdueCompleted", 0);
        data.put("overdue", 0);
        data.put("invalid", 0);
        Date today = new Date();
        if (!CollectionUtils.isEmpty(allTasks)) {
            //正常未到期
            List<GanttTasks> healths = allTasks.stream().filter(n -> n.getTaskFinishTime() == null && n.getTaskState() == 1 && n.getTaskEndDate() != null && DateUtil.daysBetween(today, n.getTaskEndDate()) >= 0).collect(Collectors.toList());
            //到期前30天内
            Integer fastOverdue = healths.stream().filter(n -> (DateUtil.daysBetween(today, n.getTaskEndDate()) <= 30)).collect(Collectors.toList()).size();
            //已完成任务
            List<GanttTasks> finished = allTasks.stream().filter(n -> n.getTaskState() == 1 && n.getTaskFinishTime() != null).collect(Collectors.toList());
            //逾期完成任务数量
            Integer overdueCompleted = finished.stream().filter(n -> n.getTaskEndDate() != null && DateUtil.daysBetween(n.getTaskEndDate(), n.getTaskFinishTime()) > 0).collect(Collectors.toList()).size();
            //按时完成
            Integer accomplishSchedule = finished.size() - overdueCompleted;
            //逾期未完成
            Integer overdueCompletion = (int) allTasks.stream()
                    .filter(n -> n.getTaskState() == 1
                            && n.getTaskFinishTime() == null
                            && n.getTaskEndDate() != null
                            && DateUtil.daysBetween(n.getTaskEndDate(), today) > 0).count();
            //逾期任务
            Integer overdue = overdueCompletion + overdueCompleted;
            //已停止
            Integer invalid = allTasks.stream().filter(n -> n.getTaskState() == 0).collect(Collectors.toList()).size();
            data.put("healths", healths.size());
            data.put("fastOverdue", fastOverdue);
            data.put("accomplishSchedule", accomplishSchedule);
            data.put("overdueCompleted", overdueCompleted);
            data.put("overdue", overdue);
            data.put("invalid", invalid);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse ganttPlanTaskStatisticsList(String uId, GanttTasksRequest request) {
        String authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        QueryMap<GanttTasksRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("queryType").put("pId").put("taskState");
        IPage<GanttTasksResponse> page = new Page<>(request.getPage(), request.getSize());
        List<GanttTasksResponse> data = baseMapper.ganttPlanTaskStatisticsList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getGanttTasksByOverdue(GanttTasksRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Gantt_Tasks", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<GanttTasksRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("planType").put("dId").put("conName").put("planTypes")
                .put("pName").put("pId").put("queryType").put("taskText").put("completed").put("excludeTypes")
                .put(request.getTaskStartDates(),"taskStartDateStart","taskStartDateEnd")
                .put(request.getTaskEndDates(),"taskEndDateStart","taskEndDateEnd").put("overdue");
        List<GanttTasksResponse> datas = baseMapper.getGanttTasksByOverdue(params.getQuery());
        Integer total = baseMapper.getGanttTasksByOverdueCount(params.getQuery());
        List<GanttTasksResponse> models = new ArrayList<>();
        Date today = new Date();
        if (!CollectionUtils.isEmpty(datas)) {
            datas.forEach(model -> {
                Integer day = DateUtil.daysBetween(model.getTaskEndDate(), today);
                if (day != null) {
                    if (day > 0) {
                        model.setAlertDays(day);
                        model.setOverdue(Boolean.TRUE);
                    } else {
                        model.setAlertDays(day * -1);
                        model.setOverdue(Boolean.FALSE);
                    }
                }
                models.add(model);
            });
        }
        return BaseResponse.ok(models, total);

    }

    @Override
    public BaseResponse getGanttTasksByComplete(GanttTasksRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<GanttTasksRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("planName").put("taskText")
                .put("planType").put("planTypes").put("planState");
        IPage<GanttTasksResponse> page = new Page<>(request.getPage(), request.getSize());
        List<GanttTasksResponse> datas = baseMapper.getGanttTasksByComplete(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public Map<String, Object> getRootGanttTasksBypId(String pId, List<String> types) {
        List<GanttTasksResponse> datas = baseMapper.getRootGanttTasksBypId(pId, types);
        List<String> plans = datas.stream().map(GanttTasksResponse::getPlanId).distinct().collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        for (String planId : plans) {
            List<GanttTasksResponse> task = datas.stream().filter(n -> n.getPlanId().equals(planId)).collect(Collectors.toList());
            data.put(planId, task);
        }
        return data;
    }

    @Override
    public BaseResponse getSecondaryNodesByTaskId(GanttTasksRequest request) {
        QueryMap<GanttTasksRequest> params = new QueryMap<>(request);
        params.put("taskId");
        List<GanttTasksResponse> datas = baseMapper.getSecondaryNodesByTaskId(params.getQuery());
        Integer total = baseMapper.getSecondaryNodesByTaskIdCount(params.getQuery());
        List<GanttTasksResponse> data = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            datas.forEach(model -> {
                if (model.getTaskFinishTime() != null)
                    model.setTaskStatus(GanttTasksStatusEnum.Complete.getIndex());
                if (model.getTaskState() == 0)
                    model.setTaskStatus(GanttTasksStatusEnum.Suspend.getIndex());
                if (model.getTaskFinishTime() == null) {
                    if (DateUtil.daysBetween(model.getTaskEndDate(), new Date()) > 0)
                        model.setTaskStatus(GanttTasksStatusEnum.Overdue.getIndex());
                    else if (DateUtil.daysBetween(new Date(), model.getTaskEndDate()) <= 30)
                        model.setTaskStatus(GanttTasksStatusEnum.Temporary.getIndex());
                    else if (DateUtil.daysBetween(new Date(), model.getTaskEndDate()) > 30)
                        model.setTaskStatus(GanttTasksStatusEnum.Normal.getIndex());
                }
                data.add(model);
            });
        }
        return BaseResponse.ok(data, total);
    }

    @Override
    public GanttTasksResponse getGanttTasksById(String taskId) {
        GanttTasks entity = baseMapper.selectById(taskId);
        GanttTasksResponse gtr = new GanttTasksResponse();
        BeanUtils.copyProperties(entity, gtr);
        return gtr;
    }

    @Override
    public BaseResponse getGanttTasksExport(GanttPlanExportRequest request) {
        if (request.getpIds() == null)
            return BaseResponse.fail(ResMsg.notReqParam);
        QueryMap<GanttPlanExportRequest> params = new QueryMap<>(request);
        params.put("planName").put("planType").put("planIds");
        List<GanttTasksExportModel> datas = baseMapper.getGanttTasksExport(params.getQuery(), request.getpIds());
        return BaseResponse.ok(datas);
    }

    @Override
    public Integer getGanttTasksCount(String teId) {
        return baseMapper.getGanttTasksCount(teId);
    }

    @Override
    public Integer getGanttTasksCompCount(String teId) {
        return baseMapper.getGanttTasksCompCount(100F, teId);
    }

    @Override
    public BaseResponse updateBeginTime(GanttTasksRequest request) {
        if (ValidateUtil.isEmpty(request.getTaskId())) return BaseResponse.fail(ResMsg.notReqParam);
        GanttTasks task = baseMapper.selectById(request.getTaskId());
        task.setTaskBeginTime(request.getTaskBeginTime());
        baseMapper.updateById(task);
        return BaseResponse.ok(task.getTaskId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse milestone2Complete(String taskId, String finishTime, LogUserInfoVo user) {
        MilestoneSubmitDTO request = new MilestoneSubmitDTO();
        request.setTaskId(taskId);
        request.setFinishTime(finishTime);
        milestone2CompleteMain(request, user);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse milestone2Complete(MilestoneSubmitDTO request, LogUserInfoVo user) {
        request.setTCode("MilestoneFeedback");
        milestone2CompleteMain(request, user);
        return BaseResponse.ok();
    }

    /**
     * 里程碑一键完成
     *
     * @param user 用户信息
     */
    private void milestone2CompleteMain(MilestoneSubmitDTO request, LogUserInfoVo user) {
        GanttTasks task = baseMapper.selectById(request.getTaskId());
        if (task == null) throw new JPMException(ResMsg.infoNotExist);
        GanttPlan plan = ganttPlanService.getById(task.getPlanId());
        //仅限里程碑进度计划可操作
        if (plan.getPlanType().equalsIgnoreCase("Milestone")) {
            Date time = DateUtil.str2date(request.getFinishTime());
            task.setTaskProgress(100F);
            task.setTaskFinishTime(time);
            List<GanttTasks> updates = new ArrayList<>();
            updates.add(task);
            List<GanttTasks> datas = baseMapper.getAllTaskByTaskId(request.getTaskId());
            datas = datas.stream().peek(data -> {
                if (data.getTaskId().equals(task.getTaskId())) {
                    data.setTaskProgress(100F);
                    if (ValidateUtil.isNotNull(time))
                        data.setTaskFinishTime(time);
                    else
                        data.setTaskFinishTime(new Date());
                }
            }).collect(Collectors.toList());
            refreshParentProgress(datas, updates, task, time);
            if (!CollectionUtils.isEmpty(updates)) updateBatchById(updates);
            ganttTasksProcessService.milestone2Generate(request, time, user);
        }
    }

    @Override
    public List<GanttTasksResponse> getAllTaskByPlanIds(List<String> planIds) {
        if (CollectionUtils.isEmpty(planIds)) return new ArrayList<>();
        List<GanttTasksResponse> datas = baseMapper.getAllTaskByPlanIds(planIds);
        List<GanttTasksResponse> data = new ArrayList<>();
        List<GanttLinksResponse> links = new ArrayList<>();
        if (datas.size() > 0) {
            List<GantSectionTaskDTO> sectionTasks = gantSectionTaskService.getGantSectionTaskByPlanIds(planIds);
            List<GanttTasksResponse> roots = datas.stream().filter(n -> n.getTaskParent().equals("0")).collect(Collectors.toList());
            if (roots.size() > 0) {
                roots.forEach(root -> {
                    List<GanttTasksResponse> child = datas.stream().filter(n -> n.getTaskParent().equals(root.getTaskId())).collect(Collectors.toList());
                    root.setTaskLevel(1);
                    setGstSections(root, sectionTasks);
                    setChildNodes(child, datas, 1, links, sectionTasks);
                    root.setChildNodes(child);
                    data.add(root);
                });
            }
        }
        return data;
    }

    @Override
    public void cpApprove2SyncGanttTaskState(ProjectCpDtl dtl, GanttPlan plan, ProjectCpRpr rpr) {
        baseMapper.updateGanttTaskState(plan.getPlanId(), dtl.getCpdCplId(), rpr.getStateTime());
    }

    @Override
    public void deleteTasksByPlanId(String planId) {
        gantSectionTaskService.deleteByPlanId(planId);
        LambdaQueryWrapper<GanttTasks> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GanttTasks::getPlanId, planId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse queryGanttTasksProcessByTaskID(String taskId) {
        CjGanttTaskProcessResponse response = ganttTasksProcessService.lambdaQuery()
                .eq(GanttTasksProcess::getTaskId, taskId)
                .ne(GanttTasksProcess::getGtpState, ApprovalStatusEnum.Invalid.getIndex())
                .orderByDesc(GanttTasksProcess::getGtpCreateTime) // 数据库层排序
                .last("limit 1")
                .oneOpt()
                .map(one -> new CjGanttTaskProcessResponse(one.getGtpUploadTimes(), one.getGtpState(), one.getGtpId()))
                .orElse(new CjGanttTaskProcessResponse(0, null, null));
        return BaseResponse.ok(response);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse rewriteGanttTasksByTaskID(String taskId) {
        List<GanttTasksProcess> list = ganttTasksProcessService.lambdaQuery()
                .eq(GanttTasksProcess::getTaskId, taskId)
                .ne(GanttTasksProcess::getGtpState, ApprovalStatusEnum.Invalid.getIndex())
                .orderByDesc(GanttTasksProcess::getGtpCreateTime) // 数据库层排序
                .list();

        if (CollectionUtils.isEmpty(list)) {
            return BaseResponse.fail("该任务没有任务详情");
        }else{
            long count = list.stream().filter(ganttTasksProcess ->
                    ganttTasksProcess.getGtpState().equals(ApprovalStatusEnum.Already_Over.getIndex())).count();


            if (count !=1L && count!=2L ) {
                return BaseResponse.fail("任务的流程状态不是已完成（7）");
            } else {
                GanttTasks one = lambdaQuery().eq(GanttTasks::getTaskId, taskId).one();
                if (Objects.isNull(one))
                    return BaseResponse.fail("该任务不存在");
                else{
                    if(count == 1L){
                        //审批通过一次
                        one.setTaskBeginTime(list.get(0).getGtpDate());
                        //第一次就填写了开始和结束时间（结束时间不为空 ）
                        if(!ValidateUtil.isEmpty(list.get(0).getGtpEndDate())){
                            one.setTaskFinishTime(list.get(0).getGtpEndDate());
                        }
                    }else{
                        //审批通过两次
                        one.setTaskBeginTime(list.get(1).getGtpDate());
                        one.setTaskFinishTime(list.get(0).getGtpEndDate());
                    }
                    saveOrUpdate(one);
                    return BaseResponse.ok(one);
                }
            }
        }
//        if (CollectionUtils.isEmpty(list)) {
//            return BaseResponse.fail("该任务没有任务详情");
//        } else if (list.size() == 1) {
//            long count = list.stream().filter(ganttTasksProcess ->
//                    ganttTasksProcess.getGtpState().equals(ApprovalStatusEnum.Already_Over.getIndex())).count();
//            if (count != 1L) {
//                return BaseResponse.fail("任务的流程状态不是已完成（7）");
//            }
//            GanttTasks one = lambdaQuery().eq(GanttTasks::getTaskId, taskId).one();
//            Date startTime = list.get(0).getGtpDate();
//            one.setTaskBeginTime(startTime);
//            saveOrUpdate(one);
//            return BaseResponse.ok(one);
//        } else if (list.size() == 2) {
//            long count = list.stream().filter(ganttTasksProcess ->
//                    ganttTasksProcess.getGtpState().equals(ApprovalStatusEnum.Already_Over.getIndex())).count();
//            if (count != 2L) {
//                return BaseResponse.fail("任务的流程状态不是已完成（7）");
//            } else {
//                Date startTime = list.get(0).getGtpDate();
//                Date endTime = list.get(1).getGtpDate();
//                GanttTasks one = lambdaQuery().eq(GanttTasks::getTaskId, taskId).one();
//                if (Objects.isNull(one))
//                    return BaseResponse.fail("该任务不存在");
//                else {
//                    //one.setTaskBeginTime(startTime);
//                    one.setTaskFinishTime(endTime);
//                    one.setTaskProgress(100F);
//                    saveOrUpdate(one);
//                    return BaseResponse.ok(one);
//                }
//            }
//        } else {
//            return BaseResponse.fail("一个任务只能有两个详情记录，请检查数据库");
//        }
    }

    @Override
    public BaseResponse getGanttTasksByProject(GanttTasksCjDTO request) {
        List<GanttTasksResponse> data = baseMapper.getGanttTasksByProject(request);
        if (!CollectionUtils.isEmpty(data)) {
            List<GanttTasksResponse> res = new ArrayList<>();
            status(data, res, request.getTaskStatusList());
            return BaseResponse.ok(res, res.size());
        }
        return BaseResponse.ok(data, data.size());
    }

    @Override
    public BaseResponse getGanttTasksByProjectClassInfo(GanttTasksCjDTO request) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        int i = 1;
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(GanttTasksCjDTO.class.getName());
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if (!classInfoAnnotation.getType().equals("date")) {
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                        List<Object> dictionaryValues = baseMapper.getValueByCode(classInfoAnnotation.getDbValue(), request);
                        dictionaryValues = ClassInfoUtils.processDictionaryValues(dictionaryValues)
                                .collect(Collectors.toList());
                        classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        classInfoAnnotation.setNo(i++);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getInvestmentControlClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        ClassInfoAnnotation status = new ClassInfoAnnotation();
        status.setTitle("任务状态");
        status.setNo(4);
        status.setType("dictionary");
        status.setValue("taskStatusList");
        List<FilterObject> filterObjects = new ArrayList<>();
        filterObjects.add(new FilterObject(GanttTasksStatusEnum.UnStart.getIndex().toString(), "未开始"));
        filterObjects.add(new FilterObject(GanttTasksStatusEnum.Normal.getIndex().toString(), "办理中"));
        filterObjects.add(new FilterObject(GanttTasksStatusEnum.Cancel.getIndex().toString(), "已取消"));
        filterObjects.add(new FilterObject(GanttTasksStatusEnum.Overdue.getIndex().toString(), "逾期"));
        filterObjects.add(new FilterObject(GanttTasksStatusEnum.After_Overdue.getIndex().toString(), "逾期完成"));
        filterObjects.add(new FilterObject(GanttTasksStatusEnum.Complete.getIndex().toString(), "完成"));
        status.setDictionaryFilterValues(filterObjects);
        classInfoAnnotationList.add(status);
        classInfoAnnotationList = classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getCjMilestonesGanttTasks(GanttTasksRequest request) {
        if (ValidateUtil.isEmpty(request.getpId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        List<GanttTasksResponse> data = baseMapper.getCjMilestonesGanttTasks(request);
        if (!CollectionUtils.isEmpty(data)) {
            List<GanttTasksResponse> res = new ArrayList<>();
            if (ValidateUtil.isNotEmpty(request.getTaskImportance()) && request.getTaskImportance().equals("99")) {//里程碑
                Date now = new Date();
                for (GanttTasksResponse d : data) {
                    if (ValidateUtil.isNotNull(d.getTaskState()) && d.getTaskState() == 0)
                        d.setTaskStatus(GanttTasksStatusEnum.Cancel.getIndex());//已取消
                    else if (ValidateUtil.isNull(d.getTaskFinishTime()) && DateUtil.dayCompare(now, d.getTaskEndDate()))
                        d.setTaskStatus(GanttTasksStatusEnum.Overdue.getIndex());//逾期未完成
                    else if (ValidateUtil.isNotNull(d.getTaskFinishTime()) && DateUtil.dayCompare(d.getTaskFinishTime(), d.getTaskEndDate()))
                        d.setTaskStatus(GanttTasksStatusEnum.After_Overdue.getIndex());//逾期完成
                    else if (ValidateUtil.isNotNull(d.getTaskFinishTime()) && DateUtil.dayCompare(d.getTaskEndDate(), d.getTaskFinishTime()))
                        d.setTaskStatus(GanttTasksStatusEnum.Complete.getIndex());//计划结束时间之前正常完成
                    else if (DateUtil.dayCompare(d.getTaskStartDate(), now) && d.getTasksProcess() < 1)
                        d.setTaskStatus(GanttTasksStatusEnum.UnStart.getIndex());//未开始
                    else
                        d.setTaskStatus(GanttTasksStatusEnum.Normal.getIndex());//办理中
                    res.add(d);
                }
            } else { //一级二级 不传则当一二级处理
                status(data, res, null);
            }
            return BaseResponse.ok(res);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getCjGanttTasksById(String taskId) {
        GanttTasksResponse data = baseMapper.getCjGanttTasksById(taskId);
        if (ValidateUtil.isNotNull(data)) {
            data.setTasksCancels(ganttTasksCancelService.getGanttTasksCannelByTaskId(taskId));
            data.setTasksOverdues(ganttTasksOverdueService.getGanttTasksOverdueByTaskId(taskId));
            data.setTasksProcesses(ganttTasksProcessService.getTasksProcessByTaskId(taskId));
        }
        return BaseResponse.ok(data);
    }

    /**
     * 检查任务是否已完成
     *
     * @param pId
     * @param taskName
     * @param gsId
     * @return
     */
    @Override
    public BaseResponse checkGanttTaskCompleted(String pId, String taskName, String gsId) {
        if (ValidateUtil.isAnObjEmpty(pId, taskName)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        GanttPlan ganttPlan = ganttPlanService.getOne(Wrappers.<GanttPlan>lambdaQuery()
                .eq(GanttPlan::getPId, pId)
                .eq(GanttPlan::getPlanState, ApprovalStatusEnum.Already_Over.getIndex())
                .select(GanttPlan::getPlanId, GanttPlan::getPtId)
        );
        if (ValidateUtil.isNull(ganttPlan)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        // 模板数据处理
        GanttPlanTemplate planTemplate = ganttPlanTemplateService.getById(ganttPlan.getPtId());
        JSONObject jsonObject = JSONUtil.parseObj(planTemplate.getPtGantt());
        JSONArray dataArray = jsonObject.getJSONArray("data");

        // 构建映射关系
        Map<String, String> idNameMap = new HashMap<>();
        JSONObject targetTask = null;
        for (int i = 0; i < dataArray.size(); i++) {
            JSONObject item = dataArray.getJSONObject(i);
            idNameMap.put(item.getStr("id"), item.getStr("text"));
            if (taskName.equals(item.getStr("text"))) {
                targetTask = item;
            }
        }
        if (ValidateUtil.isNull(targetTask)) {
            return BaseResponse.fail("任务【" + taskName + "】未在模板中定义");
        }
        // 查询所有的前置任务名称
        String id = targetTask.getStr("id");
        List<String> preTaskNames = getAllPreTaskNames(id, idNameMap);
        if (preTaskNames.isEmpty()) {
            return BaseResponse.ok(true);
        }

        // 查询当前计划下的所有任务
        List<GanttTasks> targetTasks = list(Wrappers.<GanttTasks>lambdaQuery()
                .eq(GanttTasks::getPlanId, ganttPlan.getPlanId())
                .eq(GanttTasks::getTaskText, taskName)
                .select(GanttTasks::getTaskId, GanttTasks::getGsId, GanttTasks::getTaskSort)
                .orderByAsc(GanttTasks::getTaskSort)
        );
        // 如果没有找到目标任务，直接返回
        if (targetTasks.isEmpty()) {
            return BaseResponse.ok(true);
        }
        // 获取任务的所有标段id
        Set<String> allTargetGsIds = targetTasks.stream()
                .map(t -> ValidateUtil.isEmpty(t.getGsId()) ? "no_gsId" : t.getGsId())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> targetGsIds = new LinkedHashSet<>(allTargetGsIds);

        boolean restrictToSingleGs = gsId != null && !gsId.isEmpty();
        if (restrictToSingleGs) {
            String singleGsKey = Objects.toString(gsId, "no_gsId");
            targetGsIds.clear();
            targetGsIds.add(singleGsKey);
        }

        // 查询所有前置任务
        List<GanttTasks> allPreTasks = list(Wrappers.<GanttTasks>lambdaQuery()
                .eq(GanttTasks::getPlanId, ganttPlan.getPlanId())
                .ne(GanttTasks::getTaskState, 0)
                .in(GanttTasks::getTaskText, preTaskNames)
                .select(GanttTasks::getTaskId, GanttTasks::getTaskText, GanttTasks::getGsId,
                        GanttTasks::getTaskBeginTime, GanttTasks::getTaskFinishTime,
                        GanttTasks::getMustHandle, GanttTasks::getTaskSort)
                .orderByAsc(GanttTasks::getTaskSort)
        );
        // 标段名称
        Map<String, String> gsNameMap = Optional.ofNullable(projectCpDtlMapper.getGantSectionListByPid(pId))
                .orElseGet(Collections::emptyList)
                .stream().collect(Collectors.toMap(GantSectionDTO::getId, GantSectionDTO::getGsSection, (a,b)->a));

        Map<String, Map<String, List<GanttTasks>>> preByGsThenName =
                allPreTasks.stream().collect(Collectors.groupingBy(
                        t -> Objects.toString(t.getGsId(), "no_gsId"),
                        Collectors.groupingBy(GanttTasks::getTaskText)
                ));

        Map<String, String> templateName = new LinkedHashMap<>();
        List<String> preTaskNamesWithGsName = preTaskNames.stream().peek(n -> templateName.putIfAbsent(n, n)).collect(Collectors.toList());

        List<String> problems = new ArrayList<>();
        // 判断标准
        Predicate<GanttTasks> isCompleted = t -> t.getTaskBeginTime() != null && t.getTaskFinishTime() != null;
        Predicate<GanttTasks> isSkip = t -> t.getMustHandle() != null && t.getMustHandle();

        // 有匹配标段的前置任务
        for (String gsKey : targetGsIds) {
            Map<String, List<GanttTasks>> nameMap = preByGsThenName.getOrDefault(gsKey, Collections.emptyMap());
            for (String preName : preTaskNamesWithGsName) {
                List<GanttTasks> instances = nameMap.get(preName);
                if (instances == null || instances.isEmpty()) {
                    continue;
                }
                List<GanttTasks> needCheck = instances.stream().filter(isSkip.negate()).collect(Collectors.toList());
                if (needCheck.isEmpty()) {
                    continue;
                }

                boolean anyCompleted = needCheck.stream().anyMatch(isCompleted);
                if (!anyCompleted) {
                    String showName = templateName.getOrDefault(preName, preName);
                    // 带上标段名（未分标段不加）
                    String label = "no_gsId".equals(gsKey) ? "" : "（" + gsNameMap.getOrDefault(gsKey, gsKey) + "）";
                    problems.add(showName + label);
                }
            }
        }

        // 没有匹配标段的前置任务
        for (Map.Entry<String, Map<String, List<GanttTasks>>> e : preByGsThenName.entrySet()) {
            String gsKey = e.getKey();
            if (allTargetGsIds.contains(gsKey)) {
                continue;
            }
            Map<String, List<GanttTasks>> nameMap = e.getValue();

            for (Map.Entry<String, List<GanttTasks>> ne : nameMap.entrySet()) {
                String preNorm = ne.getKey();
                List<GanttTasks> instances = ne.getValue();

                List<GanttTasks> needCheck = instances.stream().filter(isSkip.negate()).collect(Collectors.toList());
                if (needCheck.isEmpty()) {
                    continue;
                }

                boolean anyCompleted = needCheck.stream().anyMatch(isCompleted);
                if (!anyCompleted) {
                    String showName = templateName.getOrDefault(preNorm,
                            instances.isEmpty() ? preNorm : instances.get(0).getTaskText());
                    // 带上标段名（未分标段不加）
                    String label = "no_gsId".equals(gsKey) ? "" : "（" + gsNameMap.getOrDefault(gsKey, gsKey) + "）";
                    problems.add(showName + label);
                }
            }
        }

        if (!problems.isEmpty()) {
            // 排序并拼接标段名
            problems.sort(Comparator.comparing(s -> {
                GanttTasks task = allPreTasks.stream()
                        .filter(t -> t.getTaskText().equals(s) || s.startsWith(t.getTaskText()))
                        .findFirst()
                        .orElse(null);
                return task != null ? task.getTaskSort() : Integer.MAX_VALUE;
            }));
            String names = problems.stream().distinct().collect(Collectors.joining("、"));
            return BaseResponse.ok("前置任务【" + names + "】未完成", false);
        }

        return BaseResponse.ok(true);
    }

    /**
     * 获取所有前置任务名称
     *
     * @param taskId
     * @param idNameMap
     * @return
     */
    private List<String> getAllPreTaskNames(String taskId, Map<String, String> idNameMap) {
        List<String> result = new ArrayList<>();
        Deque<String> stack = new ArrayDeque<>();
        stack.push(taskId);

        // 一次性查询所有前置任务链接
        List<GanttLinksTemplate> linksTemplates = ganttLinksTemplateService.list(
                Wrappers.<GanttLinksTemplate>lambdaQuery()
                        .eq(GanttLinksTemplate::getLinkType, 0)
                        .select(GanttLinksTemplate::getLinkTarget, GanttLinksTemplate::getLinkSource)
        );
        // 将前置任务链接按目标分组
        Map<String, List<GanttLinksTemplate>> preLinksMap = linksTemplates.stream()
                .collect(Collectors.groupingBy(GanttLinksTemplate::getLinkTarget));

        while (!stack.isEmpty()) {
            String currentId = stack.pop();
            List<GanttLinksTemplate> preLinks = preLinksMap.getOrDefault(currentId, Collections.emptyList());
            if (ValidateUtil.isEmpty(preLinks)) {
                continue;
            }

            for (GanttLinksTemplate link : preLinks) {
                String sourceId = link.getLinkSource();
                if (!result.contains(sourceId)) {
                    result.add(sourceId);
                    stack.push(sourceId);
                }
            }
        }

        // 转成任务名称
        return result.stream().map(idNameMap::get).filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }

    private void status(List<GanttTasksResponse> data, List<GanttTasksResponse> res, List<Integer> taskStatusList) {
        Date now = new Date();
        for (GanttTasksResponse d : data) {
            if (ValidateUtil.isNotNull(d.getTaskState()) && d.getTaskState() == 0)//作废
                d.setTaskStatus(GanttTasksStatusEnum.Cancel.getIndex());
            else if (ValidateUtil.isNotNull(d.getTaskFinishTime())) {
                if (ValidateUtil.isNotNull(d.getTaskEndDate())
                        && DateUtil.dayCompare(d.getTaskFinishTime(), d.getTaskEndDate()))
                    d.setTaskStatus(GanttTasksStatusEnum.After_Overdue.getIndex()); //逾期完成
                else
                    d.setTaskStatus(GanttTasksStatusEnum.Complete.getIndex()); //完成
            } else {
                if (ValidateUtil.isNotNull(d.getTaskEndDate()) && DateUtil.dayCompare(now, d.getTaskEndDate()))
                    d.setTaskStatus(GanttTasksStatusEnum.Overdue.getIndex());//逾期
                else {
                    if (d.getTasksProcess() > 0) //办理中
                        d.setTaskStatus(GanttTasksStatusEnum.Normal.getIndex());
                    else //未开始
                        d.setTaskStatus(GanttTasksStatusEnum.UnStart.getIndex());
                }
            }
            //需要根据状态过滤
            if (!CollectionUtils.isEmpty(taskStatusList) && !taskStatusList.contains(d.getTaskStatus()))
                continue;
            res.add(d);
        }
    }

    public void deleteGanttTasksByPlanId(String planId) {
        QueryWrapper<GanttTasks> wrapper = new QueryWrapper<>();
        wrapper.eq("Plan_Id", planId);
        baseMapper.delete(wrapper);
    }
}

//
//public GanttTasksProcess buildGanttProcess(GanttTasks task,int time){
//    GanttTasksProcess ganttTasksProcess = new GanttTasksProcess();
//    ganttTasksProcess.setGtpId(UUID.randomUUID().toString());
//    ganttTasksProcess.setTaskId(task.getTaskId());
//    ganttTasksProcess.setGtpDate(new Date());
//    ganttTasksProcess.setGtpRate(null);
//    ganttTasksProcess.setGtpRemark(null);
//    ganttTasksProcess.setGtpCreateUid(task.getTaskUid());
//    ganttTasksProcess.setGtpCreateUname(task.getTaskUname());
//    ganttTasksProcess.setGtpCreateTime(new Date());
//    ganttTasksProcess.setGtpState(task.getTaskState());
//    ganttTasksProcess.setGtpStateTime(new Date());
//    ganttTasksProcess.setGtpUploadTimes(time);
//    return ganttTasksProcess;
//}

//@Override
//    public BaseResponse createGanttTasksByTaskID(GanttTasksProcessRequest request) {
//        String taskId = request.getTaskId();
//        Long count = ganttTasksProcessService.lambdaQuery()
//                .eq(GanttTasksProcess::getTaskId, taskId)
//                .ne(GanttTasksProcess::getGtpState, ApprovalStatusEnum.Invalid.getIndex())
//                .orderByDesc(GanttTasksProcess::getGtpCreateTime) // 数据库层排序
//                .count();
//        if (count == 0) {
//            return createGanttTasksByTaskIDFirstTime(request);
//        } else if (count == 1) {
//            GanttTasksProcess one = ganttTasksProcessService.lambdaQuery()
//                    .eq(GanttTasksProcess::getTaskId, taskId)
//                    .ne(GanttTasksProcess::getGtpState, ApprovalStatusEnum.Invalid.getIndex())
//                    .orderByDesc(GanttTasksProcess::getGtpCreateTime)
//                    .one();
//            if(!one.getGtpState().equals(ApprovalStatusEnum.Already_Over.getIndex()))
//                return BaseResponse.fail("第一个任务流程未完成，不能创建第二个任务流程");
//            else
//                return createGanttTasksByTaskIDSecondTime(request);
//        } else {
//            return BaseResponse.fail("该任务已存在多次上传记录，无法继续创建");
//        }
//    }