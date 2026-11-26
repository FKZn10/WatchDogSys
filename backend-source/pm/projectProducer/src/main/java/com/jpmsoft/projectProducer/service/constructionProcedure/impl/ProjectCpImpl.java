package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import cn.hutool.core.bean.BeanUtil;
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
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.common.model.request.proCp.ProjectCpRequest;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.UserProjectCpResponse;
import com.jpmsoft.common.model.response.proCp.ProjectCpDtlResponse;
import com.jpmsoft.common.model.response.proCp.ProjectCpResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.*;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasks;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.vo.constructionProcedure.ProjectCpAndDtlVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.constructionProcedure.*;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectCpImpl extends ServiceImpl<ProjectCpMapper, ProjectCp> implements IProjectCpService {

    @Resource
    private IProjectCpDtlService projectCpDtlService;
    @Resource
    private IProjectCpExperienceService projectCpExperienceService;
    @Resource
    private IProjectCpWorkRecordService projectCpWorkRecordService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IProjectCpLibraryService projectCpLibraryService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IGanttTasksService ganttTasksService;
    @Resource
    private IGanttPlanService ganttPlanService;
    @Resource
    private IProjectCpConfigRelationService projectCpConfigRelationService;
    @Resource
    private IProjectCplTemplateDtlService projectCplTemplateDtlService;

    @Override
    public ProjectCp selectByCpdId(String cpdId) {
        return baseMapper.selectByCpdId(cpdId);
    }

    @Override
    public BaseResponse projectApprove2generateCp(String pid, LogUserInfoVo user) {
        PreProject project = preProjectService.getById(pid);
        if (project == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(project.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        QueryWrapper<ProjectCp> wrapper = new QueryWrapper<>();
        wrapper.eq("p_id", pid);
        wrapper.ne("state", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.exists(wrapper)) return BaseResponse.fail(ResMsg.projectCpReset);
        FileTemplateTree temp = userProducerFeign.getFileTemplateByCode("ProjectProcedureFlow");
        if (temp == null) return BaseResponse.fail(String.format(ResMsg.notFoundFileTmp, "ProjectProcedureFlow"));
        List<ProjectCpLibrary> libraries = projectCpLibraryService.getAllList(user.getTeId());
        if (!CollectionUtils.isEmpty(libraries)) {
            ProjectCp cp = new ProjectCp();
            cp.setCpId(GenerateUUID.getUUID());
            cp.setpId(pid);
            cp.setCpCreateTime(new Date());
            cp.setCpCreateUid(user.getId());
            cp.setCpCreateUname(user.getName());
            cp.setTeId(user.getTeId());
            cp.setState(ApprovalStatusEnum.Already_Over.getIndex());
            cp.setStateTime(new Date());
            baseMapper.insert(cp);
            projectCpDtlService.saveBatch(projectCpDtlService.generateProjectCpWithUnCpProject(libraries, cp.getCpId()));
            sysFileService.save(sysFileService.structureFile(cp.getCpId(), temp.gettId(), cp.getState() + "", cp.getCpCreateUid(), cp.getCpCreateUname()
                    , cp.getCpCreateTime(), cp.getpId(), temp.gettName(), temp.gettCode(),
                    GenerateUUID.getUUID(), null, temp.gettCode()));
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse generateProjectCpWithUnCpProject(LogUserInfoVo user) {
        List<String> pids = baseMapper.getUnCpProjectIds();
        if (!CollectionUtils.isEmpty(pids)) {
            FileTemplateTree temp = userProducerFeign.getFileTemplateByCode("ProjectProcedureFlow");
            if (temp == null) return BaseResponse.fail(String.format(ResMsg.notFoundFileTmp, "ProjectProcedureFlow"));
            List<ProjectCpLibrary> libraries = projectCpLibraryService.getAllList(user.getTeId());
            if (!CollectionUtils.isEmpty(libraries)) {
                List<ProjectCp> projectCps = new ArrayList<>();
                List<ProjectCpDtl> projectCpDtls = new ArrayList<>();
                List<SysFile> files = new ArrayList<>();
                Date now = new Date();
                for (String pid : pids) {
                    ProjectCp cp = new ProjectCp();
                    cp.setCpId(GenerateUUID.getUUID());
                    cp.setpId(pid);
                    cp.setCpCreateTime(now);
                    cp.setCpCreateUid(user.getId());
                    cp.setCpCreateUname(user.getName());
                    cp.setState(ApprovalStatusEnum.Already_Over.getIndex());
                    cp.setTeId(user.getTeId());
                    cp.setStateTime(now);
                    projectCps.add(cp);
                    projectCpDtls.addAll(projectCpDtlService.generateProjectCpWithUnCpProject(libraries, cp.getCpId()));
                    files.add(sysFileService.structureFile(cp.getCpId(), temp.gettId(), cp.getState() + "", cp.getCpCreateUid(), cp.getCpCreateUname()
                            , cp.getCpCreateTime(), cp.getpId(), temp.gettName(), temp.gettCode(),
                            GenerateUUID.getUUID(), null, temp.gettCode()));
                }
                if (!CollectionUtils.isEmpty(projectCps)) saveBatch(projectCps);
                if (!CollectionUtils.isEmpty(projectCpDtls)) projectCpDtlService.saveBatch(projectCpDtls, 100);
                if (!CollectionUtils.isEmpty(files)) sysFileService.saveBatch(files);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProjectCp(ProjectCpRequest request) {
        if (ValidateUtil.isEmpty(request.getpId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        ProjectCp entity = new ProjectCp();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getCpId())) {
            QueryWrapper<ProjectCp> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Id", request.getpId());
            wrapper.ne("state", ApprovalStatusEnum.Invalid.getIndex());
            if (baseMapper.selectCount(wrapper) > 0)
                return BaseResponse.fail(ResMsg.recordUnFinish);
            entity.setCpId(GenerateUUID.getUUID());
            entity.setCpCreateTime(new Date());
            baseMapper.insert(entity);
            projectCpDtlService.createOrUpdateProCpDtl(request.getProCpls(), entity.getCpId());
            sysFileService.saveFile(entity.getCpId(), request.gettId(), request.getState() + "", entity.getCpCreateUid(), entity.getCpCreateUname()
                    , entity.getCpCreateTime(), entity.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getCpId());
            return BaseResponse.ok(entity.getCpId());
        } else {
            ProjectCp model = baseMapper.selectById(entity.getCpId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCpCreateTime(model.getCpCreateTime());
            entity.setCpCreateUid(model.getCpCreateUid());
            entity.setCpCreateUname(model.getCpCreateUname());
            entity.setpId(model.getpId());
            entity.setState(model.getState());
            entity.setStateTime(model.getStateTime());
            baseMapper.updateById(entity);
            projectCpDtlService.createOrUpdateProCpDtl(request.getProCpls(), entity.getCpId());
            sysFileService.saveFile(entity.getCpId(), request.gettId(), entity.getState() + "", entity.getCpCreateUid(), entity.getCpCreateUname()
                    , entity.getCpCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getCpId());
            return BaseResponse.ok(entity.getCpId());
        }
    }

    @Override
    public BaseResponse getProCpList(ProjectCpRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Cp_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ProjectCpRequest> params = getQueryMap(request, authSql);
        IPage<ProjectCpResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpResponse> datas = baseMapper.getProCpList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProCpByCpId(String cpId) {
        ProjectCp entity = baseMapper.selectById(cpId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (projectCpDtlService.getProCpDtlCountByCpId(cpId) > 0)
            return BaseResponse.fail(ResMsg.proCpProcess);
        if (projectCpExperienceService.getCpExperienceCountByCpdId(cpId) > 0 ||
                projectCpWorkRecordService.getCpWorkRecordCountByCpdId(cpId) > 0)
            return BaseResponse.fail(ResMsg.delAssociated);
        projectCpDtlService.deleteProCpDtlByCpId(cpId);
        baseMapper.deleteById(cpId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse statisticalProCp(ProjectCpRequest request) {
        QueryMap<ProjectCpRequest> params = getQueryMap(request, null);
        IPage<ProjectCpResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpResponse> datas = baseMapper.getProCpList(page, params.getQuery());
        List<String> allCpId = new ArrayList<>();
        List<ProjectCpResponse> newData = new ArrayList<>();
        datas.forEach(data -> {
            allCpId.add(data.getCpId());
        });
        List<ProjectCpDtl> dtls = projectCpDtlService.getProCpDtlByCpIds(allCpId);
        if (dtls != null && dtls.size() > 0) {
            datas.forEach(data -> {
                List<ProjectCpDtl> cpDtls = dtls.stream().filter(n -> n.getCpId().equals(data.getCpId())).collect(Collectors.toList());
                data.setCpDtlCount(cpDtls.size());
                List<ProjectCpDtl> completed = cpDtls.stream().filter(n -> Objects.equals(n.getCpdState(), ApprovalStatusEnum.Already_Over.getIndex())).collect(Collectors.toList());
                data.setCompletedCount(completed.size());
                if (!CollectionUtils.isEmpty(completed)) {
                    ProjectCpDtl newCpDtl = completed.stream().sorted(Comparator.comparing(ProjectCpDtl::getCpdCreateTime, Comparator.nullsFirst(Date::compareTo).reversed())).findFirst().get();
                    data.setCurrentNode(newCpDtl.getCpdName());
                    data.setCurrentNodeId(newCpDtl.getCpdId());
                }
                data.setUnfinishedCount(data.getCpDtlCount() - data.getCompletedCount());
                Date now = new Date();
                //超市未完成 实际完成时间为空则比较计划完成时间和当前时间  不为空则和计划完成时间对比
                List<ProjectCpDtl> overTime = cpDtls.stream().filter(n -> n.getCpdState() != ApprovalStatusEnum.Already_Over.getIndex() && ((n.getCpdEndTime() == null && n.getCpdPlanEndTime() != null && n.getCpdPlanEndTime().getTime() < now.getTime()) || (n.getCpdEndTime() != null && n.getCpdPlanEndTime() != null && n.getCpdEndTime().getTime() > n.getCpdPlanEndTime().getTime()))).collect(Collectors.toList());
                data.setOverTimeCount(overTime.size());
                Integer totalDays = cpDtls.stream().filter(n -> n.getCpdDays() != null).mapToInt(ProjectCpDtl::getCpdDays).sum();
                data.setTotalDays(totalDays);
                newData.add(data);
            });
        } else {
            return BaseResponse.ok(datas, (int) page.getTotal());
        }
        return BaseResponse.ok(newData, (int) page.getTotal());
    }

    @Override
    public BaseResponse statisticalUserProCp(ProjectCpRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortSideFiled()) ? "userCpCount" : request.getSortSideFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "DESC" : request.getSortType());
        params.put("start", !JudgeUtil.checkObject(request.getPageStart()) ? request.getPageStart() : 0);
//        params.put("size", !JudgeUtil.checkObject(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);
        params.put("uName", ValidateUtil.isEmpty(request.getuName()) ? null : request.getuName());
        //params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
//        List<UserProjectCpResponse> datas = baseMapper.statisticalUserProCp(params);
        Page<UserProjectCpResponse> datas = baseMapper.statisticalUserProCpPage(new Page<>(request.getPage(), request.getSize()), params);

//        Integer total = baseMapper.statisticalUserProCpCount(params);
        return BaseResponse.ok(datas.getRecords(), (int) datas.getTotal());
    }

    @Override
    public Boolean getProjectCpBycpltId(String cpltId) {
        QueryWrapper<ProjectCp> wrapper = new QueryWrapper<>();
        wrapper.eq("Cplt_Id", cpltId);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        else
            return false;
    }

    @Override
    public BaseResponse getCpIdByPid(String pId) {
        try {
            QueryWrapper<ProjectCp> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Id", pId);
            ProjectCp pojo = baseMapper.selectOne(wrapper);
            if (pojo != null)
                return BaseResponse.ok(pojo.getCpId());
            else
                return BaseResponse.ok(null);
        } catch (Exception ex) {
            return BaseResponse.fail(ResMsg.actError);
        }
    }

    @Override
    public BaseResponse getProjectCpByCpId(String cpId) {
        ProjectCpResponse data = baseMapper.getProjectCpByCpId(cpId);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getProCpAndDtlList(ProjectCpRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Cp_Create_Uid", OutUserAuthEnum.NOT.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ProjectCpRequest> params = getQueryMap(request, authSql);
        IPage<ProjectCpResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpResponse> models = baseMapper.getProCpList(page, params.getQuery());
        List<ProjectCpAndDtlVo> datas = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(models)) {
            List<String> cpIds = models.stream().map(ProjectCpResponse::getCpId).collect(Collectors.toList());
            List<ProjectCpDtlResponse> cpDtls = projectCpDtlService.getProjectCpDtlByCpIds(cpIds);
            models.forEach(model -> {
                ProjectCpAndDtlVo vo = new ProjectCpAndDtlVo();
                BeanUtils.copyProperties(model, vo);
                List<ProjectCpDtlResponse> cpDtl = cpDtls.stream().filter(n -> n.getCpId().equals(model.getCpId())).collect(Collectors.toList());
                vo.setProCpDtls(cpDtl);
                datas.add(vo);
            });
        }
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public ProjectCp selectByPid(String pId) {
        LambdaQueryWrapper<ProjectCp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectCp::getpId, pId);
        wrapper.ne(ProjectCp::getState, ApprovalStatusEnum.Invalid.getIndex());
        List<ProjectCp> cp = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(cp)) return cp.stream().findFirst().get();
        return null;
    }


    private QueryMap<ProjectCpRequest> getQueryMap(ProjectCpRequest request, String authSql) {
        QueryMap<ProjectCpRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("state").put("pCode").put("pType").put("proState")
                .put("pRegion").put("pProjectStage").put("pConstructionUnit").put("pCompetentDepartment")
                .put("pBuildModel").put("pImpCompany").put("pUseUnit").put("pId");
        return params;
    }

    /**
     * 根据Cpl_Id和Plan_Id查询是否需要多次办理
     *
     * @param cpdCplId
     * @param pId
     * @return
     */
    @Override
    public BaseResponse getTaskIsManyByCplIdPlanId(String cpdCplId, String pId) {
        // 获取planId
        LambdaQueryWrapper<GanttPlan> wrapperPlan = new LambdaQueryWrapper<>();
        wrapperPlan.eq(GanttPlan::getPId, pId)
                .ne(GanttPlan::getPlanState, ApprovalStatusEnum.Invalid.getIndex())
                .orderByDesc(GanttPlan::getPlanCreateTime)
                .last("limit 1");
        GanttPlan ganttPlan = ganttPlanService.getOne(wrapperPlan);
        if (ganttPlan == null) {
            return BaseResponse.ok("0");
        }
        String planId = ganttPlan.getPlanId();

        LambdaQueryWrapper<GanttTasks> wrapperTask = new LambdaQueryWrapper<>();
        wrapperTask.eq(GanttTasks::getCplId, cpdCplId);
        wrapperTask.eq(GanttTasks::getPlanId, planId);
        wrapperTask.orderByDesc(GanttTasks::getTaskSort);
        wrapperTask.last("limit 1");
        GanttTasks tasks = ganttTasksService.getOne(wrapperTask);
        if (tasks != null && tasks.getTaskIsMany()) {
            return BaseResponse.ok("1");
        } else {
            return BaseResponse.ok("0");
        }
    }

    @Override
    public BaseResponse getProCpLedgerNJCJ(ProjectCpRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Integer count = baseMapper.getProCpLedgerNJCJCount(request);
        if (count == 0) {
            Map<String, Object> result = new HashMap<>(6);
            result.put("data", Collections.emptyList());
            result.put("alreadyOver", 0);
            result.put("underApproval", 0);
            result.put("milestoneCount", 0);
            result.put("firstLevelCount", 0);
            result.put("secondLevelCount", 0);
            return BaseResponse.ok(result, count);
        }
        Page<ProjectCpRequest> page = new Page<>(request.getPage(), request.getSize(), false);
        List<ProjectCpResponse> datas = baseMapper.getProCpLedgerNJCJ(page, request);
        Map<String, Object> stats = baseMapper.getProCpLedgerNJCJStats(request);
        Map<String, Object> result = new HashMap<>(6);
        result.put("data", datas);
        result.put("alreadyOver",   stats.getOrDefault("alreadyOver", 0));
        result.put("underApproval", stats.getOrDefault("underApproval", 0));
        result.put("milestoneCount", stats.getOrDefault("milestoneCount", 0));
        result.put("firstLevelCount", stats.getOrDefault("firstLevelCount", 0));
        result.put("secondLevelCount", stats.getOrDefault("secondLevelCount", 0));
        return BaseResponse.ok(result, count);
    }

    /**
     * 根据项目id和项目建设配置生成项目配置
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse generateProjectCpWithPid(ProjectCpRequest request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getpId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 查询
        PreProject project = preProjectService.getById(request.getpId());
        if (ValidateUtil.isNull(project)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(project.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        ProjectCpConfigRelation configRelation = projectCpConfigRelationService.getOne(Wrappers.<ProjectCpConfigRelation>lambdaQuery()
                .eq(ProjectCpConfigRelation::getPType, request.getpType())
                .last(" limit 1 ")
        );
        if (ValidateUtil.isNull(configRelation)) {
            return BaseResponse.fail("缺少项目建设程序模板配置！");
        }
        List<ProjectCplTemplateDtl> templateDtls = projectCplTemplateDtlService.list(Wrappers.<ProjectCplTemplateDtl>lambdaQuery()
                .eq(ProjectCplTemplateDtl::getCpltId, configRelation.getCpltId())
        );
        List<String> cplIds = templateDtls.stream().map(ProjectCplTemplateDtl::getCpltdCplId).collect(Collectors.toList());
        List<ProjectCpLibrary> libraries = projectCpLibraryService.listByIds(cplIds);
        List<ProjectCpLibraryRequset> proCpls = BeanUtil.copyToList(libraries, ProjectCpLibraryRequset.class);
        request.setProCpls(proCpls);
        request.setCpltId(configRelation.getCpltId());
        // 创建
        this.createOrUpdateProjectCp(request);
        return BaseResponse.ok();
    }
}
