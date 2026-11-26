package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.proCp.ProjectCpDtlResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpChangeDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpChangeDtl;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpChangeDtlService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpDtlService;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCpLibraryService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectCpDtlChangeImpl extends ServiceImpl<ProjectCpChangeDtlMapper, ProjectCpChangeDtl> implements IProjectCpChangeDtlService {

    @Resource
    private IProjectCpDtlService projectCpDtlService;
    @Resource
    private IProjectCpLibraryService projectCpLibraryService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private UserProducerFeign userProducerFeign;


    @Override
    public void deleteProCpChangeDtlByCpId(String cpId) {
        QueryWrapper<ProjectCpChangeDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cp_Id", cpId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void createOrUpdateProCpChangeDtl(List<ProjectCpLibraryRequset> data, String cpId, String changeId) {
        deleteProCpChangeDtlByCpId(cpId);
        List<ProjectCpChangeDtl> all = null;
        List<ProjectCpChangeDtl> adds = new ArrayList<>();
        List<ProjectCpDtl> pcds = projectCpDtlService.getAllByCpId(changeId);
        if (!CollectionUtils.isEmpty(pcds)) all = pcds.stream().map(d -> {
            ProjectCpChangeDtl entity = new ProjectCpChangeDtl();
            BeanUtils.copyProperties(d, entity);
            entity.setId(GenerateUUID.getUUID());
            entity.setCpdCreateTime(new Date());
            return entity;
        }).collect(Collectors.toList());

        for (ProjectCpLibraryRequset cpl : data) {
            List<ProjectCpChangeDtl> olds = all.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCpdCplId()) && n.getCpdCplId().equals(cpl.getCplId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(olds)) {
                ProjectCpChangeDtl old = olds.get(0);
                old.setCpdStatus(cpl.getCplStatus());
                old.setCpId(cpId);
                adds.add(old);
            } else {
                ProjectCpChangeDtl pojo = new ProjectCpChangeDtl();
                pojo.setId(GenerateUUID.getUUID());
                pojo.setCpId(cpId);
                pojo.setCpdId(null);
                pojo.setCpdType(cpl.getCplType());
                pojo.setCpdRemark(cpl.getCplRemark());
                pojo.setCpdProId(cpl.getCplProId());
                pojo.setCpdParentId(cpl.getCplParentId());
                pojo.setCpdName(cpl.getCplName());
                pojo.setCpdHtml(cpl.getCplHtml());
                pojo.setCpdDays(cpl.getCplDays());
                pojo.setCpdCode(cpl.getCplCode());
                pojo.setCpdAdministrationUrl(cpl.getCplAdministrationUrl());
                pojo.setCpdAdministrationUrlH(cpl.getCplAdministrationUrlH());
                pojo.setCpdAdministration(cpl.getCplAdministration());
                pojo.setCpdSort(cpl.getCplSort());
                pojo.setCpdCplId(cpl.getCplId());
                pojo.setCpdState(Integer.parseInt(cpl.getCplState()));
                pojo.setTCode(cpl.gettCode());
                pojo.setCpdStatus(2);
                adds.add(pojo);
            }
        }
        if (!CollectionUtils.isEmpty(adds)) saveBatch(adds);
    }

    @Override
    public List<ProjectCpChangeDtl> getAllByCpId(String cpId) {
        QueryWrapper<ProjectCpChangeDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cp_Id", cpId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<String> getProjectCpChangeCplIdList(String id) {
        List<ProjectCpChangeDtl> data = getAllByCpId(id);
        if (!CollectionUtils.isEmpty(data))
            return data.stream().map(ProjectCpChangeDtl::getCpdCplId).collect(Collectors.toList());
        else {
            List<ProjectCpDtl> pcds = projectCpDtlService.getAllByCpId(id);
            if (!CollectionUtils.isEmpty(pcds))
                return pcds.stream().map(ProjectCpDtl::getCpdCplId).collect(Collectors.toList());
            else return null;
        }
    }

    @Override
    public void approve2SyncProjectCpDtl(String id, String changeId) {
        List<ProjectCpChangeDtl> data = getAllByCpId(id);
        List<ProjectCpDtl> olds = projectCpDtlService.getAllByCpId(changeId);
        //新增修改后所有的建设程序详情数据
        List<ProjectCpDtl> allCpDtls = new ArrayList<>();
        List<String> oldCplIds = olds.stream().map(ProjectCpDtl::getCpdCplId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(data)) {
            //region 标记取消的数据
            List<ProjectCpChangeDtl> cancels = data.stream().filter(n -> ValidateUtil.isNotNull(n.getCpdStatus()) && n.getCpdStatus() == 0).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(cancels)) {
                List<ProjectCpDtl> updates = cancels.stream().filter(n -> oldCplIds.contains(n.getCpdCplId()) && ValidateUtil.isNotEmpty(n.getCpdId())).map(d -> {
                    ProjectCpDtl entity = new ProjectCpDtl();
                    BeanUtils.copyProperties(d, entity);
                    entity.setCpId(changeId);
                    entity.setCpdStatus(0);
                    entity.setCpdState(ApprovalStatusEnum.Invalid.getIndex());
                    return entity;
                }).collect(Collectors.toList());
                projectCpDtlService.updateBatchById(updates);
                sysFileService.updateFileStateBatchIds(updates.stream().map(ProjectCpDtl::getCpdId).collect(Collectors.toList()), ApprovalStatusEnum.Invalid.getIndex());
                olds.forEach(old -> {
                    String cpdId = old.getCpdId();
                    List<ProjectCpDtl> news = updates.stream().filter(n -> n.getCpdId().equals(cpdId)).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(news)) old = news.get(0);
                    allCpDtls.add(old);
                });
            } else allCpDtls.addAll(olds);
            //endregion
            //region 标记新增的数据
            List<ProjectCpChangeDtl> adds = data.stream().filter(n -> ValidateUtil.isNotNull(n.getCpdStatus()) && !oldCplIds.contains(n.getCpdCplId()) && n.getCpdStatus() == 2).collect(Collectors.toList());
            List<SysFile> sysFiles = new ArrayList<>();
            FileTemplateTree tmp = userProducerFeign.getFileTemplateByCode("ProcedureHandle");
            if (!CollectionUtils.isEmpty(adds)) {
                List<ProjectCpDtl> creates = adds.stream().map(d -> {
                    ProjectCpDtl entity = new ProjectCpDtl();
                    BeanUtils.copyProperties(d, entity);
                    entity.setCpdId(GenerateUUID.getUUID());
                    entity.setCpId(changeId);
                    entity.setCpdStatus(1);
                    entity.setCpdState(0);
                    entity.setStateTime(new Date());
                    entity.setCpdCreateTime(new Date());
                    sysFiles.add(sysFileService.structureFile(entity.getCpdId(), tmp.gettId(), entity.getCpdState() + "", entity.getCpdCreateUid(), entity.getCpdCreateUname(), entity.getCpdCreateTime(), null, tmp.gettName(), tmp.gettCode(), GenerateUUID.getUUID(), null, tmp.gettCode()));
                    return entity;
                }).collect(Collectors.toList());
                projectCpDtlService.saveBatch(creates);
                sysFileService.saveBatch(sysFiles);
                allCpDtls.addAll(creates);
            }
            //endregion
            //重设上级状态
            projectCpDtlService.resetAllCpDtlState(allCpDtls, true);
        }
    }

    @Override
    public BaseResponse getProCpChangeDtlTreeById(String id, String teId) {
        List<ProjectCpChangeDtl> data = getAllByCpId(id);
        List<ProjectCpDtlResponse> list = data.stream().map(d -> {
            ProjectCpDtlResponse res = new ProjectCpDtlResponse();
            BeanUtils.copyProperties(d, res);
            return res;
        }).collect(Collectors.toList());
        List<ProjectCpDtlResponse> tree = new ArrayList<>();
        getTree(tree, "0", list);
        return BaseResponse.ok(tree);
    }

    private void getTree(List<ProjectCpDtlResponse> trees, String parentId, List<ProjectCpDtlResponse> list) {
        for (ProjectCpDtlResponse node : list) {
            if (node.getCpdParentId().equals(parentId)) {
                List<ProjectCpDtlResponse> departmentTrees = new ArrayList<>();
                getTree(departmentTrees, node.getCpdCplId(), list);
                node.setChildNodes(departmentTrees);
                trees.add(node);
            }
        }
    }
}
