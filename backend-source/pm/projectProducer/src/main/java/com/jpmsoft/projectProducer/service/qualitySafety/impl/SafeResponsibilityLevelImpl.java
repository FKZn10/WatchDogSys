package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityLevelDTO;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.qualitySafety.SafeResponsibilityLevelMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityLevel;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityChangeLogService;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityLevelService;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 安全责任层级服务实现。
 */
@Service
public class SafeResponsibilityLevelImpl extends ServiceImpl<SafeResponsibilityLevelMapper, SafeResponsibilityLevel> implements ISafeResponsibilityLevelService {
    private static final String ROOT_ID = "0";
    @Resource
    private ISafeResponsibilityPostService postService;
    @Resource
    private ISafeResponsibilityChangeLogService changeLogService;

    /**
     * 新增或修改责任层级；调整上级后同步刷新全部后代路径和深度。
     *
     * @param request 层级数据
     * @param uid     操作人ID
     * @param uname   操作人姓名
     * @return 层级ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSafeResponsibilityLevel(SafeResponsibilityLevelDTO request,
                                                              String uid, String uname) {
        if (request == null || StrUtil.isBlank(request.getSystemId()) || StrUtil.isBlank(request.getLevelName())) {
            throw new JPMException("体系ID和层级名称不能为空");
        }
        String parentId = StrUtil.isBlank(request.getParentId()) ? ROOT_ID : request.getParentId();
        request.setParentId(parentId);
        SafeResponsibilityLevel parent = null;
        if (!ROOT_ID.equals(parentId)) {
            parent = requireLevel(parentId);
            if (!Objects.equals(parent.getSystemId(), request.getSystemId())) {
                throw new JPMException("上级层级不属于当前责任体系");
            }
            ensureNoCycle(request.getId(), parent, request.getSystemId());
        }
        ensureLevelNameUnique(request);

        Date now = new Date();
        SafeResponsibilityLevel before = null;
        String operationType = "ADD";
        if (StrUtil.isBlank(request.getId())) {
            request.setId(GenerateUUID.getUUID());
            request.setSortNo(request.getSortNo() == null
                    ? nextSortNo(request.getSystemId(), parentId) : request.getSortNo());
            request.setStatus(request.getStatus() == null ? Boolean.TRUE : request.getStatus());
            request.setCreateUid(uid);
            request.setCreateUname(uname);
            request.setCreateTime(now);
        } else {
            before = requireLevel(request.getId());
            if (!Objects.equals(before.getSystemId(), request.getSystemId())) {
                throw new JPMException("不能将责任层级移动到其他责任体系");
            }
            operationType = "UPDATE";
            request.setCreateUid(before.getCreateUid());
            request.setCreateUname(before.getCreateUname());
            request.setCreateTime(before.getCreateTime());
        }
        int parentDepth = parent == null || parent.getLevelDepth() == null ? 0 : parent.getLevelDepth();
        String parentPath = parent == null ? null
                : (StrUtil.isBlank(parent.getLevelPath()) ? parent.getId() : parent.getLevelPath());
        request.setLevelDepth(parentDepth + 1);
        request.setLevelPath(parent == null ? request.getId() : parentPath + "/" + request.getId());
        request.setUpdateUid(uid);
        request.setUpdateUname(uname);
        request.setUpdateTime(now);
        SafeResponsibilityLevel entity = toEntity(request);
        if (before == null) {
            save(entity);
        } else {
            updateById(entity);
            refreshDescendantPaths(entity, uid, uname, now);
        }
        changeLogService.saveChangeLog(request.getSystemId(), "LEVEL", request.getId(), request.getLevelName(),
                operationType, before, request, uid, uname, "维护安全责任层级", null);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 按层级请求字段筛选树；命中节点会补齐全部祖先直至根节点。
     *
     * @param request 树筛选条件
     * @return 层级树
     */
    @Override
    public BaseResponse getSafeResponsibilityLevelTree(SafeResponsibilityLevelDTO request) {
        if (request == null || StrUtil.isBlank(request.getSystemId())) {
            throw new JPMException("体系ID不能为空");
        }
        return BaseResponse.ok(buildTree(request));
    }

    /**
     * 删除没有下级和岗位的责任层级。
     *
     * @param request 包含层级ID的请求
     * @param uid     操作人ID
     * @param uname   操作人姓名
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSafeResponsibilityLevel(SafeResponsibilityLevelDTO request, String uid, String uname) {
        if (request == null || StrUtil.isBlank(request.getId())) {
            throw new JPMException("责任层级ID不能为空");
        }
        SafeResponsibilityLevel entity = requireLevel(request.getId());
        Long childCount = count(new LambdaQueryWrapper<SafeResponsibilityLevel>()
                .eq(SafeResponsibilityLevel::getParentId, entity.getId()));
        if (childCount > 0) {
            throw new JPMException("当前层级存在下级，不能删除");
        }
        List<SafeResponsibilityPostDTO> posts = postService.getPostMapBySystemId(entity.getSystemId(), null)
                .getOrDefault(entity.getId(), Collections.emptyList());
        if (!posts.isEmpty()) {
            throw new JPMException("当前层级存在岗位，不能删除");
        }
        removeById(entity.getId());
        changeLogService.saveChangeLog(entity.getSystemId(), "LEVEL", entity.getId(), entity.getLevelName(), "DELETE",
                entity, null, uid, uname, "删除安全责任层级", null);
        return BaseResponse.ok();
    }

    /**
     * 查询体系层级树；关键字命中层级、岗位或职责时保留完整祖先链。
     *
     * @param systemId 体系ID
     * @param keyword  树筛选关键字
     * @return 层级树
     */
    @Override
    public List<SafeResponsibilityLevelDTO> getLevelTreeBySystemId(String systemId, String keyword) {
        SafeResponsibilityLevelDTO condition = new SafeResponsibilityLevelDTO();
        condition.setSystemId(systemId);
        condition.setKeyword(keyword);
        return buildTree(condition);
    }

    /**
     * 删除体系下全部岗位、职责和层级。
     *
     * @param systemId 体系ID
     */
    @Override
    public void deleteBySystemId(String systemId) {
        postService.deleteBySystemId(systemId);
        remove(new LambdaQueryWrapper<SafeResponsibilityLevel>()
                .eq(SafeResponsibilityLevel::getSystemId, systemId));
    }

    /**
     * 复制来源体系全部层级，再委托岗位服务复制岗位和职责。
     *
     * @param sourceSystemId 来源体系ID
     * @param targetSystemId 目标体系ID
     * @param uid            操作人ID
     * @param uname          操作人姓名
     */
    @Override
    public void copyBySystem(String sourceSystemId, String targetSystemId, String uid, String uname) {
        List<SafeResponsibilityLevel> sourceLevels = selectLevels(sourceSystemId);
        Map<String, String> idMap = new HashMap<>();
        sourceLevels.forEach(level -> idMap.put(level.getId(), GenerateUUID.getUUID()));
        Date now = new Date();
        for (SafeResponsibilityLevel source : sourceLevels) {
            SafeResponsibilityLevel target = new SafeResponsibilityLevel();
            BeanUtils.copyProperties(source, target);
            target.setId(idMap.get(source.getId()));
            target.setSystemId(targetSystemId);
            target.setParentId(ROOT_ID.equals(source.getParentId()) || StrUtil.isBlank(source.getParentId())
                    ? ROOT_ID : idMap.get(source.getParentId()));
            target.setLevelPath(remapPath(source.getLevelPath(), idMap));
            target.setCreateUid(uid);
            target.setCreateUname(uname);
            target.setCreateTime(now);
            target.setUpdateUid(uid);
            target.setUpdateUname(uname);
            target.setUpdateTime(now);
            save(target);
        }
        postService.copyBySystem(sourceSystemId, targetSystemId, idMap, uid, uname);
    }

    /**
     * 执行层级筛选、祖先补齐和树结构组装。
     */
    private List<SafeResponsibilityLevelDTO> buildTree(SafeResponsibilityLevelDTO condition) {
        List<SafeResponsibilityLevel> levels = selectLevels(condition.getSystemId());
        Map<String, SafeResponsibilityLevel> entityMap = levels.stream()
                .collect(Collectors.toMap(SafeResponsibilityLevel::getId, item -> item, (left, right) -> left,
                        LinkedHashMap::new));
        Map<String, List<SafeResponsibilityPostDTO>> postMap =
                postService.getPostMapBySystemId(condition.getSystemId(), condition.getKeyword());

        boolean filtered = hasFilter(condition);
        Set<String> retainedIds = new HashSet<>();
        for (SafeResponsibilityLevel level : levels) {
            if (!filtered || matchesCondition(level, condition, postMap.containsKey(level.getId()))) {
                addAncestors(level, entityMap, retainedIds);
            }
        }

        Map<String, SafeResponsibilityLevelDTO> dtoMap = new LinkedHashMap<>();
        for (SafeResponsibilityLevel level : levels) {
            if (!retainedIds.contains(level.getId())) {
                continue;
            }
            SafeResponsibilityLevelDTO dto = toDTO(level);
            dto.setChildNodes(new ArrayList<>());
            dto.setPostList(postMap.getOrDefault(level.getId(), Collections.emptyList()));
            dtoMap.put(dto.getId(), dto);
        }
        List<SafeResponsibilityLevelDTO> roots = new ArrayList<>();
        for (SafeResponsibilityLevelDTO dto : dtoMap.values()) {
            SafeResponsibilityLevelDTO parent = dtoMap.get(dto.getParentId());
            if (parent == null) {
                roots.add(dto);
            } else {
                parent.getChildNodes().add(dto);
            }
        }
        return roots;
    }

    /**
     * 判断层级查询是否包含筛选条件。
     */
    private boolean hasFilter(SafeResponsibilityLevelDTO condition) {
        return StrUtil.isNotBlank(condition.getId()) || StrUtil.isNotBlank(condition.getParentId())
                || StrUtil.isNotBlank(condition.getLevelName()) || StrUtil.isNotBlank(condition.getLevelDescription())
                || condition.getStatus() != null || StrUtil.isNotBlank(condition.getKeyword());
    }

    /**
     * 判断层级是否符合显式字段与关键字筛选条件。
     */
    private boolean matchesCondition(SafeResponsibilityLevel level, SafeResponsibilityLevelDTO condition,
                                     boolean hasMatchedPost) {
        boolean fieldMatched = (StrUtil.isBlank(condition.getId()) || Objects.equals(level.getId(), condition.getId()))
                && (StrUtil.isBlank(condition.getParentId()) || Objects.equals(level.getParentId(), condition.getParentId()))
                && (StrUtil.isBlank(condition.getLevelName())
                || StrUtil.containsIgnoreCase(level.getLevelName(), condition.getLevelName()))
                && (StrUtil.isBlank(condition.getLevelDescription())
                || StrUtil.containsIgnoreCase(level.getLevelDescription(), condition.getLevelDescription()))
                && (condition.getStatus() == null || Objects.equals(level.getStatus(), condition.getStatus()));
        if (!fieldMatched) {
            return false;
        }
        return StrUtil.isBlank(condition.getKeyword()) || hasMatchedPost
                || StrUtil.containsIgnoreCase(level.getLevelName(), condition.getKeyword())
                || StrUtil.containsIgnoreCase(level.getLevelDescription(), condition.getKeyword());
    }

    /**
     * 将命中层级及其祖先逐级加入保留集合。
     */
    private void addAncestors(SafeResponsibilityLevel level, Map<String, SafeResponsibilityLevel> entityMap,
                              Set<String> retainedIds) {
        SafeResponsibilityLevel current = level;
        Set<String> visited = new HashSet<>();
        while (current != null && visited.add(current.getId())) {
            retainedIds.add(current.getId());
            if (ROOT_ID.equals(current.getParentId()) || StrUtil.isBlank(current.getParentId())) {
                break;
            }
            current = entityMap.get(current.getParentId());
        }
    }

    /**
     * 刷新移动层级下全部后代的路径和深度。
     */
    private void refreshDescendantPaths(SafeResponsibilityLevel parent, String uid, String uname, Date now) {
        Map<String, List<SafeResponsibilityLevel>> childMap = selectLevels(parent.getSystemId()).stream()
                .collect(Collectors.groupingBy(item -> StrUtil.isBlank(item.getParentId()) ? ROOT_ID : item.getParentId()));
        List<SafeResponsibilityLevel> queue = new ArrayList<>();
        queue.add(parent);
        for (int index = 0; index < queue.size(); index++) {
            SafeResponsibilityLevel current = queue.get(index);
            for (SafeResponsibilityLevel child : childMap.getOrDefault(current.getId(), Collections.emptyList())) {
                int currentDepth = current.getLevelDepth() == null ? 1 : current.getLevelDepth();
                String currentPath = StrUtil.isBlank(current.getLevelPath()) ? current.getId() : current.getLevelPath();
                child.setLevelDepth(currentDepth + 1);
                child.setLevelPath(currentPath + "/" + child.getId());
                child.setUpdateUid(uid);
                child.setUpdateUname(uname);
                child.setUpdateTime(now);
                updateById(child);
                queue.add(child);
            }
        }
    }

    /**
     * 校验新的父级不会形成循环。
     */
    private void ensureNoCycle(String levelId, SafeResponsibilityLevel parent, String systemId) {
        if (StrUtil.isBlank(levelId)) {
            return;
        }
        Set<String> visited = new HashSet<>();
        SafeResponsibilityLevel current = parent;
        while (current != null && visited.add(current.getId())) {
            if (Objects.equals(levelId, current.getId())) {
                throw new JPMException("不能将当前层级或其下级设为上级层级");
            }
            if (ROOT_ID.equals(current.getParentId()) || StrUtil.isBlank(current.getParentId())) {
                break;
            }
            current = getOne(new LambdaQueryWrapper<SafeResponsibilityLevel>()
                    .eq(SafeResponsibilityLevel::getId, current.getParentId())
                    .eq(SafeResponsibilityLevel::getSystemId, systemId));
        }
    }

    /**
     * 校验同一上级下层级名称唯一。
     */
    private void ensureLevelNameUnique(SafeResponsibilityLevelDTO level) {
        Long count = count(new LambdaQueryWrapper<SafeResponsibilityLevel>()
                .eq(SafeResponsibilityLevel::getSystemId, level.getSystemId())
                .eq(SafeResponsibilityLevel::getParentId, level.getParentId())
                .eq(SafeResponsibilityLevel::getLevelName, level.getLevelName())
                .ne(StrUtil.isNotBlank(level.getId()), SafeResponsibilityLevel::getId, level.getId()));
        if (count > 0) {
            throw new JPMException("同一上级下层级名称不能重复");
        }
    }

    /**
     * 查询层级，不存在时抛出业务异常。
     */
    private SafeResponsibilityLevel requireLevel(String levelId) {
        SafeResponsibilityLevel level = getById(levelId);
        if (level == null) {
            throw new JPMException("安全责任层级不存在");
        }
        return level;
    }

    /**
     * 查询体系全部层级并按深度和排序号排序。
     */
    private List<SafeResponsibilityLevel> selectLevels(String systemId) {
        return list(new LambdaQueryWrapper<SafeResponsibilityLevel>()
                .eq(SafeResponsibilityLevel::getSystemId, systemId)
                .orderByAsc(SafeResponsibilityLevel::getLevelDepth)
                .orderByAsc(SafeResponsibilityLevel::getSortNo)
                .orderByAsc(SafeResponsibilityLevel::getCreateTime));
    }

    /**
     * 计算同级下一个排序号。
     */
    private int nextSortNo(String systemId, String parentId) {
        return list(new LambdaQueryWrapper<SafeResponsibilityLevel>()
                .eq(SafeResponsibilityLevel::getSystemId, systemId)
                .eq(SafeResponsibilityLevel::getParentId, parentId)).stream()
                .map(SafeResponsibilityLevel::getSortNo)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(0) + 1;
    }

    /**
     * 根据层级ID映射转换层级路径。
     */
    private String remapPath(String sourcePath, Map<String, String> idMap) {
        if (StrUtil.isBlank(sourcePath)) {
            return sourcePath;
        }
        return java.util.Arrays.stream(sourcePath.split("/"))
                .map(item -> idMap.getOrDefault(item, item))
                .collect(Collectors.joining("/"));
    }

    /**
     * 将层级实体转换为DTO。
     */
    private SafeResponsibilityLevelDTO toDTO(SafeResponsibilityLevel entity) {
        SafeResponsibilityLevelDTO dto = new SafeResponsibilityLevelDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * 将层级DTO转换为实体。
     */
    private SafeResponsibilityLevel toEntity(SafeResponsibilityLevelDTO dto) {
        SafeResponsibilityLevel entity = new SafeResponsibilityLevel();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
