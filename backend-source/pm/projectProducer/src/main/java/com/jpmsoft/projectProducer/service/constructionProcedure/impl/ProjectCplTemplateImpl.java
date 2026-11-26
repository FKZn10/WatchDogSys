package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.proCp.ProCplTemplateRequest;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.proCp.ProjectCpResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpMapper;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCplTemplateMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.UserPositionPDTO_CJ;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.*;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasks;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.rabbitMQ.Sender;
import com.jpmsoft.projectProducer.service.constructionProcedure.*;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectCplTemplateImpl extends ServiceImpl<ProjectCplTemplateMapper, ProjectCplTemplate> implements IProjectCplTemplateService {

    @Resource
    private IProjectCplTemplateDtlService projectCplTemplateDtlService;
    @Resource
    private IProjectCpLibraryService projectCpLibraryService;

    @Resource
    private IProjectCpService projectCpService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private IProjectCpDtlService projectCpDtlService;

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private IGanttPlanService ganttPlanService;
    @Resource
    private ProjectCpMapper projectCpMapper;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private IProjectCpRprService projectCpRprService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private Sender sender;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProCplTemplate(ProCplTemplateRequest request) {
        ProjectCplTemplate entity = new ProjectCplTemplate();
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<ProjectCplTemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("Cplt_Name", request.getCpltName());
        if (!ValidateUtil.isEmpty(entity.getCpltId()))
            wrapper.ne("Cplt_Id", request.getCpltId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.cpltNameExists);
        if (ValidateUtil.isEmpty(entity.getCpltId())) {
            entity.setCpltId(GenerateUUID.getUUID());
            entity.setCpltCreateTime(new Date());
            baseMapper.insert(entity);
            projectCplTemplateDtlService.createOrUpdateProCplTemplateDtl(request.getProCpLibrarys(), entity.getCpltId());
            return BaseResponse.ok();
        } else {
            ProjectCplTemplate model = baseMapper.selectById(entity.getCpltId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCpltCreateTime(model.getCpltCreateTime());
            entity.setCpltCreateUid(model.getCpltCreateUid());
            entity.setCpltCreateUname(model.getCpltCreateUname());
            entity.setCpltEditTime(new Date());
            baseMapper.updateById(entity);
            projectCplTemplateDtlService.createOrUpdateProCplTemplateDtl(request.getProCpLibrarys(), entity.getCpltId());
            return BaseResponse.ok();
        }
    }

    @Override
    public BaseResponse getProCplTemplateList(ProCplTemplateRequest request) {
        QueryWrapper<ProjectCplTemplate> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (!ValidateUtil.isEmpty(request.getCpltPtype()))
            wrapper.eq("Cplt_Ptype", request.getCpltPtype());
        if (!ValidateUtil.isEmpty(request.getCpltName()))
            wrapper.like("Cplt_Name", request.getCpltName());
        if (!ValidateUtil.isAnObjEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("Cplt_Create_Time");
        IPage<ProjectCplTemplate> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProCplTemplateById(String cpltId) {
        ProjectCplTemplate model = baseMapper.selectById(cpltId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (projectCpService.getProjectCpBycpltId(cpltId))
            return BaseResponse.fail(ResMsg.delAssociated);
        projectCplTemplateDtlService.deleteProCplTemplateDtlById(cpltId);
        baseMapper.deleteById(cpltId);
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approved2GenerateProjectCplTemp(GanttPlan plan, List<GanttTasks> tasksList) {
        LambdaQueryWrapper<GanttPlan> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(GanttPlan::getPId, plan.getPlanId());
        wrapper.ne(GanttPlan::getPlanState, ApprovalStatusEnum.Invalid.getIndex());
        if (!CollectionUtils.isEmpty(ganttPlanService.list(wrapper))) throw new JPMException("该项目已存在建设程序!");
        //存储所有需要生成的建设程序库数据
        List<ProjectCpLibrary> libraryTrees = new ArrayList<>();
        //region 生成建设程序模板数据
        ProjectCplTemplate template = new ProjectCplTemplate();
        template.setCpltId(plan.getPlanId());
        template.setCpltName(plan.getPlanName());
        template.setTeId(plan.getTeId());
        template.setCpltCreateUid(plan.getPlanCreateUid());
        template.setCpltCreateUname(plan.getPlanCreateUname());
        template.setCpltCreateTime(new Date());
        template.setCpltEditTime(template.getCpltCreateTime());
        if (ValidateUtil.isNotNull(baseMapper.selectById(plan.getPlanId())))
            baseMapper.updateById(template);
        else
            baseMapper.insert(template);
        if (!CollectionUtils.isEmpty(tasksList)) {
            // region 递归获取所有上级
            //建设程序库ID集合
            List<String> cplIds = tasksList.stream().map(GanttTasks::getCplId).collect(Collectors.toList());
            List<ProjectCpLibrary> libraries = projectCpLibraryService.list();
            List<String> cpls = new ArrayList<>();
            for (String cplId : cplIds) {
                if (CollectionUtils.isEmpty(cpls) || !cpls.contains(cplId)) {
                    List<ProjectCpLibrary> currents = libraries.stream().filter(f -> f.getCplId().equals(cplId)).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(currents)) {
                        for (ProjectCpLibrary current : currents) {
                            libraryTrees.add(current);
                            cpls.add(current.getCplId());
                            treeLibrary(libraries, cpls, libraryTrees, current.getCplParentId());
                        }
                    }
                }
            }
            //endregion
            //region 生成模板详情数据
            //删除
            projectCplTemplateDtlService.deleteProCplTemplateDtlById(template.getCpltId());
            createCpTeamp(libraryTrees, template.getCpltId());
            //endregion
        }
        //endregion
        //region 生成项目建设程序数据
        List<String> tCodes = new ArrayList<>();
        tCodes.add("ProjectProcedureFlow");
        tCodes.add("ProcedureHandle");
        if (!CollectionUtils.isEmpty(libraryTrees.stream().filter(f -> ValidateUtil.isNotEmpty(f.getTCode())).collect(Collectors.toList()))) {
            List<String> codes = libraryTrees.stream().map(ProjectCpLibrary::getTCode).filter(ValidateUtil::isNotEmpty).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(codes))
                tCodes.addAll(codes);
        }
        List<FileTemplateTree> temps = userProducerFeign.getFileTemplateByCodes(tCodes.stream().distinct().collect(Collectors.toList()));
        if (CollectionUtils.isEmpty(temps)) throw new JPMException(ResMsg.notFoundAnyFileTmp);
        FileTemplateTree cpTemp = getFileTemp(temps, "ProjectProcedureFlow");
        ProjectCp projectCp = new ProjectCp();
        projectCp.setCpId(GenerateUUID.getUUID());
        projectCp.setCpltId(template.getCpltId());
        projectCp.setState(ApprovalStatusEnum.Already_Over.getIndex());
        projectCp.setpId(plan.getPId());
        projectCp.setTeId(template.getTeId());
        projectCp.setCpCreateUname(template.getCpltCreateUname());
        projectCp.setCpCreateTime(template.getCpltCreateTime());
        projectCp.setCpCreateUid(template.getCpltCreateUid());
        projectCp.setStateTime(template.getCpltCreateTime());
        projectCpService.save(projectCp);
        sysFileService.saveFile(projectCp.getCpId(), cpTemp.gettId(), projectCp.getState() + "", projectCp.getCpCreateUid(), projectCp.getCpCreateUname()
                , projectCp.getCpCreateTime(), projectCp.getpId(), cpTemp.gettName(), cpTemp.gettCode(),
                "create", GenerateUUID.getUUID(), null, cpTemp.gettCode(), null);
        createProjectCpDtl(libraryTrees, projectCp, tasksList);
        //endregion

        // 处理项目立项的数据
        Map<String, String> msg = new HashMap<>();
        msg.put("pId", plan.getPId());
        msg.put("projectCp",JacksonUtils.toJSONString(projectCp));
        msg.put("cpTemp", JacksonUtils.toJSONString(cpTemp));
        sender.sendSyncProCplTemp(JacksonUtils.toJSONString(msg));
    }

    @Override
    public void refreshProjectCplTempAntProjectCp(GanttPlan plan, List<GanttTasks> tasksList) {
        //存储所有需要生成的建设程序库数据
        List<ProjectCpLibrary> libraryTrees = new ArrayList<>();
        if (!CollectionUtils.isEmpty(tasksList)) {
            //region 递归获取所有上级
            //建设程序库ID集合
            List<String> cplIds = tasksList.stream().map(GanttTasks::getCplId).collect(Collectors.toList());
            List<ProjectCpLibrary> libraries = projectCpLibraryService.list();
            List<String> cpls = new ArrayList<>();
            for (String cplId : cplIds) {
                if (CollectionUtils.isEmpty(cpls) || !cpls.contains(cplId)) {
                    List<ProjectCpLibrary> currents = libraries.stream().filter(f -> f.getCplId().equals(cplId)).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(currents)) {
                        for (ProjectCpLibrary current : currents) {
                            libraryTrees.add(current);
                            cpls.add(current.getCplId());
                            treeLibrary(libraries, cpls, libraryTrees, current.getCplParentId());
                        }
                    }
                }
            }
            //endregion
        }
        //新的所有ID
        List<String> cplIds = libraryTrees.stream().map(ProjectCpLibrary::getCplId).collect(Collectors.toList());
        ProjectCp cp = projectCpService.selectByPid(plan.getPId());
        if (cp != null) {
            //删除
            projectCplTemplateDtlService.deleteProCplTemplateDtlById(cp.getCpltId());
            //重新创建模板详情
            createCpTeamp(libraryTrees, cp.getCpltId());
            //当前现有项目建设程序详情
            List<ProjectCpDtl> cpDtls = projectCpDtlService.getAllByCpId(cp.getCpId());
            //需要删除的建设程序
            List<ProjectCpDtl> deleteCpDtls = cpDtls.stream().filter(f -> !cplIds.contains(f.getCpdCplId())).collect(Collectors.toList());
            List<GanttTasks> ganttTasks = tasksList.stream().filter(GanttTasks::getMustHandle).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(ganttTasks)) {
                List<String> gtCplIds = ganttTasks.stream().map(GanttTasks::getCplId).collect(Collectors.toList());
                LambdaQueryWrapper<ProjectCpDtl> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ProjectCpDtl::getCpId, cp.getCpId()).in(ProjectCpDtl::getCpdCplId, gtCplIds);
                List<ProjectCpDtl> dtls = projectCpDtlService.list(wrapper);
                deleteCpDtls.addAll(dtls);
            }
            if (!CollectionUtils.isEmpty(deleteCpDtls)){
                cpDtls.removeAll(deleteCpDtls);
                projectCpDtlService.removeBatchByIds(deleteCpDtls);
                sysFileService.removeBatchByIds(deleteCpDtls.stream().map(ProjectCpDtl::getCpdId).collect(Collectors.toList()));
            }
            List<String> oldCplIds = cpDtls.stream().map(ProjectCpDtl::getCpdCplId).collect(Collectors.toList());
            //需要新增的数据
            List<ProjectCpLibrary> adds = libraryTrees.stream().filter(f -> !oldCplIds.contains(f.getCplId())).collect(Collectors.toList());
            createProjectCpDtl(adds, cp, tasksList);
            //需要更新的数据更新计划完成时间
            if (!CollectionUtils.isEmpty(cpDtls)) {
                List<ProjectCpDtl> updateCpDtls = new ArrayList<>();
                for (ProjectCpDtl cpDtl : cpDtls) {
                    List<GanttTasks> tasks = tasksList.stream().filter(f -> f.getCplId().equals(cpDtl.getCpdCplId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(tasks)) {
                        cpDtl.setCpdPlanStartTime(tasks.stream().findFirst().get().getTaskStartDate());
                        cpDtl.setCpdPlanEndTime(tasks.stream().findFirst().get().getTaskEndDate());
                        cpDtl.setCpdPersonId(tasks.stream().findFirst().get().getTaskUid());
                        cpDtl.setCpdPersonName(tasks.stream().findFirst().get().getTaskUname());
                        updateCpDtls.add(cpDtl);
                    }
                }
                if (!CollectionUtils.isEmpty(updateCpDtls)) projectCpDtlService.updateBatchById(updateCpDtls, 200);
            }
        }
    }


    private void createProjectCpDtl(List<ProjectCpLibrary> libraryTrees, ProjectCp projectCp, List<GanttTasks> tasksList) {
        if (!CollectionUtils.isEmpty(tasksList)) {
            tasksList = tasksList.stream()
                    .filter(f -> !f.getMustHandle() && libraryTrees.stream().anyMatch(data -> data.getCplId().equals(f.getCplId())))
                    .collect(Collectors.toList());
            List<ProjectCpDtl> projectCpDtls = tasksList.stream().map(task -> {
                ProjectCpDtl pojo = new ProjectCpDtl();
                pojo.setCpdId(GenerateUUID.getUUID());
                pojo.setCpId(projectCp.getCpId());
                PreProject project = preProjectService.getById(projectCp.getpId());
                if (ValidateUtil.isNull(project)) {
                    throw new JPMException(ResMsg.infoNotExist);
                }
                if (ValidateUtil.isNotEmpty(project.getpProjectFormType()) && "项目立项".equalsIgnoreCase(task.getTaskText())) {
                    pojo.setCpdState(ApprovalStatusEnum.Already_Over.getIndex());
                } else {
                    pojo.setCpdState(ApprovalStatusEnum.Not_Started.getIndex());
                }
                pojo.setCpdStatus(1);
                pojo.setCpdPlanStartTime(task.getTaskStartDate());
                pojo.setCpdPlanEndTime(task.getTaskEndDate());
                pojo.setCpdPersonId(task.getTaskUid());
                pojo.setCpdPersonName(task.getTaskUname());
                pojo.setGsId(task.getGsId());
                pojo.setTaskImportance(task.getTaskImportance());
                pojo.setCpdCplId(task.getCplId());
                List<ProjectCpLibrary> cpLibraries = libraryTrees.stream().filter(data -> task.getCplId().equals(data.getCplId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(cpLibraries)) {
                    pojo.setCpdType(cpLibraries.stream().findFirst().get().getCplType());
                    pojo.setCpdRemark(cpLibraries.stream().findFirst().get().getCplRemark());
                    pojo.setCpdProId(cpLibraries.stream().findFirst().get().getCplProId());
                    pojo.setCpdParentId(cpLibraries.stream().findFirst().get().getCplParentId());
                    pojo.setCpdName(cpLibraries.stream().findFirst().get().getCplName());
                    pojo.setCpdHtml(cpLibraries.stream().findFirst().get().getCplHtml());
                    pojo.setCpdDays(cpLibraries.stream().findFirst().get().getCplDays());
                    pojo.setCpdCode(cpLibraries.stream().findFirst().get().getCplCode());
                    pojo.setCpdAdministrationUrl(cpLibraries.stream().findFirst().get().getCplAdministrationUrl());
                    pojo.setCpdAdministrationUrlH(cpLibraries.stream().findFirst().get().getCplAdministrationUrlH());
                    pojo.setCpdAdministration(cpLibraries.stream().findFirst().get().getCplAdministration());
                    pojo.setCpdSort(cpLibraries.stream().findFirst().get().getCplSort());
                    pojo.setTCode(cpLibraries.stream().findFirst().get().getTCode());
                    pojo.setCpdFileType(cpLibraries.stream().findFirst().get().getCplFileType());
                } else {
                    return null;
                }
                return pojo;
            }).filter(Objects::nonNull).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(projectCpDtls)) {
                projectCpDtlService.saveBatch(projectCpDtls, 200);
            }
        }
    }

    private void createCpTeamp(List<ProjectCpLibrary> libraryTrees, String cpltId) {
        //region 生成模板详情数据
        List<ProjectCplTemplateDtl> templateDtls = libraryTrees.stream().map(data -> {
            ProjectCplTemplateDtl pojo = new ProjectCplTemplateDtl();
            pojo.setCpltdId(GenerateUUID.getUUID());
            pojo.setCpltId(cpltId);
            pojo.setCpltdType(data.getCplType());
            pojo.setCpltdRemark(data.getCplRemark());
            pojo.setCpltdProId(data.getCplProId());
            pojo.setCpltdParentId(data.getCplParentId());
            pojo.setCpltdName(data.getCplName());
            pojo.setCpltdHtml(data.getCplHtml());
            pojo.setCpltdDays(data.getCplDays());
            pojo.setCpltdCode(data.getCplCode());
            pojo.setCpltdAdministrationUrl(data.getCplAdministrationUrl());
            pojo.setCpltdAdministrationUrlH(data.getCplAdministrationUrlH());
            pojo.setCpltdAdministration(data.getCplAdministration());
            pojo.setCpltdSort(data.getCplSort());
            pojo.setCpltdCplId(data.getCplId());
            pojo.setCpltdFileType(data.getCplFileType());
            return pojo;
        }).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(templateDtls)) projectCplTemplateDtlService.saveBatch(templateDtls, 200);
        //endregion
    }

    private FileTemplateTree getFileTemp(List<FileTemplateTree> temps, String code) {
        List<FileTemplateTree> temp = temps.stream().filter(f -> f.gettCode().equals(code)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(temp)) return temp.stream().findFirst().get();
        throw new JPMException(String.format(ResMsg.notFoundFileTmp, code));
    }

    private void treeLibrary(List<ProjectCpLibrary> libraries, List<String> cpls, List<ProjectCpLibrary> libraryTrees, String parentId) {
        if (!parentId.equals("0") && !cpls.contains(parentId)) {
            List<ProjectCpLibrary> currents = libraries.stream().filter(f -> f.getCplId().equals(parentId)).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(currents)) {
                for (ProjectCpLibrary current : currents) {
                    libraryTrees.add(current);
                    cpls.add(current.getCplId());
                    treeLibrary(libraries, cpls, libraryTrees, current.getCplParentId());
                }
            }
        }
    }

    @Override
    public BaseResponse generateProjectCpRpr(String pId, ProjectCp projectCp, FileTemplateTree cpTemp) {
        UserPositionPDTO_CJ glManager;
        int count = 0;
        while (true) {
            glManager = userProducerFeign.getProjectPositionUserGlManager(pId);
            if (glManager != null && StrUtil.isNotBlank(glManager.getUId()) && StrUtil.isNotBlank(glManager.getUName())) {
                break;
            }
            try {
                log.error("获取项目经理失败，正在重试...");
                Thread.sleep(5000);
                count++;
            } catch (InterruptedException e) {
                log.error("获取项目经理失败，线程被中断", e);
            }
            if (count >= 10) {
                log.error("获取项目经理失败");
                glManager = userProducerFeign.getProjectPositionUserGlManager(pId);
                break;
            }
        }
        LambdaQueryWrapper<ProjectCpDtl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProjectCpDtl::getCpId, projectCp.getCpId());
        queryWrapper.eq(ProjectCpDtl::getCpdName, "项目立项");
        List<ProjectCpDtl> projectCpDtls = projectCpDtlService.list(queryWrapper);
        if (projectCpDtls != null) {
            ProjectCpResponse projectCpByCpId = projectCpMapper.getProjectCpByCpId(projectCp.getCpId());
            PreProject project = preProjectService.getById(projectCpByCpId.getpId());
            for (ProjectCpDtl projectCpDtl : projectCpDtls) {
                ProjectCpRpr projectCpRpr = new ProjectCpRpr();
                projectCpRpr.setId(GenerateUUID.getUUID());
                projectCpRpr.setCpdId(projectCpDtl.getCpdId());
                projectCpRpr.setManyTimes(false);
                projectCpRpr.setIsEnd(true);
                if (ValidateUtil.isNotNull(glManager) &&
                        ValidateUtil.isNotNull(glManager.getUId()) &&
                        ValidateUtil.isNotNull(glManager.getUName())) {
                    projectCpRpr.setCreateUname(glManager.getUName());
                    projectCpRpr.setCreateUid(glManager.getUId());
                } else {
                    projectCpRpr.setCreateUname(null);
                    projectCpRpr.setCreateUid(project.getpProjectManager());
                }
                projectCpRpr.setCreateTime(new Date());
                projectCpRpr.setState(ApprovalStatusEnum.Already_Over.getIndex());
                projectCpRpr.setStateTime(projectCpDtl.getCpdPlanEndTime());
                projectCpRpr.setOperationType("handle");
                projectCpRpr.setNotes(null);
                projectCpRpr.setTaskId(null);
                projectCpRpr.setCpId(projectCpDtl.getCpId());
                projectCpRpr.setPId(projectCpByCpId.getpId());
                projectCpRpr.setFileExpire("99");

                // 添加文件记录
                String uuid = GenerateUUID.getUUID();
                projectCpRpr.setPcrDoc(uuid);
                String type = project.getpProjectFormType();
                if (ValidateUtil.isEmpty(type)) {
                    continue;
                }
                if ("01".equalsIgnoreCase(type)) {
                    if (this.fileNotExist(project.getpCommitteeApprovaDocument())) {
                        continue;
                    }
                    sysFileAnnexService.copyFileAnnex2NewRelation(Collections.singletonList(project.getpCommitteeApprovaDocument()), Collections.singletonList(uuid), projectCpRpr.getId(), true);
                    projectCpRpr.setFileName(project.getpName() + "项目批复文件");
                    projectCpRpr.setFileCode(project.getpApproveBudgetCode());
                    projectCpRpr.setFileApprovalTime(project.getpApproveBudgetDate());
                } else if ("02".equalsIgnoreCase(type)) {
                    if (this.fileNotExist(project.getpRecordFile())) {
                        continue;
                    }
                    sysFileAnnexService.copyFileAnnex2NewRelation(Collections.singletonList(project.getpRecordFile()), Collections.singletonList(uuid), projectCpRpr.getId(), true);
                    projectCpRpr.setFileName(project.getpName() + "项目备案文件");
                    projectCpRpr.setFileCode(project.getpRecordNum());
                    projectCpRpr.setFileApprovalTime(project.getpRecordDate());
                } else if ("03".equalsIgnoreCase(type)){
                    if (this.fileNotExist(project.getpApplyFile())) {
                        continue;
                    }
                    sysFileAnnexService.copyFileAnnex2NewRelation(Collections.singletonList(project.getpApplyFile()), Collections.singletonList(uuid), projectCpRpr.getId(), true);
                    projectCpRpr.setFileName(project.getpName() + "项目立项文件");
                    projectCpRpr.setFileCode(type);
                    projectCpRpr.setFileApprovalTime(project.getpApplyDate());
                } else {
                    continue;
                }

                // 保存数据
                projectCpRprService.save(projectCpRpr);
                sysFileService.saveFile(projectCpRpr.getId(), cpTemp.gettId(), projectCpRpr.getState() + "", projectCpRpr.getCreateUid(), projectCpRpr.getCreateUname()
                        , projectCpRpr.getCreateTime(), projectCpRpr.getPId(), cpTemp.gettName(), cpTemp.gettCode(),
                        "create", GenerateUUID.getUUID(), null, cpTemp.gettCode(), null);
            }
        }
        return BaseResponse.ok();
    }

    private boolean fileNotExist(String fileRelationId) {
        return sysFileAnnexService.count(Wrappers.<SysFileannex>lambdaQuery()
                .eq(SysFileannex::getFileRelationId, fileRelationId)) <= 0;
    }
}
