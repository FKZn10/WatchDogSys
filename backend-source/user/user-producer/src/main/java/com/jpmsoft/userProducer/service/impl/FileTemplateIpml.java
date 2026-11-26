package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysExtendConfDTO;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.SysFileTemplateDtlDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.FileTemplateMapper;
import com.jpmsoft.userProducer.pojo.dto.FileTemplateDto;
import com.jpmsoft.userProducer.pojo.entity.FileTemplate;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.IFileTemplateService;
import com.jpmsoft.userProducer.service.ISysExtendConfService;
import com.jpmsoft.userProducer.service.ISysFileTemplateDtlService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.HtmlUtils;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 流程文档模板
 * <p>
 * create by:Aaron
 * create time: 2021-06-14
 */
@Service
public class FileTemplateIpml extends ServiceImpl<FileTemplateMapper, FileTemplate> implements IFileTemplateService {


    @Resource
    private RedisUtils redisUtils;

    @Resource
    private ISysExtendConfService sysExtendConfService;
    @Resource
    private ISysFileTemplateDtlService sysFileTemplateDtlService;

    /**
     * 获取流程文档模板信息列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getFileTemplateList(FileTemplateDto request) {
        QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
        if (!CheckNullUtil.checkString(request.gettName())) wrapper.like("tName", request.gettName());
        if (!CheckNullUtil.checkString(request.gettCode())) wrapper.eq("tCode", request.gettCode());
        if (ValidateUtil.isNotEmpty(request.getTeId())) wrapper.eq("Te_Id", request.getTeId());
        if ((!CheckNullUtil.checkString(request.getSortFiled()))) {
            if (request.getSortType().equalsIgnoreCase("ASC")) wrapper.orderByAsc(request.getSortFiled());
            else wrapper.orderByDesc(request.getSortFiled());
        }
        List<FileTemplate> list = baseMapper.selectList(wrapper);
        List<FileTemplate> data = list.stream().skip((request.getPage() - 1) * request.getSize()).limit(request.getSize()).collect(Collectors.toList());
        return BaseResponse.ok(data, list.size());
    }

    @Override
    public List<FileTemplateDto> getFileTemplateList(List<String> idlist, String teId) {
        List<FileTemplateDto> list = getAllFileTemplateList(teId);
        List<FileTemplateDto> date = list.stream().filter(item -> idlist.contains(item.gettId())).collect(Collectors.toList());
        return date;
    }

    @Override
    public String getFileTmpIdByCode(String code, String token) {
        if (ValidateUtil.isEmpty(code)) return null;
        List<FileTemplate> data = getListByCode(code, token);
        if (!CollectionUtils.isEmpty(data)) return data.get(0).gettId();
        return null;
    }


    @Override
    public FileTemplateTree getFileTemplateByCode(String tCode, String token) {
        try {
            List<FileTemplate> data = getListByCode(tCode, token);
            if (!CollectionUtils.isEmpty(data)) {
                FileTemplate ft = data.get(0);
                FileTemplateTree tree = new FileTemplateTree();
                BeanUtils.copyProperties(ft, tree);
                return tree;
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public List<FileTemplateTree> getFileTemplateByCodes(List<String> tCode, String token) {
        try {
            String teId = redisUtils.getTeId(token);
            QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
            if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
            wrapper.in("T_Code", tCode);
            List<FileTemplate> data = baseMapper.selectList(wrapper);
            if (!CollectionUtils.isEmpty(data)) {
                return data.stream().map(ft -> {
                    FileTemplateTree tree = new FileTemplateTree();
                    tree.settCode(ft.gettCode());
                    tree.settId(ft.gettId());
                    tree.settName(ft.gettName());
                    return tree;
                }).collect(Collectors.toList());
            }
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public BaseResponse getFileTemplateByParent(String id, Boolean excludeCancel, String tName, String tCode, String token) {
        List<FileTemplateDto> all = getAllFileTemplateList(redisUtils.getTeId(token));
        List<FileTemplateTree> trees = new ArrayList<>();
        all.forEach(model -> {
            FileTemplateTree vueTree = this.dtoToVueTree(model);
            vueTree.setChildren(new ArrayList<>());
            vueTree.setFileTemplateDtls(model.getFileTemplateDtls());
            trees.add(vueTree);
        });
        // 过滤
        List<FileTemplateTree> filtered = filterFlatWithParents(trees, tName, tCode);
        List<FileTemplateTree> data;
        if (excludeCancel) {
            data = filtered.stream().filter(f -> ValidateUtil.allNotEmpty(f.gettStats(), f.gettParentid()) && "1".equals(f.gettStats()) && f.gettParentid().equals(id)).collect(Collectors.toList());
        } else {
            data = filtered.stream().filter(f -> ValidateUtil.isNotEmpty(f.gettParentid()) && f.gettParentid().equals(id)).collect(Collectors.toList());
        }
        return BaseResponse.ok(data);
    }

    public FileTemplateTree dtoToVueTree(FileTemplateDto model) {
        FileTemplateTree vueTree = new FileTemplateTree();
        vueTree.setKey(model.gettId());
        vueTree.setTitle(model.gettName());
        vueTree.setValue(model.gettId());
        vueTree.setPid(model.gettParentid());
        vueTree.settId(model.gettId());
        vueTree.settName(model.gettName());
        vueTree.settCode(model.gettCode());
        vueTree.settFlowpage(model.gettFlowpage());
        vueTree.settInfopage(model.gettInfopage());
        vueTree.settUpdatepage(model.gettUpdatepage());
        vueTree.settPrintpage(model.gettPrintpage());
        vueTree.settSystem(model.gettSystem());
        vueTree.settGeneral(model.gettGeneral());
        vueTree.settWordpage(model.gettWordpage());
        vueTree.settExcelpage(model.gettExcelpage());
        vueTree.settReportpage(model.gettReportpage());
        vueTree.settCoordinationpage(model.gettCoordinationpage());
        vueTree.settType(model.gettType());
        vueTree.settCreatetime(model.gettCreatetime());
        vueTree.settCreateuser(model.gettCreateuser());
        vueTree.settCreateusername(model.gettCreateusername());
        vueTree.setUpdateTime(model.getUpdateTime());
        vueTree.setUpdateUid(model.getUpdateUid());
        vueTree.setUpdateUserName(model.getUpdateUserName());
        vueTree.settNeedflow(model.gettNeedflow());
        vueTree.settAllowdraft(model.gettAllowdraft());
        vueTree.settContent(model.gettContent());
        vueTree.settDescription(model.gettDescription());
        vueTree.settHelper(model.gettHelper());
        vueTree.settTable(model.gettTable());
        vueTree.settParentid(model.gettParentid());
        vueTree.settSort(model.gettSort() == null ? 0 : model.gettSort());
        vueTree.settStats(model.gettStats());
        vueTree.settSolution(model.gettSolution());
        vueTree.settClient(model.gettClient());
        vueTree.settPrimaryKey(model.gettPrimaryKey());
        vueTree.settFileTips(model.gettFileTips());
        //流程字段
        vueTree.settStateKey(model.gettStateKey());
        vueTree.settStateTimeKey(model.gettStateTimeKey());
        vueTree.settListRequestUrl(model.gettListRequestUrl());
        vueTree.settListRequestType(model.gettListRequestType());
        vueTree.settListRequestParameter(model.gettListRequestParameter());
        vueTree.settCreateRequestParameter(model.gettCreateRequestParameter());
        vueTree.settCreateRequestType(model.gettCreateRequestType());
        vueTree.settCreateRequestUrl(model.gettCreateRequestUrl());
        vueTree.settDetailsRequestParameter(model.gettDetailsRequestParameter());
        vueTree.settDetailsRequestType(model.gettDetailsRequestType());
        vueTree.settDetailsRequestUrl(model.gettDetailsRequestUrl());
        vueTree.settPageSize(model.gettPageSize());
        vueTree.settSlotTemplate(model.gettSlotTemplate());
        vueTree.settColumnContent(model.gettColumnContent());
        vueTree.settTaskName(model.gettTaskName());
        vueTree.settAppFlowpage(model.gettAppFlowpage());
        vueTree.settAppInfopage(model.gettAppInfopage());
        vueTree.settAppUpdatepage(model.gettAppUpdatepage());
        vueTree.setSysExtendConfs(model.getSysExtendConfs());
        vueTree.settIsArchivists(model.gettIsArchivists());
        vueTree.settTableSet(model.gettTableSet());
        vueTree.settIsDraft(model.gettIsDraft());
        vueTree.setAdId(model.getAdId());

        return vueTree;
    }

    /**
     * 平铺过滤
     *
     * @param list
     * @param tName
     * @param tCode
     * @return
     */
    public List<FileTemplateTree> filterFlatWithParents(List<FileTemplateTree> list, String tName, String tCode) {
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        if (ValidateUtil.isEmpty(tName) && ValidateUtil.isEmpty(tCode)) {
            return list;
        }

        // id -> node
        Map<String, FileTemplateTree> idMap = list.stream()
                .filter(n -> ValidateUtil.isNotEmpty(n.gettId()))
                .collect(Collectors.toMap(FileTemplateTree::gettId, n -> n, (a, b) -> a));

        // 先找出命中的节点 id
        String searchTName = CheckNullUtil.checkString(tName) ? null : tName.toLowerCase(Locale.ROOT);
        String searchTCode = CheckNullUtil.checkString(tCode) ? null : tCode.toLowerCase(Locale.ROOT);
        Set<String> keepIds = new LinkedHashSet<>();
        for (FileTemplateTree node : list) {
            boolean nameMatches = searchTName == null || (node.gettName() != null && node.gettName().toLowerCase(Locale.ROOT).contains(searchTName));
            boolean codeMatches = searchTCode == null || (node.gettCode() != null && node.gettCode().toLowerCase(Locale.ROOT).contains(searchTCode));

            if (nameMatches && codeMatches) {
                String curId = node.gettId();
                while (ValidateUtil.isNotEmpty(curId) && idMap.containsKey(curId) && keepIds.add(curId)) {
                    FileTemplateTree cur = idMap.get(curId);
                    String pid = cur.gettParentid();
                    if (ValidateUtil.isEmpty(pid) || !idMap.containsKey(pid)) {
                        break;
                    }
                    curId = pid;
                }
            }
        }

        List<FileTemplateTree> result = new ArrayList<>();
        for (FileTemplateTree node : list) {
            if (keepIds.contains(node.gettId())) {
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 新增、修改、删除、流程文档模板信息
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdateOrDeleteFileTemplateWord(FileTemplateDto request, String token) {
        String tId = request.gettId();
        String requestType = request.getType().toUpperCase(Locale.ENGLISH);
        if (requestType.equals("CREATE") || requestType.equals("CREATE2") || requestType.equals("UPDATE")) {
            //修改删除优先验证code是否重复
            QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
            wrapper.eq("T_Code", request.gettCode());
            if (ValidateUtil.isNotEmpty(request.getTeId())) wrapper.eq("Te_Id", request.getTeId());
            if (requestType.equals("UPDATE")) wrapper.ne("T_ID", request.gettId());
            if (baseMapper.selectCount(wrapper) > 0) return BaseResponse.fail(BaseMsg.infoIsExist);
        }
        List<FileTemplateDto> datas = getAllFileTemplateList(request.getTeId());
        String allKey = redisUtils.tenantKey("allFileTemplateList", request.getTeId());
        String treeKey = redisUtils.tenantKey("FileTemplateTree", request.getTeId());
        String excludeCancel = redisUtils.tenantKey("ExcludeCancelFileTempTree", request.getTeId());
        if (requestType.equals("CREATE") || requestType.equals("CREATE2")) {
            FileTemplate projo = new FileTemplate();
            if (CheckNullUtil.checkString(request.gettId())) {
                projo.settId(GenerateUUID.getUUID());
            } else {
                projo.settId(request.gettId());
            }
            projo.settName(request.gettName());
            projo.settCode(request.gettCode());
            projo.settFlowpage(request.gettFlowpage());
            projo.settInfopage(request.gettInfopage());
            projo.settUpdatepage(request.gettUpdatepage());
            projo.settPrintpage(request.gettPrintpage());
            projo.settSystem(request.gettSystem());
            projo.settGeneral(request.gettGeneral());
            projo.settWordpage(request.gettWordpage());
            projo.settExcelpage(request.gettExcelpage());
            projo.settReportpage(request.gettReportpage());
            projo.settCoordinationpage(request.gettCoordinationpage());
            projo.settType(request.gettType());
            projo.settCreatetime(request.gettCreatetime());
            projo.settCreateuser(request.gettCreateuser());
            projo.settCreateusername(request.gettCreateusername());
            projo.settNeedflow(request.gettNeedflow());
            projo.settAllowdraft(request.gettAllowdraft());
            projo.settContent(request.gettContent());
            projo.settDescription(request.gettDescription());
            projo.settHelper(HtmlUtils.sanitizeHtml(request.gettHelper()));
            projo.settTable(request.gettTable());
            projo.settParentid(request.gettParentid());
            List<FileTemplateDto> sameLevels = datas.stream().filter(n ->n.gettParentid()!=null&& n.gettParentid().equals(request.gettParentid())).collect(Collectors.toList());
            projo.settSort(sameLevels.size() + 1);
            projo.settStats(request.gettStats());
            projo.settSolution(request.gettSolution());
            projo.settClient(request.gettClient());
            projo.settCreatetime(new Date());
            projo.settPrimaryKey(request.gettPrimaryKey());
            //流程字段
            projo.settStateKey(request.gettStateKey());
            projo.settStateTimeKey(request.gettStateTimeKey());
            projo.settFileTips(request.gettFileTips());
            projo.settListRequestUrl(request.gettListRequestUrl());
            projo.settListRequestType(request.gettListRequestType());
            projo.settListRequestParameter(request.gettListRequestParameter());
            projo.settCreateRequestParameter(request.gettCreateRequestParameter());
            projo.settCreateRequestType(request.gettCreateRequestType());
            projo.settCreateRequestUrl(request.gettCreateRequestUrl());
            projo.settDetailsRequestParameter(request.gettDetailsRequestParameter());
            projo.settDetailsRequestType(request.gettDetailsRequestType());
            projo.settDetailsRequestUrl(request.gettDetailsRequestUrl());
            projo.settPageSize(request.gettPageSize());
            projo.settSlotTemplate(request.gettSlotTemplate());
            projo.settColumnContent(request.gettColumnContent());
            projo.settTaskName(request.gettTaskName());
            projo.settAppFlowpage(request.gettAppFlowpage());
            projo.settAppInfopage(request.gettAppInfopage());
            projo.settAppUpdatepage(request.gettAppUpdatepage());
            projo.setTeId(request.getTeId());
            projo.settIsArchivists(request.gettIsArchivists());
            projo.settTableSet(request.gettTableSet());
            projo.settIsDraft(request.gettIsDraft());
            projo.setAdId(request.getAdId());
            baseMapper.insert(projo);
            sysExtendConfService.createOrUpdateSysExtendConf(request.getSysExtendConfs(), projo.gettId());
            redisUtils.delete(allKey, treeKey, excludeCancel);
            // 处理子表
            sysFileTemplateDtlService.createOrUpdateSysFileTemplateDtlBatch(request.getFileTemplateDtls(), projo.gettId());
            return BaseResponse.ok();
        } else if (requestType.equals("UPDATE")) {
            FileTemplate projo = baseMapper.selectById(request.gettId());
            if (projo != null) {
                if (request.gettParentid().equals(projo.gettId()))
                    return BaseResponse.fail(BaseMsg.selectSuperiorError);
                projo.settName(request.gettName());
                projo.settCode(request.gettCode());
                projo.settFlowpage(request.gettFlowpage());
                projo.settInfopage(request.gettInfopage());
                projo.settUpdatepage(request.gettUpdatepage());
                projo.settPrintpage(request.gettPrintpage());
                projo.settSystem(request.gettSystem());
                projo.settGeneral(request.gettGeneral());
                projo.settWordpage(request.gettWordpage());
                projo.settExcelpage(request.gettExcelpage());
                projo.settReportpage(request.gettReportpage());
                projo.settCoordinationpage(request.gettCoordinationpage());
                projo.settType(request.gettType());
                // projo.settCreatetime(request.gettCreatetime());
                // projo.settCreateuser(request.gettCreateuser());
                // projo.settCreateusername(request.gettCreateusername());
                // 只需要在修改文档模板时刷新更新人的信息，其他更新操作暂时不需要
                LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
                projo.setUpdateTime(new Date());
                projo.setUpdateUid(user.getId());
                projo.setUpdateUserName(user.getName());
                projo.settNeedflow(request.gettNeedflow());
                projo.settAllowdraft(request.gettAllowdraft());
                projo.settContent(request.gettContent());
                projo.settDescription(request.gettDescription());
                projo.settHelper(HtmlUtils.sanitizeHtml(request.gettHelper()));
                projo.settTable(request.gettTable());
                projo.settStats(request.gettStats());
                projo.settSolution(request.gettSolution());
                projo.settPrimaryKey(request.gettPrimaryKey());
                projo.settClient(request.gettClient());
                //流程字段
                projo.settStateKey(request.gettStateKey());
                projo.settStateTimeKey(request.gettStateTimeKey());
                projo.settFileTips(request.gettFileTips());
                projo.settListRequestUrl(request.gettListRequestUrl());
                projo.settListRequestType(request.gettListRequestType());
                projo.settListRequestParameter(request.gettListRequestParameter());
                projo.settCreateRequestParameter(request.gettCreateRequestParameter());
                projo.settCreateRequestType(request.gettCreateRequestType());
                projo.settCreateRequestUrl(request.gettCreateRequestUrl());
                projo.settDetailsRequestParameter(request.gettDetailsRequestParameter());
                projo.settDetailsRequestType(request.gettDetailsRequestType());
                projo.settDetailsRequestUrl(request.gettDetailsRequestUrl());
                projo.settPageSize(request.gettPageSize());
                projo.settSlotTemplate(request.gettSlotTemplate());
                projo.settColumnContent(request.gettColumnContent());
                projo.settTaskName(request.gettTaskName());
                projo.settAppFlowpage(request.gettAppFlowpage());
                projo.settAppInfopage(request.gettAppInfopage());
                projo.settAppUpdatepage(request.gettAppUpdatepage());
                projo.setTeId(request.getTeId());
                projo.settIsArchivists(request.gettIsArchivists());
                projo.settTableSet(request.gettTableSet());
                projo.settIsDraft(request.gettIsDraft());
                projo.setAdId(request.getAdId());
                if (request.gettParentid() != null && !projo.gettParentid().equals(request.gettParentid())) {
                    //修改父级
                    //原同级节点下后续排序全部前进一级  新节点下当前节点排序为最后一级
                    List<FileTemplateDto> oldNodes = datas.stream().filter(n -> n.gettParentid().equals(projo.gettParentid()) && n.gettSort() > projo.gettSort()).collect(Collectors.toList());
                    List<FileTemplateDto> pNodes = datas.stream().filter(n -> n.gettParentid().equals(request.gettParentid())).collect(Collectors.toList());
                    projo.settSort(pNodes.size() + 1);
                    projo.settParentid(request.gettParentid());
                    List<FileTemplate> data = new ArrayList<>();
                    data.add(projo);
                    oldNodes.forEach(model -> {
                        model.settSort(model.gettSort() - 1);
                        FileTemplate t = new FileTemplate();
                        BeanUtils.copyProperties(model, t);
                        data.add(t);
                    });
                    updateBatchById(data);
                } else baseMapper.updateById(projo);
                sysExtendConfService.createOrUpdateSysExtendConf(request.getSysExtendConfs(), projo.gettId());
                redisUtils.delete(allKey, treeKey, excludeCancel);
                sysFileTemplateDtlService.createOrUpdateSysFileTemplateDtlBatch(request.getFileTemplateDtls(), projo.gettId());
                return BaseResponse.ok();
            } else {
                return BaseResponse.fail(BaseMsg.infoNotExist);
            }

        } else if (requestType.equals("DELETE")) {
            FileTemplate pojo = baseMapper.selectById(request.gettId());
            if (pojo != null) {
                sysFileTemplateDtlService.createOrUpdateSysFileTemplateDtlBatch(request.getFileTemplateDtls(), pojo.gettId());
                List<FileTemplateDto> childs = datas.stream().filter(n ->n.gettParentid()!=null&& n.gettParentid().equals(pojo.gettId())).collect(Collectors.toList());
                Integer chilCount = childs.size();
                if (pojo.gettParentid().equals("0")) {
                    List<FileTemplateDto> roots = datas.stream().filter(n ->n.gettParentid()!=null&& n.gettParentid().equals("0") && !n.gettId().equals(pojo.gettId())).collect(Collectors.toList());
                    Integer rootCount = roots.size();
                    if (rootCount == 0) {
                        return BaseResponse.fail(BaseMsg.onlyRootNode);
                    } else if (chilCount > 0) {
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    } else {
                        deleteToChangeOtherSort(pojo, datas);
                        sysExtendConfService.deleteSysExtendConfByTid(request.gettId());
                        baseMapper.deleteById(request.gettId());
                        redisUtils.delete(allKey, treeKey, excludeCancel);
                        return BaseResponse.ok();
                    }
                } else {
                    if (chilCount > 0) {
                        return BaseResponse.fail(BaseMsg.delSubFirst);
                    } else {
                        deleteToChangeOtherSort(pojo, datas);
                        sysExtendConfService.deleteSysExtendConfByTid(request.gettId());
                        baseMapper.deleteById(request.gettId());
                        redisUtils.delete(allKey, treeKey, excludeCancel);
                        return BaseResponse.ok();
                    }
                }
            } else {
                return BaseResponse.fail(BaseMsg.infoNotExist);
            }
        } else {
            return BaseResponse.fail(BaseMsg.unknownAction);
        }

    }


    private Integer getFileTemplateCount(String parentId) {
        QueryWrapper<FileTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("T_ParentID", parentId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }


    private Integer getRootFileTemplateCount(String dId) {
        QueryWrapper<FileTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("T_ParentID", '0');
        queryWrapper.ne("T_ID", dId);
        return baseMapper.selectCount(queryWrapper).intValue();
    }


    /**
     * 获取流程文档模板信息（树形结构）
     *
     * @return
     */
    public BaseResponse getFlowFileTemplateByTree(String tName, String tCode, String token) {
        String teId = redisUtils.getTeId(token);
        String rdKey = redisUtils.tenantKey("FileTemplateTree", teId);
        Object FileTemplateTrees = redisUtils.get(rdKey);
        if (FileTemplateTrees != null) {
            List<FileTemplateTree> data = JacksonUtils.parseObject(FileTemplateTrees.toString(), new TypeReference<List<FileTemplateTree>>() {
            });
            // 过滤
            data = fileTemplateTreeListFilter(data, tName, tCode);
            return new BaseResponse(true, "获取成功", data);
        } else {
            List<FileTemplateDto> list = getAllFileTemplateList(teId);
            List<FileTemplateDto> parentIdList = list.stream().filter(item ->item.gettParentid()!=null&& "0".equals(item.gettParentid())).collect(Collectors.toList());
            List<FileTemplateTree> treeList = new ArrayList<>();
            parentIdList.forEach(model -> {
                FileTemplateTree vueTree = this.dtoToVueTree(model);
                vueTree.setFileTemplateDtls(model.getFileTemplateDtls());
                List<FileTemplateTree> vueTrees = new ArrayList<>();
                GetDictionaryTreeChildren(vueTrees, model.gettId(), list);
                vueTree.setChildren(vueTrees);
                treeList.add(vueTree);
            });
            redisUtils.set(rdKey, JacksonUtils.toJSONString(treeList));
            // 过滤
            List<FileTemplateTree> filtered = fileTemplateTreeListFilter(treeList, tName, tCode);
            return BaseResponse.ok(filtered);
        }
    }

    /**
     * 过滤树形结构
     * <p>
     * 根据传入的tName和tCode进行过滤，返回包含匹配节点及其全部父级节点的树形结构
     * @param treeList
     * @param tName
     * @param tCode
     * @return
     */
    public List<FileTemplateTree> fileTemplateTreeListFilter(List<FileTemplateTree> treeList, String tName, String tCode) {
        if (CollectionUtils.isEmpty(treeList)) {
            return new ArrayList<>();
        }
        // 如果都没有查询条件，直接返回原列表
        if (ValidateUtil.isEmpty(tName) && ValidateUtil.isEmpty(tCode)) {
            return treeList;
        }

        List<FileTemplateTree> result = new ArrayList<>();
        for (FileTemplateTree node : treeList) {
            // 递归过滤子节点
            List<FileTemplateTree> validChildren = fileTemplateTreeListFilter(node.getChildren(), tName, tCode);

            // 检查当前节点是否匹配 (AND 逻辑: 如果传入了参数，必须匹配)
            boolean nameMatches = CheckNullUtil.checkString(tName) || (node.gettName() != null && node.gettName().toLowerCase(Locale.ROOT).contains(tName.toLowerCase(Locale.ROOT)));
            boolean codeMatches = CheckNullUtil.checkString(tCode) || (node.gettCode() != null && node.gettCode().toLowerCase(Locale.ROOT).contains(tCode.toLowerCase(Locale.ROOT)));

            boolean isSelfMatch = nameMatches && codeMatches;

            // 如果当前节点匹配，或者其子节点中有匹配项（即需要保留父级路径），则加入结果集
            if (isSelfMatch || !CollectionUtils.isEmpty(validChildren)) {
                node.setChildren(validChildren);
                result.add(node);
            }
        }
        return result;
    }

    @Override
    public BaseResponse getFileTempByTreeExcludeCancel(String tName, String tCode, String token) {
        String teId = redisUtils.getTeId(token);
        String rdKey = redisUtils.tenantKey("ExcludeCancelFileTempTree", teId);
        Object FileTemplateTrees = redisUtils.get(rdKey);
        if (FileTemplateTrees != null) {
            List<FileTemplateTree> data = JacksonUtils.parseObject(FileTemplateTrees.toString(), new TypeReference<List<FileTemplateTree>>() {
            });
            // 过滤
            data = fileTemplateTreeListFilter(data, tName, tCode);
            return new BaseResponse(true, "获取成功", data);
        } else {
            List<FileTemplateDto> all = getAllFileTemplateList(teId);
            List<FileTemplateDto> list = all.stream().filter(n -> ValidateUtil.isNotEmpty(n.gettStats()) && n.gettStats().equals("1")).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(list)) return BaseResponse.ok();
            List<FileTemplateDto> parentIdList = list.stream().filter(item ->item.gettParentid()!=null&& item.gettParentid().equals("0")).collect(Collectors.toList());
            if (CollectionUtils.isEmpty(parentIdList)) return BaseResponse.ok();
            List<FileTemplateTree> treeList = new ArrayList<>();
            parentIdList.forEach(model -> {
                FileTemplateTree vueTree = new FileTemplateTree();
                BeanUtils.copyProperties(model, vueTree);
                vueTree.setKey(model.gettId());
                vueTree.setTitle(model.gettName());
                vueTree.setValue(model.gettId());
                vueTree.setPid(model.gettParentid());
                vueTree.settSort(model.gettSort() == null ? 0 : model.gettSort());
                vueTree.setAdId(model.getAdId());
                List<FileTemplateTree> vueTrees = new ArrayList<>();
                GetDictionaryTreeChildren(vueTrees, model.gettId(), list);
                vueTree.setChildren(vueTrees);
                treeList.add(vueTree);
            });
            redisUtils.set(rdKey, JacksonUtils.toJSONString(treeList));
            // 过滤
            List<FileTemplateTree> filtered = fileTemplateTreeListFilter(treeList, tName, tCode);
            return BaseResponse.ok(filtered);
        }
    }

    @Override
    public BaseResponse upOrDownFileTemplateSort(String tId, String action, String token) {
        String teId = redisUtils.getTeId(token);
        FileTemplate entity = baseMapper.selectById(tId);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        wrapper.eq("t_parentid", entity.gettParentid());
        wrapper.orderByAsc("t_sort");
        List<FileTemplate> sameLevels = baseMapper.selectList(wrapper);
        Integer index = sameLevels.indexOf(sameLevels.stream().filter(f -> f.gettId().equals(tId)).findFirst().get());
        Integer changeIndex;
        if (action.equals("up") && index > 0) {
            changeIndex = index - 1;
        } else if (action.equals("down") && index < sameLevels.size() - 1) {
            changeIndex = index + 1;
        } else return BaseResponse.fail(BaseMsg.notMove);
        FileTemplate temp = sameLevels.get(index);
        sameLevels.set(index, sameLevels.get(changeIndex));
        sameLevels.set(changeIndex, temp);
        int sort = 1;
        List<FileTemplate> updateData = new ArrayList<>();
        for (FileTemplate s : sameLevels) {
            s.settSort(sort);
            sort++;
            updateData.add(s);
        }
        updateBatchById(updateData);
        redisUtils.delete(redisUtils.tenantKey("allFileTemplateList", teId), redisUtils.tenantKey("FileTemplateTree", teId), redisUtils.tenantKey("ExcludeCancelFileTempTree", teId));
        return BaseResponse.ok();
    }

    private void GetDictionaryTreeChildren(List<FileTemplateTree> treeList, String parentId, List<FileTemplateDto> dataList) {
        List<FileTemplateDto> list = dataList.stream().filter(item ->item.gettParentid()!=null&& item.gettParentid().equals(parentId)).collect(Collectors.toList());
        list.forEach(model -> {
            FileTemplateTree vueTree = this.dtoToVueTree(model);
            vueTree.setFileTemplateDtls(model.getFileTemplateDtls());
            List<FileTemplateTree> vueTrees = new ArrayList<>();
            GetDictionaryTreeChildren(vueTrees, model.gettId(), dataList);
            vueTree.setChildren(vueTrees);
            treeList.add(vueTree);
        });
    }

    private List<FileTemplateDto> getAllFileTemplateList(String teId) {
        String key = redisUtils.tenantKey("allFileTemplateList", teId);
        Object dictionaryTreeList = redisUtils.get(key);
        List<FileTemplateDto> list;
        if (dictionaryTreeList != null) {
            list = JacksonUtils.parseObject(dictionaryTreeList.toString(), new TypeReference<List<FileTemplateDto>>() {
            });
        } else {
            list = baseMapper.getAllFileTemplateList(teId);
            List<SysExtendConfDTO> confs = sysExtendConfService.getAllSysExtendConf();
            if (!CollectionUtils.isEmpty(confs)) {
                list = list.stream().map(data -> {
                    data.setSysExtendConfs(confs.stream().filter(n -> ValidateUtil.isNotEmpty(n.getTId()) && n.getTId().equals(data.gettId())).collect(Collectors.toList()));
                    return data;
                }).collect(Collectors.toList());
            }
            Set<String> tIds = list.stream().map(FileTemplateDto::gettId).collect(Collectors.toSet());
            List<SysFileTemplateDtlDTO> dtls = sysFileTemplateDtlService.getSysFileTemplateDtlBytIds(tIds);
            Map<String, List<SysFileTemplateDtlDTO>> dtlMap = dtls.stream().collect(Collectors.groupingBy(SysFileTemplateDtlDTO::getTId));
            if (!CollectionUtils.isEmpty(dtls)) {
                list = list.stream().peek(data -> {
                    List<SysFileTemplateDtlDTO> sysFileTemplateDtls = dtlMap.get(data.gettId());
                    if (ValidateUtil.isNotEmpty(sysFileTemplateDtls)) {
                        data.setFileTemplateDtls(sysFileTemplateDtls);
                    }
                }).collect(Collectors.toList());
            }
            redisUtils.set(key, JacksonUtils.toJSONString(list));
        }
        return list;
    }

    /**
     * 删除当前节点后 同级后续排序进1
     *
     * @param pojo
     */
    private void deleteToChangeOtherSort(FileTemplate pojo, List<FileTemplateDto> datas) {
        List<FileTemplateDto> sameLevels = datas.stream().filter(n -> n.gettParentid().equals(pojo.gettParentid()) && n.gettSort() > pojo.gettSort()).collect(Collectors.toList());
        List<FileTemplate> changes = new ArrayList<>();
        sameLevels.forEach(model -> {
            model.settSort(model.gettSort() - 1);
            FileTemplate t = new FileTemplate();
            BeanUtils.copyProperties(model, t);
            changes.add(t);
        });
        if (!CollectionUtils.isEmpty(changes)) updateBatchById(changes);
    }

    private List<FileTemplate> getListByCode(String code, String token) {
        String teId = redisUtils.getTeId(token);
        QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        wrapper.eq("T_Code", code);
        return baseMapper.selectList(wrapper);
    }


    private List<FileTemplate> getListByParent(String id, Boolean excludeCancel, String token) {
        String teId = redisUtils.getTeId(token);
        QueryWrapper<FileTemplate> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId)) wrapper.eq("Te_Id", teId);
        wrapper.eq("T_ParentID", id);
        if (excludeCancel) wrapper.eq("T_Stats", 1);
        wrapper.orderByAsc("T_Sort");
        return baseMapper.selectList(wrapper);
    }


    public String getFileTemplateTableById(String id) {
        String str = "";
        FileTemplate pojo = baseMapper.selectById(id);
        if (Objects.nonNull(pojo)) {
            str = pojo.gettTable() + "," + pojo.gettPrimaryKey();
        }
        return str;
    }
}
