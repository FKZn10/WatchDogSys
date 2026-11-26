package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.*;
import com.jpmsoft.common.model.response.projectOutline.OutlineNodeDatasourceResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineSystemLibResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineSystemLibMapper;
import com.jpmsoft.projectProducer.pojo.dto.projectOutline.OutlineLibConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineLibRisk;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineNodeDatasource;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineNodeTemp;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineSystemLib;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.projectOutline.*;
import com.jpmsoft.projectProducer.utils.FreemarkerUtils;
import com.jpmsoft.projectProducer.utils.OutlineLib.BuildOpts;
import com.jpmsoft.projectProducer.utils.OutlineLib.OutlineLibUtils;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tianhelin
 * @date 2025/6/11
 */
@Slf4j
@Service
public class OutlineSystemLibServiceImpl extends ServiceImpl<OutlineSystemLibMapper, OutlineSystemLib> implements IOutlineSystemLibService {
    @Resource
    private IOutlineLibRiskService outlineLibRiskService;
    @Resource
    private IOutlineNodeDatasourceService outlineNodeDatasourceService;
    @Resource
    private IOutlineNodeTempService outlineNodeTempService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IOutlineSystemManageService outlineSystemManageService;
    @Resource
    private IOutlineProjectService outlineProjectService;
    @Resource
    private IOutlineLibImageService outlineLibImageService;

    /**
     * 获取体系库树形结构
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getOutlineSystemLibTree(OutlineSystemLibRequest request) {
        List<OutlineSystemLibResponse> responses = baseMapper.getOutlineSystemLibList(request);
        if (ValidateUtil.isEmpty(responses)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        List<OutlineSystemLibResponse> libResponses = OutlineLibUtils.buildTreeOrList(
                responses,
                BuildOpts.<OutlineSystemLibResponse>builder()
                        .idGetter(OutlineSystemLibResponse::getId)
                        .parentIdGetter(OutlineSystemLibResponse::getOslParentId)
                        .sortGetter(OutlineSystemLibResponse::getOslSort)
                        .childrenSetter(OutlineSystemLibResponse::setChildNodes)
                        .catalogSetter(OutlineSystemLibResponse::setOslCatalogName)
                        .nameGetter(OutlineSystemLibResponse::getOslName)
                        .sortRules(request.getSortRules())
                        .levelBottomSetter(OutlineSystemLibResponse::setOslLevelBottom)
                        .withLevelBottom(true)
                        .build()
        );
        return BaseResponse.ok(libResponses);
    }

    /**
     * 查询体系库树形结构
     * 内部调用
     *
     * @param request
     * @return
     */
    @Override
    public List<OutlineSystemLibResponse> selectOutlineSystemLibTree(OutlineSystemLibRequest request) {
        return selectOutlineSystemLibTree(request, true);
    }

    /**
     * 查询体系库树形结构
     * 内部调用
     *
     * @param request
     * @param treeMode
     * @return
     */
    @Override
    public List<OutlineSystemLibResponse> selectOutlineSystemLibTree(OutlineSystemLibRequest request, Boolean treeMode) {
        List<OutlineSystemLibResponse> responses = baseMapper.getOutlineSystemLibList(request);
        if (ValidateUtil.isEmpty(responses)) {
            return Collections.emptyList();
        }
        return OutlineLibUtils.buildTreeOrList(
                responses,
                BuildOpts.<OutlineSystemLibResponse>builder()
                        .idGetter(OutlineSystemLibResponse::getId)
                        .parentIdGetter(OutlineSystemLibResponse::getOslParentId)
                        .sortGetter(OutlineSystemLibResponse::getOslSort)
                        .childrenSetter(OutlineSystemLibResponse::setChildNodes)
                        .withIndexPrefix(false)
                        .treeMode(treeMode)
                        .build()
        );
    }

    /**
     * 获取体系库详情
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getOutlineSystemLibById(String id) {
        if (ValidateUtil.isNull(id) || "undefined".equalsIgnoreCase(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineSystemLib systemLib = this.getById(id);
        if (ValidateUtil.isNull(systemLib)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineSystemLibResponse response = new OutlineSystemLibResponse();
        BeanUtils.copyProperties(systemLib, response);
        // response.setOutlineNodeTempList(outlineNodeTempService.getAllOutlineNodeTempByOslIds(Collections.singletonList(id)));
        // response.setOutlineLibRiskList(outlineLibRiskService.getAllOutlineLibRiskByOslIds(Collections.singletonList(id)));
        // response.setOutlineNodeDatasourceList(outlineNodeDatasourceService.getAllOutlineNodeDatasourceByOslIds(Collections.singletonList(id)));
        return BaseResponse.ok(response);
    }

    /**
     * 创建或更新体系库
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOutlineSystemLib(OutlineSystemLibRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isEmpty(request.getOslName().trim())) {
            return BaseResponse.fail("标题不能为空！");
        }
        OutlineSystemLib entity = new OutlineSystemLib();
        BeanUtils.copyProperties(request, entity);
        entity.setOslName(request.getOslName().trim());
        entity.setOslContent(outlineProjectService.base64Decoding(request.getOslContent()));
        boolean isNew = ValidateUtil.isEmpty(request.getId());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        if (isNew) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            Integer sort = baseMapper.selectSort(request.getOsmId(), request.getOslParentId());
            entity.setOslSort(sort == null ? 0 : sort + 1);
        }
        saveOrUpdate(entity);
        outlineSystemManageService.refreshLastUpdateTime(request.getOsmId(), user);
        if (!ValidateUtil.isAnObjEmpty(request.getOslFileRelationId(), request.getOslFileIds())) {
            sysFileAnnexService.refreshRelationFile(request.getOslFileRelationId(), request.getOslFileIds(), entity.getId());
            outlineLibImageService.handleOutlineFile(entity.getId(), Arrays.asList(request.getOslFileIds().split(",")));
        }
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 批量创建体系库
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse batchCreateOutlineSystemLib(OutlineSystemManageRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        this.deleteOutlineSubtablesByOsmId(request.getId());
        this.remove(Wrappers.<OutlineSystemLib>lambdaQuery().eq(OutlineSystemLib::getOsmId, request.getId()));

        List<OutlineSystemLibRequest> outlineSystemLibList = request.getOutlineSystemLibList();
        List<OutlineSystemLib> libs = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(outlineSystemLibList)) {
            outlineSystemLibList.forEach(lib -> {
                OutlineSystemLib entity = new OutlineSystemLib();
                BeanUtils.copyProperties(lib, entity);
                entity.setOslName(lib.getOslName().trim());
                libs.add(entity);
            });
            this.saveBatch(this.filterValidParentNodes(libs));
        }
        Set<String> validOslIds = libs.stream().map(OutlineSystemLib::getId).collect(Collectors.toSet());

        List<OutlineNodeTempRequest> outlineNodeTempList = request.getOutlineNodeTempList();
        List<OutlineNodeTemp> temps = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(outlineNodeTempList)) {
            outlineNodeTempList.forEach(temp -> {
                OutlineNodeTemp entity = new OutlineNodeTemp();
                BeanUtils.copyProperties(temp, entity);
                temps.add(entity);
            });
            outlineNodeTempService.saveBatch(temps.stream()
                    .filter(n -> validOslIds.contains(n.getOslId()))
                    .collect(Collectors.toList()));
        }

        List<OutlineLibRiskRequest> outlineLibRiskList = request.getOutlineLibRiskList();
        List<OutlineLibRisk> risks = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(outlineLibRiskList)) {
            outlineLibRiskList.forEach(risk -> {
                OutlineLibRisk entity = new OutlineLibRisk();
                BeanUtils.copyProperties(risk, entity);
                risks.add(entity);
            });
            outlineLibRiskService.saveBatch(risks.stream()
                    .filter(r -> validOslIds.contains(r.getOslId()))
                    .collect(Collectors.toList()));
        }

        List<OutlineNodeDatasourceRequest> nodeDatasourceList = request.getOutlineNodeDatasourceList();
        List<OutlineNodeDatasource> datasources = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(nodeDatasourceList)) {
            nodeDatasourceList.forEach(ds -> {
                OutlineNodeDatasource entity = new OutlineNodeDatasource();
                BeanUtils.copyProperties(ds, entity);
                datasources.add(entity);
            });
            outlineNodeDatasourceService.saveBatch(datasources.stream()
                    .filter(ds -> validOslIds.contains(ds.getOslId()))
                    .collect(Collectors.toList()));
        }

        return BaseResponse.ok();
    }

    /**
     * 过滤掉父节点在当前导入数据中不存在的记录
     *
     * @param libs 当前导入的体系库数据列表
     * @return 过滤后的合法数据
     */
    private List<OutlineSystemLib> filterValidParentNodes(List<OutlineSystemLib> libs) {
        Set<String> allIds = libs.stream()
                .map(OutlineSystemLib::getId)
                .collect(Collectors.toSet());
        return libs.stream()
                .filter(lib -> {
                    String parentId = lib.getOslParentId();
                    return ValidateUtil.isEmpty(parentId) || "0".equals(parentId) || allIds.contains(parentId);
                })
                .collect(Collectors.toList());
    }

    /**
     * 删除体系库
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteOutlineSystemLibById(String id, String token) {
        OutlineSystemLib entity = this.getById(id);
        if (ValidateUtil.isNull(entity))
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (baseMapper.exists(Wrappers.<OutlineSystemLib>lambdaQuery().eq(OutlineSystemLib::getOslParentId, id)))
            return BaseResponse.fail(ResMsg.delDownNodeFirst);
        outlineLibRiskService.deleteByOslId(id);
        outlineNodeDatasourceService.deleteByOslId(id);
        outlineNodeTempService.deleteByOslId(id);
        baseMapper.deleteById(id);
        // 处理删除后的其他目录的排序字段
        List<OutlineSystemLib> siblings = this.list(Wrappers.<OutlineSystemLib>lambdaQuery()
                .eq(OutlineSystemLib::getOslParentId, entity.getOslParentId())
                .orderByAsc(OutlineSystemLib::getOslSort, OutlineSystemLib::getCreateTime));
        int sort = 1;
        for (OutlineSystemLib lib : siblings) {
            lib.setOslSort(sort++);
        }
        this.updateBatchById(siblings);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineSystemManageService.refreshLastUpdateTime(entity.getOsmId(), user);
        return BaseResponse.ok();
    }

    /**
     * 删除子表数据
     *
     * @param osmId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOutlineSubtablesByOsmId(String osmId) {
        outlineLibRiskService.deleteByOsmId(osmId);
        outlineNodeDatasourceService.deleteByOsmId(osmId);
        outlineNodeTempService.deleteByOsmId(osmId);
        this.remove(Wrappers.<OutlineSystemLib>lambdaQuery().eq(OutlineSystemLib::getOsmId, osmId));
    }

    /**
     * 批量更新体系库启用状态
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse batchUpdateSystemLibOslStatus(OutlineSystemLibRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<String> ids = request.getIds();
        if (ValidateUtil.isNotEmpty(ids)) {
            lambdaUpdate()
                    .in(OutlineSystemLib::getId, ids)
                    .set(OutlineSystemLib::getOslStatus, request.getOslStatus())
                    .update();
        }
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineSystemManageService.refreshLastUpdateTime(request.getOsmId(), user);
        return BaseResponse.ok();
    }

    /**
     * 更新体系库树排序
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateOutlineSystemLibSort(OutlineSystemLibRequest request, String token) {
        if (ValidateUtil.isEmpty(request.getId()) || ValidateUtil.isEmpty(request.getNewId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineSystemLib moveNode = this.getById(request.getId());
        OutlineSystemLib targetNode = this.getById(request.getNewId());
        if (ValidateUtil.isNull(moveNode) || ValidateUtil.isNull(targetNode)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<OutlineSystemLib> siblings = this.list(Wrappers.<OutlineSystemLib>lambdaQuery()
                .eq(OutlineSystemLib::getOslParentId, moveNode.getOslParentId())
                .eq(OutlineSystemLib::getOsmId, moveNode.getOsmId())
                .orderByAsc(OutlineSystemLib::getOslSort));
        OutlineLibUtils.moveNodeAndResort(
                siblings,
                moveNode,
                targetNode,
                OutlineSystemLib::getId,
                OutlineSystemLib::setOslSort
        );
        this.updateBatchById(siblings);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineSystemManageService.refreshLastUpdateTime(request.getOsmId(), user);
        return BaseResponse.ok();
    }

    /**
     * 刷新体系模板管理最后更新时间
     *
     * @param oslId
     * @param user
     */
    @Override
    public void refreshSystemManageLastUpdateTime(String oslId, LogUserInfoVo user) {
        OutlineSystemLib systemLib = this.getOne(Wrappers.<OutlineSystemLib>lambdaQuery().eq(OutlineSystemLib::getId, oslId).select(OutlineSystemLib::getOsmId).last("limit 1"));
        outlineSystemManageService.refreshLastUpdateTime(systemLib.getOsmId(), user);
    }

    /**
     * 查找或替换大纲体系模板
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse searchOrReplaceOutlineSystemLib(OutlineSystemLibRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        String searchValue = request.getOslName().trim();
        if (ValidateUtil.isEmpty(searchValue)) {
            return BaseResponse.ok();
        }
        request.setOslName(null);
        List<OutlineSystemLibResponse> responses = baseMapper.getOutlineSystemLibList(request);
        if (ValidateUtil.isEmpty(responses)) {
            return BaseResponse.ok();
        }
        List<OutlineSystemLibResponse> libResponses = OutlineLibUtils.buildTreeOrList(
                responses,
                BuildOpts.<OutlineSystemLibResponse>builder()
                        .idGetter(OutlineSystemLibResponse::getId)
                        .parentIdGetter(OutlineSystemLibResponse::getOslParentId)
                        .sortRules(request.getSortRules())
                        .nameGetter(OutlineSystemLibResponse::getOslName)
                        .catalogSetter(OutlineSystemLibResponse::setOslCatalogName)
                        .treeMode(false)
                        .withIndexPrefix(true)
                        .build()
        );

        // 处理多字段查询的","分隔
        List<String> searchValues = OutlineLibUtils.keywordsHandler(searchValue);
        // 筛选出匹配的项目
        List<OutlineSystemLibResponse> filterResponses = OutlineLibUtils.filterByKeywords(
                libResponses,
                searchValues,
                OutlineSystemLibResponse::getOslName,
                OutlineSystemLibResponse::getOslContent
        );
        if (ValidateUtil.isEmpty(filterResponses)) {
            return BaseResponse.ok();
        }

        if (ValidateUtil.isNull(request.getNewOslName())) {
            OutlineLibConfDTO outlineLibConf = outlineProjectService.getOutlineLibConf();
            List<OutlineSystemLibResponse> resultList = OutlineLibUtils.highlightMatchedContent(
                    filterResponses,
                    searchValues,
                    outlineLibConf,
                    OutlineSystemLibResponse::getOslCatalogName,
                    OutlineSystemLibResponse::getOslContent,
                    OutlineSystemLibResponse::setOslCatalogName,
                    OutlineSystemLibResponse::setOslContent,
                    OutlineSystemLibResponse::setOslContentExtend,
                    OutlineSystemLibResponse::setIndex,
                    OutlineSystemLibResponse::new
            );
            return BaseResponse.ok(resultList, resultList.size());
        } else {
            if (!searchValue.equalsIgnoreCase(request.getNewOslName())) {
                List<String> newValues = OutlineLibUtils.keywordsHandler(request.getNewOslName());
                OutlineLibUtils.replaceKeywords(
                        filterResponses,
                        searchValues,
                        newValues,
                        OutlineSystemLibResponse::getOslName,
                        OutlineSystemLibResponse::getOslContent,
                        OutlineSystemLibResponse::setOslName,
                        OutlineSystemLibResponse::setOslContent
                );
                // TODO 替换的后续逻辑待定，先暂时直接存表
                List<OutlineSystemLib> updateList = new ArrayList<>();
                for (OutlineSystemLibResponse item : filterResponses) {
                    OutlineSystemLib outlineSystemLib = new OutlineSystemLib();
                    BeanUtils.copyProperties(item, outlineSystemLib);
                    updateList.add(outlineSystemLib);
                }

                if (ValidateUtil.isNotEmpty(updateList)) {
                    this.updateBatchById(updateList);
                    LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
                    this.refreshSystemManageLastUpdateTime(updateList.get(0).getId(), user);
                }
                return BaseResponse.ok();
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 检查模板叶子节点是否为空
     *
     * @param osmId
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse checkEmptyOutlineSystemLib(String osmId, String token) {
        if (ValidateUtil.isEmpty(osmId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        this.checkAndAutoAddDatasource(osmId, token);
        List<OutlineSystemLibResponse> responses = baseMapper.checkEmptyOutlineSystemLib(osmId);
        if (ValidateUtil.isEmpty(responses)) {
            return BaseResponse.ok(Collections.emptyList());
        } else {
            List<String> list = responses.stream().map(OutlineSystemLibResponse::getId).collect(Collectors.toList());
            return BaseResponse.ok(list, list.size());
        }
    }

    /**
     * 在lib有编制模板的情况下，判断添加前置业务后，对应的数据源是否添加完全
     * 若未完全，自动关联对应的数据源
     *
     * @param osmId
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse checkAndAutoAddDatasource(String osmId, String token) {
        if (ValidateUtil.isEmpty(osmId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        // 查询所有的temp
        List<OutlineSystemLibResponse> systemLibs = baseMapper.getLibWithTempByOsmId(osmId);
        if (ValidateUtil.isEmpty(systemLibs)) {
            return BaseResponse.ok();
        }
        systemLibs = systemLibs.stream()
                .filter(lib -> ValidateUtil.isNotEmpty(lib.getOslContent()) && lib.getOslContent().contains("${"))
                .collect(Collectors.toList());
        if (ValidateUtil.isEmpty(systemLibs)) {
            return BaseResponse.ok();
        }

        // 所有含有模板的目录id
        Set<String> allOslIds = systemLibs.stream()
                .map(OutlineSystemLibResponse::getId)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toSet());

        // alias -> odId
        Map<String, String> aliasToOdIdMap = outlineNodeDatasourceService.getOdIdAndAliasMap();
        if (ValidateUtil.isEmpty(aliasToOdIdMap)) {
            return BaseResponse.ok();
        }

        // 目录 -> 数据源
        List<OutlineNodeDatasourceResponse> existingDsAll = outlineNodeDatasourceService.getAllOutlineNodeDatasourceByOslIds(new ArrayList<>(allOslIds));
        Map<String, Set<String>> oslToExistingOdIds = new HashMap<>();
        if (ValidateUtil.isNotEmpty(existingDsAll)) {
            oslToExistingOdIds = existingDsAll.stream()
                    .filter(ds -> ValidateUtil.isNotEmpty(ds.getOslId()) && ValidateUtil.isNotEmpty(ds.getOdId()))
                    .collect(Collectors.groupingBy(
                            OutlineNodeDatasourceResponse::getOslId,
                            Collectors.mapping(OutlineNodeDatasourceResponse::getOdId, Collectors.toSet())
                    ));
        }

        // 目录 -> 模板
        Map<String, Set<String>> oslToExistingTCodes = outlineNodeTempService.getTCodesGroupedByOslIds(new ArrayList<>(allOslIds));
        if (oslToExistingTCodes == null) {
            oslToExistingTCodes = new HashMap<>();
        }

        // 收集所有需要的odId
        Map<String, Set<String>> oslToPotentialOdIds = new HashMap<>();
        Set<String> allPotentialOdIds = new HashSet<>();
        for (OutlineSystemLibResponse lib : systemLibs) {
            String oslId = lib.getId();
            Set<String> prefixes = FreemarkerUtils.extractPrefixes(lib.getOslContent());
            if (ValidateUtil.isEmpty(prefixes)) {
                continue;
            }

            Set<String> potentialOdIds = prefixes.stream()
                    .map(aliasToOdIdMap::get)
                    .filter(ValidateUtil::isNotEmpty)
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            if (ValidateUtil.isNotEmpty(potentialOdIds)) {
                oslToPotentialOdIds.put(oslId, potentialOdIds);
                allPotentialOdIds.addAll(potentialOdIds);
            }
        }
        if (ValidateUtil.isEmpty(allPotentialOdIds)) {
            return BaseResponse.ok();
        }

        // odId -> tCodes
        Map<String, Set<String>> odIdToTCodes = outlineNodeDatasourceService.getTCodesByOdIds(new ArrayList<>(allPotentialOdIds));
        if (ValidateUtil.isEmpty(odIdToTCodes)) {
            return BaseResponse.ok();
        }

        // 仅根据现有的目录tCode进行过滤，然后补目录-数据源
        List<OutlineNodeDatasource> toAddNodeDatasources = new ArrayList<>();

        // 获取当前用户
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);

        for (Map.Entry<String, Set<String>> e : oslToPotentialOdIds.entrySet()) {
            String oslId = e.getKey();
            Set<String> potentialOdIds = e.getValue();
            if (ValidateUtil.isEmpty(potentialOdIds)) {
                continue;
            }

            Set<String> existingOdIds = new HashSet<>(oslToExistingOdIds.getOrDefault(oslId, new HashSet<>()));
            Set<String> existingTCodes = new HashSet<>(oslToExistingTCodes.getOrDefault(oslId, new HashSet<>()));

            if (existingTCodes.isEmpty()) {
                continue;
            }

            for (String odId : potentialOdIds) {
                if (existingOdIds.contains(odId)) {
                    continue;
                }
                Set<String> tCodesOfOd = odIdToTCodes.get(odId);
                if (ValidateUtil.isEmpty(tCodesOfOd)) {
                    continue;
                }
                // 至少有一个tCode是匹配的，才添加
                boolean compatible = tCodesOfOd.stream().anyMatch(existingTCodes::contains);
                if (!compatible) {
                    continue;
                }

                // 避免重复添加
                if (outlineNodeDatasourceService.datasourceExist(odId, oslId)) {
                    continue;
                }

                OutlineNodeDatasource ds = new OutlineNodeDatasource();
                ds.setId(GenerateUUID.getUUID());
                ds.setOdId(odId);
                ds.setOslId(oslId);
                ds.setCreateTime(new Date());
                ds.setCreateUid(user.getId());
                ds.setCreateUname(user.getName());
                toAddNodeDatasources.add(ds);

                // 本地镜像更新，避免同一批次重复添加
                existingOdIds.add(odId);
            }
            oslToExistingOdIds.put(oslId, existingOdIds);
        }

        if (ValidateUtil.isNotEmpty(toAddNodeDatasources)) {
            outlineNodeDatasourceService.saveBatch(toAddNodeDatasources);
        }

        return BaseResponse.ok();
    }
}
