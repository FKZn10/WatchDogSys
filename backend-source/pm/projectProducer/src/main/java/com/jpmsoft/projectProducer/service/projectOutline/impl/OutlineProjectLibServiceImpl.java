package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.projectOutline.*;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectLibResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectManageResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectTempResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineProjectLibMapper;
import com.jpmsoft.projectProducer.pojo.dto.projectOutline.OutlineLibConfDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.*;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.projectOutline.*;
import com.jpmsoft.projectProducer.utils.HtmlUtils;
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
public class OutlineProjectLibServiceImpl extends ServiceImpl<OutlineProjectLibMapper, OutlineProjectLib> implements IOutlineProjectLibService {
    @Resource
    private IOutlineProjectRiskService outlineProjectRiskService;
    @Resource
    private IOutlineProjecDatasourceService outlineProjecDatasourceService;
    @Resource
    private IOutlineProjectTempService outlineProjectTempService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IOutlineProjectManageService outlineProjectManageService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IOutlineProjectService outlineProjectService;
    @Resource
    private IOutlineProjectStyleService outlineProjectStyleService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IOutlineLibImageService outlineLibImageService;

    /**
     * 获取大纲体系树形结构
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectLibTree(OutlineProjectLibRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<OutlineProjectLibResponse> responses = baseMapper.getOutlineProjectLibTree(request);
        if (ValidateUtil.isEmpty(responses)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        if (ValidateUtil.isNull(request.getNoContent()) || !request.getNoContent()) {
            BaseResponse preTypeVerifyIds = outlineProjectManageService.getPreTypeVerifyIds(request.getOpmId());
            if (preTypeVerifyIds.getSuccess()) {
                List<OutlineProjectTempResponse> verifyIds = (List<OutlineProjectTempResponse>) preTypeVerifyIds.getData();
                if (ValidateUtil.isNotEmpty(verifyIds)) {
                    responses.forEach(res -> {
                        List<Map<String, String>> matched = verifyIds.stream()
                                .filter(temp -> temp.getOplId().equals(res.getId()))
                                .map(temp -> {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("tCode", temp.getTCode());
                                    map.put("tName", temp.getTName());
                                    return map;
                                })
                                .collect(Collectors.toList());
                        if (ValidateUtil.isNotEmpty(matched)) {
                            res.setPreTypeList(matched);
                        } else {
                            res.setPreTypeList(Collections.emptyList());
                        }
                    });
                }
            }
        }
        List<OutlineProjectLibResponse> libResponses = OutlineLibUtils.buildTreeOrList(
                responses,
                BuildOpts.<OutlineProjectLibResponse>builder()
                        .idGetter(OutlineProjectLibResponse::getId)
                        .parentIdGetter(OutlineProjectLibResponse::getOplParentId)
                        .sortGetter(OutlineProjectLibResponse::getOplSort)
                        .childrenSetter(OutlineProjectLibResponse::setChildNodes)
                        .catalogSetter(OutlineProjectLibResponse::setOplCatalogName)
                        .nameGetter(OutlineProjectLibResponse::getOplName)
                        .scoreGetter(OutlineProjectLibResponse::getOplScore)
                        .sortRules(request.getSortRules())
                        .levelBottomSetter(OutlineProjectLibResponse::setOplLevelBottom)
                        .withScore(true)
                        .withLevelBottom(true)
                        .build()
        );
        return BaseResponse.ok(libResponses);
    }

    /**
     * 查询大纲体系树形结构
     * 内部调用
     *
     * @param request
     * @return
     */
    @Override
    public List<OutlineProjectLibResponse> selectOutlineProjectLibTree(OutlineProjectLibRequest request) {
        List<OutlineProjectLibResponse> responses = baseMapper.getOutlineProjectLibTree(request);
        if (ValidateUtil.isEmpty(responses)) {
            return Collections.emptyList();
        }
        return OutlineLibUtils.buildTreeOrList(
                responses,
                BuildOpts.<OutlineProjectLibResponse>builder()
                        .idGetter(OutlineProjectLibResponse::getId)
                        .parentIdGetter(OutlineProjectLibResponse::getOplParentId)
                        .sortGetter(OutlineProjectLibResponse::getOplSort)
                        .childrenSetter(OutlineProjectLibResponse::setChildNodes)
                        .withIndexPrefix(false)
                        .build()
        );
    }

    /**
     * 查询大纲体系（自定结构）
     * 内部调用
     *
     * @param request
     * @param treeMode
     * @param withIndexPrefix
     * @param withOutContent
     * @param withLevel
     * @return
     */
    @Override
    public List<OutlineProjectLibResponse> selectOutlineProjectLibTree(OutlineProjectLibRequest request, Boolean treeMode, Boolean withIndexPrefix, Boolean withOutContent, Boolean withLevel, Boolean prettyPrint) {
        List<OutlineProjectLibResponse> responses = baseMapper.getOutlineProjectLibTree(request);
        if (ValidateUtil.isEmpty(responses)) {
            return Collections.emptyList();
        }
        return OutlineLibUtils.buildTreeOrList(
                responses,
                BuildOpts.<OutlineProjectLibResponse>builder()
                        .idGetter(OutlineProjectLibResponse::getId)
                        .parentIdGetter(OutlineProjectLibResponse::getOplParentId)
                        .sortGetter(OutlineProjectLibResponse::getOplSort)
                        .childrenSetter(OutlineProjectLibResponse::setChildNodes)
                        .catalogSetter(OutlineProjectLibResponse::setOplCatalogName)
                        .nameGetter(OutlineProjectLibResponse::getOplName)
                        .contentSetter(OutlineProjectLibResponse::setOplContent)
                        .contentGetter(OutlineProjectLibResponse::getOplContent)
                        .levelSetter(OutlineProjectLibResponse::setOplLevel)
                        .sortRules(request.getSortRules())
                        .treeMode(treeMode)
                        .withIndexPrefix(withIndexPrefix)
                        .withOutContent(withOutContent)
                        .withLevel(withLevel)
                        .prettyPrint(prettyPrint)
                        .build(),
                outlineProjectStyleService.getStyleDtlMapByCategory("02")
        );
    }

    /**
     * 获取大纲体系详情
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectLibById(String id) {
        if (ValidateUtil.isNull(id) || "undefined".equalsIgnoreCase(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectLib outlineProjectLib = this.getById(id);
        if (ValidateUtil.isNull(outlineProjectLib)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineProjectLibResponse response = new OutlineProjectLibResponse();
        BeanUtils.copyProperties(outlineProjectLib, response);
        // response.setOutlineProjectTempList(outlineProjectTempService.getOutlineProjectTempByOplIds(Collections.singletonList(id)));
        // response.setOutlineProjectRiskList(outlineProjectRiskService.getOutlineProjectRiskByOplId(id));
        // response.setOutlineProjecDatasourceList(outlineProjecDatasourceService.getOutlineProjecDatasourceByOplIds(Collections.singletonList(id)));
        return BaseResponse.ok(response);
    }

    /**
     * 创建或更新大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOutlineProjectLib(OutlineProjectLibRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isEmpty(request.getOplName().trim())) {
            return BaseResponse.fail("标题不能为空！");
        }
        OutlineProjectLib entity = new OutlineProjectLib();
        BeanUtils.copyProperties(request, entity);
        entity.setOplName(request.getOplName().trim());
        entity.setOplContent(outlineProjectService.base64Decoding(request.getOplContent()));
        // entity.setOpnContent(outlineProjectService.base64Decoding(request.getOpnContent()));
        boolean isNew = ValidateUtil.isEmpty(entity.getId());
        LogUserInfoVo userInfoVo = redisUtils.getUserInfoByToken(token);
        if (isNew) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateUid(userInfoVo.getId());
            entity.setCreateUname(userInfoVo.getName());
            entity.setCreateTime(new Date());
            Integer sort = baseMapper.selectSort(request.getOpmId(), request.getOplParentId());
            entity.setOplSort(sort == null ? 0 : sort + 1);
            boolean success = this.save(entity);
            if (!success) {
                return BaseResponse.fail("新增失败");
            }
        } else {
            OutlineProjectLib model = this.getById(entity.getId());
            if (ValidateUtil.isNull(model)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            boolean success = this.updateById(entity);
            if (!success) {
                return BaseResponse.fail("更新失败");
            }
        }
        if (!ValidateUtil.isAnObjEmpty(request.getOplFileRelationId(), request.getOplFileIds())) {
            sysFileAnnexService.refreshRelationFile(request.getOplFileRelationId(), request.getOplFileIds(), entity.getId());
            outlineLibImageService.handleOutlineFile(entity.getId(), Arrays.asList(request.getOplFileIds().split(",")));
        }
        outlineProjectManageService.refreshLastUpdateTime(request.getId(), userInfoVo);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 删除大纲体系
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteOutlineProjectLibById(String id, String token) {
        OutlineProjectLib entity = this.getById(id);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (baseMapper.exists(Wrappers.<OutlineProjectLib>lambdaQuery().eq(OutlineProjectLib::getOplParentId, id))) {
            return BaseResponse.fail(ResMsg.delDownNodeFirst);
        }
        this.deleteOutlineSubtables(Collections.singletonList(id));
        this.removeById(id);
        // 处理删除后的其他目录的排序字段
        List<OutlineProjectLib> siblings = this.list(Wrappers.<OutlineProjectLib>lambdaQuery()
                .eq(OutlineProjectLib::getOplParentId, entity.getOplParentId())
                .orderByAsc(OutlineProjectLib::getOplSort, OutlineProjectLib::getCreateTime));
        int sort = 1;
        for (OutlineProjectLib lib : siblings) {
            lib.setOplSort(sort++);
        }
        this.updateBatchById(siblings);
        LogUserInfoVo userInfoVo = redisUtils.getUserInfoByToken(token);
        outlineProjectManageService.refreshLastUpdateTime(entity.getId(), userInfoVo);
        return BaseResponse.ok();
    }

    /**
     * 删除子表数据
     *
     * @param oplIds
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOutlineSubtables(List<String> oplIds) {
        if (ValidateUtil.isEmpty(oplIds)) {
            return;
        }
        outlineProjecDatasourceService.remove(Wrappers.<OutlineProjecDatasource>lambdaQuery().in(OutlineProjecDatasource::getOplId, oplIds));
        outlineProjectTempService.remove(Wrappers.<OutlineProjectTemp>lambdaQuery().in(OutlineProjectTemp::getOplId, oplIds));
        outlineProjectRiskService.remove(Wrappers.<OutlineProjectRisk>lambdaQuery().in(OutlineProjectRisk::getOplId, oplIds));
    }

    /**
     * 根据opmId删除子表数据
     *
     * @param opmId
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOutlineSubtablesByOpmId(String opmId) {
        List<OutlineProjectLib> list = list(Wrappers.<OutlineProjectLib>lambdaQuery().eq(OutlineProjectLib::getOpmId, opmId));
        if (ValidateUtil.isNotEmpty(list)) {
            List<String> oplIds = list.stream().map(OutlineProjectLib::getId).collect(Collectors.toList());
            this.deleteOutlineSubtables(oplIds);
        }
        this.remove(Wrappers.<OutlineProjectLib>lambdaQuery().eq(OutlineProjectLib::getOpmId, opmId));
    }

    /**
     * 批量创建项目大纲
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse batchCreateOutlineProjectLib(OutlineProjectManageRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        this.deleteOutlineSubtablesByOpmId(request.getId());
        this.remove(Wrappers.<OutlineProjectLib>lambdaQuery().eq(OutlineProjectLib::getOpmId, request.getId()));

        List<OutlineProjectLibRequest> outlineProjectLibList = request.getOutlineProjectLibList();
        List<OutlineProjectLib> libs = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(outlineProjectLibList)) {
            outlineProjectLibList.forEach(lib -> {
                OutlineProjectLib entity = new OutlineProjectLib();
                BeanUtils.copyProperties(lib, entity);
                entity.setOplName(lib.getOplName().trim());
                libs.add(entity);
            });
            this.saveBatch(this.filterValidParentNodes(libs));
        }
        Set<String> validOplIds = libs.stream().map(OutlineProjectLib::getId).collect(Collectors.toSet());

        List<OutlineProjectTempRequest> outlineProjectTempList = request.getOutlineProjectTempList();
        List<OutlineProjectTemp> temps = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(outlineProjectTempList)) {
            outlineProjectTempList.forEach(temp -> {
                OutlineProjectTemp entity = new OutlineProjectTemp();
                BeanUtils.copyProperties(temp, entity);
                temps.add(entity);
            });
            outlineProjectTempService.saveBatch(temps.stream()
                    .filter(n -> validOplIds.contains(n.getOplId()))
                    .collect(Collectors.toList()));
        }

        List<OutlineProjectRiskRequest> outlineProjectRiskList = request.getOutlineProjectRiskList();
        List<OutlineProjectRisk> risks = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(outlineProjectRiskList)) {
            outlineProjectRiskList.forEach(risk -> {
                OutlineProjectRisk entity = new OutlineProjectRisk();
                BeanUtils.copyProperties(risk, entity);
                risks.add(entity);
            });
            outlineProjectRiskService.saveBatch(risks.stream()
                    .filter(r -> validOplIds.contains(r.getOplId()))
                    .collect(Collectors.toList()));
        }

        List<OutlineProjecDatasourceRequest> outlineProjecDatasourceList = request.getOutlineProjecDatasourceList();
        List<OutlineProjecDatasource> datasources = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(outlineProjecDatasourceList)) {
            outlineProjecDatasourceList.forEach(ds -> {
                OutlineProjecDatasource entity = new OutlineProjecDatasource();
                BeanUtils.copyProperties(ds, entity);
                datasources.add(entity);
            });
            outlineProjecDatasourceService.saveBatch(datasources.stream()
                    .filter(ds -> validOplIds.contains(ds.getOplId()))
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
    private List<OutlineProjectLib> filterValidParentNodes(List<OutlineProjectLib> libs) {
        Set<String> allIds = libs.stream()
                .map(OutlineProjectLib::getId)
                .collect(Collectors.toSet());
        return libs.stream()
                .filter(lib -> {
                    String parentId = lib.getOplParentId();
                    return ValidateUtil.isEmpty(parentId) || "0".equals(parentId) || allIds.contains(parentId);
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取大纲体系列表
     *
     * @param opmId
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectLibListByOpmId(String opmId) {
        if (ValidateUtil.isEmpty(opmId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectManage projectManage = outlineProjectManageService.getById(opmId);
        if (ValidateUtil.isNull(projectManage)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<OutlineProjectLibResponse> outlineProjectLibs = baseMapper.getOutlineProjectLibListByOpmId(opmId);
        if (ValidateUtil.isEmpty(outlineProjectLibs)) {
            return BaseResponse.ok();
        }
        // 处理列表的排序，按照目录结构的排序字段进行排序，并处理目录的序号
        List<OutlineProjectLibResponse> libResponses = OutlineLibUtils.buildTreeOrList(
                outlineProjectLibs,
                BuildOpts.<OutlineProjectLibResponse>builder()
                        .idGetter(OutlineProjectLibResponse::getId)
                        .parentIdGetter(OutlineProjectLibResponse::getOplParentId)
                        .sortGetter(OutlineProjectLibResponse::getOplSort)
                        .childrenSetter(OutlineProjectLibResponse::setChildNodes)
                        .catalogSetter(OutlineProjectLibResponse::setOplCatalogName)
                        .nameGetter(OutlineProjectLibResponse::getOplName)
                        .sortRules(projectManage.getSortRules())
                        .contentSetter(OutlineProjectLibResponse::setOplContent)
                        .contentGetter(OutlineProjectLibResponse::getOplContent)
                        .prettyPrint(true)
                        .treeMode(false)
                        .build(),
                outlineProjectStyleService.getStyleDtlMapByCategory("02")
        );
        return BaseResponse.ok(libResponses);
    }

    /**
     * 更新项目大纲排序
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateOutlineProjectLibSort(OutlineProjectLibRequest request, String token) {
        if (ValidateUtil.isEmpty(request.getId()) || ValidateUtil.isEmpty(request.getNewId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectLib moveNode = this.getById(request.getId());
        OutlineProjectLib targetNode = this.getById(request.getNewId());
        if (ValidateUtil.isNull(moveNode) || ValidateUtil.isNull(targetNode)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<OutlineProjectLib> siblings = this.list(Wrappers.<OutlineProjectLib>lambdaQuery()
                .eq(OutlineProjectLib::getOplParentId, moveNode.getOplParentId())
                .eq(OutlineProjectLib::getOpmId, moveNode.getOpmId())
                .orderByAsc(OutlineProjectLib::getOplSort));
        OutlineLibUtils.moveNodeAndResort(
                siblings,
                moveNode,
                targetNode,
                OutlineProjectLib::getId,
                OutlineProjectLib::setOplSort
        );
        this.updateBatchById(siblings);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineProjectManageService.refreshLastUpdateTime(request.getId(), user);
        return BaseResponse.ok();
    }

    /**
     * 获取项目对应的大纲体系树形结构
     *
     * @param pId
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectLibListByPid(String pId, String oplName, String opmCode) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectManage manage = outlineProjectManageService.getOne(Wrappers.<OutlineProjectManage>lambdaQuery().eq(OutlineProjectManage::getPId, pId).last("limit 1"));
        if (ValidateUtil.isNull(manage)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineProjectManageResponse response = new OutlineProjectManageResponse();
        BeanUtils.copyProperties(manage, response);
        OutlineProjectLibRequest request = new OutlineProjectLibRequest();
        request.setOpmId(manage.getId());
        request.setOplNameExact(oplName);
        request.setOpmCode(opmCode);
        List<OutlineProjectLibResponse> responses = baseMapper.getOutlineProjectLibTree(request);
        if (ValidateUtil.isEmpty(responses)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        List<OutlineProjectLibResponse> libResponses = OutlineLibUtils.buildTreeOrList(
                responses,
                BuildOpts.<OutlineProjectLibResponse>builder()
                        .idGetter(OutlineProjectLibResponse::getId)
                        .parentIdGetter(OutlineProjectLibResponse::getOplParentId)
                        .sortGetter(OutlineProjectLibResponse::getOplSort)
                        .childrenSetter(OutlineProjectLibResponse::setChildNodes)
                        .catalogSetter(OutlineProjectLibResponse::setOplCatalogName)
                        .nameGetter(OutlineProjectLibResponse::getOplName)
                        .sortRules(request.getSortRules())
                        .treeMode(ValidateUtil.isEmpty(oplName))
                        .forceListMode(ValidateUtil.isNotEmpty(oplName))
                        .build()
        );
        return BaseResponse.ok(libResponses);
    }

    /**
     * 刷新项目管理最后更新时间
     *
     * @param oplId
     * @param user
     */
    @Override
    public void refreshProjectManageLastUpdateTime(String oplId, LogUserInfoVo user) {
        OutlineProjectLib projectLib = this.getOne(Wrappers.<OutlineProjectLib>lambdaQuery().eq(OutlineProjectLib::getId, oplId).select(OutlineProjectLib::getOpmId).last("limit 1"));
        outlineProjectManageService.refreshLastUpdateTime(projectLib.getOpmId(), user);
    }

    /**
     * 查找或替换大纲体系
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse searchOrReplaceOutlineProjectLib(OutlineProjectLibRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        String searchValue = request.getOplName().trim();
        if (ValidateUtil.isEmpty(searchValue)) {
            return BaseResponse.ok();
        }
        request.setOplName(null);
        List<OutlineProjectLibResponse> responses = baseMapper.getOutlineProjectLibTree(request);
        if (ValidateUtil.isEmpty(responses)) {
            return BaseResponse.ok();
        }
        List<OutlineProjectLibResponse> libResponses = OutlineLibUtils.buildTreeOrList(
                responses,
                BuildOpts.<OutlineProjectLibResponse>builder()
                        .idGetter(OutlineProjectLibResponse::getId)
                        .parentIdGetter(OutlineProjectLibResponse::getOplParentId)
                        .sortRules(request.getSortRules())
                        .nameGetter(OutlineProjectLibResponse::getOplName)
                        .catalogSetter(OutlineProjectLibResponse::setOplCatalogName)
                        .treeMode(false)
                        .withIndexPrefix(true)
                        .build()
        );

        // 处理多字段查询的","分隔
        List<String> searchValues = OutlineLibUtils.keywordsHandler(searchValue);
        // 筛选出匹配的项目
        List<OutlineProjectLibResponse> filterResponses = OutlineLibUtils.filterByKeywords(
                libResponses,
                searchValues,
                OutlineProjectLibResponse::getOplName,
                OutlineProjectLibResponse::getOplContent
        );
        if (ValidateUtil.isEmpty(filterResponses)) {
            return BaseResponse.ok();
        }

        if (ValidateUtil.isNull(request.getNewOplName())) {
            OutlineLibConfDTO outlineLibConf = outlineProjectService.getOutlineLibConf();
            List<OutlineProjectLibResponse> resultList = OutlineLibUtils.highlightMatchedContent(
                    filterResponses,
                    searchValues,
                    outlineLibConf,
                    OutlineProjectLibResponse::getOplCatalogName,
                    OutlineProjectLibResponse::getOplContent,
                    OutlineProjectLibResponse::setOplCatalogName,
                    OutlineProjectLibResponse::setOplContent,
                    OutlineProjectLibResponse::setOplContentExtend,
                    OutlineProjectLibResponse::setIndex,
                    OutlineProjectLibResponse::new
            );
            return BaseResponse.ok(resultList, resultList.size());
        } else {
            if (!searchValue.equalsIgnoreCase(request.getNewOplName())) {
                List<String> newValues = OutlineLibUtils.keywordsHandler(request.getNewOplName());
                OutlineLibUtils.replaceKeywords(
                        filterResponses,
                        searchValues,
                        newValues,
                        OutlineProjectLibResponse::getOplName,
                        OutlineProjectLibResponse::getOplContent,
                        OutlineProjectLibResponse::setOplName,
                        OutlineProjectLibResponse::setOplContent
                );

                // TODO 替换的后续逻辑待定，先暂时直接存表
                List<OutlineProjectLib> updateList = new ArrayList<>();
                for (OutlineProjectLibResponse item : filterResponses) {
                    OutlineProjectLib outlineProjectLib = new OutlineProjectLib();
                    BeanUtils.copyProperties(item, outlineProjectLib);
                    updateList.add(outlineProjectLib);
                }

                if (ValidateUtil.isNotEmpty(updateList)) {
                    this.updateBatchById(updateList);
                    LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
                    this.outlineProjectManageService.refreshLastUpdateTime(updateList.get(0).getId(), user);
                }
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 校验当前内容是否包含其他项目
     *
     * @param opmId
     * @return
     */
    @Override
    public BaseResponse verifyProjectLibWithProjectName(String opmId) {
        if (ValidateUtil.isEmpty(opmId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectManage projectManage = outlineProjectManageService.getById(opmId);
        if (ValidateUtil.isNull(projectManage)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        List<OutlineProjectLibResponse> outlineProjectLibs = baseMapper.getOutlineProjectLibListByOpmId(opmId);
        if (ValidateUtil.isEmpty(outlineProjectLibs)) {
            return BaseResponse.ok();
        }
        List<OutlineProjectLibResponse> libResponses = OutlineLibUtils.buildTreeOrList(
                outlineProjectLibs,
                BuildOpts.<OutlineProjectLibResponse>builder()
                        .idGetter(OutlineProjectLibResponse::getId)
                        .parentIdGetter(OutlineProjectLibResponse::getOplParentId)
                        .treeMode(false)
                        .withIndexPrefix(false)
                        .build()
        );
        List<String> contents = new ArrayList<>();
        libResponses.forEach(response -> {
            contents.add(response.getOplContent());
            contents.add(response.getOplName());
        });

        // 查询所有的项目名称
        List<String> projectNames = baseMapper.getAllProjectName(projectManage.getPId());

        return BaseResponse.ok(OutlineLibUtils.matchProjectName(projectNames, contents));
    }

    /**
     * 根据大纲体系id获取FileId
     *
     * @param oplId
     * @return
     */
    @Override
    public BaseResponse getFileIdByOplId(String oplId) {
        OutlineProjectLib projectLib = this.getById(oplId);
        if (ValidateUtil.isNull(projectLib)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // 查询业务功能列表
        List<OutlineProjectTempResponse> responses = outlineProjectTempService.getOutlineProjectTempByOplIds(Collections.singletonList(oplId));
        if (ValidateUtil.isEmpty(responses)) {
            return BaseResponse.ok();
        }
        OutlineProjectManage projectManage = outlineProjectManageService.getById(projectLib.getOpmId());
        List<String> tCodes = responses.stream().map(OutlineProjectTempResponse::getTCode).collect(Collectors.toList());
        if (ValidateUtil.isEmpty(tCodes)) {
            return BaseResponse.ok();
        }
        List<SysFile> result = baseMapper.getFileIdByTCodes(projectManage.getPId(), tCodes);
        return BaseResponse.ok(result);
    }

    /**
     * 根据项目id和大纲体系编码获取大纲体系id
     *
     * @param pId
     * @param tCode
     * @return
     */
    @Override
    public BaseResponse getOplIdByPidAndTCode(String pId, String tCode) {
        if (ValidateUtil.isEmpty(pId) || ValidateUtil.isEmpty(tCode)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String outlineTCode = sysConfigService.getConfigByCode("outlineTCode");
        if (ValidateUtil.isNotEmpty(outlineTCode)) {
            // 拆分
            List<String> outlineTCodeList = Arrays.stream(outlineTCode.split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            if (outlineTCodeList.contains(tCode)) {
                return BaseResponse.fail(ResMsg.notReqParam);
            }
        } else {
            log.warn("无outlineTCode配置");
        }
        List<OutlineProjectManage> manages = outlineProjectManageService.list(
                Wrappers.<OutlineProjectManage>lambdaQuery()
                        .eq(OutlineProjectManage::getPId, pId)
                        .ne(OutlineProjectManage::getState, ApprovalStatusEnum.Invalid.getIndex())
                        // .eq(OutlineProjectManage::getState, ApprovalStatusEnum.Already_Over.getIndex())
                        .select(OutlineProjectManage::getId, OutlineProjectManage::getOpmCode)
        );
        if (ValidateUtil.isEmpty(manages)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // 构建opmId和opmCode的映射关系
        Map<String, String> typeMap = manages.stream().collect(Collectors.toMap(OutlineProjectManage::getId, OutlineProjectManage::getOpmCode));
        List<String> opmIds = manages.stream().map(OutlineProjectManage::getId).collect(Collectors.toList());
        List<OutlineProjectLibResponse> result = baseMapper.getOplIdByPidAndTCode(opmIds);
        if (ValidateUtil.isEmpty(result)) {
            return BaseResponse.ok(ResMsg.infoNotExist);
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, List<OutlineProjectLibResponse>> groupedByOpmCode = result.stream()
                .filter(lib -> typeMap.containsKey(lib.getOpmId()))
                .collect(Collectors.groupingBy(lib -> typeMap.get(lib.getOpmId())));
        for (Map.Entry<String, List<OutlineProjectLibResponse>> entry : groupedByOpmCode.entrySet()) {
            String opmCode = entry.getKey();
            // 排序
            List<OutlineProjectLibResponse> libs = OutlineLibUtils.flatSortByTree(
                    entry.getValue(),
                    BuildOpts.<OutlineProjectLibResponse>builder()
                            .idGetter(OutlineProjectLibResponse::getId)
                            .parentIdGetter(OutlineProjectLibResponse::getOplParentId)
                            .sortGetter(OutlineProjectLibResponse::getOplSort)
                            .build()
            );
            // 过滤掉不需要的tCode
            Map<String, OutlineProjectStyleDtl> styleDtlMap = outlineProjectStyleService.getStyleDtlMapByCategory("02");
            libs = libs.stream()
                    .filter(lib -> tCode.equals(lib.getTCode()))
                    .peek(lib -> lib.setOplContent(HtmlUtils.prettyHtmlPrint(lib.getOplContent(), styleDtlMap)))
                    .collect(Collectors.toList());
            Map<String, Object> groupMap = new HashMap<>();
            groupMap.put("opmCode", opmCode);
            groupMap.put("libs", libs);
            groupMap.put("riskCount", libs.stream().mapToInt(OutlineProjectLibResponse::getRiskCount).sum());
            resultList.add(groupMap);
        }
        return BaseResponse.ok(resultList);
    }

    /**
     * 检查大纲叶子节点是否为空
     *
     * @param opmId
     * @return
     */
    @Override
    public BaseResponse checkEmptyOutlineProjectLib(String opmId) {
        if (ValidateUtil.isEmpty(opmId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<OutlineProjectLibResponse> responses = baseMapper.checkEmptyOutlineProjectLib(opmId);
        if (ValidateUtil.isEmpty(responses)) {
            return BaseResponse.ok(Collections.emptyList());
        } else {
            List<String> list = responses.stream().map(OutlineProjectLibResponse::getId).collect(Collectors.toList());
            return BaseResponse.ok(list, list.size());
        }
    }
}
