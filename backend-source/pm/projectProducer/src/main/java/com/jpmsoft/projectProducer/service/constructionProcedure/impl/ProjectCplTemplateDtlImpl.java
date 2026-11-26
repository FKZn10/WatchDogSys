package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.common.model.response.proCp.ProjectCplTemplateDtlResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCplTemplateDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.construction.LibraryFileTypeDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCplTemplateDtl;
import com.jpmsoft.projectProducer.service.constructionProcedure.IProjectCplTemplateDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
public class ProjectCplTemplateDtlImpl extends ServiceImpl<ProjectCplTemplateDtlMapper, ProjectCplTemplateDtl> implements IProjectCplTemplateDtlService {

    @Override
    public void createOrUpdateProCplTemplateDtl(List<ProjectCpLibraryRequset> proCpLibrarys, String cpltId) {
        deleteProCplTemplateDtlById(cpltId);
        List<ProjectCplTemplateDtl> pojos = new ArrayList<>();
        if (proCpLibrarys != null && proCpLibrarys.size() > 0)
            proCpLibrarys = proCpLibrarys.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(ProjectCpLibraryRequset::getCplId))), ArrayList::new));
//        List<ProjectCpLibraryRequset> datas = proCpLibrarys.stream().filter(n -> n.getCplParentId().equals("0")).collect(Collectors.toList());
        proCpLibrarys.forEach(data -> {
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
            pojos.add(pojo);
//            List<ProjectCpLibraryRequset> childDatas = proCpLibrarys.stream().filter(n -> n.getCplParentId().equals(data.getCplId())).collect(Collectors.toList());
//            if (childDatas.size() > 0)
//                analysisAndAssembleList(pojos, childDatas, proCpLibrarys, cpltId, pojo.getCpltdId());
        });
        saveBatch(pojos);
    }

    @Override
    public void deleteProCplTemplateDtlById(String cpltId) {
        QueryWrapper<ProjectCplTemplateDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cplt_Id", cpltId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getProCplTemplateDtlTreeByCpltId(String cpltId) {
        List<ProjectCplTemplateDtl> list = baseMapper.getProCplTemplateDtlByCpltId(cpltId);
        List<ProjectCplTemplateDtl> rootNodes = list.stream().filter(n -> n.getCpltdParentId().equals("0")).collect(Collectors.toList());
        List<ProjectCplTemplateDtlResponse> trees = new ArrayList<>();
        rootNodes.forEach(node -> {
            ProjectCplTemplateDtlResponse tree = new ProjectCplTemplateDtlResponse();
            BeanUtils.copyProperties(node, tree);
            List<ProjectCplTemplateDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCplTemplateDtl> childs = list.stream().filter(n -> n.getCpltdParentId().equals(node.getCpltdCplId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getProCplTemplateDtlChildNodes(childNodes, childs, list);
            tree.setChildNodes(childNodes);
            trees.add(tree);
        });
        return BaseResponse.ok(trees);
    }

    @Override
    public BaseResponse getProCplTemplateDtlIdListByCpltId(String cpltId) {
        List<ProjectCplTemplateDtl> list = baseMapper.getProCplTemplateDtlByCpltId(cpltId);
        List<String> data = list.stream().map(ProjectCplTemplateDtl::getCpltdCplId).collect(Collectors.toList());
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse deleteProCplTemplateDtlByCpltdId(String cpltdId) {
//        QueryWrapper<ProjectCplTemplateDtlPojo> wrapper = new QueryWrapper<>();
//        wrapper.eq("Cpltd_Id", cpltdId);
//        ProjectCplTemplateDtlPojo entity = baseMapper.selectOne(wrapper);
//        if (entity == null)
//            return BaseResponse.fail(ResMsg.infoNotExist);
//        QueryWrapper<ProjectCplTemplateDtlPojo> query = new QueryWrapper<>();
//        query.eq("Cpltd_Id", entity.getCpltId());
//        query.eq("Cpltd_Parent_Id", entity.getCpltdCplId());
//        if (baseMapper.selectCount(query) > 0)
//            return BaseResponse.fail("存在子节点!");
//        baseMapper.deleteById(cpltdId);
        return BaseResponse.fail(ResMsg.stateNotDel);
    }

    @Override
    public Integer getProCplTemplateDtlCountByCplId(String cplId) {
        QueryWrapper<ProjectCplTemplateDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpltd_Cpl_Id", cplId);
        return baseMapper.selectCount(wrapper).intValue();
    }

    @Override
    public void updateLibraryFileTypeBath(LibraryFileTypeDTO request) {
        if (request==null) return;
        if (CollectionUtils.isEmpty(request.getIds())) return;
        baseMapper.updateLibraryFileTypeBath(request.getIds(),request.getFileType());
    }


    private void getProCplTemplateDtlChildNodes(List<ProjectCplTemplateDtlResponse> trees, List<ProjectCplTemplateDtl> rootNodes, List<ProjectCplTemplateDtl> list) {
        rootNodes.forEach(node -> {
            ProjectCplTemplateDtlResponse tree = new ProjectCplTemplateDtlResponse();
            BeanUtils.copyProperties(node, tree);
            List<ProjectCplTemplateDtlResponse> childNodes = new ArrayList<>();
            List<ProjectCplTemplateDtl> childs = list.stream().filter(n -> n.getCpltdParentId().equals(node.getCpltdCplId())).collect(Collectors.toList());
            if (childs.size() > 0)
                getProCplTemplateDtlChildNodes(childNodes, childs, list);
            tree.setChildNodes(childNodes);
            trees.add(tree);
        });
    }

    @Deprecated
    private void analysisAndAssembleList(List<ProjectCplTemplateDtl> pojos, List<ProjectCpLibraryRequset> datas, List<ProjectCpLibraryRequset> proCpLibrarys, String cpltId, String parentId) {
        datas.forEach(data -> {
            ProjectCplTemplateDtl pojo = new ProjectCplTemplateDtl();
            pojo.setCpltdId(GenerateUUID.getUUID());
            pojo.setCpltId(cpltId);
            pojo.setCpltdType(data.getCplType());
            pojo.setCpltdRemark(data.getCplRemark());
            pojo.setCpltdProId(data.getCplProId());
            pojo.setCpltdParentId(parentId);
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
            pojos.add(pojo);
            List<ProjectCpLibraryRequset> childDatas = proCpLibrarys.stream().filter(n -> n.getCplParentId().equals(data.getCplId())).collect(Collectors.toList());
            if (childDatas.size() > 0)
                analysisAndAssembleList(pojos, childDatas, proCpLibrarys, cpltId, pojo.getCpltdId());
        });
    }
}
