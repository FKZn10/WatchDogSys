package com.jpmsoft.projectProducer.service.constructionProcedure.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRequset;
import com.jpmsoft.common.model.request.proCp.ProjectCpLibraryRiskRequest;
import com.jpmsoft.common.model.response.proCp.ProjectCpLibraryResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.constructionProcedure.ProjectCpLibraryMapper;
import com.jpmsoft.projectProducer.pojo.dto.construction.LibraryFileTypeDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpChangeDtl;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpLibrary;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpLibraryRisk;
import com.jpmsoft.projectProducer.service.constructionProcedure.*;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProjectCpLibraryImpl extends ServiceImpl<ProjectCpLibraryMapper, ProjectCpLibrary> implements IProjectCpLibraryService {


    @Resource
    private IProjectCplTemplateDtlService projectCplTemplateDtlService;
    @Resource
    private IProjectCpDtlService projectCpDtlService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private IProjectCpChangeDtlService projectCpChangeDtlService;
    @Resource
    private IProjectCpLibraryRiskService projectCpLibraryRiskService;

    @Override
    public BaseResponse createOrUpdateProCpLibrary(ProjectCpLibraryRequset request) {
        ProjectCpLibrary entity = new ProjectCpLibrary();
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<ProjectCpLibrary> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpl_Code", request.getCplCode());
        if (!ValidateUtil.isEmpty(entity.getCplId()))
            wrapper.ne("Cpl_Id", request.getCplId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        if (ValidateUtil.isEmpty(entity.getCplId())) {
            entity.setCplId(GenerateUUID.getUUID());
            entity.setCplCreateTime(new Date());
            if (ValidateUtil.isEmpty(entity.getCplParentId()))
                entity.setCplParentId("0");
            QueryWrapper<ProjectCpLibrary> listWrapper = new QueryWrapper<>();
            listWrapper.eq("Cpl_Parent_Id", request.getCplParentId());
            Integer count = baseMapper.selectCount(listWrapper).intValue();
            entity.setCplSort(count + 1);
            baseMapper.insert(entity);
        } else {
            ProjectCpLibrary model = baseMapper.selectById(entity.getCplId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (model.getCplId().equals(request.getCplParentId()))
                return BaseResponse.fail(ResMsg.parentNodeError);
            entity.setCplCreateTime(model.getCplCreateTime());
            entity.setCplCreateUid(model.getCplCreateUid());
            entity.setCplCreateUname(model.getCplCreateUname());
//            entity.setCplSort(model.getCplSort());
            //20220922新增，建设程序库修改时可以修改父级关联(zcl)
            String oldParentId = model.getCplParentId();
            String newParentId = entity.getCplParentId();
            if (!oldParentId.equals(newParentId)) {
                //父级id发生变动，重新生成编号
                //获取原编号下的所有兄弟信息
                QueryWrapper<ProjectCpLibrary> oldListWrapper = new QueryWrapper<>();
                oldListWrapper.eq("Cpl_Parent_Id", model.getCplParentId());
                oldListWrapper.ne("Cpl_Id", entity.getCplId());
                oldListWrapper.orderByAsc("Cpl_Sort");
                List<ProjectCpLibrary> libraryList = baseMapper.selectList(oldListWrapper);
                if (libraryList.size() > 0) {
                    for (int i = 0; i < libraryList.size(); i++) {
                        ProjectCpLibrary obj = libraryList.get(i);
                        obj.setCplSort(i + 1);
                        baseMapper.updateById(obj);
                    }
                }
                //获取现父级下的最大编号信息
                QueryWrapper<ProjectCpLibrary> listWrapper = new QueryWrapper<>();
                listWrapper.eq("Cpl_Parent_Id", request.getCplParentId());
                Integer countNew = baseMapper.selectCount(listWrapper).intValue();
                entity.setCplSort(countNew + 1);
            }
            //新增代码结束
            baseMapper.updateById(entity);
        }
        projectCpLibraryRiskService.remove(Wrappers.<ProjectCpLibraryRisk>lambdaQuery().eq(ProjectCpLibraryRisk::getCplId, entity.getCplId()));
        if (ValidateUtil.isNotEmpty(request.getRiskList())) {
            List<ProjectCpLibraryRisk> riskList = new ArrayList<>();
            for (ProjectCpLibraryRiskRequest riskResponse : request.getRiskList()) {
                ProjectCpLibraryRisk risk = new ProjectCpLibraryRisk();
                risk.setId(GenerateUUID.getUUID());
                risk.setRpmId(riskResponse.getRpmId());
                risk.setCplId(entity.getCplId());
                riskList.add(risk);
            }
            projectCpLibraryRiskService.saveBatch(riskList);
        }
        return BaseResponse.ok(entity.getCplId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteProCpLibraryById(String cplId) {
        ProjectCpLibrary model = baseMapper.selectById(cplId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<ProjectCpLibrary> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpl_Parent_Id", cplId);
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.delAssociated);
        if (projectCpDtlService.getProCpDtlCountByCplId(cplId) > 0 ||
                projectCplTemplateDtlService.getProCplTemplateDtlCountByCplId(cplId) > 0)
            return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(cplId);
        List<ProjectCpLibrary> sameLevelData = baseMapper.selectSameLevelBySort(model.getCplParentId(), model.getCplSort());
        if (sameLevelData != null && sameLevelData.size() > 0) {
            List<ProjectCpLibrary> updateSorts = new ArrayList<>();
            sameLevelData.forEach(entity -> {
                entity.setCplSort(entity.getCplSort() - 1);
                updateSorts.add(entity);
            });
            if (updateSorts != null && updateSorts.size() > 0)
                updateBatchById(updateSorts);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProCpLibraryTreeList(ProjectCpLibraryRequset request) {
        List<ProjectCpLibrary> list = baseMapper.getList(request);
        if (ValidateUtil.isNotEmpty(request.getCplName()) || ValidateUtil.isNotEmpty(request.getCplCode())) {
            list = filterCpLibraryWithParents(list, request);
        }
        return BaseResponse.ok(buildCpLibraryTree(list));
    }

    @Override
    public BaseResponse getProCpChangeDtlLibraryTreeList(String id, String teId) {
        List<ProjectCpChangeDtl> data = projectCpChangeDtlService.getAllByCpId(id);
        if (CollectionUtils.isEmpty(data)) {
            List<ProjectCpDtl> pcds = projectCpDtlService.getAllByCpId(id);
            if (!CollectionUtils.isEmpty(pcds))
                data = pcds.stream().map(d -> {
                    ProjectCpChangeDtl entity = new ProjectCpChangeDtl();
                    BeanUtils.copyProperties(d, entity);
                    return entity;
                }).collect(Collectors.toList());
        }
        QueryWrapper<ProjectCpLibrary> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.orderByAsc("cpl_sort");
        List<ProjectCpLibrary> allCpls = baseMapper.selectList(wrapper);
        List<ProjectCpLibrary> list = new ArrayList<>();
        for (ProjectCpLibrary cpl : allCpls) {
            if (!CollectionUtils.isEmpty(data)) {
                List<ProjectCpChangeDtl> ds = data.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCpdCplId()) && n.getCpdCplId().equals(cpl.getCplId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(ds)) {
                    cpl.setCplState(ds.get(0).getCpdState() + "");
                    cpl.setCplStatus(ds.get(0).getCpdStatus());
                } else {
                    cpl.setCplState("0");
                    cpl.setCplStatus(1);
                }
            }
            list.add(cpl);
        }
        return BaseResponse.ok(buildCpLibraryTree(list));
    }

    @Override
    public BaseResponse upOrDownProCpLibrary(String cplId, String action, String teId) {
        ProjectCpLibrary entity = baseMapper.selectById(cplId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<ProjectCpLibrary> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("Cpl_Parent_Id", entity.getCplParentId());
        wrapper.orderByAsc("Cpl_Sort");
        List<ProjectCpLibrary> list = baseMapper.selectList(wrapper);
        if (list.size() > 1) {
            ProjectCpLibrary entity1;
            int index = list.indexOf(entity);
            if (action.toUpperCase(Locale.ENGLISH).equals("UP")) {
                if (index == 0)
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = list.get(index - 1);
            } else if (action.toUpperCase(Locale.ENGLISH).equals("DOWN")) {
                if (index == list.size())
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = list.get(index + 1);
            } else
                return BaseResponse.fail(ResMsg.actError);
            Integer sort = entity.getCplSort();
            entity.setCplSort(entity1.getCplSort());
            entity1.setCplSort(sort);
            updateBatchById(Arrays.asList(entity, entity1));
        } else {
            return BaseResponse.fail(ResMsg.actError);
        }
        return BaseResponse.ok();
    }

    @Override
    public List<ProjectCpLibrary> getAllList(String teId) {
        QueryWrapper<ProjectCpLibrary> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id",teId);
        wrapper.orderByAsc("Cpl_Sort");
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BaseResponse updateLibraryFileTypeBath(LibraryFileTypeDTO request) {
        if (CollectionUtils.isEmpty(request.getIds())) return BaseResponse.ok();
        baseMapper.updateLibraryFileTypeBath(request.getIds(),request.getFileType());
        projectCplTemplateDtlService.updateLibraryFileTypeBath(request);
        return BaseResponse.ok();
    }

    /**
     * 过滤出符合条件的
     *
     * @param list
     * @param request
     * @return
     */
    private List<ProjectCpLibrary> filterCpLibraryWithParents(List<ProjectCpLibrary> list, ProjectCpLibraryRequset request) {
        Map<String, ProjectCpLibrary> idMap = list.stream()
                .collect(Collectors.toMap(ProjectCpLibrary::getCplId, item -> item, (oldValue, newValue) -> oldValue));
        Set<String> keepIds = new HashSet<>();
        for (ProjectCpLibrary node : list) {
            if (!matchCpLibrarySearch(node, request)) {
                continue;
            }
            ProjectCpLibrary current = node;
            while (current != null && keepIds.add(current.getCplId())) {
                current = idMap.get(current.getCplParentId());
            }
        }
        return list.stream().filter(n -> keepIds.contains(n.getCplId())).collect(Collectors.toList());
    }

    /**
     * 判断节点是否符合查询条件
     *
     * @param node
     * @param request
     * @return
     */
    private boolean matchCpLibrarySearch(ProjectCpLibrary node, ProjectCpLibraryRequset request) {
        boolean nameMatched = ValidateUtil.isEmpty(request.getCplName())
                || (ValidateUtil.isNotEmpty(node.getCplName()) && node.getCplName().toLowerCase(Locale.ENGLISH).contains(request.getCplName().toLowerCase(Locale.ENGLISH)));
        boolean codeMatched = ValidateUtil.isEmpty(request.getCplCode())
                || (ValidateUtil.isNotEmpty(node.getCplCode()) && node.getCplCode().toLowerCase(Locale.ENGLISH).contains(request.getCplCode().toLowerCase(Locale.ENGLISH)));
        boolean statusMatched = ValidateUtil.isEmpty(request.getCplState())
                || (ValidateUtil.isNotEmpty(node.getCplState()) && node.getCplState().equals(request.getCplState()));
        return nameMatched && codeMatched && statusMatched;
    }

    /**
     * 构建树形结构
     *
     * @param list
     * @return
     */
    private List<ProjectCpLibraryResponse> buildCpLibraryTree(List<ProjectCpLibrary> list) {
        Map<String, List<ProjectCpLibrary>> parentMap = list.stream()
                .collect(Collectors.groupingBy(item -> ValidateUtil.isEmpty(item.getCplParentId()) ? "0" : item.getCplParentId(), LinkedHashMap::new, Collectors.toList()));
        List<ProjectCpLibrary> rootNodes = parentMap.getOrDefault("0", new ArrayList<>());
        return buildCpLibraryChildTree(rootNodes, parentMap);
    }

    /**
     * 构建子节点树形结构
     *
     * @param nodes
     * @param parentMap
     * @return
     */
    private List<ProjectCpLibraryResponse> buildCpLibraryChildTree(List<ProjectCpLibrary> nodes, Map<String, List<ProjectCpLibrary>> parentMap) {
        List<ProjectCpLibraryResponse> trees = new ArrayList<>();
        for (ProjectCpLibrary node : nodes) {
            ProjectCpLibraryResponse tree = new ProjectCpLibraryResponse();
            BeanUtils.copyProperties(node, tree);
            tree.setCplProId(ValidateUtil.isEmpty(tree.getCplProId()) ? null : tree.getCplProId());
            tree.setChildNodes(buildCpLibraryChildTree(parentMap.getOrDefault(node.getCplId(), new ArrayList<>()), parentMap));
            trees.add(tree);
        }
        return trees;
    }
}
