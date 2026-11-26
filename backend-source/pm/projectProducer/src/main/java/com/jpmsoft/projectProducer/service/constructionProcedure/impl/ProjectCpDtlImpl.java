package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.BatchUpdatePerson;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import com.jpmsoft.common.model.request.TimeFrameRequest;
import com.jpmsoft.common.model.request.proCp.ProCpDtlTemporaryRequest;
import com.jpmsoft.common.model.request.proCp.ProjectCpDtlRequest;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.model.response.proCp.ProCpDtlTemporaryResponse;
import com.jpmsoft.common.model.response.proCp.ProjectCpDtlResponse;
import com.jpmsoft.common.model.response.proCp.ProjectCpLibraryRiskResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.construction.LibraryFileTypeDTO;
import com.jpmsoft.projectProducer.pojo.dto.other.ProjectInfoDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCp;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpLibrary;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpRpr;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttPlan;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.GanttTasks;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.request.ProjectCpInfoRequest;
import com.jpmsoft.projectProducer.service.constructionProcedure.*;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttPlanService;
import com.jpmsoft.projectProducer.service.ganttPlan.IGanttTasksService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.jpmsoft.common.enums.ApprovalStatusEnum.Already_Over;

@Service
public class ProjectCpDtlImpl extends ServiceImpl<ProjectCpDtlMapper, ProjectCpDtl> implements IProjectCpDtlService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IProjectCpExperienceService projectCpExperienceService;
    @Resource
    private IProjectCpWorkRecordService projectCpWorkRecordService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private IProjectCpLibraryService projectCpLibraryService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private IProjectCpService projectCpService;
    @Resource
    private IProjectCpRprService projectCpRprService;
    @Resource
    private ProjectCpDtlCancelImpl projectCpDtlCancelImpl;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IGanttTasksService ganttTasksService;
    @Resource
    private IGanttPlanService ganttPlanService;
    @Resource
    private IApiUtilsService apiUtils;
    @Resource
    private IProjectCpLibraryRiskService projectCpLibraryRiskService;

    @Override
    public List<ProjectCpDtl> generateProjectCpWithUnCpProject(List<ProjectCpLibrary> libraries, String cpId) {
        if (!CollectionUtils.isEmpty(libraries)) {
            List<ProjectCpDtl> pojos = new ArrayList<>();
            libraries.forEach(data -> {
                ProjectCpDtl pojo = new ProjectCpDtl();
                pojo.setCpdId(GenerateUUID.getUUID());
                pojo.setCpId(cpId);
                pojo.setCpdType(data.getCplType());
                pojo.setCpdRemark(data.getCplRemark());
                pojo.setCpdProId(data.getCplProId());
                pojo.setCpdParentId(data.getCplParentId());
                pojo.setCpdName(data.getCplName());
                pojo.setCpdHtml(data.getCplHtml());
                pojo.setCpdDays(data.getCplDays());
                pojo.setCpdCode(data.getCplCode());
                pojo.setCpdAdministrationUrl(data.getCplAdministrationUrl());
                pojo.setCpdAdministrationUrlH(data.getCplAdministrationUrlH());
                pojo.setCpdAdministration(data.getCplAdministration());
                pojo.setCpdSort(data.getCplSort());
                pojo.setCpdCplId(data.getCplId());
                pojo.setCpdState(ApprovalStatusEnum.Not_Started.getIndex());
                pojo.setTCode(data.getTCode());
                pojo.setCpdStatus(1);
                pojo.setCpdFileType(data.getCplFileType());
                pojos.add(pojo);
            });
            return pojos;
        }
        return null;
    }

    @Override
    public void createOrUpdateProCpDtl(List<ProjectCpLibraryRequset> proCplTemolateDtls, String cpId) {
        QueryWrapper<ProjectCpDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cp_Id", cpId);
        List<ProjectCpDtl> entitys = baseMapper.selectList(wrapper);
        if (entitys != null && entitys.size() > 0) {
            //删除
            //entitys有 而proCplTemolateDtls内没有的
            List<ProjectCpDtl> removeList = entitys.stream()
                    .filter(item -> !proCplTemolateDtls.stream()
                            .map(ProjectCpLibraryRequset::getCplId)
                            .collect(Collectors.toList())
                            .contains(item.getCpdCplId()))
                    .collect(Collectors.toList());
            //状态不为0不允许删除 （节点提交办理记录则修改其所有父级状态。 节点提交办理完成也要修改其所有父级状态）
            List<String> removeListIds = removeList.stream().filter(n -> n.getCpdState() == 0).map(ProjectCpDtl::getCpdId).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(removeListIds))
                baseMapper.deleteBatchIds(removeListIds);
            //  proCplTemolateDtls有 而entitys内没有的
            List<ProjectCpLibraryRequset> addList = proCplTemolateDtls.stream()
                    .filter(item -> !entitys.stream()
                            .map(ProjectCpDtl::getCpdCplId)
                            .collect(Collectors.toList())
                            .contains(item.getCplId()))
                    .collect(Collectors.toList());
            addProDtl(addList, cpId);
        } else {
            //新增
            addProDtl(proCplTemolateDtls, cpId);
        }
    }

    private void addProDtl(List<ProjectCpLibraryRequset> dtls, String cpId) {
        if (!CollectionUtils.isEmpty(dtls)) {
            List<ProjectCpDtl> pojos = new ArrayList<>();
            dtls.forEach(data -> {
                ProjectCpDtl pojo = new ProjectCpDtl();
                pojo.setCpdId(GenerateUUID.getUUID());
                pojo.setCpId(cpId);
                pojo.setCpdType(data.getCplType());
                pojo.setCpdRemark(data.getCplRemark());
                pojo.setCpdProId(data.getCplProId());
                pojo.setCpdParentId(data.getCplParentId());
                pojo.setCpdName(data.getCplName());
                pojo.setCpdHtml(data.getCplHtml());
                pojo.setCpdDays(data.getCplDays());
                pojo.setCpdCode(data.getCplCode());
                pojo.setCpdAdministrationUrl(data.getCplAdministrationUrl());
                pojo.setCpdAdministrationUrlH(data.getCplAdministrationUrlH());
                pojo.setCpdAdministration(data.getCplAdministration());
                pojo.setCpdSort(data.getCplSort());
                pojo.setCpdCplId(data.getCplId());
                pojo.setCpdState(ApprovalStatusEnum.Not_Started.getIndex());
                pojo.setTCode(data.gettCode());
                pojo.setCpdStatus(1);
                pojo.setCpdFileType(data.getCplFileType());
                pojos.add(pojo);
            });
            if (pojos.size() > 0)
                saveBatch(pojos);
        }
    }

    @Override
    public void deleteProCpDtlByCpId(String cpId) {
        QueryWrapper<ProjectCpDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cp_Id", cpId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void resetAllCpDtlState(List<ProjectCpDtl> data, boolean resetFile) {
        if (!CollectionUtils.isEmpty(data)) {
            List<String> idList = data.stream().map(ProjectCpDtl::getCpdId).collect(Collectors.toList());
            //非最顶层
            List<ProjectCpDtl> noTops = data.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCpdParentId()) && !n.getCpdParentId().equals("0")).collect(Collectors.toList());
            for (ProjectCpDtl d : noTops) {
                List<ProjectCpDtl> childs = data.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCpdParentId()) && n.getCpdStatus() == 1 && n.getCpdParentId().equals(d.getCpdCplId())).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(childs)) {//没有下级则表示当前对象d为最下级
                    List<ProjectCpDtl> parents = data.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCpdCplId()) && n.getCpdCplId().equals(d.getCpdParentId())).collect(Collectors.toList());
                    ProjectCpDtl parent = parents.stream().findFirst().get();
                    //d的同级节点未取消节点
                    List<ProjectCpDtl> tj = data.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCpdParentId()) && n.getCpdParentId().equals(d.getCpdParentId()) && n.getCpdStatus() != 0).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(tj)) {
                        //未发起
                        List<ProjectCpDtl> wfq = tj.stream().filter(n -> n.getCpdState() == 0).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(wfq)) {
                            if (wfq.size() == tj.size()) parent.setCpdState(0);
                            else parent.setCpdState(1);
                        } else {
                            //已完成
                            List<ProjectCpDtl> ywc = tj.stream().filter(n -> n.getCpdState() == 7).collect(Collectors.toList());
                            parent.setCpdState(1);
                            if (!CollectionUtils.isEmpty(ywc)) {
                                //全部完成则上级完成
                                if (ywc.size() == tj.size()) parent.setCpdState(7);
                            }
                        }
                    }
                    //更新data里的数据
                    data.set(idList.indexOf(parent.getCpdId()), parent);
                    resetParentState(data, idList, parent);
                }
            }
            updateBatchById(data);
            if (resetFile) {
                List<Integer> states = data.stream().map(ProjectCpDtl::getCpdState).distinct().collect(Collectors.toList());
                for (Integer state : states) {
                    List<String> ids = data.stream().filter(n -> Objects.equals(n.getCpdState(), state)).map(ProjectCpDtl::getCpdId).collect(Collectors.toList());
                    sysFileService.updateFileStateBatchIds(ids, state);
                }
            }
        }
    }

    private void resetParentState(List<ProjectCpDtl> data, List<String> idList, ProjectCpDtl d) {
        if (ValidateUtil.isNotEmpty(d.getCpdParentId()) && !d.getCpdParentId().equals("0")) {
            List<ProjectCpDtl> parents = data.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCpdCplId()) && n.getCpdCplId().equals(d.getCpdParentId())).collect(Collectors.toList());
            ProjectCpDtl parent = parents.stream().findFirst().get();
            //d的同级节点未取消节点
            List<ProjectCpDtl> tj = data.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCpdParentId()) && n.getCpdParentId().equals(d.getCpdParentId()) && n.getCpdStatus() != 0).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(tj)) {
                //未发起
                List<ProjectCpDtl> wfq = tj.stream().filter(n -> n.getCpdState() == 0).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(wfq)) {
                    if (wfq.size() == tj.size()) parent.setCpdState(0);
                    else parent.setCpdState(1);
                } else {
                    //已完成
                    List<ProjectCpDtl> ywc = tj.stream().filter(n -> n.getCpdState() == 7).collect(Collectors.toList());
                    parent.setCpdState(1);
                    if (!CollectionUtils.isEmpty(ywc)) {
                        //全部完成则上级完成
                        if (ywc.size() == tj.size()) parent.setCpdState(7);
                    }
                }
            }
            //更新data里的数据
            data.set(idList.indexOf(parent.getCpdId()), parent);
            resetParentState(data, idList, parent);
        }
    }

    @Override
    public BaseResponse getProCpDtlTreeByCpId(String cpId, String teId) {
        //20220923 调整建设程序树的组成，以建设程序库为主（zcl-update）
        List<ProjectCpDtlResponse> list = baseMapper.getProCpDtlByCpId(cpId);
        List<String> cplIds = list.stream()
                .map(ProjectCpDtlResponse::getCpdCplId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<ProjectCpLibraryRiskResponse> libraryRiskByCplIds = Collections.emptyList();
        if (!cplIds.isEmpty()) {
            libraryRiskByCplIds = projectCpLibraryRiskService.getProjectCpLibraryRiskByCplIds(cplIds);
        }
        Map<String, List<ProjectCpLibraryRiskResponse>> riskMap = (libraryRiskByCplIds == null || libraryRiskByCplIds.isEmpty())
                        ? Collections.emptyMap()
                        : libraryRiskByCplIds.stream().collect(Collectors.groupingBy(ProjectCpLibraryRiskResponse::getCplId));
        list.forEach(item -> item.setRiskList(riskMap.getOrDefault(item.getCpdCplId(), Collections.emptyList())));
        List<ProjectCpLibrary> cplList = projectCpLibraryService.getAllList(teId);
        List<ProjectCpLibrary> rootNodes = cplList.stream().filter(n -> n.getCplParentId().equals("0")).collect(Collectors.toList());
        List<ProjectCpDtlResponse> trees = new ArrayList<>();
        rootNodes.forEach(node -> {
            ProjectCpDtlResponse tree = new ProjectCpDtlResponse();
            List<ProjectCpDtlResponse> cpdList = list.stream().filter(n -> n.getCpdCplId().equals(node.getCplId())).collect(Collectors.toList());
            if (cpdList.size() > 0) {
                tree = cpdList.get(0);
            } else {
                tree = cpLibraryToCpDtl(node, cpId);
            }
            List<ProjectCpDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCpLibrary> childs = cplList.stream().filter(n -> n.getCplParentId().equals(node.getCplId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getProCpDtlChildNodes(childNodes, list, childs, cplList, cpId);
            if (childNodes.size() > 0 || cpdList.size() > 0) {
                tree.setChildNodes(childNodes);
                trees.add(tree);
            }
        });
        //region 匹配前置任务名称以及前置任务状态 aaron 2024/01/24
        if (!CollectionUtils.isEmpty(trees)) {
            List<ProjectCpDtlResponse> all = new ArrayList<>();
            for (ProjectCpDtlResponse tree : trees) {
                all.add(tree);
                getTreeAll(tree.getChildNodes(), all);
            }
            all.forEach(a -> {
                if (ValidateUtil.isNotEmpty(a.getCpdProId())) {
                    List<ProjectCpDtlResponse> pros = all.stream().filter(f -> f.getCpdCplId().equals(a.getCpdProId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(pros)) {
                        ProjectCpDtlResponse pro = pros.stream().findFirst().get();
                        a.setCpdProName(pro.getCpdName());
                        a.setCpdProState(pro.getCpdState());
                    }
                }
            });
//            return BaseResponse.ok(all);
        }
        //endregion
        return BaseResponse.ok(trees);
    }

    @Override
    public BaseResponse getProCpDtlTreeByCpIdNJCJ(String cpId, String teId) {
        List<ProjectCpDtlResponse> list = baseMapper.getProCpDtlByCpId(cpId);
        return BaseResponse.ok(list);
    }

    private void getTreeAll(List<ProjectCpDtlResponse> childs, List<ProjectCpDtlResponse> all) {
        if (!CollectionUtils.isEmpty(childs)) {
            childs.forEach(c -> {
                all.add(c);
                getTreeAll(c.getChildNodes(), all);
            });
        }
    }

    @Override
    public BaseResponse getProCpDtlIdListByCpId(String cpId) {
        List<ProjectCpDtlResponse> list = baseMapper.getProCpDtlByCpId(cpId);
        List<String> data = list.stream().map(ProjectCpDtlResponse::getCpdCplId).collect(Collectors.toList());
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getProCpDtlIdListByPid(String pId) {
        List<String> list = baseMapper.getProCpDtlIdListByPid(pId);
        return BaseResponse.ok(list);
    }


    @Override
    public BaseResponse getProCpDtlListByCpId(ProjectCpDtlRequest request) {
        if (ValidateUtil.isAllEmpty(request.getCpId(), request.getpId())
                && ValidateUtil.isNull(request.getStatisticalType()))
            return BaseResponse.fail(ResMsg.notReqParam);
        QueryMap<ProjectCpDtlRequest> params = new QueryMap<>(request);
        params.put("cpId").put("statisticalType").put("pId").put("cpdStatus");
        IPage<ProjectCpDtlResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpDtlResponse> data = baseMapper.getProCpDtlListByCpId(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProCpDtlListByStatistics(ProjectCpDtlRequest request) {
        if (request.getQueryType() == null)
            return BaseResponse.fail(ResMsg.notReqParam);
        if (request.getQueryType() == 1)
            return getProCpDtlByUser(request);
        else if (request.getQueryType() == 2)
            return getProCpDtlListByCpId(request);
        else
            return BaseResponse.fail(ResMsg.actError);
    }


    @Override
    public List<ProjectCpDtl> getProCpDtlByCpIds(List<String> cpIds) {
        if (cpIds == null)
            return new ArrayList<>();
        if (cpIds.size() < 1)
            return new ArrayList<>();
        QueryWrapper<ProjectCpDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpd_Status", 1);
        wrapper.and(wr -> {
            wr.eq("Cp_Id", cpIds.get(0));
            for (int i = 1; i < cpIds.size(); i++)
                wr.or().eq("Cp_Id", cpIds.get(i));
        });
        wrapper.orderByAsc("Cpd_Sort");
        List<ProjectCpDtl> data = baseMapper.selectList(wrapper);
        return data;
    }

    @Override
    public BaseResponse getProCpDtlByUser(ProjectCpDtlRequest request) {
        if (ValidateUtil.isAllEmpty(request.getCpdPersonId(), request.getpId())
                && ValidateUtil.isNull(request.getStatisticalType()))
            return BaseResponse.fail(ResMsg.notReqParam);
        QueryMap<ProjectCpDtlRequest> params = new QueryMap<>(request);
        params.put("cpdPersonId").put("statisticalType").put("pId").put("cpdStatus");
        IPage<ProjectCpDtlResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpDtlResponse> data = baseMapper.getProCpDtlByUser(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProCpDtlByPid(String pId, String teId) {
        List<ProjectCpDtl> list = baseMapper.getProCpDtlByPid(pId);
        //20220923 调整建设程序树的组成，以建设程序库为主（zcl-update）
        List<ProjectCpLibrary> cplList = projectCpLibraryService.getAllList(teId);
        List<ProjectCpLibrary> rootNodes = cplList.stream().filter(n -> n.getCplParentId().equals("0")).collect(Collectors.toList());

        List<ProjectCpDtlResponse> trees = new ArrayList<>();
        rootNodes.forEach(node -> {
            String cpdId = "";
            if (list.size() > 0) {
                cpdId = list.get(0).getCpdId();
            }
            ProjectCpDtlResponse tree = new ProjectCpDtlResponse();
            List<ProjectCpDtl> cpdList = list.stream().filter(n -> n.getCpdCplId().equals(node.getCplId())).collect(Collectors.toList());
            if (cpdList.size() > 0) {
                BeanUtils.copyProperties(cpdList.get(0), tree);
            } else {
                tree = cpLibraryToCpDtl(node, cpdId);
            }
            List<ProjectCpDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCpLibrary> childs = cplList.stream().filter(n -> n.getCplParentId().equals(node.getCplId())).collect(Collectors.toList());
            if (childs.size() > 0) {
                getProCpDtlPojoChildNodes(childNodes, list, childs, cplList, cpdId);
            }
            if (childNodes.size() > 0 || cpdList.size() > 0) {
                tree.setChildNodes(childNodes);
                trees.add(tree);
            }
        });
        return BaseResponse.ok(trees);
    }

    @Override
    public BaseResponse getProCpDtlTreeByPid(ProjectCpDtlRequest request) {
        if (ValidateUtil.isEmpty(request.getpId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        List<ProjectCpDtl> list = baseMapper.getProCpDtlByPid(request.getpId());
        List<String> docIds = list.stream().filter(n -> !ValidateUtil.isEmpty(n.getCpdDoc())).map(ProjectCpDtl::getCpdDoc).collect(Collectors.toList());
        List<ProjectCpDtlResponse> datas = new ArrayList<>();
        //region 先匹配文件信息
        if (docIds != null && docIds.size() > 0) {
            List<FileResponse> files = sysFileAnnexService.getFileByFileRelationIds(docIds);
            for (ProjectCpDtl pojo : list) {
                ProjectCpDtlResponse data = new ProjectCpDtlResponse();
                BeanUtils.copyProperties(pojo, data);
                if (!ValidateUtil.isEmpty(pojo.getCpdDoc())) {
                    List<FileResponse> file = files.stream().filter(n -> n.getRelationid().equals(pojo.getCpdDoc())).collect(Collectors.toList());
                    if (file != null && file.size() > 0)
                        data.setFiles(file);
                }
                datas.add(data);
            }
        } else {
            //筛选有文件
            if (request.getHaveDoc() != null && request.getHaveDoc() == true)
                return BaseResponse.ok(new ArrayList<>());
            for (ProjectCpDtl pojo : list) {
                ProjectCpDtlResponse data = new ProjectCpDtlResponse();
                BeanUtils.copyProperties(pojo, data);
                datas.add(data);
            }
        }
        //endregion

        //记录筛选后所有的Id
        HashSet<String> set = new HashSet<>();

        //region 过滤筛选
        if (!ValidateUtil.isEmpty(request.getCpdName()) || (request.getHaveDoc() != null && request.getHaveDoc() == true)) {
            //需要筛选是否有文件和根据程序名筛选
            List<ProjectCpDtlResponse> filterList = new ArrayList<>();
            //根据程序名筛选
            if (!ValidateUtil.isEmpty(request.getCpdName()))
                filterList = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getCpdName()) && n.getCpdName().contains(request.getCpdName())).collect(Collectors.toList());
            else
                filterList = datas;

            //过滤没有文件的数据
            if (filterList != null && filterList.size() > 0 && request.getHaveDoc() != null && request.getHaveDoc() == true)
                filterList = filterList.stream().filter(n -> n.getFiles() != null && n.getFiles().size() > 0).collect(Collectors.toList());

            //筛选后无数据
            if (filterList == null || filterList.size() < 1) //筛选后无数据直接返回
                return BaseResponse.ok(new ArrayList<>());
            for (ProjectCpDtlResponse res : filterList) {
                set.add(res.getCpdCplId());
                getAllParentIds(set, list, res.getCpdParentId());
            }
        }
        //endregion

        //region 组树结构
        //20220923 调整建设程序树的组成，以建设程序库为主（zcl-update）
        List<ProjectCpLibrary> cplList = projectCpLibraryService.getAllList(request.getTeId());
        List<ProjectCpLibrary> rootNodes = cplList.stream().filter(n -> n.getCplParentId().equals("0")).collect(Collectors.toList());
        List<ProjectCpDtlResponse> trees = new ArrayList<>();
        for (ProjectCpLibrary node : rootNodes) {
            ProjectCpDtlResponse tree = new ProjectCpDtlResponse();
            List<ProjectCpDtlResponse> cpdList = datas.stream().filter(n -> n.getCpdCplId().equals(node.getCplId())).collect(Collectors.toList());
            if (cpdList.size() > 0) {
                tree = cpdList.get(0);
            } else {
                tree = cpLibraryToCpDtl(node, request.getCpId());
            }
            if (set != null && set.size() > 0)
                if (!set.contains(tree.getCpdCplId()))
                    continue;
            List<ProjectCpDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCpLibrary> childs = new ArrayList<>();
            if (set != null && set.size() > 0)
                childs = cplList.stream().filter(n -> n.getCplParentId().equals(node.getCplId()) && set.contains(n.getCplId())).collect(Collectors.toList());
            else
                childs = cplList.stream().filter(n -> n.getCplParentId().equals(node.getCplId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getProCpDtlPojoChildNodes(childNodes, datas, childs, cplList, request.getCpId(), set);
            if (childNodes.size() > 0 || cpdList.size() > 0) {
                tree.setChildNodes(childNodes);
                trees.add(tree);
            }
        }
        //endregion

        return BaseResponse.ok(trees);
    }

    private void getAllParentIds(HashSet<String> set, List<ProjectCpDtl> list, String parentId) {
        if (parentId != null && !parentId.equals("0")) {
            List<ProjectCpDtl> pojos = list.stream().filter(n -> n.getCpdCplId().equals(parentId)).collect(Collectors.toList());
            if (pojos != null && pojos.size() > 0) {
                ProjectCpDtl parent = pojos.stream().findFirst().get();
                set.add(parent.getCpdCplId());
                getAllParentIds(set, list, parent.getCpdParentId());
            }
        }
    }

    @Override
    public BaseResponse deleteProCpDtlByCpDtlId(String cpDtlId) {
        QueryWrapper<ProjectCpDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpd_Id", cpDtlId);
        wrapper.ne("Cpd_State", Already_Over.getIndex());
        ProjectCpDtl entity = baseMapper.selectOne(wrapper);
        if (entity == null)
            return BaseResponse.fail(ResMsg.notProCpDtl);
        if (projectCpWorkRecordService.getCpWorkRecordCountByCpId(cpDtlId) > 0
                || projectCpExperienceService.getCpExperienceCountByCpId(cpDtlId) > 0)
            return BaseResponse.fail(ResMsg.delAssociated);
        QueryWrapper<ProjectCpDtl> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Cpd_Parent_Id", entity.getCpdCplId());
        if (baseMapper.selectCount(queryWrapper) > 0)
            return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(cpDtlId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateProCpDtlByCpDtlId(ProjectCpDtlRequest request) {
        if (ValidateUtil.isEmpty(request.getCpdId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        ProjectCpDtl entity = baseMapper.selectById(request.getCpdId());
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        entity.setCpdPlanStartTime(request.getCpdPlanStartTime());
        entity.setCpdPlanEndTime(request.getCpdPlanEndTime());
        entity.setCpdPersonId(request.getCpdPersonId());
        entity.setCpdPersonName(request.getCpdPersonName());
        entity.setCpdAdministration(request.getCpdAdministration());
        entity.setCpdAdministrationUrl(request.getCpdAdministrationUrl());
        entity.setCpdRemark(request.getCpdRemark());
        entity.setCpdProId(request.getCpdProId());
        entity.setCpdDays(request.getCpdDays());
        entity.setCpdStartTime(request.getCpdStartTime());
        entity.setCpdFileName(request.getCpdFileName());
        entity.setCpdFileCode(request.getCpdFileCode());
        entity.setCpdReplyTime(request.getCpdReplyTime());
        entity.setCpdCreateUid(request.getCpdCreateUid());
        entity.setCpdCreateUname(request.getCpdCreateUname());
        entity.setCpdCerEndTime(request.getCpdCerEndTime());
        entity.setCpdState(request.getCpdState());
        entity.setCpdFileType(request.getCpdFileType());
        baseMapper.updateById(entity);
        return BaseResponse.ok();
    }

    @Override
    public Integer getProCpDtlCountByCplId(String cplId) {
        QueryWrapper<ProjectCpDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpd_Cpl_Id", cplId);
        return baseMapper.selectCount(wrapper).intValue();
    }

    @Override
    public Integer getProCpDtlCountByCpId(String cpId) {
        QueryWrapper<ProjectCpDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cp_Id", cpId);
        wrapper.ne("Cpd_State", ApprovalStatusEnum.Not_Started.getIndex());
        return baseMapper.selectCount(wrapper).intValue();
    }

    /**
     * 当前节点更改为办理中状态需要更改所有上级节点为办理中
     * 递归逻辑: 当前节点状态完成-》判断当前是否所有同级节点状态完成
     * 是->修改上级状态完成 否->校验上级是否为办理中（非办理中则修改所有上级为办理中）
     *
     * @param entity 当前节点状态更改后的实体
     * @param dtlId
     * @param state
     */
    @Override
    public void updateProCpDtlAllParentState(ProjectCpDtl entity, String dtlId, Integer state, String cleanCreateTime) {
        if (ValidateUtil.isNotEmpty(dtlId) && ValidateUtil.isNotNull(state)) {
            //生成项目经验 或者 办理记录时 修改当前节点为办理中
            //前提: 当前事项为新建未办理状态(可能事项已完成依然追加项目经验和办理记录)
            entity = baseMapper.selectById(dtlId);
            if (Objects.equals(entity.getCpdState(), ApprovalStatusEnum.Not_Started.getIndex())) {
                entity.setCpdState(state);
                baseMapper.updateById(entity);
            } else
                return;
        }
        QueryWrapper<ProjectCpDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cp_Id", entity.getCpId());
        List<ProjectCpDtl> entitys = baseMapper.selectList(wrapper);
        List<ProjectCpDtl> updateStateEntitys = new ArrayList<>();
        checkOrUpdateParentState(updateStateEntitys, entitys, entity, cleanCreateTime);
        updateBatchById(updateStateEntitys);
    }

    @Override
    public BaseResponse statisticalProCpAndDtl(TimeFrameRequest request) {
        Map<String, Object> params = new HashMap<>();
        if (request != null) {
            params.put("pRegion", ValidateUtil.isEmpty(request.getpRegion()) ? null : request.getpRegion());
            params.put("pType", ValidateUtil.isEmpty(request.getpType()) ? null : request.getpType());
            params.put("state", ValidateUtil.isEmpty(request.getState()) ? null : request.getState());
            params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
            params.put("pStatus", ValidateUtil.isEmpty(request.getpStatus()) ? null : request.getpStatus());
            params.put("pProjectStage", ValidateUtil.isEmpty(request.getpProjectStage()) ? null : request.getpProjectStage());
            params.put("pConstructionUnit", ValidateUtil.isEmpty(request.getpConstructionUnit()) ? null : request.getpConstructionUnit());
            params.put("pCompetentDepartment", ValidateUtil.isEmpty(request.getpCompetentDepartment()) ? null : request.getpCompetentDepartment());
        }
        return BaseResponse.ok(baseMapper.statisticalProCpAndDtl(params));
    }

    @Override
    public BaseResponse statisticalProCpDtlByTimeFrame(TimeFrameRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("pRegion", ValidateUtil.isEmpty(request.getpRegion()) ? null : request.getpRegion());
        params.put("pType", ValidateUtil.isEmpty(request.getpType()) ? null : request.getpType());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        params.put("pProjectStage", ValidateUtil.isEmpty(request.getpProjectStage()) ? null : request.getpProjectStage());
        params.put("pConstructionUnit", ValidateUtil.isEmpty(request.getpConstructionUnit()) ? null : request.getpConstructionUnit());
        params.put("pCompetentDepartment", ValidateUtil.isEmpty(request.getpCompetentDepartment()) ? null : request.getpCompetentDepartment());
        params.put("startTime", ValidateUtil.isEmpty(request.getStartTime()) ? null : DateUtil.String2Date(request.getStartTime(), "yyyy-MM-dd"));
        params.put("endTime", ValidateUtil.isEmpty(request.getEndTime()) ? null : DateUtil.DateChangeDay(DateUtil.String2Date(request.getEndTime(), "yyyy-MM-dd"), 1));
        params.put("cpdState", Already_Over.getIndex());
        return BaseResponse.ok(baseMapper.statisticalProCpDtlByTimeFrame(params));
    }

    @Override
    public BaseResponse getProCpDtlComplateList(ProjectCpDtlRequest request) {
        QueryMap<ProjectCpDtlRequest> params = new QueryMap<>(request);
        params.put("pType").put("cpdState").put("pProjectStage")
                .put("pConstructionUnit").put("pCompetentDepartment").put("pRegion");
        IPage<ProjectCpDtlResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpDtlResponse> datas = baseMapper.getProCpDtlComplateList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    /**
     * 当前节点更改为办理中状态需要更改所有上级节点为办理中
     * 递归逻辑: 当前节点状态完成-》判断当前是否所有同级节点状态完成 是->修改上级状态完成 否->校验上级是否为办理中（非办理中则修改所有上级为办理中）
     *
     * @param updateStateEntitys
     * @param entitys
     */
    private void checkOrUpdateParentState(List<ProjectCpDtl> updateStateEntitys, List<ProjectCpDtl> entitys, ProjectCpDtl entity, String cleanCreateTime) {
        if (entity.getCpdParentId().equals("0"))
            return;
        if (Objects.equals(entity.getCpdState(), ApprovalStatusEnum.Under_Approval.getIndex())) {//办理中
            List<ProjectCpDtl> parentPojo = entitys.stream().filter(n -> n.getCpdCplId().equals(entity.getCpdParentId())).collect(Collectors.toList());
            if (parentPojo != null && parentPojo.size() > 0) {
                ProjectCpDtl parent = parentPojo.get(0);
                if (!parent.getCpdState().equals(entity.getCpdState())) {
                    parent.setCpdState(ApprovalStatusEnum.Under_Approval.getIndex());
                    updateStateEntitys.add(parent);
                    checkOrUpdateParentState(updateStateEntitys, entitys, parent, null);
                }
            }
        } else if (Objects.equals(entity.getCpdState(), Already_Over.getIndex())) {//办理完成
            //所有同级节点
            List<ProjectCpDtl> sameLevels = entitys.stream().filter(n -> n.getCpdParentId().equals(entity.getCpdParentId())).collect(Collectors.toList());
            //所有同级节点且状态为办理完成
            List<ProjectCpDtl> peerCompletion = sameLevels.stream().filter(n -> Objects.equals(n.getCpdState(), Already_Over.getIndex())).collect(Collectors.toList());
            //上级父节点
            List<ProjectCpDtl> parentPojo = entitys.stream().filter(n -> n.getCpdCplId().equals(entity.getCpdParentId())).collect(Collectors.toList());
            if (parentPojo != null && parentPojo.size() > 0) {
                ProjectCpDtl parent = parentPojo.get(0);
                if (sameLevels.size() == peerCompletion.size()) { //更改上级为办理完成
                    parent.setCpdState(Already_Over.getIndex());
                    updateStateEntitys.add(parent);
                    checkOrUpdateParentState(updateStateEntitys, entitys, parent, cleanCreateTime);
                } else {
                    //校验上级是否为办理中
                    if (parent.getCpdState() != ApprovalStatusEnum.Under_Approval.getIndex()) {
                        parent.setCpdState(ApprovalStatusEnum.Under_Approval.getIndex());
                        updateStateEntitys.add(parent);
                        checkOrUpdateParentState(updateStateEntitys, entitys, parent, cleanCreateTime);
                    }
                }
            }
        } else if (Objects.equals(entity.getCpdState(), ApprovalStatusEnum.Not_Started.getIndex())) { //删除
            //所有同级节点
            List<ProjectCpDtl> sameLevels = entitys.stream().filter(n -> n.getCpdParentId().equals(entity.getCpdParentId())).collect(Collectors.toList());

            //所有同级节点且状态为办理完成
            List<ProjectCpDtl> peerCompletion = sameLevels.stream().filter(n -> Objects.equals(n.getCpdState(), Already_Over.getIndex())).collect(Collectors.toList());

            //上级父节点
            List<ProjectCpDtl> parentPojo = entitys.stream().filter(n -> n.getCpdCplId().equals(entity.getCpdParentId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(sameLevels)) {
                //判断是不是有同级节点，并且有不等等于0的
                List<ProjectCpDtl> cpDtlList = sameLevels.stream().filter(e -> !e.getCpdState().equals(0) || e.getCpdState().equals(7)).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(cpDtlList)) {
                    ProjectCpDtl parent = parentPojo.get(0);
                    if (ValidateUtil.isNotEmpty(cleanCreateTime) && cleanCreateTime.equals("1")) {
                        parent.setCpdCreateTime(null);
                    }
                    parent.setCpdState(ApprovalStatusEnum.Not_Started.getIndex());
                    updateStateEntitys.add(parent);
                    checkOrUpdateParentState(updateStateEntitys, entitys, parent, cleanCreateTime);
                } else {
                    if (parentPojo != null && parentPojo.size() > 0) {
                        ProjectCpDtl parent = parentPojo.get(0);
                        if (ValidateUtil.isNotEmpty(cleanCreateTime) && cleanCreateTime.equals("1")) {
                            parent.setCpdCreateTime(null);
                        }
                        if (!CollectionUtils.isEmpty(peerCompletion)) {
                            parent.setCpdState(ApprovalStatusEnum.Under_Approval.getIndex());
                        } else {
                            parent.setCpdState(ApprovalStatusEnum.Not_Started.getIndex());
                        }
                        updateStateEntitys.add(parent);
                        checkOrUpdateParentState(updateStateEntitys, entitys, parent, cleanCreateTime);
                    }
                }
            }

        }
    }

//    @Override
//    public List<ProjectCpDtlPojo> getProCpDtlByUserIds(List<String> userIds) {
//        if (userIds == null)
//            return new ArrayList<>();
//        if (userIds.size() < 1)
//            return new ArrayList<>();
//        QueryWrapper<ProjectCpDtlPojo> wrapper = new QueryWrapper<>();
//        wrapper.eq("Cpd_Person_Id", userIds.get(0));
//        for (int i = 1; i < userIds.size(); i++)
//            wrapper.or().eq("Cpd_Person_Id", userIds.get(i));

    /// /        wrapper.orderByAsc()
//        List<ProjectCpDtlPojo> data = baseMapper.selectList(wrapper);
//        return data;
//    }
    @Override
    public BaseResponse submitProcessingResults(ProjectCpDtlRequest request) {
        if (JudgeUtil.checkObject(request.getCpdId(), request.getCpdCreateUid()))
            return BaseResponse.fail(ResMsg.notReqParam);
        ProjectCpDtl entity = baseMapper.selectById(request.getCpdId());
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        Date now = new Date();
        if (entity.getCpdState() != request.getFlowState())
            entity.setStateTime(now);
        entity.setCpdState(request.getFlowState());
        entity.setCpdPersonId(request.getCpdPersonId());
        entity.setCpdPersonName(request.getCpdPersonName());
        entity.setCpdPlanStartTime(ValidateUtil.isNull(request.getCpdPlanStartTime()) ? null : request.getCpdPlanStartTime());
        entity.setCpdPlanEndTime(ValidateUtil.isNull(request.getCpdPlanEndTime()) ? null : request.getCpdPlanEndTime());
        entity.setCpdStartTime(ValidateUtil.isNull(request.getCpdStartTime()) ? null : request.getCpdStartTime());
        entity.setCpdEndTime(ValidateUtil.isNull(request.getCpdEndTime()) ? null : request.getCpdEndTime());
        entity.setCpdDoc(request.getCpdDoc());
        entity.setCpdOtherFile(request.getCpdOtherFile());
        if (ValidateUtil.isNotNull(request.getCleanCreateTime()) && request.getCleanCreateTime().equals("1")) {
            entity.setCpdCreateTime(null);
        } else {
            entity.setCpdCreateTime(ValidateUtil.isNull(request.getCpdCreateTime()) ? now : request.getCpdCreateTime());
        }
        entity.setCpdCreateUid(request.getCpdCreateUid());
        entity.setCpdCreateUname(request.getCpdCreateUname());
        entity.setCpdFileName(request.getCpdFileName());
        entity.setCpdFileCode(request.getCpdFileCode());
        entity.setCpdReplyTime(ValidateUtil.isNull(request.getCpdReplyTime()) ? null : request.getCpdReplyTime());
        entity.setCpdCerEndTime(ValidateUtil.isNull(request.getCpdCerEndTime()) ? null : request.getCpdCerEndTime());
        entity.setTeId(request.getTeId());
        baseMapper.updateById(entity);
        updateProCpDtlAllParentState(entity, null, null, request.getCleanCreateTime());
        if (sysFileService.havingSysFile(entity.getCpdId())) {
            sysFileService.saveFile(entity.getCpdId(), request.gettId(), request.getFlowState() + "", entity.getCpdCreateUid(), entity.getCpdCreateUname()
                    , entity.getCpdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getCpdDoc(), request.getFileRelationId(), request.getCpdOtherFile()),
                    Arrays.asList(request.getCpdDocIds(), request.getFileIds(), request.getCpdOtherFileIds()), request.getCpdId());
        } else {
            sysFileService.saveFile(entity.getCpdId(), request.gettId(), request.getFlowState() + "", entity.getCpdCreateUid(), entity.getCpdCreateUname()
                    , entity.getCpdCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getCpdDoc(), request.getFileRelationId(), request.getCpdOtherFile()), request.getCpdId());
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse proCpDtlHaveSysFile(String cpdId) {
        return BaseResponse.ok(sysFileService.havingSysFile(cpdId));
    }


    private void getProCpDtlChildNodes(List<ProjectCpDtlResponse> reatrnTrees, List<ProjectCpDtlResponse> list, List<ProjectCpLibrary> rootNodes, List<ProjectCpLibrary> allNodes, String cpId) {
        //20220923 调整建设程序树的组成，以建设程序库为主（zcl-create）
        rootNodes.forEach(node -> {
            ProjectCpDtlResponse tree = new ProjectCpDtlResponse();
            List<ProjectCpDtlResponse> cpdList = list.stream().filter(n -> n.getCpdCplId().equals(node.getCplId())).collect(Collectors.toList());
            if (cpdList.size() > 0) {
                tree = cpdList.get(0);
            } else {
                tree = cpLibraryToCpDtl(node, cpId);
            }
            List<ProjectCpDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCpLibrary> childs = allNodes.stream().filter(n -> n.getCplParentId().equals(node.getCplId())).collect(Collectors.toList());
            if (childs.size() > 0) {
                getProCpDtlChildNodes(childNodes, list, childs, allNodes, cpId);
            }
            if (childNodes.size() > 0 || cpdList.size() > 0) {
                tree.setChildNodes(childNodes);
                reatrnTrees.add(tree);
            }
        });
    }


    private void getProCpDtlPojoChildNodes(List<ProjectCpDtlResponse> trees, List<ProjectCpDtlResponse> list, List<ProjectCpLibrary> rootNodes, List<ProjectCpLibrary> allNodes, String cpId, HashSet<String> set) {
        //20220923 调整建设程序树的组成，以建设程序库为主（zcl-create）
        for (ProjectCpLibrary node : rootNodes) {
            ProjectCpDtlResponse tree = new ProjectCpDtlResponse();
            List<ProjectCpDtlResponse> cpdList = list.stream().filter(n -> n.getCpdCplId().equals(node.getCplId())).collect(Collectors.toList());
            if (cpdList.size() > 0) {
                tree = cpdList.get(0);
            } else {
                tree = cpLibraryToCpDtl(node, cpId);
            }
            if (set != null && set.size() > 0)
                if (!set.contains(tree.getCpdCplId()))
                    continue;
            List<ProjectCpDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCpLibrary> childs = new ArrayList<>();
            if (set != null && set.size() > 0) {
                childs = allNodes.stream().filter(n -> n.getCplParentId().equals(node.getCplId()) && set.contains(n.getCplId())).collect(Collectors.toList());
            } else {
                childs = allNodes.stream().filter(n -> n.getCplParentId().equals(node.getCplId())).collect(Collectors.toList());
            }
            if (childs.size() > 0)
                getProCpDtlPojoChildNodes(childNodes, list, childs, allNodes, cpId, set);
            if (childNodes.size() > 0 || cpdList.size() > 0) {
                tree.setChildNodes(childNodes);
                trees.add(tree);
            }
        }
    }


    private void getProCpDtlPojoChildNodes(List<ProjectCpDtlResponse> trees, List<ProjectCpDtl> list, List<ProjectCpLibrary> rootNodes, List<ProjectCpLibrary> allNodes, String cpId) {
        //20220923 调整建设程序树的组成，以建设程序库为主（zcl-create）
        rootNodes.forEach(node -> {
            ProjectCpDtlResponse tree = new ProjectCpDtlResponse();
            List<ProjectCpDtl> cpdList = list.stream().filter(n -> n.getCpdCplId().equals(node.getCplId())).collect(Collectors.toList());
            if (cpdList.size() > 0) {
                BeanUtils.copyProperties(cpdList.get(0), tree);
            } else {
                tree = cpLibraryToCpDtl(node, cpId);
            }
            List<ProjectCpDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCpLibrary> childs = allNodes.stream().filter(n -> n.getCplParentId().equals(node.getCplId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getProCpDtlPojoChildNodes(childNodes, list, childs, allNodes, cpId);
            if (childNodes.size() > 0 || cpdList.size() > 0) {
                tree.setChildNodes(childNodes);
                trees.add(tree);
            }
        });
    }


    private ProjectCpDtlResponse cpLibraryToCpDtl(ProjectCpLibrary obj, String cpId) {
        //20220923 调整建设程序树的组成，以建设程序库为主（zcl-create）
        ProjectCpDtlResponse node = new ProjectCpDtlResponse();
        node.setCpdId(GenerateUUID.getUUID());
        node.setCpId(cpId);
        node.setCpdType(obj.getCplType());
        node.setCpdRemark(obj.getCplRemark());
        node.setCpdProId(obj.getCplProId());
        node.setCpdParentId(obj.getCplParentId());
        node.setCpdName(obj.getCplName());
        node.setCpdHtml(obj.getCplHtml());
        node.setCpdDays(obj.getCplDays());
        node.setCpdCode(obj.getCplCode());
        node.setCpdAdministrationUrl(obj.getCplAdministrationUrl());
        node.setCpdAdministration(obj.getCplAdministration());
        node.setCpdSort(obj.getCplSort());
        node.setCpdCplId(obj.getCplId());
        node.setCpdFileType(obj.getCplFileType());
        return node;
    }

    @Deprecated
    private void getProCpDtlChildNodesOld(List<ProjectCpDtlResponse> trees, List<ProjectCpDtlResponse> rootNodes, List<ProjectCpDtlResponse> list) {
        rootNodes.forEach(node -> {
            ProjectCpDtlResponse tree = new ProjectCpDtlResponse();
            BeanUtils.copyProperties(node, tree);
            List<ProjectCpDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCpDtlResponse> childs = list.stream().filter(n -> n.getCpdParentId().equals(node.getCpdCplId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getProCpDtlChildNodesOld(childNodes, childs, list);
            tree.setChildNodes(childNodes);
            trees.add(tree);
        });
    }

    @Deprecated
    private void getProCpDtlPojoChildNodesOld(List<ProjectCpDtlResponse> trees, List<ProjectCpDtlResponse> rootNodes, List<ProjectCpDtlResponse> list, HashSet<String> set) {
        for (ProjectCpDtlResponse node : rootNodes) {
            if (set != null && set.size() > 0)
                if (!set.contains(node.getCpdCplId()))
                    continue;
            List<ProjectCpDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCpDtlResponse> childs = new ArrayList<>();
            if (set != null && set.size() > 0)
                childs = list.stream().filter(n -> n.getCpdParentId().equals(node.getCpdCplId()) && set.contains(n.getCpdCplId())).collect(Collectors.toList());
            else
                childs = list.stream().filter(n -> n.getCpdParentId().equals(node.getCpdCplId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getProCpDtlPojoChildNodesOld(childNodes, childs, list, set);
            node.setChildNodes(childNodes);
            trees.add(node);
        }
    }

    @Deprecated
    private void getProCpDtlPojoChildNodesOld(List<ProjectCpDtlResponse> trees, List<ProjectCpDtl> rootNodes, List<ProjectCpDtl> list) {
        rootNodes.forEach(node -> {
            ProjectCpDtlResponse tree = new ProjectCpDtlResponse();
            BeanUtils.copyProperties(node, tree);
            List<ProjectCpDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCpDtl> childs = list.stream().filter(n -> n.getCpdParentId().equals(node.getCpdCplId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getProCpDtlPojoChildNodesOld(childNodes, childs, list);
            tree.setChildNodes(childNodes);
            trees.add(tree);
        });
    }

    @Deprecated
    private void analysisAndAssembleList(List<ProjectCpDtl> pojos, List<ProjectCpLibraryRequset> datas, List<ProjectCpLibraryRequset> proCplTemolateDtls, String cpId, String parentId) {
        datas.forEach(data -> {
            ProjectCpDtl pojo = new ProjectCpDtl();
            pojo.setCpdId(GenerateUUID.getUUID());
            pojo.setCpId(cpId);
            pojo.setCpdType(data.getCplType());
            pojo.setCpdRemark(data.getCplRemark());
            pojo.setCpdProId(data.getCplProId());
            pojo.setCpdParentId(parentId);
            pojo.setCpdName(data.getCplName());
            pojo.setCpdHtml(data.getCplHtml());
            pojo.setCpdDays(data.getCplDays());
            pojo.setCpdCode(data.getCplCode());
            pojo.setCpdAdministrationUrl(data.getCplAdministrationUrl());
            pojo.setCpdAdministration(data.getCplAdministration());
            pojo.setCpdSort(data.getCplSort());
            pojo.setCpdCplId(data.getCplId());
            pojo.setCpdFileType(data.getCplFileType());
            pojos.add(pojo);
            List<ProjectCpLibraryRequset> childDatas = proCplTemolateDtls.stream().filter(n -> n.getCplParentId().equals(data.getCplId())).collect(Collectors.toList());
            if (childDatas.size() > 0)
                analysisAndAssembleList(pojos, childDatas, proCplTemolateDtls, cpId, pojo.getCpdId());
        });
    }


    @Override
    public BaseResponse getProCpDtlTemporaryListByuid(ProCpDtlTemporaryRequest request) {
        QueryMap<ProCpDtlTemporaryRequest> params = new QueryMap<>(request);
        params.put("pending").put("pId").put("pName").put("cpdName")
                .put("cpdPersonId");
        if (ValidateUtil.isNotNull(request.getTemporaryTime()))
            params.put("temporaryTime", DateUtil.Date2String(request.getTemporaryTime(), "yyyy-MM-dd"));
        IPage<ProCpDtlTemporaryResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProCpDtlTemporaryResponse> datas = baseMapper.getProCpDtlTemporaryListByuid(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }


    @Override
    public BaseResponse batchUpdateCpDtlPerson(BatchUpdatePerson request) {
        if (request == null || ValidateUtil.isAnObjEmpty(request.getPersonId(), request.getPersonName())
                || CollectionUtils.isEmpty(request.getAllIds()))
            return BaseResponse.fail(ResMsg.notReqParam);
        baseMapper.batchUpdateCpDtlPerson(request);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse batchUpdateCpDtlCreateUser(BatchUpdatePerson request) {
        if (request == null || ValidateUtil.isAnObjEmpty(request.getPersonId(), request.getPersonName())
                || CollectionUtils.isEmpty(request.getAllIds()))
            return BaseResponse.fail(ResMsg.notReqParam);
        baseMapper.batchUpdateCpDtlCreateUser(request);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse batchUpdateCpDtlPersonWithDepPm(BatchUpdatePerson request, String token) {
        if (ValidateUtil.isEmpty(request.getCpId())) return BaseResponse.fail(ResMsg.notId);
        ProjectCp pc = projectCpService.getById(request.getCpId());
        if (pc == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(pc.getState(), Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        getPersonId(request, token);
        List<String> ids = baseMapper.notPersonIdData(request.getCpId());
        if (CollectionUtils.isEmpty(ids)) return BaseResponse.ok();
        request.setAllIds(ids);
        return batchUpdateCpDtlPerson(request);
    }

    @Override
    public BaseResponse batchUpdateCpDtlCreateUserWithDepPm(BatchUpdatePerson request, String token) {
        if (ValidateUtil.isEmpty(request.getCpId())) return BaseResponse.fail(ResMsg.notId);
        ProjectCp pc = projectCpService.getById(request.getCpId());
        if (pc == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(pc.getState(), Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        getPersonId(request, token);
        List<String> ids = baseMapper.notCreateUidData(request.getCpId());
        if (CollectionUtils.isEmpty(ids)) return BaseResponse.ok();
        request.setAllIds(ids);
        return batchUpdateCpDtlCreateUser(request);
    }

    private void getPersonId(BatchUpdatePerson request, String token) {
        BaseResponse res = userProducerFeign.getPmUserInProjectDep(request.getpId(), token);
        if (res == null || !res.getSuccess()) throw new JPMException(ResMsg.userPmCannotGet);
        SysUser user = JacksonUtils.parseObject(JacksonUtils.toJSONString(res.getData()), SysUser.class);
        request.setPersonId(user.getUId());
        request.setPersonName(user.getUName());
    }

    @Override
    public void updateCpDtlStatus(String cpdId, Integer cpdStatus) {
        ProjectCpDtl pojo = baseMapper.selectById(cpdId);
        if (pojo != null) {
            pojo.setCpdStatus(cpdStatus);
            baseMapper.updateById(pojo);
        }
    }

    @Override
    public BaseResponse getCpDtlById(String cpdId) {
        ProjectCpDtlResponse data = baseMapper.getCpDtlById(cpdId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse setCpDtlStatus(String cpdId) {
        ProjectCpDtl entity = baseMapper.selectById(cpdId);
        updateProCpDtlAllParentState(entity, null, null, null);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse updateFileTypeBath(LibraryFileTypeDTO request) {
        if (!CollectionUtils.isEmpty(request.getIds())) {
            baseMapper.updateFileTypeBath(request.getIds(), request.getFileType());
        }
        return BaseResponse.ok();
    }

    @Override
    public List<ProjectCpDtlResponse> getProjectCpDtlByCpIds(List<String> cpIds) {
        if (ValidateUtil.isEmpty(cpIds)) return null;
        return baseMapper.getProjectCpDtlByCpIds(cpIds);
    }

    @Override
    public List<ProjectCpDtl> getAllByCpId(String cpId) {
        QueryWrapper<ProjectCpDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cp_Id", cpId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public ProjectCpDtl getByCpAndCpl(String cpId, String cplId) {
        LambdaQueryWrapper<ProjectCpDtl> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProjectCpDtl::getCpId, cpId);
        wrapper.eq(ProjectCpDtl::getCpdCplId, cplId);
        wrapper.ne(ProjectCpDtl::getCpdState, ApprovalStatusEnum.Invalid.getIndex());
        wrapper.ne(ProjectCpDtl::getCpdStatus, "0");
        List<ProjectCpDtl> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) return data.stream().findFirst().get();
        return null;
    }


    @Override
    public BaseResponse getInfo(ProjectCpInfoRequest request) {
        //添加鉴权String authSql = DataAuthSqlUtil.getAuthSql(uId,"Create_Uid","id",uType);
/*        String authSql = DataAuthSqlUtil.getAuthSql(request.getUId(), "Create_Uid", "id", request.getUType());
        request.setAuthSql(authSql);*/
        String authSql = sysUserService.getAuthSql("Project_Cp_Dtl", request.getUId(), null, OutUserAuthEnum.NOT.getIndex());
        request.setAuthSql(authSql);
        IPage<ProjectInfoDTO> data = baseMapper.getProjectInfo(new Page<>(request.getPage(), request.getSize()), request);
        return BaseResponse.ok(data.getRecords(), (int) data.getTotal());
    }

    @Override
    public BaseResponse createProjectCpInfo(ProjectCpDtl projectCpDtl) {
        projectCpDtl.setCpdId(UUID.randomUUID().toString());
        save(projectCpDtl);
        return createProjectCpDtl(projectCpDtl);
    }

    @Override
    public BaseResponse cancelProjectCpInfo(String cpdId, String cprId) {
        ProjectCpRpr projectCpRpr = projectCpRprService.getById(cprId);
        if (ValidateUtil.isNull(projectCpRpr)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        RecursivelyCancelDownwards(cpdId);
        ProjectCpDtl entity = getById(cpdId);
        RecursivelyCancelUpwards(entity.getCpdParentId());
        if (projectCpRpr.getState().equals(Already_Over.getIndex())) {
            ProjectCp cp = projectCpService.getById(entity.getCpId());
            GanttPlan gp = ganttPlanService.selectByPid(cp.getpId());
            if (gp != null) {
                LambdaQueryWrapper<GanttTasks> tw = new LambdaQueryWrapper<>();
                tw.eq(GanttTasks::getPlanId, gp.getPlanId());
                tw.eq(GanttTasks::getCplId, entity.getCpdCplId());
                tw.eq(GanttTasks::getTaskType, "01");
                tw.eq(ValidateUtil.isNotNull(entity.getGsId()), GanttTasks::getGsId, entity.getGsId());
                List<GanttTasks> tasks = ganttTasksService.list(tw);
                if (!CollectionUtils.isEmpty(tasks)) {
                    tasks.forEach(e -> e.setMustHandle(true));
                    ganttTasksService.updateBatchById(tasks);
                }
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse dealWith(String id) {
        ProjectCpRpr projectCpRpr = projectCpRprService.getById(id);
        if (Objects.isNull(projectCpRpr))
            return BaseResponse.fail("projectCpRpr is null");
        return RecursivelyUpdateParentNodesForCompleted(projectCpRpr);
    }

    @Override
    public BaseResponse dealWithNotPlan(String id) {
        //获取file表信息
        SysFile sysFile = sysFileService.getById(id);
        if (Objects.isNull(sysFile))
            return BaseResponse.fail("sysFile is null");

        if ("7".equals(sysFile.getFileflowstatus())) {
            String tId = sysFile.gettId();
            if (ValidateUtil.isNotEmpty(tId)) {
                String tableStr = apiUtils.getFileTemplateTableById(tId);
                if (ValidateUtil.isNotEmpty(tableStr)) {
                    String[] tables = tableStr.split(",");
                    String table = tables[0];
                    String keyStr = tables[1];
                    if (ValidateUtil.isNotEmpty(keyStr) && ValidateUtil.isNotEmpty(table)) {
                        Map<String, Object> map = baseMapper.selectInfoInTable(table, keyStr, id);
                        if (Objects.nonNull(map)) {
                            String isEnd = "";
                            String cpdId = "";
                            for (String key : map.keySet()) {
                                if (key.toLowerCase().equals("is_End".toLowerCase())) {
                                    isEnd = map.get(key) + "";
                                }
                                if (key.toLowerCase().equals("cpd_Id".toLowerCase())) {
                                    cpdId = map.get(key) + "";
                                }
                            }
                            if (("1".equals(isEnd) || "true".equals(isEnd) && ValidateUtil.isNotEmpty(cpdId)) ){
                                ProjectCpDtl obj = baseMapper.selectById(cpdId);
                                ProjectCpDtlRequest projectCpDtlRequest = new ProjectCpDtlRequest();
                                BeanUtils.copyProperties(obj, projectCpDtlRequest);
                                projectCpDtlRequest.setCpdState(7);
                                return updateProCpDtlByCpDtlId(projectCpDtlRequest);
                            }

                        }
                    }
                }
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 新增程序
     * 在程序库向上递归找到所有程序库的程序，然后加入到dtl中
     */
    public BaseResponse createProjectCpDtl(ProjectCpDtl entity) {
        createProjectCpDtlRecursively(entity);
        return BaseResponse.ok();
    }

    /**
     * 递归将新增
     *
     * @param entity
     */
    private void createProjectCpDtlRecursively(ProjectCpDtl entity) {
        String cxkId = entity.getCpdCplId();
        ProjectCpLibrary library = projectCpLibraryService.getById(cxkId);
        if (library == null || library.getCplParentId() == null) return;

        ProjectCpLibrary parent = projectCpLibraryService.lambdaQuery()
                .eq(ProjectCpLibrary::getCplId, library.getCplParentId())
                .one();
        if (parent == null) return;

        ProjectCp projectCp = projectCpService.lambdaQuery()
                .eq(ProjectCp::getCpId, entity.getCpdId())
                .one();
        if (projectCp == null) return;

        List<String> libaryList = lambdaQuery()
                .eq(ProjectCpDtl::getCpdId, projectCp.getCpId())
                .list()
                .stream()
                .map(ProjectCpDtl::getCpdCplId)
                .collect(Collectors.toList());

        if (!libaryList.contains(parent.getCplId())) {
            ProjectCpDtl projectCpDtl = new ProjectCpDtl();
            BeanUtils.copyProperties(parent, projectCpDtl);
            BeanUtils.copyProperties(entity, projectCpDtl);
            projectCpDtl.setCpdId(UUID.randomUUID().toString());
            save(projectCpDtl);
            createProjectCpDtlRecursively(projectCpDtl);  // Continue recursion with new entity
        }
    }

    /**
     * 向上取消节点状态
     */
    private void RecursivelyCancelUpwards(String parentId) {
        // 获取所有子节点
        List<ProjectCpDtl> sonList = lambdaQuery()
                .eq(ProjectCpDtl::getCpdParentId, parentId)
                .list();

        // 检查是否所有子节点都已取消（status=0）
        boolean allCancelled = sonList.stream()
                .allMatch(son -> son.getCpdStatus() == 0);

        if (!allCancelled) {
            return;
        }

        // 获取当前父节点
        ProjectCpDtl entity = getById(parentId);
        if (entity == null) {
            return;
        }

        // 取消当前节点
        updateCpDtlStatus(parentId, 0);

        // 继续向上递归
        RecursivelyCancelUpwards(entity.getCpdParentId());
    }


    /**
     * 向下取消子节点的状态
     */
    private void RecursivelyCancelDownwards(String id) {
        updateCpDtlStatus(id, 0);
        List<String> sonList = lambdaQuery().eq(ProjectCpDtl::getCpdParentId, id).list().stream().map(projectCpDtl -> projectCpDtl.getCpdId()).collect(Collectors.toList());
        if (sonList.isEmpty()) {
            return;
        } else {
            sonList.forEach(sonId -> {
                updateCpDtlStatus(sonId, 0);
                RecursivelyUpdateDownwards(sonId);
            });
        }
    }


    /**
     * 修改当前节点的状态->修改当前节点的父节点和子节点的状态
     *
     * @param projectCpRpr
     */
    public BaseResponse RecursivelyUpdateParentNodesForCompleted(ProjectCpRpr projectCpRpr) {
        if (projectCpRpr.getState().equals(Already_Over.getIndex())) {
            LambdaQueryWrapper<ProjectCpRpr> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ProjectCpRpr::getCpdId, projectCpRpr.getCpdId());
            wrapper.eq(ProjectCpRpr::getState, Already_Over.getIndex());
            List<ProjectCpRpr> rprs = projectCpRprService.list(wrapper);
            Optional<Date> maxDate = rprs.stream()
                    .map(ProjectCpRpr::getFileApprovalTime)
                    .filter(date -> date != null) // 过滤 null
                    .max(Date::compareTo);
            Optional<Date> minDate = rprs.stream()
                    .map(ProjectCpRpr::getFileApprovalTime)
                    .filter(date -> date != null)
                    .min(Date::compareTo);
            Date startTime = minDate.orElse(null);
            Date finishTime = maxDate.orElse(null);
            ProjectCpDtl entity = getById(projectCpRpr.getCpdId());
            entity.setCpdStartTime(startTime);
            if (projectCpRpr.getIsEnd()) {
                entity.setCpdEndTime(finishTime);
                entity.setCpdState(Already_Over.getIndex());
            }
            baseMapper.updateById(entity);
            ProjectCp cp = projectCpService.getById(entity.getCpId());
            GanttPlan gp = ganttPlanService.selectByPid(cp.getpId());
            if (gp != null) {
                LambdaQueryWrapper<GanttTasks> tw = new LambdaQueryWrapper<>();
                tw.eq(GanttTasks::getPlanId, gp.getPlanId());
                tw.eq(GanttTasks::getCplId, entity.getCpdCplId());
                tw.eq(GanttTasks::getTaskType, "01");
                tw.eq(ValidateUtil.isNotNull(entity.getGsId()), GanttTasks::getGsId, entity.getGsId());
                List<GanttTasks> tasks = ganttTasksService.list(tw);
                if (!CollectionUtils.isEmpty(tasks)) {
                    tasks.forEach(e -> {
                        e.setTaskProgress(100F);
                        e.setTaskBeginTime(startTime);
                        if (projectCpRpr.getIsEnd()) {
                            e.setTaskFinishTime(finishTime);
                        }
                    });
                    ganttTasksService.updateBatchById(tasks);
                }
            }
//            //downwards
//            RecursivelyUpdateDownwards(cpdLd);
//            //upwards
//            RecursivelyUpdateUpwards(cpdLd);
        }
        return BaseResponse.ok();
    }

    /**
     * 向下递归修改子节点的思路
     */
    private void RecursivelyUpdateDownwards(String cpdLd) {
        List<ProjectCpDtl> list = lambdaQuery().eq(ProjectCpDtl::getCpdParentId, cpdLd).list();
        list.stream().forEach(son -> {
            if (!son.getCpdState().equals(Already_Over.getIndex())) {
                son.setCpdState(Already_Over.getIndex());
                updateById(son);
                RecursivelyUpdateDownwards(son.getCpId());
            }
        });
    }

    /**
     * 向上递归修改父节点思路
     * //ProjectCpDtl->get SonProjectCpDtl() -> List<ProjectCpDtl>
     * //List<ProjectCpDtl> -> validate()->true/false
     * //true->update->recursive
     * //false->return
     *
     * @param cpdLd
     */
    private void RecursivelyUpdateUpwards(String cpdLd) {
        ProjectCpDtl entity = getById(cpdLd);
        String cpdParentId = entity.getCpdParentId();
        //projectCpRpr->get ParentProjectCpDtl()->ProjectCpDtl
        if (validate(lambdaQuery().eq(ProjectCpDtl::getCpdParentId, cpdParentId).list())) {
            ProjectCpDtl parent = getById(cpdParentId);
            parent.setCpdState(Already_Over.getIndex());
            RecursivelyUpdateUpwards(cpdParentId);
        } else {
            return;
        }
    }

    private boolean validate(List<ProjectCpDtl> sonList) {
        return sonList.stream().allMatch(son -> son.getCpdState().equals(Already_Over.getIndex()));
    }

    @Override
    public BaseResponse createProjectCpDtlBath(List<ProjectCpDtl> list) {
        Boolean s = saveBatch(list);
        return BaseResponse.ok(s);
    }


    @Override
    public BaseResponse selectProjectCpRprByCpdId(String cpdId, String uId, String uType) {
        //添加鉴权
        String authSql = sysUserService.getAuthSql("Project_Cp_Rpr", uId, "create_uid", OutUserAuthEnum.Approver_Creator.getIndex(), "state", "id", uType);
        List<ProjectCpRpr> data = baseMapper.selectProjectCpRprByCpdId(authSql, cpdId);
        return BaseResponse.ok(data, data.size());
    }

    @Override
    public BaseResponse getGantSectionListByPid(String pId) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<GantSectionDTO> datas = baseMapper.getGantSectionListByPid(pId);
        return BaseResponse.ok(datas);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateCpdTimeByCprId(ProjectCpDtlRequest request) {
        if (ValidateUtil.isEmpty(request.getCprId()) && ValidateUtil.isEmpty(request.getCpdId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        LambdaQueryWrapper<ProjectCpRpr> cpRprLambdaQueryWrapper = new LambdaQueryWrapper<>();
        cpRprLambdaQueryWrapper.eq(ProjectCpRpr::getCpdId, request.getCpdId());
        List<ProjectCpRpr> projectCpRprs = projectCpRprService.list(cpRprLambdaQueryWrapper);
        if (CollectionUtils.isEmpty(projectCpRprs)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        ProjectCpDtl cpd = getById(request.getCpdId());

        // 判断是否是第一次办理
        if (projectCpRprs.size() == 1 && request.getCprId().equals(projectCpRprs.get(0).getId())) {
            // 第一次办理，设置 cpdStartTime
            cpd.setCpdStartTime(projectCpRprs.get(0).getFileApprovalTime());
        }
        ProjectCpRpr currentRecord = projectCpRprService.getById(request.getCprId());
        if (currentRecord != null && currentRecord.getIsEnd() != null && currentRecord.getIsEnd()) {
            // 如果 isEnd == true，设置 cpdEndTime
            cpd.setCpdEndTime(currentRecord.getFileApprovalTime());
        }
        baseMapper.updateById(cpd);
        return BaseResponse.ok();


    }

    @Override
    public BaseResponse getAllCpDtlList(TimeFrameRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<ProjectCpDtlResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectCpDtlResponse> data = baseMapper.getAllCpDtlList(page, request);
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }
}
