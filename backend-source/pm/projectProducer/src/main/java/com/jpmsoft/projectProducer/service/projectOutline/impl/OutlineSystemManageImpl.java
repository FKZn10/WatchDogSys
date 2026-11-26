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
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineSystemManageMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineSystemLib;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineSystemManage;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.projectOutline.*;
import com.jpmsoft.projectProducer.utils.DateFormatUtil;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.OutlineLib.OutlineLibUtils;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author tianhelin
 * @date 2025/6/11
 */
@Service
public class OutlineSystemManageImpl extends ServiceImpl<OutlineSystemManageMapper, OutlineSystemManage> implements IOutlineSystemManageService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private IOutlineSystemLibService outlineSystemLibService;
    @Resource
    private IOutlineLibRiskService outlineLibRiskService;
    @Resource
    private IOutlineNodeDatasourceService outlineNodeDatasourceService;
    @Resource
    private IOutlineNodeTempService outlineNodeTempService;
    @Resource
    private RedisUtils redisUtils;

    /**
     * 获取体系模板分页
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getOutlineSystemManagePage(OutlineSystemManageRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<OutlineSystemManageRequest> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<OutlineSystemManageRequest> map = new QueryMap<>(request);
        map.put("osmName").put("osmNameList").put("osmCode").put("osmCodeList").put("osmStatus")
                .put("osmStatusList").put("osmProjectType").put("osmProjectTypeList").put("osmDepartmentId")
                .put("osmDepartmentIdList").put("createTime").put("state").put("stateList");
        List<OutlineSystemManageResponse> data = baseMapper.getOutlineSystemManagePage(page, map.getQuery());
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }

    /**
     * 获取体系模板详情
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getOutlineSystemManageById(String id) {
        if (ValidateUtil.isNull(id) || "undefined".equalsIgnoreCase(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineSystemManageResponse outlineSystemManage = this.selectOutlineSystemManageById(id);
        if (ValidateUtil.isNull(outlineSystemManage)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        return BaseResponse.ok(outlineSystemManage);
    }

    /**
     * 查询体系模板详情
     * 内部调用，默认不包含子表数据
     *
     * @param id
     * @return
     */
    @Override
    public OutlineSystemManageResponse selectOutlineSystemManageById(String id) {
        return selectOutlineSystemManageById(id, false);
    }

    /**
     * 查询体系模板详情
     * 内部调用
     *
     * @param id
     * @param includeSubtables
     * @return
     */
    @Override
    public OutlineSystemManageResponse selectOutlineSystemManageById(String id, Boolean includeSubtables) {
        OutlineSystemManageResponse outlineSystemManage = baseMapper.getOutlineSystemManageById(id);
        if (ValidateUtil.isNull(outlineSystemManage)) {
            return null;
        }
        if (includeSubtables) {
            OutlineSystemLibRequest request = new OutlineSystemLibRequest();
            request.setOsmId(id);
            // 树结构
            outlineSystemManage.setOutlineSystemLibList(outlineSystemLibService.selectOutlineSystemLibTree(request));
            List<OutlineSystemLib> systemLibs = outlineSystemLibService.list(Wrappers.<OutlineSystemLib>lambdaQuery().eq(OutlineSystemLib::getOsmId, id).select(OutlineSystemLib::getId));
            if (ValidateUtil.isNotEmpty(systemLibs)) {
                List<String> oslIds = systemLibs.stream().map(OutlineSystemLib::getId).collect(Collectors.toList());
                // 体系模板管理
                outlineSystemManage.setOutlineNodeTempList(outlineNodeTempService.getAllOutlineNodeTempByOslIds(oslIds));
                // 体系库风险指标
                outlineSystemManage.setOutlineLibRiskList(outlineLibRiskService.getAllOutlineLibRiskByOslIds(oslIds));
                // 体系库节点数据源
                outlineSystemManage.setOutlineNodeDatasourceList(outlineNodeDatasourceService.getAllOutlineNodeDatasourceByOslIds(oslIds));
            }
        }
        return outlineSystemManage;
    }

    /**
     * 创建或更新体系模板
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse createOrUpdateOutlineSystemManage(OutlineSystemManageRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineSystemManage entity = new OutlineSystemManage();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(request.getOsmName())) {
            entity.setOsmName(request.getOsmName().trim());
        }
        LogUserInfoVo userInfoVo = redisUtils.getUserInfoByToken(token);
        boolean isNew = ValidateUtil.isEmpty(entity.getId());
        if (isNew) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setCreateUid(userInfoVo.getId());
            entity.setCreateUname(userInfoVo.getName());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setStateTime(new Date());
            // entity.setUpdateTime(new Date());
            // entity.setUpdateUid(userInfoVo.getId());
            // entity.setUpdateUname(userInfoVo.getName());
        } else {
            OutlineSystemManage model = this.getById(entity.getId());
            if (ValidateUtil.isNull(model)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            if (JudgeUtil.nonModifyState(model.getState())) {
                return BaseResponse.fail(ResMsg.stateNotModify);
            }
            entity.setCreateTime(model.getCreateTime());
            entity.setCreateUid(model.getCreateUid());
            entity.setCreateUname(model.getCreateUname());
            entity.setUpdateTime(new Date());
            entity.setUpdateUid(userInfoVo.getId());
            entity.setUpdateUname(userInfoVo.getName());
        }

        boolean success = isNew ? this.save(entity) : this.updateById(entity);
        if (success) {
            List<OutlineSystemLibRequest> outlineSystemLib = request.getOutlineSystemLibList();
            if (ValidateUtil.isNotEmpty(outlineSystemLib)) {
                outlineSystemLib.forEach(model -> model.setOsmId(entity.getId()));
                request.setOutlineSystemLibList(outlineSystemLib);
                request.setId(entity.getId());
                outlineSystemLibService.batchCreateOutlineSystemLib(request);
            }
            if (isNew) {
                sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                        entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                        request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                        request.getFileRelationId(), null, request.gettCode(), null);
            } else {
                sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                        entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                        request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                        request.getFileRelationId(), null, request.gettCode(), null);
            }
            return BaseResponse.ok(entity.getId());
        } else {
            return BaseResponse.fail(isNew ? "新增失败" : "更新失败");
        }
    }

    /**
     * 删除体系模板
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteOutlineSystemManageById(OutlineSystemManageRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineSystemManage model = this.getById(request.getId());
        if (ValidateUtil.isNull(model)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        sysFileService.deleteFileById(request.getId());
        outlineSystemLibService.deleteOutlineSubtablesByOsmId(request.getId());
        this.removeById(request.getId());
        return BaseResponse.ok();
    }

    /**
     * 批量更新体系模板状态
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse batchUpdateSystemManageStatus(OutlineSystemManageRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<String> ids = request.getIds();
        if (ValidateUtil.isNotEmpty(ids)) {
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            lambdaUpdate()
                    .in(OutlineSystemManage::getId, ids)
                    .set(OutlineSystemManage::getState, request.getState())
                    .set(OutlineSystemManage::getUpdateTime, new Date())
                    .set(OutlineSystemManage::getUpdateUid, user.getId())
                    .set(OutlineSystemManage::getUpdateUname, user.getName())
                    .update();
        }
        return BaseResponse.ok();
    }

    /**
     * 复制体系模板
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse copyOutlineSystemManageById(String id, String token) {
        if (ValidateUtil.isNull(id) || "undefined".equalsIgnoreCase(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineSystemManageResponse response = this.selectOutlineSystemManageById(id, true);
        if (ValidateUtil.isNull(response)) {
            return BaseResponse.ok(ResMsg.infoNotExist);
        }
        OutlineSystemManageRequest request = this.buildCopiedRequest(response, token);
        return this.createOrUpdateOutlineSystemManage(request, token);
    }

    public OutlineSystemManageRequest buildCopiedRequest(OutlineSystemManageResponse response, String token) {
        if (ValidateUtil.isNull(response)) {
            return null;
        }

        OutlineSystemManageRequest request = new OutlineSystemManageRequest();
        BeanUtils.copyProperties(response, request);
        request.setId(null);

        Date now = new Date();
        // 名字处理
        String name = request.getOsmName();
        if (name != null) {
            if (name.matches(".* - \\d{14}$")) {
                name = name.replaceAll(" - \\d{14}$", " - " + DateFormatUtil.dateToString(now, "yyyyMMddHHmmss"));
            } else {
                name += " - " + DateFormatUtil.dateToString(now, "yyyyMMddHHmmss");
            }
        }
        request.setOsmName(name);

        Map<String, String> oslIdMap = new HashMap<>();
        List<OutlineSystemLibRequest> newSystemLibList = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(response.getOutlineSystemLibList())) {
            response.getOutlineSystemLibList().forEach(systemLib -> convertSystemLibIterative(systemLib, "0", newSystemLibList, oslIdMap));
        }
        request.setOutlineSystemLibList(newSystemLibList);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);

        List<OutlineNodeTempResponse> outlineNodeTempList = response.getOutlineNodeTempList();
        if (ValidateUtil.isNotEmpty(outlineNodeTempList)) {
            List<OutlineNodeTempRequest> newTempList = new ArrayList<>();
            outlineNodeTempList.forEach(temp -> {
                OutlineNodeTempRequest newTemp = new OutlineNodeTempRequest();
                BeanUtils.copyProperties(temp, newTemp);
                newTemp.setId(GenerateUUID.getUUID());
                newTemp.setOslId(oslIdMap.get(temp.getOslId()));
                newTemp.setCreateTime(now);
                newTemp.setCreateUid(user.getId());
                newTemp.setCreateUname(user.getName());
                newTempList.add(newTemp);
            });
            request.setOutlineNodeTempList(newTempList);
        }

        List<OutlineLibRiskResponse> outlineLibRiskList = response.getOutlineLibRiskList();
        if (ValidateUtil.isNotEmpty(outlineLibRiskList)) {
            List<OutlineLibRiskRequest> newRiskList = new ArrayList<>();
            outlineLibRiskList.forEach(risk -> {
                OutlineLibRiskRequest newRisk = new OutlineLibRiskRequest();
                BeanUtils.copyProperties(risk, newRisk);
                newRisk.setId(GenerateUUID.getUUID());
                newRisk.setOslId(oslIdMap.get(risk.getOslId()));
                newRisk.setCreateTime(now);
                newRisk.setCreateUid(user.getId());
                newRisk.setCreateUname(user.getName());
                newRiskList.add(newRisk);
            });
            request.setOutlineLibRiskList(newRiskList);
        }

        List<OutlineNodeDatasourceResponse> outlineNodeDatasourceList = response.getOutlineNodeDatasourceList();
        if (ValidateUtil.isNotEmpty(outlineNodeDatasourceList)) {
            List<OutlineNodeDatasourceRequest> newDatasourceList = new ArrayList<>();
            outlineNodeDatasourceList.forEach(ds -> {
                OutlineNodeDatasourceRequest newDs = new OutlineNodeDatasourceRequest();
                BeanUtils.copyProperties(ds, newDs);
                newDs.setId(GenerateUUID.getUUID());
                newDs.setOslId(oslIdMap.get(ds.getOslId()));
                newDs.setCreateTime(now);
                newDs.setCreateUid(user.getId());
                newDs.setCreateUname(user.getName());
                newDatasourceList.add(newDs);
            });
            request.setOutlineNodeDatasourceList(newDatasourceList);
        }

        return request;
    }

    private void convertSystemLibIterative(OutlineSystemLibResponse root, String newParentId, List<OutlineSystemLibRequest> targetList, Map<String, String> idMap) {
        Deque<OutlineLibUtils.NodePair<OutlineSystemLibResponse>> stack = new ArrayDeque<>();
        stack.push(new OutlineLibUtils.NodePair<>(root, newParentId));

        while (!stack.isEmpty()) {
            OutlineLibUtils.NodePair<OutlineSystemLibResponse> current = stack.pop();
            OutlineSystemLibResponse node = current.node;
            String parentId = current.parentId;

            String newId = GenerateUUID.getUUID();
            idMap.put(node.getId(), newId);
            OutlineSystemLibRequest newItem = new OutlineSystemLibRequest();
            BeanUtils.copyProperties(node, newItem);
            newItem.setId(newId);
            newItem.setOslParentId(parentId);
            targetList.add(newItem);

            if (ValidateUtil.isNotEmpty(node.getChildNodes())) {
                for (OutlineSystemLibResponse child : node.getChildNodes()) {
                    stack.push(new OutlineLibUtils.NodePair<>(child, newId));
                }
            }
        }
    }

    /**
     * 刷新最后更新时间
     *
     * @param osmId
     * @param user
     */
    @Override
    public void refreshLastUpdateTime(String osmId, LogUserInfoVo user) {
        this.update(Wrappers.<OutlineSystemManage>lambdaUpdate()
                .eq(OutlineSystemManage::getId, osmId)
                .set(OutlineSystemManage::getUpdateTime, new Date())
                .set(OutlineSystemManage::getUpdateUid, user.getId())
                .set(OutlineSystemManage::getUpdateUname, user.getName())
        );
    }

    /**
     * 导入项目管理大纲Word
     * 内部调用
     *
     * @param map
     * @param token
     * @return
     */
    @Override
    public BaseResponse importOutlineProjectWord(LinkedHashMap<String, String> map, String osmId, String token) {
        if (ValidateUtil.isEmpty(map) || ValidateUtil.isNull(osmId) || "undefined".equalsIgnoreCase(osmId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineSystemManageResponse response = selectOutlineSystemManageById(osmId);
        if (ValidateUtil.isNull(response)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isNotEmpty(response.getOutlineSystemLibList())) {
            return BaseResponse.fail("已存在内容，无法导入");
        }
        OutlineSystemManageRequest request = new OutlineSystemManageRequest();
        BeanUtils.copyProperties(response, request);
        // 构建目录结构
        List<OutlineSystemLibRequest> outlineSystemLibList = this.buildOutlineFromMap(map, token);
        if (ValidateUtil.isEmpty(outlineSystemLibList)) {
            return BaseResponse.fail("导入内容为空或格式不正确");
        }
        request.setOutlineSystemLibList(outlineSystemLibList);
        return this.createOrUpdateOutlineSystemManage(request, token);
    }

    /**
     * 根据HTML内容构建体系目录结构
     *
     * @param map HTML内容映射，键为h1~h6标签和标题，值为对应内容
     * @return
     */
    private List<OutlineSystemLibRequest> buildOutlineFromMap(LinkedHashMap<String, String> map, String token) {
        if (ValidateUtil.isEmpty(map)) {
            return Collections.emptyList();
        }
        Date now = new Date();
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        List<OutlineSystemLibRequest> outlineSystemLibList = new LinkedList<>();

        // 每个层级最近的父id
        Map<Integer, String> parentMap = new HashMap<>();
        // 每个层级的排序计数器
        Map<Integer, Integer> levelSortCounter = new HashMap<>();

        for (String key : map.keySet()) {
            Matcher matcher = Pattern.compile("h(\\d+)").matcher(key);
            if (!matcher.find()) {
                continue;
            }

            int level = Integer.parseInt(matcher.group(1));
            String title = this.titleHandle(key.substring(3));

            OutlineSystemLibRequest libRequest = new OutlineSystemLibRequest();
            libRequest.setId(GenerateUUID.getUUID());

            // 设置父id
            String parentId = "0";
            for (int l = level - 1; l >= 1; l--) {
                if (parentMap.containsKey(l)) {
                    parentId = parentMap.get(l);
                    break;
                }
            }
            libRequest.setOslParentId(parentId);

            // 设置排序值
            int sort = levelSortCounter.getOrDefault(level, 0) + 1;
            libRequest.setOslSort(sort);
            levelSortCounter.put(level, sort);
            libRequest.setOslName(title);
            libRequest.setOslStatus("1");
            libRequest.setOslContent(ValidateUtil.isEmpty(map.get(key)) ? null : map.get(key).trim());
            libRequest.setCreateTime(now);
            libRequest.setCreateUid(user.getId());
            libRequest.setCreateUname(user.getName());
            outlineSystemLibList.add(libRequest);

            // 更新当前层级的父id
            parentMap.put(level, libRequest.getId());
            // 清理比当前层级更深的parentMap和sort
            for (int deeper = level + 1; deeper <= 6; deeper++) {
                parentMap.remove(deeper);
                levelSortCounter.remove(deeper);
            }
        }
        return outlineSystemLibList;
    }

    /**
     * 标题处理
     *
     * @param title
     * @return
     */
    private String titleHandle(String title) {
        if (title == null) {
            return null;
        }
        int firstSpace = title.indexOf(" ");
        if (firstSpace != -1 && firstSpace < title.length() - 1) {
            return title.substring(firstSpace + 1).trim();
        }
        return title.trim();
    }
}
