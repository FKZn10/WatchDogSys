package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import com.jpmsoft.common.model.request.Plan.GantSectionTaskDTO;
import com.jpmsoft.common.model.request.Plan.GanttPlanChangeDTO;
import com.jpmsoft.common.model.response.Plan.GanttLinksChangeVO;
import com.jpmsoft.common.model.response.Plan.GanttPlanChangeVO;
import com.jpmsoft.common.model.response.Plan.GanttTasksChangeVO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttPlanChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttLinks;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlanChange;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasks;
import com.jpmsoft.projectProducer.rabbitMQ.Sender;
import com.jpmsoft.projectProducer.service.ganttPlan.*;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/23 10:03
 */
@Service
public class GanttPlanChangeImpl extends FileImpl<GanttPlanChangeMapper, GanttPlanChange, GanttPlanChangeDTO> implements IGanttPlanChangeService {

    @Resource
    private IGanttTasksChangeService ganttTasksChangeService;
    @Resource
    private IGanttTasksRecordService ganttTasksRecordService;
    @Resource
    private IGanttLinksChangeService ganttLinksChangeService;

    @Resource
    private IGanttPlanService ganttPlanService;
    @Resource
    private IGanttTasksService ganttTasksService;
    @Resource
    private IGanttLinksService ganttLinksService;
    @Resource
    private IGantSectionTaskService gantSectionTaskService;
    @Resource
    private IGantSectionService gantSectionService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private Sender sender;
    @Resource
    private IGantSectionChangeService gantSectionChangeService;
    @Resource
    private IGantSectionTaskChangeService gantSectionTaskChangeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGanttPlanChange(GanttPlanChangeDTO request) {
         if (ValidateUtil.isEmpty(request.getPlanId()) && can2Change(request.getPlanSource()))
            return BaseResponse.fail(ResMsg.secBidPlanChangeExist);
        List<String> canDel = ganttTasksChangeService.canNot2Delete(request.getGanttTasks());
        if (!CollectionUtils.isEmpty(canDel))
            return BaseResponse.fail(String.format(ResMsg.ganttTaskNotDel, String.join(",", canDel)));
        String action = "create";
        if (ValidateUtil.isNotEmpty(request.getPlanId())) action = "update";
        GanttPlanChange entity = new GanttPlanChange();
        //版本号
        if (ValidateUtil.isEmpty(request.getPlanId()))
            request.setPlanNumber("V" + getVersionNumber(request.getPlanSource()) + ".0");
        createOrUpdateFileEntity(entity, request);
        Map<String, String> keys = ganttTasksChangeService.createOrUpdateTaskChange(request.getGanttTasks(), entity.getPlanId());
        if (action.equalsIgnoreCase("create"))//仅新建变更时记录一次当时变更前进度计划任务详情数据
            ganttTasksRecordService.createGanttTasksRecord(request.getGanttTasksRecords(), entity.getPlanId());
        ganttLinksChangeService.createOrUpdateLinksChange(keys, request.getGanttLinks(), entity.getPlanId());
        gantSectionChangeService.createOrUpdateGantSection(request.getGantSections(), entity.getPlanId());
        gantSectionTaskChangeService.createOrUpdateSectionTask(request.getGanttTasks(), entity.getPlanId(), null);
        return BaseResponse.ok(entity.getPlanId());
    }

    private boolean can2Change(String sourceId) {
        if (ValidateUtil.isEmpty(sourceId)) throw new JPMException(ResMsg.notReqParam);
        QueryWrapper<GanttPlanChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Plan_Source", sourceId);
        wrapper.ne("Plan_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.ne("Plan_State", ApprovalStatusEnum.Invalid.getIndex());
        return baseMapper.exists(wrapper);
    }

    @Override
    public BaseResponse getGanttPlanChangeList(GanttPlanChangeDTO request, String uid) {
        String authSql = sysUserService.getAuthSql("Gantt_Plan_Change", uid, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Plan_State");
        if (ValidateUtil.isEmpty(authSql)) return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<GanttPlanChangeDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("planType").put("planName").put("planSource")
                .put("pName").put("pId").put("planDId").put("planState").put("conName");
        IPage<GanttPlanChangeVO> page = new Page<>(request.getPage(), request.getSize());
        List<GanttPlanChangeVO> datas = baseMapper.getGanttPlanChangeList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getGanttPlanChangeById(String id) {
        GanttPlanChangeVO data = selectGanttPlanChangeById(id);
        List<GantSectionTaskDTO> gantSectionTasks = gantSectionTaskChangeService.getGantSectionTaskByPlanId(id, null);
        if (!CollectionUtils.isEmpty(data.getGanttTasks()))
            data.setGanttTasks(setLevel(data.getGanttTasks(), gantSectionTasks));
        if (!CollectionUtils.isEmpty(data.getGanttTasksRecords()))
            data.setGanttTasksRecords(setLevel(data.getGanttTasksRecords(), gantSectionTasks));
        return BaseResponse.ok(data);
    }

    private List<GanttTasksChangeVO> setLevel(List<GanttTasksChangeVO> tasks, List<GantSectionTaskDTO> gantSectionTasks) {
        List<GanttTasksChangeVO> tree = new ArrayList<>();
        List<GanttTasksChangeVO> roots = tasks.stream().filter(n -> n.getTaskParent().equals("0")).collect(Collectors.toList());
        if (roots.size() > 0) {
            roots.forEach(root -> {
                List<GanttTasksChangeVO> child = tasks.stream().filter(n -> n.getTaskParent().equals(root.getTaskId())).collect(Collectors.toList());
                root.setTaskLevel(1);
                setGstSections(root, gantSectionTasks);
                tree.add(root);
                setChildLevel(child, tasks, tree, 1, gantSectionTasks);
            });
        }
        return tree;
    }

    private void setGstSections(GanttTasksChangeVO task, List<GantSectionTaskDTO> sectionTasks) {
        if (!CollectionUtils.isEmpty(sectionTasks)) {
            List<GantSectionTaskDTO> sections = sectionTasks.stream().filter(f -> f.getTaskId().equals(task.getTaskId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(sections)) {
                task.setGstSections(sections.stream().map(GantSectionTaskDTO::getGstSection).collect(Collectors.toList()));
            }
        }
    }

    private void setChildLevel(List<GanttTasksChangeVO> roots, List<GanttTasksChangeVO> datas, List<GanttTasksChangeVO> tree, int level, List<GantSectionTaskDTO> gantSectionTasks) {
        if (roots.size() > 0) {
            level += 1;
            final int currentLevel = level;
            roots.forEach(root -> {
                List<GanttTasksChangeVO> child = datas.stream().filter(n -> n.getTaskParent().equals(root.getTaskId())).collect(Collectors.toList());
                root.setTaskLevel(currentLevel);
                setGstSections(root, gantSectionTasks);
                tree.add(root);
                setChildLevel(child, datas, tree, currentLevel, gantSectionTasks);
            });
        }
    }

    private List<GanttTasksChangeVO> getTree(List<GanttTasksChangeVO> tasks) {
        List<GanttTasksChangeVO> tree = new ArrayList<>();
        List<GanttTasksChangeVO> roots = tasks.stream().filter(n -> n.getTaskParent().equals("0")).collect(Collectors.toList());
        if (roots.size() > 0) {
            roots.forEach(root -> {
                List<GanttTasksChangeVO> child = tasks.stream().filter(n -> n.getTaskParent().equals(root.getTaskId())).collect(Collectors.toList());
                root.setTaskLevel(1);
                setChildNodes(child, tasks, 1);
                root.setChildNodes(child);
                tree.add(root);
            });
        }
        return tree;
    }

    private void setChildNodes(List<GanttTasksChangeVO> roots, List<GanttTasksChangeVO> datas, int level) {
        if (roots.size() > 0) {
            level += 1;
            final int currentLevel = level;
            roots.forEach(root -> {
                List<GanttTasksChangeVO> child = datas.stream().filter(n -> n.getTaskParent().equals(root.getTaskId())).collect(Collectors.toList());
                root.setTaskLevel(currentLevel);
                setChildNodes(child, datas, currentLevel);
                root.setChildNodes(child);
            });
        }
    }

    private GanttPlanChangeVO selectGanttPlanChangeById(String id) {
        GanttPlanChangeVO data = baseMapper.selectGanttPlanChangeById(id);
        if (data != null) {
            data.setGanttLinks(ganttLinksChangeService.getGanttLinksChangeByPlanId(id));
            data.setGanttTasks(ganttTasksChangeService.getGanttTasksChangeByPlanId(id));
            data.setGanttTasksRecords(ganttTasksRecordService.getGanttTasksRecordByPlanId(id));
            data.setGantSections(gantSectionChangeService.selectByPlanId(id));
        } else throw new JPMException(ResMsg.infoNotExist);
        return data;
    }

    @Override
    public BaseResponse approved2SyncGanttPlan(String id) {
        GanttPlanChangeVO data = selectGanttPlanChangeById(id);
        if (data == null || !Objects.equals(data.getPlanState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        //region 进度计划数据同步
        GanttPlan plan = ganttPlanService.getById(data.getPlanSource());
        if (plan == null) return BaseResponse.fail(ResMsg.infoNotExist);
        plan.setPlanStart(data.getPlanStart());
        plan.setPlanEnd(data.getPlanEnd());
        ganttPlanService.updateById(plan);
        //endregion
        //region 同步任务以及任务关联线数据
        List<GanttTasksChangeVO> tasks = data.getGanttTasks();
        List<GanttLinksChangeVO> links = data.getGanttLinks();
        List<GanttTasksChangeVO> taskChanges = tasks.stream().filter(n -> ValidateUtil.isNotEmpty(n.getTaskChangeType()) && !n.getTaskChangeType().equals("无变更")).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(taskChanges)) {
            List<GanttTasksChangeVO> taskAdds = taskChanges.stream().filter(n -> n.getTaskChangeType().equals("新增")).collect(Collectors.toList());
            List<GanttTasksChangeVO> taskUps = taskChanges.stream().filter(n -> n.getTaskChangeType().equals("变更")).collect(Collectors.toList());
            List<String> taskDels = taskChanges.stream().filter(n -> n.getTaskChangeType().equals("删除")).map(GanttTasksChangeVO::getTaskSource).collect(Collectors.toList());
            //变更记录taskId 任务taskId
            Map<String, String> idMapping = new HashMap<>();
            //记录taskId对应taskSource
            Map<String, String> keys = new HashMap<>();
            for (GanttTasksChangeVO task : tasks) {
                if (!task.getTaskChangeType().equals("删除") && ValidateUtil.isNotEmpty(task.getTaskSource()))
                    keys.put(task.getTaskId(), task.getTaskSource());
            }
            //region 删除任务
            if (!CollectionUtils.isEmpty(taskDels))
                ganttTasksService.removeBatchByIds(taskDels);

            //endregion
            List<GantSectionDTO> gantSection = gantSectionChangeService.selectByPlanId(data.getPlanId());
            //region 新增任务
            if (!CollectionUtils.isEmpty(taskAdds)) {
                ganttTasksService.saveBatch(taskAdds.stream().map(add -> {
                    GanttTasks task = new GanttTasks();
                    BeanUtils.copyProperties(add, task);
                    String oldId = task.getTaskId();
                    task.setTaskId(GenerateUUID.getUUID());
                    task.setPlanId(data.getPlanSource());
                    keys.put(oldId, task.getTaskId());
                    idMapping.put(add.getTaskId(), task.getTaskId());
                    List<GantSectionDTO> gs = gantSection.stream().filter(f -> f.getId().equals(add.getGsId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(gs))
                        task.setGsId(gs.stream().findFirst().get().getGsSource());
                    List<String> parents = tasks.stream().filter(n -> n.getTaskId().equals(task.getTaskParent())).map(GanttTasksChangeVO::getTaskSource).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(parents)) task.setTaskParent(parents.get(0));
                    else task.setTaskParent("0");
                    task.setTaskState(ValidateUtil.isNull(task.getTaskState()) ? 1 : task.getTaskState());
                    return task;
                }).collect(Collectors.toList()));
            }
            //endregion
            //region 变更任务
            if (!CollectionUtils.isEmpty(taskUps)) {
                ganttTasksService.updateBatchById(taskUps.stream().map(up -> {
                    GanttTasks task = new GanttTasks();
                    BeanUtils.copyProperties(up, task);
                    task.setTaskId(up.getTaskSource());
                    task.setPlanId(data.getPlanSource());
                    idMapping.put(up.getTaskId(), task.getTaskId());
                    List<String> parents = tasks.stream().filter(n -> n.getTaskId().equals(task.getTaskParent())).map(GanttTasksChangeVO::getTaskSource).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(parents)) task.setTaskParent(parents.get(0));
                    else task.setTaskParent("0");
                    task.setTaskState(ValidateUtil.isNull(task.getTaskState()) ? 1 : task.getTaskState());
                    return task;
                }).collect(Collectors.toList()));
            }
            //endregion

            //region 重构关联线
            if (!CollectionUtils.isEmpty(links)) {
                ganttLinksService.deleteGanttLinksByPlanId(data.getPlanSource());
                List<GanttLinks> newLinks = links.stream().map(l -> {
                    if (keys.containsKey(l.getLinkTarget()) && keys.containsKey(l.getLinkSource())) {
                        GanttLinks link = new GanttLinks();
                        link.setPlanId(data.getPlanSource());
                        link.setLinkType(l.getLinkType());
                        link.setLinkId(GenerateUUID.getUUID());
                        link.setLinkTarget(keys.get(l.getLinkTarget()));
                        link.setLinkSource(keys.get(l.getLinkSource()));
                        return link;
                    } else return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(newLinks))
                    ganttLinksService.saveBatch(newLinks);
            }
            //endregion
            //region 重构标段计划关系
            gantSectionService.createOrUpdateGantSection(gantSection, plan.getPlanId());
            //endregion
            //region 重构标段任务关系
//            gantSectionTaskService.syncSectionTaskChange(gantSectionTaskChangeService.getGantSectionTaskByPlanId(data.getPlanId(),
//                            taskChanges.stream().filter(n -> n.getTaskChangeType().equals("删除")).map(GanttTasksChangeVO::getTaskId).collect(Collectors.toList())), plan.getPlanId()
//                    , idMapping);
            //endregion
        }
        //endregion
        return BaseResponse.ok(data.getPlanSource());
    }

    @Override
    public BaseResponse approved2SyncGanttPlanAndProjectCp(String id) {
        BaseResponse res = approved2SyncGanttPlan(id);
        if (!res.getSuccess() || res.getStatusCode() != 200) return res;
        //发送同步建设程序消息
        sender.sendSyncPlan2Cp(res.getData() + "");
        return res;
    }

    private long getVersionNumber(String sourceId) {
        if (ValidateUtil.isEmpty(sourceId)) return 0L;
        QueryWrapper<GanttPlanChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Plan_Source", sourceId);
//        wrapper.eq("Plan_State",);
        return baseMapper.selectCount(wrapper) + 1L;
    }
}
