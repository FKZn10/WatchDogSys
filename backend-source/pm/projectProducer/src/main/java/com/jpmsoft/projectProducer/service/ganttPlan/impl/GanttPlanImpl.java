package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.KeyValueDTO;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import com.jpmsoft.common.model.request.Plan.GanttPlanRequest;
import com.jpmsoft.common.model.response.Plan.GanttPlanResponse;
import com.jpmsoft.common.model.response.Plan.GanttTasksResponse;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.ProjectStageEnum;
import com.jpmsoft.projectProducer.mapper.ganttPlan.GanttPlanMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.GanttTaskCjStatisticsDTO;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.ScheduleExpirationTimeDTO;
import com.jpmsoft.projectProducer.pojo.dto.project.DepartmentPCjConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.request.ganttplan.PreProjectPage;
import com.jpmsoft.projectProducer.pojo.request.ganttplan.TasksInfoRequest;
import com.jpmsoft.projectProducer.pojo.response.PreprojectInfoResponse;
import com.jpmsoft.projectProducer.pojo.response.TaskImportanceResponse;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCplTemplateService;
import com.jpmsoft.projectProducer.service.ganttPlan.*;
import com.jpmsoft.projectProducer.service.other.*;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.DictionaryUtils;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GanttPlanImpl extends ServiceImpl<GanttPlanMapper, GanttPlan> implements IGanttPlanService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IGanttTasksService ganttTasksService;
    @Resource
    private IGanttLinksService ganttLinksService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IGantSectionService gantSectionService;
    @Resource
    private IGantSectionTaskService gantSectionTaskService;
    @Resource
    private IProjectCplTemplateService projectCplTemplateService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysExtendValueService sysExtendValueService;

    @Override
    public GanttPlan selectByPid(String pId) {
        LambdaQueryWrapper<GanttPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GanttPlan::getPId, pId);
        wrapper.ne(GanttPlan::getPlanState, ApprovalStatusEnum.Invalid.getIndex());
        wrapper.and(d -> d.ne(GanttPlan::getPlanType, "Milestone").or().isNull(GanttPlan::getPlanType));//排除里程碑
        List<GanttPlan> ganttPlans = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(ganttPlans)) return ganttPlans.stream().findFirst().get();
        return null;
    }

    /**
     * 项目下是否存在非作废得进度计划
     *
     * @param pId
     * @param planId
     * @return
     */
    public Boolean projectHaveExistGanttPlan(String pId, String planId) {
        LambdaQueryWrapper<GanttPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GanttPlan::getPId, pId);
        wrapper.ne(GanttPlan::getPlanState, ApprovalStatusEnum.Invalid.getIndex());
        wrapper.and(d -> d.ne(GanttPlan::getPlanType, "Milestone").or().isNull(GanttPlan::getPlanType));//排除里程碑
        if (ValidateUtil.isNotEmpty(planId))
            wrapper.ne(GanttPlan::getPlanId, planId);
        return baseMapper.exists(wrapper);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGanttPlanProjectOnly(GanttPlanRequest request) {
        if (projectHaveExistGanttPlan(request.getpId(), request.getPlanId()))
            return BaseResponse.fail("当前项目已存在进度计划，请勿重复操作！");
        return createOrUpdateGanttPlan(request);
    }

    @Override
    public BaseResponse projectExistGanttPlan(String pId, String planId) {
        return BaseResponse.ok(projectHaveExistGanttPlan(pId, planId));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateGanttPlan(GanttPlanRequest request) {
        GanttPlan entity = new GanttPlan();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getPlanId())) {
            projectOnlyOneMilestonePlan(request);
            entity.setPlanId(GenerateUUID.getUUID());
            entity.setPlanCreateTime(new Date());
            entity.setPlanStateTime(null);
            entity.setPlanState(ValidateUtil.isEmpty(request.getPlanState()) ? ApprovalStatusEnum.Not_Started.getIndex():request.getPlanState());
            entity.setPlanEnableState(1);
            entity.setPlanFinishState(0);
            baseMapper.insert(entity);
            ganttTasksService.createOrUpdateGanttTasks(request.getGanttTasks(), entity.getPlanId(), "create");
            ganttLinksService.createOrUpdateGanttLinks(request.getGanttLinks(), entity.getPlanId(), null);
            gantSectionService.createOrUpdateGantSection(request.getGantSections(), entity.getPlanId());
            gantSectionTaskService.createOrUpdateSectionTask(request.getGanttTasks(), entity.getPlanId(), null);
            sysFileService.saveFile(entity.getPlanId(), request.gettId(), entity.getPlanState() + "", entity.getPlanCreateUid(), entity.getPlanCreateUname()
                    , entity.getPlanCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getPlanId());
            return BaseResponse.ok(entity.getPlanId());
        } else {
            GanttPlan model = baseMapper.selectById(request.getPlanId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (Objects.equals(model.getPlanState(), ApprovalStatusEnum.Invalid.getIndex()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setPlanCreateTime(model.getPlanCreateTime());
            entity.setPlanCreateUid(model.getPlanCreateUid());
            entity.setPlanCreateUname(model.getPlanCreateUname());
            entity.setPlanState(model.getPlanState());
            entity.setPlanStateTime(model.getPlanStateTime());
            //entity.setPId(model.getPId());
            baseMapper.updateById(entity);
            List<String> relDel = ganttTasksService.createOrUpdateGanttTasks(request.getGanttTasks(), entity.getPlanId(), "update");
            gantSectionService.createOrUpdateGantSection(request.getGantSections(), entity.getPlanId());
            ganttLinksService.createOrUpdateGanttLinks(request.getGanttLinks(), entity.getPlanId(), relDel);
            gantSectionTaskService.createOrUpdateSectionTask(request.getGanttTasks(), entity.getPlanId(), relDel);
            sysFileService.saveFile(entity.getPlanId(), request.gettId(), entity.getPlanState() + "", entity.getPlanCreateUid(), entity.getPlanCreateUname()
                    , entity.getPlanCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), entity.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getPlanId());
            return BaseResponse.ok(entity.getPlanId());
        }
    }

    @Override
    public BaseResponse getGanttPlanList(GanttPlanRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Plan_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<GanttPlanRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("uId", uId).put("planType").put("planName").put("planTypes").put("excludeTypes")
                .put("pName").put("pId").put("planDId").put("planState").put("conName").put("pPlanStartYear").put("pCompetentDepartment")
                .put("pDepartmentNameList").put("pBuildModelList").put("pCompetentDepartmentNameList")
                .put("pNameList").put("createUnameList").put("stateList").put("createTimeList")
                .put("planCreateUname").put(request.getPlanStartDates(),"planStartDateStart","planStartDateEnd")
                .put(request.getPlanEndDates(),"planEndDateStart","planEndDateEnd");
        //region (2022年5月12日新增：外部用户只能查看自己创建的、自己单位人员创建的、需要自己办理的、需要自己单位人员办理的进度计划信息,同时的拥有项目权限)zhang；
        //（2022年5月17日追加：项目下的监理单位和造价咨询单位可以查看外部单位在该项目下创建的所有进度计划数据）
        SysUser user = sysUserService.getById(uId);
        if (user != null) {
            if (!ValidateUtil.isEmpty(user.getUType()) && user.getUType().equals("2")) {
                params.put("uType", user.getUType());
            } else {
                params.put("uType", null);
            }
        } else {
            params.put("uType", null);
        }
        //endregion
        IPage<GanttPlanResponse> page = new Page<>(request.getPage(), request.getSize());
        List<GanttPlanResponse> datas = baseMapper.getGanttPlanList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getGanttPlanListNJCJ(GanttPlanRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        QueryMap<GanttPlanRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("uId", uId).put("planType").put("planName").put("planTypes").put("excludeTypes")
                .put("pName").put("pId").put("planDId").put("planState").put("conName").put("pPlanStartYear").put("pCompetentDepartment")
                .put("pDepartmentNameList").put("pBuildModelList").put("pCompetentDepartmentNameList")
                .put("pNameList").put("createUnameList").put("stateList").put("createTimeList");
        // region (2022年5月12日新增：外部用户只能查看自己创建的、自己单位人员创建的、需要自己办理的、需要自己单位人员办理的进度计划信息,同时的拥有项目权限)zhang；
        //（2022年5月17日追加：项目下的监理单位和造价咨询单位可以查看外部单位在该项目下创建的所有进度计划数据）
        SysUser user = sysUserService.getById(uId);
        if (user != null) {
            if (!ValidateUtil.isEmpty(user.getUType()) && user.getUType().equals("2")) {
                params.put("uType", user.getUType());
            } else {
                params.put("uType", null);
            }
        } else {
            params.put("uType", null);
        }
        // endregion
        IPage<GanttPlanResponse> page = new Page<>(request.getPage(), request.getSize());

        // 审批、沟通、抄送
        Set<String> planIds = baseMapper.getPlanidsByUid(uId);
        params.put("njcj", "01");
        params.put("planIds", planIds);

        List<GanttPlanResponse> datas = baseMapper.getGanttPlanList(page, params.getQuery());

        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getGanttPlanClassInfo(GanttPlanRequest request, String uId) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        int i = 1;
        try {
            String authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Plan_State");
            if (ValidateUtil.isEmpty(authSql))
                return BaseResponse.fail(ResMsg.noPermission);
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(GanttPlanRequest.class.getName());
            QueryMap<GanttPlanRequest> params = new QueryMap<>(request);
            params.put("authSql", authSql).put("uId", uId).put("planType").put("planName").put("planTypes").put("excludeTypes")
                    .put("pName").put("pId").put("planDId").put("planState").put("conName").put("pPlanStartYear").put("pCompetentDepartment")
                    .put("pDepartmentNameList").put("pBuildModelList").put("pCompetentDepartmentNameList")
                    .put("pNameList").put("createUnameList").put("stateList").put("createTimeList");
            //region (2022年5月12日新增：外部用户只能查看自己创建的、自己单位人员创建的、需要自己办理的、需要自己单位人员办理的进度计划信息,同时的拥有项目权限)zhang；
            //（2022年5月17日追加：项目下的监理单位和造价咨询单位可以查看外部单位在该项目下创建的所有进度计划数据）
            SysUser user = sysUserService.getById(uId);
            if (user != null) {
                if (!ValidateUtil.isEmpty(user.getUType()) && user.getUType().equals("2")) {
                    params.put("uType", user.getUType());
                } else {
                    params.put("uType", null);
                }
            } else {
                params.put("uType", null);
            }
            // 审批、沟通、抄送
            Set<String> planIds = baseMapper.getPlanidsByUid(uId);
            params.put("njcj", "01");
            params.put("planIds", planIds);
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if (!classInfoAnnotation.getType().equals("date")) {
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())) {
                        List<Object> dictionaryValues = baseMapper.getValueByCode(classInfoAnnotation.getDbValue(), params.getQuery());
                        dictionaryValues = ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                        classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        classInfoAnnotation.setNo(i++);
                    }
                }
            }
        } catch (Exception e) {
            log.error("getGanttPlanClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        classInfoAnnotationList = classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());
        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse getGanttPlanByPlanId(String planId) {
        GanttPlanResponse data = baseMapper.getGanttPlanByPlanId(planId);
        if (data != null) {
            data = ganttTasksService.getGanttTasksTreeByPlanId(data);
            data.setGantSections(gantSectionService.selectByPlanId(planId));
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse statisticsProjectPlan(String uId) {
        String authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        List<GanttPlan> allPlans = baseMapper.statisticsProjectPlan(authSql);
        List<String> pIds = allPlans.stream().map(GanttPlan::getPId).distinct().collect(Collectors.toList());
        List<KeyValueDTO> stages;
        if (!CollectionUtils.isEmpty(pIds)) stages = baseMapper.ganttPlanStatisticsProjectStage(pIds);
        else stages = new ArrayList<>();
        Map<String, Integer> data = new HashMap<>();
        data.put("all", pIds.size());
        for (ProjectStageEnum e : ProjectStageEnum.values()) {
            List<KeyValueDTO> stage = stages.stream().filter(n -> !ValidateUtil.isEmpty(n.getTheKey()) && n.getTheKey().equals(e.getkey())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(stage))
                data.put(e.getkey(), Integer.parseInt(stage.stream().findFirst().get().getTheValue()));
            else
                data.put(e.getkey(), 0);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse statisticsProJectPlanRanking(GanttPlanRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = new HashMap<>();
        params.put("authSql", authSql);
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("pName", !JudgeUtil.checkObject(request.getpName()) ? request.getpName() : null);
        params.put("planName", !JudgeUtil.checkObject(request.getPlanName()) ? request.getPlanName() : null);
        params.put("teId", !JudgeUtil.checkObject(request.getTeId()) ? request.getTeId() : null);
        params.put("planType", ValidateUtil.isNotEmpty(request.getPlanType()) ? request.getPlanType() : null);
        params.put("planTypes", ValidateUtil.isNotEmpty(request.getPlanTypes()) ? request.getPlanTypes() : null);
        List<GanttPlanResponse> datas = baseMapper.statisticsProJectPlanRanking(params);
        Integer total = baseMapper.statisticsProJectPlanRankingCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse statisticsPlanTotalBypId(String pId) {
        QueryWrapper<GanttPlan> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.isNotNull("Plan_Enable_State");
        wrapper.isNotNull("Plan_Finish_State");
        wrapper.eq("Plan_State", ApprovalStatusEnum.Already_Over.getIndex());
        List<GanttPlan> datas = baseMapper.selectList(wrapper);
        Map<String, Integer> data = new HashMap<>();
        data.put("total", 0);
        data.put("healths", 0);
        data.put("fastOverdue", 0);
        data.put("accomplishSchedule", 0);
        data.put("overdueCompleted", 0);
        data.put("overdue", 0);
        data.put("suspend", 0);
        Date now = new Date();
        if (datas != null && datas.size() > 0) {
            data.put("total", datas.size());
            //暂停的计划
            List<GanttPlan> suspendData = datas.stream().filter(n -> n.getPlanEnableState() == 0).collect(Collectors.toList());
            if (suspendData != null)
                data.put("suspend", suspendData.size());
            //正常未到期
            List<GanttPlan> healths = datas.stream().filter(n -> n.getPlanFinishState() == 0 && n.getPlanEnableState() == 1 && n.getPlanEnd() != null && DateUtil.daysBetween(now, n.getPlanEnd()) >= 0).collect(Collectors.toList());
            if (healths != null) {
                data.put("healths", healths.size());
                //到期前30天内
                Integer fastOverdue = healths.stream().filter(n -> (DateUtil.daysBetween(now, n.getPlanEnd()) <= 30)).collect(Collectors.toList()).size();
                data.put("fastOverdue", fastOverdue);
            }
            //逾期未完成
            Integer overdueCompletion = datas.stream().filter(n -> n.getPlanFinishState() == 0 && DateUtil.daysBetween(n.getPlanEnd(), now) > 0).collect(Collectors.toList()).size();
            //已完成任务
            List<GanttPlan> finished = datas.stream().filter(n -> n.getPlanFinishState() == 1 && n.getPlanEnableState() == 1).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(finished)) {
                //逾期完成任务数量
                Integer overdueCompleted = finished.stream().filter(n -> n.getPlanActualEnd() != null && DateUtil.daysBetween(n.getPlanActualEnd(), n.getPlanEnd()) > 0).collect(Collectors.toList()).size();
                //按时完成
                Integer accomplishSchedule = finished.size() - overdueCompleted;
                data.put("accomplishSchedule", accomplishSchedule);
                data.put("overdueCompleted", overdueCompleted);
                data.put("overdue", overdueCompletion + overdueCompleted);
            } else
                data.put("overdue", overdueCompletion);
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse statisticsPlanTotal(String teId) {
        Integer pTotal = baseMapper.getProjectCount(teId);
        Map<String, Integer> data = new HashMap<>();
        data.put("pTotal", 0);
        data.put("tTotal", 0);
        data.put("completed", 0);
        data.put("uncompleted", 0);
        Date now = new Date();
        if (pTotal != null && pTotal > 0) {
            data.put("pTotal", pTotal);
            Integer tasks = ganttTasksService.getGanttTasksCount(teId);
            if (tasks != null && tasks > 0) {
                data.put("tTotal", tasks);
                Integer completed = ganttTasksService.getGanttTasksCompCount(teId);
                if (tasks != null)
                    data.put("completed", completed);
                data.put("uncompleted", data.get("tTotal") - data.get("completed"));
            }
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getGanttPlanAndTaskBypId(String pId, String type) {
        List<String> types = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(type)) types = Arrays.asList(type.split(","));
        List<GanttPlanResponse> datas = baseMapper.getGanttPlanAndTaskBypId(pId, types);
        List<GanttPlanResponse> data = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            Map<String, Object> tasks = ganttTasksService.getRootGanttTasksBypId(pId, types);
            datas.forEach(model -> {
                if (model.getPlanId() != null)
                    model.setGanttTasks((List) tasks.get(model.getPlanId()));
                data.add(model);
            });
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse GanttPlanStatisticsList(String uId, GanttPlanRequest request) {
        String authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<GanttPlanRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("queryType").put("pId");
        IPage<GanttPlanResponse> page = new Page<>(request.getPage(), request.getSize());
        List<GanttPlanResponse> data = baseMapper.ganttPlanStatisticsList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse ganttPlanMilestoneList(GanttPlanRequest request, String uId) {
        request.setPlanType("Milestone");//里程碑进度计划
        BaseResponse res = getGanttPlanList(request, uId);
        if (ValidateUtil.isNotNull(res.getData())) {
            List<GanttPlanResponse> data = JacksonUtils.parseArray(JacksonUtils.toJSONString(res.getData()), GanttPlanResponse.class);
            List<String> planIds = data.stream().map(GanttPlanResponse::getPlanId).collect(Collectors.toList());
            List<GanttTasksResponse> tasks = ganttTasksService.getAllTaskByPlanIds(planIds);
            List<GantSectionDTO> sections = gantSectionService.selectByPlanIds(planIds);
            data.forEach(d -> {
                d.setGanttTasks(tasks.stream().filter(f -> f.getPlanId().equals(d.getPlanId())).collect(Collectors.toList()));
                d.setGantSections(sections.stream().filter(f -> f.getPlanId().equals(d.getPlanId())).collect(Collectors.toList()));
            });
            res.setData(data);
        }
        return res;
    }

    @Override
    public BaseResponse getPreprojectInfo(String uId, PreProjectPage request) {
        // 拿到岗位编码
        List<String> codes = null;
        String authSql = "WHERE 1=1";
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getType())) {
            DepartmentPCjConfDTO dto = new DepartmentPCjConfDTO();
            codes = new ArrayList<>();
            codes.add(dto.getGldwXmjl());
            codes.add(dto.getXcfzr());
        } else {
            authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
        }
        // 获取临期配置
        ScheduleExpirationTimeDTO scheduleExpirationTime = getScheduleExpirationTime();

        // 设置查询参数
        QueryMap<PreProjectPage> params = new QueryMap<>(request);
        params.put("uId", uId).put("authSql", authSql).put("codes", codes);
        params.put("pName").put("type").put("pCompetentDepartmentNameList").put("pCategory")
                .put("pCategoryList").put("pProjectManagerNameList").put("pEngineerNameList");

        DepartmentPCjConfDTO conf = sysConfigService.getDepartmentPCjConf();
        params.put("ppCode1", conf.getGldwXmjl()).put("ppCode2", conf.getJtJbr());

        // 查询所有符合条件的数据
        Page<PreprojectInfoResponse> page = new Page<>(request.getPage(), request.getSize());
        List<PreprojectInfoResponse> preprojectListInfo = baseMapper.getPreprojectListInfo(page, params.getQuery(), scheduleExpirationTime);

        return BaseResponse.ok(preprojectListInfo, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getPreprojectClassInfo(String uId, PreProjectPage request) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(PreProjectPage.class.getName());
            // 拿到岗位编码
            List<String> codes = null;
            String authSql = "WHERE 1=1";
            if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getType())) {
                DepartmentPCjConfDTO dto = new DepartmentPCjConfDTO();
                codes = new ArrayList<>();
                codes.add(dto.getGldwXmjl());
                codes.add(dto.getXcfzr());
            } else {
                authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
                if (ValidateUtil.isEmpty(authSql)) {
                    return BaseResponse.fail(ResMsg.noPermission);
                }
            }
            // 获取临期配置
            ScheduleExpirationTimeDTO scheduleExpirationTime = getScheduleExpirationTime();

            // 设置查询参数
            QueryMap<PreProjectPage> params = new QueryMap<>(request);
            params.put("uId", uId).put("authSql", authSql).put("codes", codes);
            params.put("pName").put("type").put("pCompetentDepartmentNameList").put("pCategory")
                    .put("pCategoryList").put("pProjectManagerNameList").put("pEngineerNameList");

            DepartmentPCjConfDTO conf = sysConfigService.getDepartmentPCjConf();
            params.put("ppCode1", conf.getGldwXmjl()).put("ppCode2", conf.getJtJbr());

            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            List<Map<String, Object>> rawResult = baseMapper.getPreprojectListInfoValues(selectClause, params.getQuery(), scheduleExpirationTime);

            classInfoAnnotationList = ClassInfoUtils.resultsHandle(rawResult, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getPreprojectClassInfo error: ", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }

        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public Workbook exportPreprojectInfoExcel(String uId, PreProjectPage request) {
        Workbook workbook = new XSSFWorkbook();

        List<String> codes = null;
        String authSql = "WHERE 1=1";
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getType())) {
            DepartmentPCjConfDTO dto = new DepartmentPCjConfDTO();
            codes = new ArrayList<>();
            codes.add(dto.getGldwXmjl());
            codes.add(dto.getXcfzr());
        } else {
            authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
            if (ValidateUtil.isEmpty(authSql)) {
                return workbook;
            }
        }
        // 获取临期配置
        ScheduleExpirationTimeDTO scheduleExpirationTime = getScheduleExpirationTime();

        // 设置查询参数
        QueryMap<PreProjectPage> params = new QueryMap<>(request);
        params.put("uId", uId).put("authSql", authSql).put("codes", codes);
        params.put("pName").put("type").put("pCompetentDepartmentNameList").put("pCategory")
                .put("pCategoryList").put("pProjectManagerNameList").put("pEngineerNameList");

        DepartmentPCjConfDTO conf = sysConfigService.getDepartmentPCjConf();
        params.put("ppCode1", conf.getGldwXmjl()).put("ppCode2", conf.getJtJbr());

        // 查询所有符合条件的数据
        if (request.getPage() == 0) {
            request.setPage(1);
        }
        if (request.getSize() == 0) {
            request.setSize(Integer.MAX_VALUE);
        }
        Page<PreprojectInfoResponse> page = new Page<>(request.getPage(), request.getSize());
        List<PreprojectInfoResponse> dateList = baseMapper.getPreprojectListInfo(page, params.getQuery(), scheduleExpirationTime);

        String[] rowTitle = new String[]{"序号", "项目名称", "项目分类", "项目管理单位", "项目经理", "集团工程部经办人", "临期任务数量", "逾期任务数量", "应完成数量", "逾期率"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("进度计划列表");
        // 宽列的索引
        List<Integer> wideColumns = Arrays.asList(1, 3);
        for (int i = 0; i < rowTitle.length; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 2000);
            } else if (wideColumns.contains(i)) {
                sheet.setColumnWidth(i, 8000);
            } else {
                sheet.setColumnWidth(i, 4000);
            }
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        CellStyle percentageStyle = workbook.createCellStyle();
        percentageStyle.setAlignment(HorizontalAlignment.CENTER);
        percentageStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        percentageStyle.setBorderTop(BorderStyle.MEDIUM);
        percentageStyle.setBorderBottom(BorderStyle.MEDIUM);
        percentageStyle.setBorderLeft(BorderStyle.MEDIUM);
        percentageStyle.setBorderRight(BorderStyle.MEDIUM);
        DataFormat format = workbook.createDataFormat();
        percentageStyle.setDataFormat(format.getFormat("0.00%"));

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }
        if (!CollectionUtils.isEmpty(dateList)) {
            JSONArray NDTZXMFL = DictionaryUtils.dictList("JPMsoft_NDTZXMFL");
            int count = 1;
            for (PreprojectInfoResponse data : dateList) {
                row = sheet.createRow(rowNum++);
                int index = 0;

                cell = row.createCell(index++);
                cell.setCellValue(count++);
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getPName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getPCategory()) ? "/" : DictionaryUtils.processDictList(NDTZXMFL, data.getPCategory()));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getPCompetentDepartmentName()) ? "/" : data.getPCompetentDepartmentName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getPProjectManagerName()) ? "/" : data.getPProjectManagerName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getPEngineerName()) ? "/" : data.getPEngineerName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getLqCount());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getYqCount());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getYwcCount());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getWwcRate() / 100.0);
                cell.setCellStyle(percentageStyle);
            }
        }
        return workbook;
    }

    @Override
    public BaseResponse getTaskImportance(String uId, PreProjectPage page) {
        // 拿到岗位编码
        List<String> codes = null;
        String authSql = "WHERE 1=1";
        if (ValidateUtil.isNull(page) || ValidateUtil.isEmpty(page.getType())) {
            DepartmentPCjConfDTO dto = new DepartmentPCjConfDTO();
            codes = new ArrayList<>();
            codes.add(dto.getGldwXmjl());
            codes.add(dto.getXcfzr());
        } else {
            authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
        }
        // 获取临期配置
        ScheduleExpirationTimeDTO scheduleExpirationTime = getScheduleExpirationTime();
        GanttTaskCjStatisticsDTO importanceInfo = baseMapper.getTaskImportanceInfo(authSql, uId, page, codes, scheduleExpirationTime);
        if (importanceInfo != null) {
            int lcbLq = Optional.of(importanceInfo.getLcbLq()).orElse(0);
            int yjLq = Optional.of(importanceInfo.getYjLq()).orElse(0);
            int ejLq = Optional.of(importanceInfo.getEjLq()).orElse(0);
            importanceInfo.setLqCount(lcbLq + yjLq + ejLq);
        }
        return BaseResponse.ok(importanceInfo);
    }


    @Override
    public BaseResponse getTasksInfo(String uId, TasksInfoRequest request) {
        List<String> codes = new ArrayList<>();
        String authSql = "WHERE 1=1";
        if ((ValidateUtil.isEmpty(request.getPlanId()) && ValidateUtil.isEmpty(request.getPId())) && ValidateUtil.isEmpty(request.getType())) {
            DepartmentPCjConfDTO dto = new DepartmentPCjConfDTO();
            codes.add(dto.getGldwXmjl());
            codes.add(dto.getXcfzr());
        } else if (ValidateUtil.isNotEmpty(request.getType())) {
            authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
        }
        IPage<PreprojectInfoResponse> page = new Page<>(request.getPage(), request.getSize());
        Boolean qType = null;
        if (ValidateUtil.isNotEmpty(request.getQueryType())) qType = request.getQueryType().equals("1");

        QueryMap<TasksInfoRequest> params = new QueryMap<>(request);
        params.put("pId").put("planId").put("pName").put("taskText").put("taskImportance")
                .put("taskType").put("exclusionComCancel").put("exclusionFinish").put("gsIds")
                .put("taskImportances").put("type").put("pCategoryList").put("pCompetentDepartmentNameList")
                .put("pProjectManagerNameList").put("pEngineerNameList").put("pCategory");
        params.put("authSql", authSql).put("uId", uId).put("qType", qType);
        params.put("codes", codes);

        // 获取临期配置
        ScheduleExpirationTimeDTO scheduleExpirationTime = getScheduleExpirationTime();
        List<GanttTasksResponse> dateList = baseMapper.getTaskPIdInfo(page, params.getQuery(), scheduleExpirationTime);
        return BaseResponse.ok(dateList, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getTasksClassInfo(String uId, TasksInfoRequest request) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(TasksInfoRequest.class.getName());
            List<String> codes = new ArrayList<>();
            String authSql = "WHERE 1=1";
            if ((ValidateUtil.isEmpty(request.getPlanId()) && ValidateUtil.isEmpty(request.getPId())) && ValidateUtil.isEmpty(request.getType())) {
                DepartmentPCjConfDTO dto = new DepartmentPCjConfDTO();
                codes.add(dto.getGldwXmjl());
                codes.add(dto.getXcfzr());
            } else if (ValidateUtil.isNotEmpty(request.getType())) {
                authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
                if (ValidateUtil.isEmpty(authSql)) {
                    return BaseResponse.fail(ResMsg.noPermission);
                }
            }
            Boolean qType = null;
            if (ValidateUtil.isNotEmpty(request.getQueryType())) qType = request.getQueryType().equals("1");

            QueryMap<TasksInfoRequest> params = new QueryMap<>(request);
            params.put("pId").put("planId").put("pName").put("taskText").put("taskImportance")
                    .put("taskType").put("exclusionComCancel").put("exclusionFinish").put("gsIds")
                    .put("taskImportances").put("type").put("pCategoryList").put("pCompetentDepartmentNameList")
                    .put("pProjectManagerNameList").put("pEngineerNameList").put("pCategory");
            params.put("authSql", authSql).put("uId", uId).put("qType", qType);
            params.put("codes", codes);

            // 获取临期配置
            ScheduleExpirationTimeDTO scheduleExpirationTime = getScheduleExpirationTime();

            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            List<Map<String, Object>> rawResult = baseMapper.getTasksClassInfoValues(selectClause, params.getQuery(), scheduleExpirationTime);

            classInfoAnnotationList = ClassInfoUtils.resultsHandle(rawResult, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getPreprojectClassInfo error: ", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }

        return BaseResponse.ok(classInfoAnnotationList);
    }

    private Map<String, Map<String, Integer>> statisticsMap(List<TaskImportanceResponse> tasks) {
        // 初始化计数器
        Map<String, Integer> lqCounter = new HashMap<>();
        Map<String, Integer> yqCounter = new HashMap<>();
        lqCounter.put("99", 0);
        lqCounter.put("01", 0);
        lqCounter.put("02", 0);
        yqCounter.put("99", 0);
        yqCounter.put("01", 0);
        yqCounter.put("02", 0);
        for (TaskImportanceResponse response : tasks) {
            if (response.getLqTaskImportance() != null) {
                String[] lqArray = response.getLqTaskImportance().split(",");
                for (String importance : lqArray) {
                    if ("99".equals(importance)) {
                        lqCounter.put("99", lqCounter.getOrDefault("99", 0) + 1);
                    } else if ("01".equals(importance)) {
                        lqCounter.put("01", lqCounter.getOrDefault("01", 0) + 1);
                    } else if ("02".equals(importance)) {
                        lqCounter.put("02", lqCounter.getOrDefault("02", 0) + 1);
                    }
                }
            }
            if (response.getYqTaskImportance() != null) {
                String[] yqArray = response.getYqTaskImportance().split(",");
                for (String importance : yqArray) {
                    if ("99".equals(importance)) {
                        yqCounter.put("99", yqCounter.getOrDefault("99", 0) + 1);
                    } else if ("01".equals(importance)) {
                        yqCounter.put("01", yqCounter.getOrDefault("01", 0) + 1);
                    } else if ("02".equals(importance)) {
                        yqCounter.put("02", yqCounter.getOrDefault("02", 0) + 1);
                    }
                }
            }
        }
        // 计算总和
        lqCounter.put("AdventSum", lqCounter.get("99") + lqCounter.get("01") + lqCounter.get("02"));
        yqCounter.put("OverdueSum", yqCounter.get("99") + yqCounter.get("01") + yqCounter.get("02"));
        Map<String, Map<String, Integer>> map = new HashMap<>();
        map.put("Advent", lqCounter);
        map.put("Overdue", yqCounter);
        return map;
    }

    @Override
    public Workbook exportTasksOverdueDueInfoExcel(String uId, TasksInfoRequest request) {
        Workbook workbook = new XSSFWorkbook();

        List<String> codes = new ArrayList<>();
        String authSql = "WHERE 1=1";
        if ((ValidateUtil.isEmpty(request.getPlanId()) && ValidateUtil.isEmpty(request.getPId())) && ValidateUtil.isEmpty(request.getType())) {
            DepartmentPCjConfDTO dto = new DepartmentPCjConfDTO();
            codes.add(dto.getGldwXmjl());
            codes.add(dto.getXcfzr());
        } else if (ValidateUtil.isNotEmpty(request.getType())) {
            authSql = sysUserService.getAuthSql("Gantt_Plan", uId, "Plan_Create_Uid", OutUserAuthEnum.Organization.getIndex());
            if (ValidateUtil.isEmpty(authSql)) {
                return workbook;
            }
        }
        if (request.getPage() == 0) {
            request.setPage(1);
        }
        if (request.getSize() == 0) {
            request.setSize(Integer.MAX_VALUE);
        }
        IPage<PreprojectInfoResponse> page = new Page<>(request.getPage(), request.getSize());
        Boolean qType = null;
        if (ValidateUtil.isNotEmpty(request.getQueryType())) qType = request.getQueryType().equals("1");

        QueryMap<TasksInfoRequest> params = new QueryMap<>(request);
        params.put("pId").put("planId").put("pName").put("taskText").put("taskImportance")
                .put("taskType").put("exclusionComCancel").put("exclusionFinish").put("gsIds")
                .put("taskImportances").put("type").put("pCategoryList").put("pCompetentDepartmentNameList")
                .put("pProjectManagerNameList").put("pEngineerNameList").put("pCategory");
        params.put("authSql", authSql).put("uId", uId).put("qType", qType);
        params.put("codes", codes);

        // 获取临期配置
        ScheduleExpirationTimeDTO scheduleExpirationTime = getScheduleExpirationTime();
        List<GanttTasksResponse> dateList = baseMapper.getTaskPIdInfo(page, params.getQuery(), scheduleExpirationTime);

        String[] rowTitle = new String[]{"序号", "项目名称", "项目分类", "项目管理单位", "项目经理", "集团工程部经办人", "节点名称", "任务标段", "任务分级", "任务分类", "计划开始时间", "计划完成时间", "实际开始时间", "实际完成时间"};

        // 创建一个工作表(sheet)
        Sheet sheet = workbook.createSheet("逾期临期任务");
        // 宽列的索引
        List<Integer> wideColumns = Arrays.asList(1, 3, 6, 7);
        for (int i = 0; i < rowTitle.length; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 2000);
            } else if (wideColumns.contains(i)) {
                sheet.setColumnWidth(i, 7000);
            } else {
                sheet.setColumnWidth(i, 4000);
            }
        }

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        CellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        dateStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        dateStyle.setBorderTop(BorderStyle.MEDIUM);
        dateStyle.setBorderBottom(BorderStyle.MEDIUM);
        dateStyle.setBorderLeft(BorderStyle.MEDIUM);
        dateStyle.setBorderRight(BorderStyle.MEDIUM);
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat(DateFormatUtil.FORMAT_PATTERN_OF_DAY_1));

        int rowNum = 0;
        Row row = sheet.createRow(rowNum);
        Cell cell;
        rowNum++;
        for (int i = 0; i < rowTitle.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(rowTitle[i]);
            cell.setCellStyle(style);
        }
        if (!CollectionUtils.isEmpty(dateList)) {
            JSONArray JDZYX = DictionaryUtils.dictList("JPMsoft_JDZYX");
            JSONArray JDFL = DictionaryUtils.dictList("JPMsoft_JDFL");
            JSONArray NDTZXMFL = DictionaryUtils.dictList("JPMsoft_NDTZXMFL");
            int count = 1;
            for (GanttTasksResponse data : dateList) {
                row = sheet.createRow(rowNum++);
                int index = 0;

                cell = row.createCell(index++);
                cell.setCellValue(count++);
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(data.getPName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getpCategory()) ? "/" : DictionaryUtils.processDictList(NDTZXMFL, data.getpCategory()));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getpCompetentDepartmentName()) ? "/" : data.getpCompetentDepartmentName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getpProjectManagerName()) ? "/" : data.getpProjectManagerName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getpEngineerName()) ? "/" : data.getpEngineerName());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getTaskText()) ? "/" : data.getTaskText());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getGsSection()) ? "全标段" : data.getGsSection());
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getTaskImportance()) ? "/" : DictionaryUtils.processDictList(JDZYX, data.getTaskImportance()));
                cell.setCellStyle(style);

                cell = row.createCell(index++);
                cell.setCellValue(ValidateUtil.isEmpty(data.getTaskType()) ? "/" : DictionaryUtils.processDictList(JDFL, data.getTaskType()));
                cell.setCellStyle(style);

                if (data.getTaskStartDate() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data.getTaskStartDate());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    cell = row.createCell(index++);
                    cell.setCellValue(cal.getTime());
                    cell.setCellStyle(dateStyle);
                } else {
                    cell = row.createCell(index++);
                    cell.setCellValue("/");
                    cell.setCellStyle(dateStyle);
                }

                if (data.getTaskEndDate() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data.getTaskEndDate());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    cell = row.createCell(index++);
                    cell.setCellValue(cal.getTime());
                    cell.setCellStyle(dateStyle);
                } else {
                    cell = row.createCell(index++);
                    cell.setCellValue("/");
                    cell.setCellStyle(dateStyle);
                }

                if (data.getTaskBeginTime() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data.getTaskBeginTime());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    cell = row.createCell(index++);
                    cell.setCellValue(cal.getTime());
                    cell.setCellStyle(dateStyle);
                } else {
                    cell = row.createCell(index++);
                    cell.setCellValue("/");
                    cell.setCellStyle(dateStyle);
                }

                if (data.getTaskFinishTime() != null) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data.getTaskFinishTime());
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    cell = row.createCell(index++);
                    cell.setCellValue(cal.getTime());
                    cell.setCellStyle(dateStyle);
                } else {
                    cell = row.createCell(index++);
                    cell.setCellValue("/");
                    cell.setCellStyle(dateStyle);
                }
            }
        }
        return workbook;
    }

    public BaseResponse approved2GenerateProjectCplTemp(String planId) {
        GanttPlan plan = baseMapper.selectById(planId);
        if (!Objects.equals(plan.getPlanState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        projectCplTemplateService.approved2GenerateProjectCplTemp(plan, ganttTasksService.getCplGanttTasksByPlanId(planId,"01"));
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse refreshProjectCplTempAntProjectCp(String planId) {
        GanttPlan plan = baseMapper.selectById(planId);
        if (plan == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(plan.getPlanState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        projectCplTemplateService.refreshProjectCplTempAntProjectCp(plan, ganttTasksService.getCplGanttTasksByPlanId(planId,"01"));
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteUnStartGanttPlan(String planId) {
        GanttPlan gp = baseMapper.selectById(planId);
        if (gp == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(gp.getPlanState(), ApprovalStatusEnum.Not_Started.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        ganttLinksService.deleteGanttLinksByPlanId(planId);
        gantSectionService.deleteByPlanId(planId);
        ganttTasksService.deleteTasksByPlanId(planId);
        baseMapper.deleteById(planId);
        sysExtendValueService.deleteByFileId(planId);
        sysFileService.deleteFileById(planId);
        return BaseResponse.ok();
    }


    public List<GanttPlan> getAllGanttPlan() {
        QueryWrapper<GanttPlan> wrapper = new QueryWrapper<>();
        return baseMapper.selectList(wrapper);
    }

    /**
     * 项目里程碑进度计划前置校验
     * 单个项目有且仅有一个为作废的里程碑计划
     *
     * @param request
     */
    private void projectOnlyOneMilestonePlan(GanttPlanRequest request) {
        if (ValidateUtil.isAnObjEmpty(request.getpId(), request.getPlanType())) return;
        //里程碑类型编码为固定值Milestone
        if (request.getPlanType().equalsIgnoreCase("Milestone")) {
            QueryWrapper<GanttPlan> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Id", request.getpId());
            wrapper.eq("Plan_Type", request.getPlanType());
            wrapper.ne("Plan_State", ApprovalStatusEnum.Invalid.getIndex());
            if (baseMapper.exists(wrapper)) throw new JPMException(ResMsg.onlyOneMilestonePlan);
        }
    }

    /**
     * 获取进度执行临期时间配置
     *
     * @return
     */
    private ScheduleExpirationTimeDTO getScheduleExpirationTime() {
        String scheduleExpirationTime = sysConfigService.getConfigByCode("scheduleExpirationTime");
        int milestone = 5;
        int firstLevel = 5;
        int secondLevel = 5;
        if (StrUtil.isNotBlank(scheduleExpirationTime)) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(scheduleExpirationTime);
                JsonNode milestoneNode = jsonNode.get("milestone");
                JsonNode firstLevelNode = jsonNode.get("firstLevel");
                JsonNode secondLevelNode = jsonNode.get("secondLevel");
                if (milestoneNode != null && milestoneNode.isInt()) {
                    milestone = milestoneNode.asInt();
                } else {
                    log.warn("milestone 配置缺失或无效，使用默认值: {}", milestone);
                }
                if (firstLevelNode != null && firstLevelNode.isInt()) {
                    firstLevel = firstLevelNode.asInt();
                } else {
                    log.warn("firstLevel 配置缺失或无效，使用默认值: {}", firstLevel);
                }
                if (secondLevelNode != null && secondLevelNode.isInt()) {
                    secondLevel = secondLevelNode.asInt();
                } else {
                    log.warn("secondLevel 配置缺失或无效，使用默认值: {}", secondLevel);
                }
            } catch (JsonProcessingException e) {
                log.error("解析JSON字符串失败", e);
            }
        }
        return new ScheduleExpirationTimeDTO(milestone, firstLevel, secondLevel);
    }

    @Override
    public BaseResponse getGanttPlanLedgerNJCJ(GanttPlanRequest request) {
        request.setStateList(Arrays.asList(0, 1, 2, 4, 5, 6, 7, 8, 9));
        QueryMap<GanttPlanRequest> params = new QueryMap<>(request);
        params.put("authSql", " where 1=1 ").put("stateList").put("pName").put("pCompetentDepartment");
        IPage<GanttPlanResponse> page = new Page<>(request.getPage(), request.getSize());
        List<GanttPlanResponse> datas = baseMapper.getGanttPlanLedgerNJCJ(page, params.getQuery());
        if (ValidateUtil.isEmpty(datas)) {
            Map<String, Object> resultMap = new HashMap<>(8);
            resultMap.put("data", datas);
            resultMap.put("alreadyOver", 0);
            resultMap.put("underApproval", 0);
            resultMap.put("notStart", 0);
            resultMap.put("milestoneCount", 0);
            resultMap.put("firstLevelCount", 0);
            resultMap.put("secondLevelCount", 0);
            return BaseResponse.ok(resultMap, Math.toIntExact(page.getTotal()));
        }
        Map<String, Object> stats = baseMapper.getGanttPlanLedgerNJCJStats(params.getQuery());
        Map<String, Object> resultMap = new HashMap<>(7);
        resultMap.put("data", datas);
        resultMap.put("alreadyOver", stats.getOrDefault("alreadyOver", 0));
        resultMap.put("underApproval", stats.getOrDefault("underApproval", 0));
        resultMap.put("notStart", stats.getOrDefault("notStart", 0));
        resultMap.put("milestoneCount", stats.getOrDefault("milestoneCount", 0));
        resultMap.put("firstLevelCount", stats.getOrDefault("firstLevelCount", 0));
        resultMap.put("secondLevelCount", stats.getOrDefault("secondLevelCount", 0));
        return BaseResponse.ok(resultMap, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getPreprojectInfoLedgerNJCJ(GanttPlanRequest request) {
        request.setStateList(Collections.singletonList(
                ApprovalStatusEnum.Already_Over.getIndex()
        ));
        QueryMap<GanttPlanRequest> params = new QueryMap<>(request);
        params.put("authSql", " where 1=1 ").put("stateList").put("pName").put("pCompetentDepartment");
        IPage<GanttPlanResponse> page = new Page<>(request.getPage(), request.getSize());
        List<GanttPlanResponse> datas = baseMapper.getPreprojectInfoLedgerNJCJ(page, params.getQuery());
        if (ValidateUtil.isEmpty(datas)) {
            Map<String, Object> resultMap = new HashMap<>(7);
            resultMap.put("data", datas);
            resultMap.put("alreadyOver", 0);
            resultMap.put("underApproval", 0);
            resultMap.put("mustHandle", 0);
            resultMap.put("milestoneCount", 0);
            resultMap.put("firstLevelCount", 0);
            resultMap.put("secondLevelCount", 0);
            return BaseResponse.ok(resultMap, Math.toIntExact(page.getTotal()));
        }
        Map<String, Object> stats = baseMapper.getPreprojectInfoLedgerNJCJStats(params.getQuery());
        Map<String, Object> resultMap = new HashMap<>(7);
        resultMap.put("data", datas);
        resultMap.put("alreadyOver", stats.getOrDefault("alreadyOver", 0));
        resultMap.put("underApproval", stats.getOrDefault("underApproval", 0));
        resultMap.put("mustHandle", stats.getOrDefault("mustHandle", 0));
        resultMap.put("milestoneCount", stats.getOrDefault("milestoneCount", 0));
        resultMap.put("firstLevelCount", stats.getOrDefault("firstLevelCount", 0));
        resultMap.put("secondLevelCount", stats.getOrDefault("secondLevelCount", 0));
        return BaseResponse.ok(resultMap, Math.toIntExact(page.getTotal()));
    }
}
