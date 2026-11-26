package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.projectOutline.*;
import com.jpmsoft.common.model.response.projectOutline.*;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineProjectLibMapper;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineProjectManageMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectManage;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineSystemLib;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskWarn;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.projectOutline.*;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskWarnService;
import com.jpmsoft.projectProducer.utils.HtmlUtils;
import com.jpmsoft.projectProducer.utils.OutlineLib.BuildOpts;
import com.jpmsoft.projectProducer.utils.OutlineLib.OutlineLibUtils;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tianhelin
 * @date 2025/6/11
 */
@Service
public class OutlineProjectManageImpl extends ServiceImpl<OutlineProjectManageMapper, OutlineProjectManage> implements IOutlineProjectManageService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private IOutlineProjectLibService outlineProjectLibService;
    @Resource
    private IOutlineProjectRiskService outlineProjectRiskService;
    @Resource
    private IOutlineProjecDatasourceService outlineProjecDatasourceService;
    @Resource
    private IOutlineSystemManageService outlineSystemManageService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IOutlineProjectTempService outlineProjectTempService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IOutlineNodeDatasourceService outlineNodeDatasourceService;
    @Resource
    private IOutlineSystemLibService outlineSystemLibService;
    @Resource
    private IOutlineLibRiskService outlineLibRiskService;
    @Resource
    private IOutlineNodeTempService outlineNodeTempService;
    @Resource
    private OutlineProjectLibMapper outlineProjectLibMapper;
    @Resource
    private IRiskWarnService riskWarnService;
    @Resource
    private IOutlineDelayDatasourceService outlineDelayDatasourceService;

    /**
     * 获取项目管理大纲管理分页
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectManagePage(OutlineProjectManageRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<OutlineProjectManageRequest> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<OutlineProjectManageRequest> map = new QueryMap<>(request);
        map.put("id").put("ids").put("pId").put("pIds").put("osmId").put("createTime")
                .put("createUid").put("createUname").put("pNameList").put("state")
                .put("stateList").put("opmStatus").put("osmName").put("pName").put("opmCode");
        List<OutlineProjectManageResponse> data = baseMapper.getOutlineProjectManagePage(page, map.getQuery());
        // 过滤出已作废的
        Set<String> opmIds = data.stream().filter(item -> ApprovalStatusEnum.Invalid.getIndex().equals(item.getState())).map(OutlineProjectManageResponse::getId).collect(Collectors.toSet());
        if (ValidateUtil.isNotEmpty(opmIds)) {
            outlineDelayDatasourceService.deleteByOpmIds(opmIds);
        }
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }

    /**
     * 获取项目管理详情
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectManageById(String id) {
        if (ValidateUtil.isNull(id) || "undefined".equalsIgnoreCase(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectManageResponse data = baseMapper.getOutlineProjectManageById(id);
        if (ValidateUtil.isNull(data)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // OutlineProjectLibRequest request = new OutlineProjectLibRequest();
        // request.setOpmId(id);
        // data.setOutlineProjectLibList(outlineProjectLibService.selectOutlineProjectLibTree(request));
        // List<OutlineProjectLib> list = outlineProjectLibService.list(Wrappers.<OutlineProjectLib>lambdaQuery().eq(OutlineProjectLib::getOpmId, id).select(OutlineProjectLib::getId));
        // if (ValidateUtil.isNotEmpty(list)) {
        //     List<String> oplIds = list.stream().map(OutlineProjectLib::getId).collect(Collectors.toList());
        //     data.setOutlineProjectTempList(outlineProjectTempService.getOutlineProjectTempByOplIds(oplIds));
        //     data.setOutlineProjecDatasourceList(outlineProjecDatasourceService.getOutlineProjecDatasourceByOplIds(oplIds));
        // }
        // data.setOutlineProjectRiskList(outlineProjectRiskService.getAllOutlineProjectRiskByOpmId(id));
        return BaseResponse.ok(data);
    }

    /**
     * 内部调用查询项目管理详情
     *
     * @param id
     * @return
     */
    @Override
    public OutlineProjectManageResponse selectOutlineProjectManageById(String id) {
        OutlineProjectManageResponse data = baseMapper.getOutlineProjectManageById(id);
        if (ValidateUtil.isNull(data)) {
            return null;
        }
        OutlineProjectLibRequest request = new OutlineProjectLibRequest();
        request.setOpmId(id);
        request.setSortRules(data.getSortRules());
        data.setOutlineProjectLibListFlow(outlineProjectLibService.selectOutlineProjectLibTree(request, false, true, false, true, false));
        return data;
    }

    /**
     * 创建或更新项目管理
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOutlineProjectManage(OutlineProjectManageRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectManage entity = new OutlineProjectManage();
        BeanUtils.copyProperties(request, entity);
        boolean isNew = ValidateUtil.isEmpty(entity.getId());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        if (isNew) {
            if (existsProject(entity.getPId(), entity.getOpmCode())) {
                return BaseResponse.fail(ResMsg.projectCpReset);
            }
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setStateTime(new Date());
            entity.setOpmUnit(ValidateUtil.isNotEmpty(request.getOpmUnit()) ? request.getOpmUnit() : user.getCName());
            OutlineSystemManageResponse systemManage = outlineSystemManageService.selectOutlineSystemManageById(request.getOsmId());
            if (ValidateUtil.isNull(systemManage)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            // 补上manage的内容
            List<OutlineSystemLib> systemLibs = outlineSystemLibService.list(Wrappers.<OutlineSystemLib>lambdaQuery().eq(OutlineSystemLib::getOsmId, request.getOsmId()).select(OutlineSystemLib::getId));
            if (ValidateUtil.isNotEmpty(systemLibs)) {
                List<String> oslIds = systemLibs.stream().map(OutlineSystemLib::getId).collect(Collectors.toList());
                // 体系模板管理
                List<OutlineNodeTempResponse> outlineNodeTempList = outlineNodeTempService.getAllOutlineNodeTempByOslIds(oslIds);
                if (ValidateUtil.isNotEmpty(outlineNodeTempList)) {
                    systemManage.setOutlineNodeTempList(outlineNodeTempList);
                    // 处理前置功能的校验
                    // String preTypeVerify = this.handlePreTypeVerify(outlineNodeTempList, request);
                    // if (ValidateUtil.isNotEmpty(preTypeVerify)) {
                    //     return BaseResponse.fail(preTypeVerify);
                    // }
                }
                // 体系库风险指标
                systemManage.setOutlineLibRiskList(outlineLibRiskService.getAllOutlineLibRiskByOslIds(oslIds));
                // 体系库节点数据源
                systemManage.setOutlineNodeDatasourceList(outlineNodeDatasourceService.getAllOutlineNodeDatasourceByOslIds(oslIds));
            }
            OutlineSystemLibRequest req = new OutlineSystemLibRequest();
            req.setOsmId(request.getOsmId());
            // 树结构
            systemManage.setOutlineSystemLibList(outlineSystemLibService.selectOutlineSystemLibTree(req));
            entity.setOpmCode(systemManage.getOsmCode());
            request = this.buildCoveredRequest(request, systemManage, token);
        } else {
            OutlineProjectManage model = this.getById(entity.getId());
            if (ValidateUtil.isNull(model)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            entity.setCreateTime(model.getCreateTime());
            entity.setCreateUid(model.getCreateUid());
            entity.setCreateUname(model.getCreateUname());
            entity.setUpdateTime(new Date());
            entity.setUpdateUid(user.getId());
            entity.setUpdateUname(user.getName());
        }

        boolean success = isNew ? this.save(entity) : this.updateById(entity);
        if (success) {
            List<OutlineProjectLibRequest> outlineProjectLib = null;
            if (request != null) {
                outlineProjectLib = request.getOutlineProjectLibList();
            }
            if (ValidateUtil.isNotEmpty(outlineProjectLib)) {
                outlineProjectLib.forEach(model -> model.setOpmId(entity.getId()));
                request.setOutlineProjectLibList(outlineProjectLib);
                outlineProjectLibService.batchCreateOutlineProjectLib(request);
            }
            if (isNew) {
                sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                        entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                        request.getPId(), request.getFileTitle(), request.getFileCode(), "create",
                        request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
                // sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
            } else {
                sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                        entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                        request.getPId(), request.getFileTitle(), request.getFileCode(), "update",
                        request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
                // sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
            }
            // 事务结束后调用前置功能校验
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    getPreTypeVerifyIds(entity.getId());
                }
            });
            return BaseResponse.ok(entity.getId());
        } else {
            return BaseResponse.fail(isNew ? "新增失败" : "更新失败");
        }
    }

    /**
     * 根据pId判断项目是否已生成
     *
     * @param pId
     * @return
     */
    private boolean existsProject(String pId, String opmCode) {
        return baseMapper.exists(Wrappers.<OutlineProjectManage>lambdaQuery()
                .eq(OutlineProjectManage::getPId, pId)
                .ne(OutlineProjectManage::getState, ApprovalStatusEnum.Invalid.getIndex())
                .eq(OutlineProjectManage::getOpmCode, opmCode)
        );
    }

    /**
     * 删除项目管理
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    public BaseResponse deleteOutlineProjectManageById(String id, String token) {
        if (ValidateUtil.isNull(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectManage model = this.getById(id);
        if (ValidateUtil.isNull(model)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        this.removeById(id);
        sysFileService.deleteFileById(id);
        // 删除关联
        outlineProjectLibService.deleteOutlineSubtablesByOpmId(id);
        return BaseResponse.ok();
    }

    /**
     * 批量更新项目状态
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse batchUpdateProjectManageStatus(OutlineProjectManageRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<String> ids = request.getIds();
        if (ValidateUtil.isNotEmpty(ids)) {
            lambdaUpdate()
                    .in(OutlineProjectManage::getId, ids)
                    .set(OutlineProjectManage::getState, request.getState())
                    .update();
        }
        return BaseResponse.ok();
    }

    /**
     * 批量更新项目管理启用状态
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse batchUpdateProjectManageOpmStatus(OutlineProjectManageRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<String> ids = request.getIds();
        if (ValidateUtil.isNotEmpty(ids)) {
            lambdaUpdate()
                    .in(OutlineProjectManage::getId, ids)
                    .set(OutlineProjectManage::getOpmStatus, request.getOpmStatus())
                    .update();
        }
        return BaseResponse.ok();
    }

    /**
     * 将模板转化成项目管理大纲
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse coverSystemManageToProjectManage(OutlineProjectManageRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        return this.createOrUpdateOutlineProjectManage(request, token);
    }

    /**
     * 处理前置功能的校验
     *
     * @param systemTemps
     * @param request
     * @return
     */
    private String handlePreTypeVerify(List<OutlineNodeTempResponse> systemTemps, OutlineProjectManageRequest request) {
        if (ValidateUtil.isEmpty(systemTemps)) {
            return "";
        }

        // 获取所有需要判断前置的模板代码
        Set<String> tCodes = systemTemps.stream()
                .filter(temp -> "01".equalsIgnoreCase(temp.getPreType()))
                .map(OutlineNodeTempResponse::getTCode)
                .collect(Collectors.toSet());

        if (tCodes.isEmpty()) {
            return "";
        }

        // 获取当前项目已有的模板代码
        Set<String> existingTCodes = sysFileService.list(Wrappers.<SysFile>lambdaQuery()
                        .ne(SysFile::getFileflowstatus, ApprovalStatusEnum.Invalid.getIndex())
                        .eq(SysFile::getFileproject, request.getPId()))
                .stream()
                .filter(Objects::nonNull)
                .map(SysFile::gettCode)
                .collect(Collectors.toSet());

        // 未完善的前置功能
        Set<String> missingTCodes = new HashSet<>(tCodes);
        missingTCodes.removeAll(existingTCodes);

        if (missingTCodes.isEmpty()) {
            return "";
        }

        // 获取tName
        List<String> missingTNames = Optional.ofNullable(baseMapper.getTNamesByTCodes(missingTCodes))
                .orElse(Collections.emptyList());

        return !missingTNames.isEmpty()
                ? "该项目以下前置功能未完善：" + String.join("，", missingTNames)
                : "该项目前置功能不完善，请先完善前置功能后再进行转化";
    }

    /**
     * 获取已完善前置功能的模板映射
     *
     * @param systemTemps
     * @param systemDatasourceList
     * @param pId
     * @return
     */
    private Map<String, List<String>> getPreTypeVerifyFinishedTemplateMap(List<OutlineNodeTempResponse> systemTemps, List<OutlineNodeDatasourceResponse> systemDatasourceList, String pId) {
        if (ValidateUtil.isEmpty(systemTemps)) {
            return Collections.emptyMap();
        }
        // 获取所有需要判断前置的模板代码
        Set<String> tCodes = systemTemps.stream()
                .filter(temp -> "01".equalsIgnoreCase(temp.getPreType()))
                .map(OutlineNodeTempResponse::getTCode)
                .collect(Collectors.toSet());

        if (tCodes.isEmpty()) {
            return Collections.emptyMap();
        }

        // 流程文件查询
        List<SysFile> files = sysFileService.list(
                Wrappers.<SysFile>lambdaQuery()
                        .eq(SysFile::getFileproject, pId)
                        .eq(SysFile::getFileflowstatus, ApprovalStatusEnum.Already_Over.getIndex()));
        // 审批通过的，也就是已完善的
        Set<String> existingTCodes = files.stream()
                .map(SysFile::gettCode)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toSet());
        Set<String> finishedTCodes = new HashSet<>(tCodes);
        finishedTCodes.retainAll(existingTCodes);

        // 如果没有已完善的前置功能，则直接返回空
        if (finishedTCodes.isEmpty()) {
            return Collections.emptyMap();
        }

        // 构建oslId -> 别名列表的映射
        return getOslIdToAliasesMap(systemTemps, systemDatasourceList, finishedTCodes);
    }

    /**
     * 构建oslId -> 别名列表的映射
     *
     * @param systemTemps
     * @param systemDatasourceList
     * @param finishedTCodes
     * @return
     */
    private static Map<String, List<String>> getOslIdToAliasesMap(List<OutlineNodeTempResponse> systemTemps, List<OutlineNodeDatasourceResponse> systemDatasourceList, Set<String> finishedTCodes) {
        Map<String, List<String>> oslIdToAliasesMap = new HashMap<>();
        for (OutlineNodeTempResponse temp : systemTemps) {
            if (finishedTCodes.contains(temp.getTCode())) {
                List<String> aliases = new ArrayList<>();
                if (ValidateUtil.isNotEmpty(systemDatasourceList)) {
                    for (OutlineNodeDatasourceResponse ds : systemDatasourceList) {
                        if (temp.getOslId().equals(ds.getOslId()) && ValidateUtil.isNotEmpty(ds.getTCodes()) && ds.getTCodes().contains(temp.getTCode())) {
                            aliases.add(ds.getOdAlias());
                        }
                    }
                }
                if (ValidateUtil.isNotEmpty(aliases)) {
                    oslIdToAliasesMap.put(temp.getOslId(), aliases);
                }
            }
        }
        return oslIdToAliasesMap;
    }

    /**
     * 构建转化
     *
     * @param request
     * @param response
     * @param token
     * @return
     */
    private OutlineProjectManageRequest buildCoveredRequest(OutlineProjectManageRequest request, OutlineSystemManageResponse response, String token) {
        if (ValidateUtil.isNull(response)) {
            return null;
        }

        Date now = new Date();
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);

        List<OutlineNodeTempResponse> systemTemps = response.getOutlineNodeTempList();
        List<OutlineLibRiskResponse> systemRisks = response.getOutlineLibRiskList();
        List<OutlineNodeDatasourceResponse> systemDatasourceList = response.getOutlineNodeDatasourceList();
        Map<String, List<OutlineNodeDatasourceResponse>> datasourceMap;
        if (ValidateUtil.isNotEmpty(systemDatasourceList)) {
            datasourceMap = systemDatasourceList.stream().collect(Collectors.groupingBy(OutlineNodeDatasourceResponse::getOslId, Collectors.toList()));
        } else {
            datasourceMap = new HashMap<>();
        }

        Map<String, List<String>> aliasesMap = this.getPreTypeVerifyFinishedTemplateMap(systemTemps, systemDatasourceList, request.getPId());

        Map<String, String> oslIdMap = new HashMap<>();
        List<OutlineProjectLibRequest> newProjectLibList = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(response.getOutlineSystemLibList())) {
            response.getOutlineSystemLibList().forEach(systemLib ->
                    convertToProjectLibTree(systemLib, "0", newProjectLibList, oslIdMap, user, now, request.getPId(), datasourceMap, aliasesMap)
            );
        }
        request.setOutlineProjectLibList(newProjectLibList);

        if (ValidateUtil.isNotEmpty(systemTemps)) {
            List<OutlineProjectTempRequest> projectTemps = new ArrayList<>();
            for (OutlineNodeTempResponse temp : systemTemps) {
                OutlineProjectTempRequest projectTemp = new OutlineProjectTempRequest();
                projectTemp.setId(GenerateUUID.getUUID());
                projectTemp.setOplId(oslIdMap.get(temp.getOslId()));
                projectTemp.setTCode(temp.getTCode());
                projectTemp.setTName(temp.getTName());
                projectTemp.setOpdSort(temp.getOdtSort());
                projectTemp.setCreateTime(now);
                projectTemp.setCreateUid(user.getId());
                projectTemp.setCreateUname(user.getName());
                projectTemp.setPreType(temp.getPreType());
                projectTemps.add(projectTemp);
            }
            request.setOutlineProjectTempList(projectTemps);
        }

        if (ValidateUtil.isNotEmpty(systemRisks)) {
            List<OutlineProjectRiskRequest> projectRisks = new ArrayList<>();
            for (OutlineLibRiskResponse risk : systemRisks) {
                OutlineProjectRiskRequest projectRisk = new OutlineProjectRiskRequest();
                projectRisk.setId(GenerateUUID.getUUID());
                projectRisk.setRiskId(risk.getRiskId());
                projectRisk.setOprSort(risk.getOlrSort());
                projectRisk.setOplId(oslIdMap.get(risk.getOslId()));
                projectRisk.setCreateTime(now);
                projectRisk.setCreateUid(user.getId());
                projectRisk.setCreateUname(user.getName());
                projectRisks.add(projectRisk);
            }
            request.setOutlineProjectRiskList(projectRisks);
        }

        if (ValidateUtil.isNotEmpty(systemDatasourceList)) {
            List<OutlineProjecDatasourceRequest> projectDatasourceList = new ArrayList<>();
            for (OutlineNodeDatasourceResponse ds : systemDatasourceList) {
                OutlineProjecDatasourceRequest projectDs = new OutlineProjecDatasourceRequest();
                BeanUtils.copyProperties(ds, projectDs);
                projectDs.setId(GenerateUUID.getUUID());
                projectDs.setOplId(oslIdMap.get(ds.getOslId()));
                String odStatus = ds.getOdStatus();
                if (odStatus != null) {
                    projectDs.setOdStatus("1".equals(odStatus));
                } else {
                    projectDs.setOdStatus(true);
                }
                projectDs.setCreateTime(now);
                projectDs.setCreateUid(user.getId());
                projectDs.setCreateUname(user.getName());
                projectDatasourceList.add(projectDs);
            }
            request.setOutlineProjecDatasourceList(projectDatasourceList);
        }

        return request;
    }

    /**
     * 递归转换系统库树结构为项目库树结构
     *
     * @param root
     * @param newParentId
     * @param targetList
     * @param idMap
     * @param user
     * @param now
     * @param pId
     * @param datasourceMap
     * @param finishedTemplateMap
     */
    private void convertToProjectLibTree(
            OutlineSystemLibResponse root,
            String newParentId,
            List<OutlineProjectLibRequest> targetList,
            Map<String, String> idMap,
            LogUserInfoVo user,
            Date now,
            String pId,
            Map<String, List<OutlineNodeDatasourceResponse>> datasourceMap,
            Map<String, List<String>> finishedTemplateMap
    ) {
        Deque<OutlineLibUtils.NodePair<OutlineSystemLibResponse>> stack = new ArrayDeque<>();
        stack.push(new OutlineLibUtils.NodePair<>(root, newParentId));

        while (!stack.isEmpty()) {
            OutlineLibUtils.NodePair<OutlineSystemLibResponse> current = stack.pop();
            OutlineSystemLibResponse node = current.node;
            String parentId = current.parentId;

            String newId = GenerateUUID.getUUID();
            idMap.put(node.getId(), newId);
            OutlineProjectLibRequest newItem = new OutlineProjectLibRequest();
            newItem.setId(newId);
            newItem.setOplParentId(parentId);
            newItem.setOplName(node.getOslName());
            newItem.setOplCode(node.getOslCode());
            newItem.setOplRemark(node.getOslRemark());
            newItem.setOplStatus(node.getOslStatus());
            newItem.setOplSort(node.getOslSort());
            // 渲染数据源
            String oslContent = node.getOslContent();
            // 如果整个内容只有空格和各种换行符，则将其直接设置为null，避免渲染后导出时出现空白目录
            if (HtmlUtils.isInvalidHtml(oslContent)) {
                oslContent = null;
            } else {
                oslContent = outlineNodeDatasourceService.handleDatasourceRender(node, pId, datasourceMap.get(node.getId()), finishedTemplateMap);
            }
            newItem.setOplContent(oslContent);
            // 模板原数据
            // newItem.setOpnContent(oslContent);
            newItem.setOplBussnis(node.getOslBussnis());
            newItem.setCreateTime(now);
            newItem.setCreateUid(user.getId());
            newItem.setCreateUname(user.getName());
            targetList.add(newItem);

            if (ValidateUtil.isNotEmpty(node.getChildNodes())) {
                for (OutlineSystemLibResponse child : node.getChildNodes()) {
                    stack.push(new OutlineLibUtils.NodePair<>(child, newId));
                }
            }
        }
    }

    /**
     * 获取项目对应的项目管理大纲
     *
     * @param pId
     * @param opmCode
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectManageByPid(String pId, String opmCode) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectManageResponse response = baseMapper.getOutlineProjectManageByPid(pId, opmCode);
        if (ValidateUtil.isNull(response)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineProjectLibRequest request = new OutlineProjectLibRequest();
        request.setOpmId(response.getId());
        response.setOutlineProjectLibList(outlineProjectLibService.selectOutlineProjectLibTree(request, true, true, true, false, false));
        List<OutlineProjectLibResponse> libResponses = outlineProjectLibService.selectOutlineProjectLibTree(request, false, true, false, false, true);
        response.setOutlineProjectLibListFlow(libResponses);
        if (ValidateUtil.isNotEmpty(libResponses)) {
            List<String> oplIds = libResponses.stream().map(OutlineProjectLibResponse::getId).collect(Collectors.toList());
            response.setOutlineProjectTempList(outlineProjectTempService.getOutlineProjectTempByOplIds(oplIds));
            response.setOutlineProjecDatasourceList(outlineProjecDatasourceService.getOutlineProjecDatasourceByOplIds(oplIds));
        }
        response.setOutlineProjectRiskList(outlineProjectRiskService.getAllOutlineProjectRiskByOpmId(response.getId()));
        return BaseResponse.ok(response);
    }

    /**
     * 获取项目对应的项目管理大纲列表
     *
     * @param pId
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectManageByPidList(String pId) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<OutlineProjectManageResponse> manages = baseMapper.getOutlineProjectManageByPidList(pId);
        return BaseResponse.ok(manages);
    }

    /**
     * 刷新最后更新时间
     *
     * @param opmId
     * @param user
     */
    @Override
    public void refreshLastUpdateTime(String opmId, LogUserInfoVo user) {
        this.update(Wrappers.<OutlineProjectManage>lambdaUpdate()
                .eq(OutlineProjectManage::getId, opmId)
                .set(OutlineProjectManage::getUpdateTime, new Date())
                .set(OutlineProjectManage::getUpdateUid, user.getId())
                .set(OutlineProjectManage::getUpdateUname, user.getName())
        );
    }

    /**
     * 获取前置功能未完善的模板
     *
     * @param opmId
     * @return
     */
    @Override
    public BaseResponse getPreTypeVerifyIds(String opmId) {
        if (ValidateUtil.isEmpty(opmId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectManage projectManage = getById(opmId);
        if (ValidateUtil.isNull(projectManage)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineProjectLibRequest request = new OutlineProjectLibRequest();
        request.setOpmId(opmId);
        request.setNoContent(true);
        List<OutlineProjectLibResponse> projectLibs = outlineProjectLibMapper.getOutlineProjectLibTree(request);
        if (ValidateUtil.isEmpty(projectLibs)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        List<String> oplIds = projectLibs.stream()
                .map(OutlineProjectLibResponse::getId)
                .collect(Collectors.toList());
        List<OutlineProjectTempResponse> projectTempByOplIds = outlineProjectTempService.getOutlineProjectTempByOplIds(oplIds);
        if (ValidateUtil.isEmpty(projectTempByOplIds)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        Set<String> tCodes = projectTempByOplIds.stream()
                .filter(temp -> "01".equalsIgnoreCase(temp.getPreType()))
                .map(OutlineProjectTempResponse::getTCode)
                .collect(Collectors.toSet());

        // 流程文件查询
        List<SysFile> files = sysFileService.list(
                Wrappers.<SysFile>lambdaQuery()
                        .eq(SysFile::getFileproject, projectManage.getPId())
                        .ne(SysFile::getFileflowstatus, ApprovalStatusEnum.Invalid.getIndex()));
        // 排除作废的，也就是给前端返回使用的已完善的
        Set<String> existingTCodes = files.stream()
                .map(SysFile::gettCode)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toSet());
        // 审批中的
        Set<String> approvingTCodes = files.stream()
                .filter(file -> !String.valueOf(ApprovalStatusEnum.Already_Over.getIndex()).equals(file.getFileflowstatus()))
                .map(SysFile::gettCode)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toSet());
        Set<String> missingTCodes = new HashSet<>(tCodes);
        missingTCodes.removeAll(existingTCodes);

        // 如果没有未完善的前置功能，则直接返回空列表
        if (missingTCodes.isEmpty() && approvingTCodes.isEmpty()) {
            outlineDelayDatasourceService.handleDelayRender(Collections.emptyList(), projectManage.getPId());
            return BaseResponse.ok(Collections.emptyList());
        }

        // 处理目录名及前缀
        List<OutlineProjectLibResponse> outlineProjectLibs = OutlineLibUtils.buildTreeOrList(
                projectLibs,
                BuildOpts.<OutlineProjectLibResponse>builder()
                        .idGetter(OutlineProjectLibResponse::getId)
                        .parentIdGetter(OutlineProjectLibResponse::getOplParentId)
                        .sortGetter(OutlineProjectLibResponse::getOplSort)
                        .catalogSetter(OutlineProjectLibResponse::setOplCatalogName)
                        .nameGetter(OutlineProjectLibResponse::getOplName)
                        .sortRules(projectManage.getSortRules())
                        .treeMode(false)
                        .build()
        );
        Map<String, OutlineProjectLibResponse> libById = outlineProjectLibs.stream()
                .collect(Collectors.toMap(
                        OutlineProjectLibResponse::getId,
                        Function.identity(),
                        (a, b) -> a
                ));
        List<OutlineProjectTempResponse> result = new ArrayList<>();
        if (!missingTCodes.isEmpty()) {
            result = getProjectTemp(projectTempByOplIds, missingTCodes, libById);
        }
        missingTCodes.addAll(approvingTCodes);
        outlineDelayDatasourceService.handleDelayRender(getProjectTemp(projectTempByOplIds, missingTCodes, libById), projectManage.getPId());
        return BaseResponse.ok(result);
    }

    /**
     * 获取前置功能未完善的模板，交由下一步处理
     *
     * @param projectTempByOplIds
     * @param missingTCodes
     * @param libById
     * @return
     */
    private List<OutlineProjectTempResponse> getProjectTemp(List<OutlineProjectTempResponse> projectTempByOplIds, Set<String> missingTCodes, Map<String, OutlineProjectLibResponse> libById) {
        if (ValidateUtil.isEmpty(projectTempByOplIds) || ValidateUtil.isEmpty(missingTCodes) || ValidateUtil.isEmpty(libById)) {
            return Collections.emptyList();
        }
        List<OutlineProjectTempResponse> result = projectTempByOplIds.stream()
                .filter(temp -> "01".equalsIgnoreCase(temp.getPreType()) && missingTCodes.contains(temp.getTCode()))
                .collect(Collectors.toList());
        // 为每个模板添加目录名和前缀
        for (OutlineProjectTempResponse temp : result) {
            OutlineProjectLibResponse lib = libById.get(temp.getOplId());
            if (ValidateUtil.isNotNull(lib)) {
                temp.setOplName(lib.getOplName());
                temp.setOplCatalogName(lib.getOplCatalogName());
            } else {
                temp.setOplName("未找到对应目录");
            }
        }
        return result;
    }

    /**
     * 获取大纲台账统计
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectLedgerCount(OutlineProjectManageRequest request) {
        Page<OutlineProjectManageRequest> page = new Page<>(1, Integer.MAX_VALUE);
        QueryMap<OutlineProjectManageRequest> map = new QueryMap<>(request);
        map.put("pName").put("opmCode").put("state", 7);
        List<OutlineProjectManageResponse> list = baseMapper.getOutlineProjectManagePage(page, map.getQuery());
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok(new HashMap<String, Integer>() {{
                put("total", 0);
                put("warning", 0);
                put("finished", 0);
                put("rate", 0);
            }});
        }
        Set<String> pIds = list.stream().map(OutlineProjectManageResponse::getPId).collect(Collectors.toSet());
        List<RiskWarn> riskWarns = riskWarnService.list(
                Wrappers.<RiskWarn>lambdaQuery()
                        .in(RiskWarn::getPId, pIds)
                        .ne(RiskWarn::getRwState, 3)
                        .select(RiskWarn::getRwState)
        );
        long finished = riskWarns.stream().filter(rw -> rw.getRwState() == 7).count();
        int rate;
        if (finished > 0) {
            rate = (int) ((double) finished / riskWarns.size() * 100);
        } else {
            rate = 0;
        }
        return BaseResponse.ok(new HashMap<String, Integer>() {{
            put("total", list.size());
            put("warning", riskWarns.size());
            put("finished", (int) finished);
            put("rate", rate);
        }});
    }

    /**
     * 获取大纲台账分页
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectLedgerPage(OutlineProjectManageRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<OutlineProjectManageResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OutlineProjectManageResponse> datas = baseMapper.getOutlineProjectLedgerPage(page, request);
        if (ValidateUtil.isEmpty(datas)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        // 查询全部的id对应的已有的预警
        List<String> ids = datas.stream().map(OutlineProjectManageResponse::getId).collect(Collectors.toList());
        List<OutlineProjectRiskResponse> projectRiskLedgerByOpmIds = outlineProjectRiskService.getProjectRiskLedgerByOpmIds(ids);
        datas.forEach(opm -> {
            List<OutlineProjectRiskResponse> risks = projectRiskLedgerByOpmIds.stream()
                    .filter(risk -> risk.getOpmId().equals(opm.getId()))
                    .collect(Collectors.toList());
            // 处理风险预警
            if (ValidateUtil.isNotEmpty(risks)) {
                long warning = risks.stream().filter(r -> r.getRwState() != 3).count();
                long finished = risks.stream().filter(r -> r.getRwState() == 7).count();
                opm.setRiskWarning((int) warning);
                opm.setRiskFinished((int) finished);
                if (warning + finished > 0) {
                    opm.setRiskRate((int) ((double) finished / (warning + finished) * 100));
                } else {
                    opm.setRiskRate(0);
                }
            } else {
                opm.setRiskWarning(0);
                opm.setRiskFinished(0);
                opm.setRiskRate(0);
            }
        });
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }
}
