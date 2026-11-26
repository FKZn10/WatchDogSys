package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.SysCommonExternalMapper;
import com.jpmsoft.userProducer.pojo.dto.SysCommonExternalDTO;
import com.jpmsoft.userProducer.pojo.entity.SysCommonExternal;
import com.jpmsoft.userProducer.pojo.entity.SysCommonGlobal;
import com.jpmsoft.userProducer.pojo.entity.SysCommonUser;
import com.jpmsoft.userProducer.pojo.vo.SysCommonExternalTreeVO;
import com.jpmsoft.userProducer.service.ISysCommonExternalService;
import com.jpmsoft.userProducer.service.ISysCommonGlobalService;
import com.jpmsoft.userProducer.service.ISysCommonUserService;
import com.jpmsoft.userProducer.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 常用功能外部系统入口
 */
@Service
public class SysCommonExternalImpl extends ServiceImpl<SysCommonExternalMapper, SysCommonExternal> implements ISysCommonExternalService {
    @Resource
    private ISysCommonGlobalService globalService;
    @Resource
    private ISysCommonUserService userService;

    /**
     * 按父级关系构造外部系统入口树，默认只返回启用项。
     *
     * @param request 可选的节点ID、父级ID、名称、URL和状态筛选条件
     * @return 按排序值和主键升序排列的层级树
     */
    @Override
    public BaseResponse getSysCommonExternalTree(SysCommonExternalDTO request) {
        short status = request != null && request.getStatus() != null ? request.getStatus() : (short) 1;
        LambdaQueryWrapper<SysCommonExternal> query = new LambdaQueryWrapper<>();
        query.orderByAsc(SysCommonExternal::getSortNo, SysCommonExternal::getId);
        List<SysCommonExternal> allRows = list(query);
        List<SysCommonExternal> rows = allRows.stream()
                .filter(item -> item.getStatus() != null && item.getStatus() == status)
                .collect(Collectors.toList());
        Map<String, SysCommonExternal> allById = allRows.stream()
                .collect(Collectors.toMap(SysCommonExternal::getId, item -> item));
        Map<String, SysCommonExternal> byId = rows.stream()
                .collect(Collectors.toMap(SysCommonExternal::getId, item -> item));
        Map<String, List<SysCommonExternal>> byParent = new HashMap<>();
        List<SysCommonExternal> roots = new ArrayList<>();
        for (SysCommonExternal item : rows) {
            String parentId = item.getParentId();
            if (!StringUtils.hasText(parentId) || "0".equals(parentId)
                    || !byId.containsKey(parentId) && (status != 1 || !allById.containsKey(parentId))) {
                roots.add(item);
            } else {
                byParent.computeIfAbsent(parentId, ignored -> new ArrayList<>()).add(item);
            }
        }
        if (request != null && StringUtils.hasText(request.getId())) {
            SysCommonExternal selected = byId.get(request.getId().trim());
            roots = selected == null ? new ArrayList<>() : new ArrayList<>(java.util.Collections.singletonList(selected));
        } else if (request != null && StringUtils.hasText(request.getParentId())
                && !"0".equals(request.getParentId().trim())) {
            roots = new ArrayList<>(byParent.getOrDefault(request.getParentId().trim(), java.util.Collections.emptyList()));
        }
        String name = request == null || !StringUtils.hasText(request.getName()) ? null : request.getName().trim();
        String url = request == null || !StringUtils.hasText(request.getUrl()) ? null : request.getUrl().trim();
        List<SysCommonExternalTreeVO> tree = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (SysCommonExternal root : roots) {
            SysCommonExternalTreeVO node = buildNode(root, byParent, new HashSet<>(), visited);
            if (retainMatches(node, name, url)) {
                tree.add(node);
            }
        }
        // 异常的循环父级关系无法从根节点到达，仍以一个节点为根展示，避免整组数据消失。
        if ((request == null || !StringUtils.hasText(request.getId()) && !StringUtils.hasText(request.getParentId()))) {
            for (SysCommonExternal item : rows) {
                if (visited.contains(item.getId())) {
                    continue;
                }
                if (status == 1 && hasInactiveAncestor(item, allById)) {
                    continue;
                }
                SysCommonExternalTreeVO node = buildNode(item, byParent, new HashSet<>(), visited);
                if (retainMatches(node, name, url)) {
                    tree.add(node);
                }
            }
        }
        return BaseResponse.ok(tree);
    }

    /**
     * 判断节点是否处于已停用的上级之下。
     *
     * @param item    当前入口
     * @param allById 全部入口索引
     * @return 是否存在停用的上级
     */
    private boolean hasInactiveAncestor(SysCommonExternal item, Map<String, SysCommonExternal> allById) {
        Set<String> seen = new HashSet<>(Collections.singleton(item.getId()));
        String parentId = item.getParentId();
        while (StringUtils.hasText(parentId) && !"0".equals(parentId) && seen.add(parentId)) {
            SysCommonExternal parent = allById.get(parentId);
            if (parent == null) {
                return false;
            }
            if (parent.getStatus() == null || parent.getStatus() != 1) {
                return true;
            }
            parentId = parent.getParentId();
        }
        return false;
    }

    /**
     * 创建或更新外部系统入口，校验父级关系、保持同级排序并同步沿用旧图标的个人记录。
     *
     * @param request 入口名称、父级、URL及可选展示设置
     * @return 保存后的入口
     */
    @Override
    @Transactional
    public BaseResponse createOrUpdateSysCommonExternal(SysCommonExternalDTO request) {
        if (request == null || !StringUtils.hasText(request.getName())) {
            return BaseResponse.fail("请输入外部系统入口名称");
        }
        String name = request.getName().trim();
        if (name.length() > 100) {
            return BaseResponse.fail("名称不能超过100个字符");
        }
        String url = request.getUrl() == null ? "" : request.getUrl().trim();
        if (!url.isEmpty() && !validUrl(url)) {
            return BaseResponse.fail("跳转地址仅支持有效的http或https URL");
        }
        if (request.getIcon() != null && request.getIcon().length() > 500) {
            return BaseResponse.fail("图标标识不能超过500个字符");
        }
        if (request.getStatus() != null && request.getStatus() != 0 && request.getStatus() != 1) {
            return BaseResponse.fail("状态只能为0或1");
        }
        String id = StringUtils.hasText(request.getId()) ? request.getId().trim() : null;
        String parentId = StringUtils.hasText(request.getParentId()) && !"0".equals(request.getParentId().trim())
                ? request.getParentId().trim() : "0";
        SysCommonExternal entity = id == null ? null : getById(id);
        if (id != null && entity == null) {
            return BaseResponse.fail("外部系统入口不存在");
        }
        String oldIcon = entity == null ? null : entity.getIcon();
        if (id != null && id.equals(parentId)) {
            return BaseResponse.fail("不能将入口设置为自己的上级");
        }
        if (!"0".equals(parentId)) {
            SysCommonExternal parent = getById(parentId);
            if (parent == null) {
                return BaseResponse.fail("上级入口不存在");
            }
            short nextStatus = request.getStatus() != null ? request.getStatus()
                    : entity != null && entity.getStatus() != null ? entity.getStatus() : (short) 1;
            Set<String> visited = new HashSet<>();
            while (parent != null && visited.add(parent.getId())) {
                if (parent.getId().equals(id)) {
                    return BaseResponse.fail("不能将入口移动到自己的下级");
                }
                if (nextStatus == 1 && (parent.getStatus() == null || parent.getStatus() != 1)) {
                    return BaseResponse.fail("请先启用上级入口");
                }
                String ancestorId = parent.getParentId();
                parent = StringUtils.hasText(ancestorId) && !"0".equals(ancestorId)
                        ? getById(ancestorId) : null;
            }
            if (parent != null) {
                return BaseResponse.fail("上级入口关系存在循环");
            }
        }
        Date now = new Date();
        if (entity == null) {
            entity = new SysCommonExternal();
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(now);
            entity.setCreateUid(UserUtils.getUserInfo().getId());
            entity.setStatus((short) 1);
        }
        boolean parentChanged = !Objects.equals(
                StringUtils.hasText(entity.getParentId()) ? entity.getParentId() : "0", parentId);
        entity.setParentId(parentId);
        entity.setName(name);
        entity.setUrl(url);
        entity.setIcon(request.getIcon());
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        if (request.getSortNo() != null) {
            entity.setSortNo(request.getSortNo());
        } else if (entity.getSortNo() == null || parentChanged) {
            LambdaQueryWrapper<SysCommonExternal> siblingQuery = new LambdaQueryWrapper<>();
            if ("0".equals(parentId)) {
                siblingQuery.and(wrapper -> wrapper.eq(SysCommonExternal::getParentId, "0").or().isNull(SysCommonExternal::getParentId));
            } else {
                siblingQuery.eq(SysCommonExternal::getParentId, parentId);
            }
            long next = list(siblingQuery).stream()
                    .map(SysCommonExternal::getSortNo).filter(Objects::nonNull)
                    .max(Long::compareTo).orElse(0L) + 1;
            entity.setSortNo(next);
        }
        entity.setUpdateUid(UserUtils.getUserInfo().getId());
        entity.setUpdateTime(now);
        if (!saveOrUpdate(entity)) {
            return BaseResponse.fail("保存外部系统入口失败");
        }
        if (id != null && !Objects.equals(oldIcon, entity.getIcon())) {
            LambdaUpdateWrapper<SysCommonUser> update = new LambdaUpdateWrapper<SysCommonUser>()
                    .set(SysCommonUser::getIcon, entity.getIcon())
                    .eq(SysCommonUser::getTargetType, 2)
                    .eq(SysCommonUser::getTargetId, entity.getId());
            if (oldIcon == null) {
                update.isNull(SysCommonUser::getIcon);
            } else {
                update.eq(SysCommonUser::getIcon, oldIcon);
            }
            userService.update(update);
        }
        return BaseResponse.ok(entity);
    }

    /**
     * 按主键读取外部系统入口详情。
     *
     * @param id 入口ID
     * @return 入口详情或不存在提示
     */
    @Override
    public BaseResponse getSysCommonExternalById(String id) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入外部系统入口ID");
        }
        SysCommonExternal entity = getById(id.trim());
        return entity == null ? BaseResponse.fail("外部系统入口不存在") : BaseResponse.ok(entity);
    }

    /**
     * 删除没有子节点且没有全局或个人常用功能引用的入口。
     *
     * @param id 入口ID
     * @return 删除结果
     */
    @Override
    @Transactional
    public BaseResponse deleteSysCommonExternalById(String id) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入外部系统入口ID");
        }
        String targetId = id.trim();
        if (getById(targetId) == null) {
            return BaseResponse.fail("外部系统入口不存在");
        }
        if (count(new LambdaQueryWrapper<SysCommonExternal>().eq(SysCommonExternal::getParentId, targetId)) > 0) {
            return BaseResponse.fail("请先删除下级入口");
        }
        if (globalService.count(new LambdaQueryWrapper<SysCommonGlobal>()
                .eq(SysCommonGlobal::getTargetType, 2).eq(SysCommonGlobal::getTargetId, targetId)) > 0
                || userService.count(new LambdaQueryWrapper<SysCommonUser>()
                .eq(SysCommonUser::getTargetType, 2).eq(SysCommonUser::getTargetId, targetId)) > 0) {
            return BaseResponse.fail("该入口已被常用功能引用，请先移除相关配置");
        }
        return removeById(targetId) ? BaseResponse.ok() : BaseResponse.fail("删除外部系统入口失败");
    }

    /**
     * 切换入口启用状态；启用下级时要求其所有上级已启用。
     *
     * @param id 入口ID
     * @return 更新后的入口
     */
    @Override
    @Transactional
    public BaseResponse switchSysCommonExternalById(String id) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入外部系统入口ID");
        }
        SysCommonExternal entity = getById(id.trim());
        if (entity == null) {
            return BaseResponse.fail("外部系统入口不存在");
        }
        if (entity.getStatus() == null || entity.getStatus() != 0 && entity.getStatus() != 1) {
            return BaseResponse.fail("外部系统入口状态无效");
        }
        if (entity.getStatus() == 0) {
            String parentId = entity.getParentId();
            Set<String> visited = new HashSet<>(Collections.singleton(entity.getId()));
            while (StringUtils.hasText(parentId) && !"0".equals(parentId)) {
                if (!visited.add(parentId)) {
                    return BaseResponse.fail("上级入口关系存在循环");
                }
                SysCommonExternal parent = getById(parentId);
                if (parent == null || parent.getStatus() == null || parent.getStatus() != 1) {
                    return BaseResponse.fail("请先启用上级入口");
                }
                parentId = parent.getParentId();
            }
        }
        entity.setStatus(entity.getStatus() == 1 ? (short) 0 : (short) 1);
        entity.setUpdateUid(UserUtils.getUserInfo().getId());
        entity.setUpdateTime(new Date());
        return updateById(entity) ? BaseResponse.ok(entity) : BaseResponse.fail("切换外部系统入口状态失败");
    }

    /**
     * 在同一父级下交换相邻入口，并重新写入连续排序值。
     *
     * @param id     当前入口ID
     * @param action up表示上移，down表示下移
     * @return 移动结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse upOrDownSysCommonExternalSort(String id, String action) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入外部系统入口ID");
        }
        if (!"up".equals(action) && !"down".equals(action)) {
            return BaseResponse.fail("action只能是up或down");
        }
        SysCommonExternal current = getById(id.trim());
        if (current == null) {
            return BaseResponse.fail("外部系统入口不存在");
        }
        LambdaQueryWrapper<SysCommonExternal> query = new LambdaQueryWrapper<>();
        String parentId = current.getParentId();
        if (!StringUtils.hasText(parentId) || "0".equals(parentId)) {
            query.and(wrapper -> wrapper.isNull(SysCommonExternal::getParentId).or().eq(SysCommonExternal::getParentId, "0"));
        } else {
            query.eq(SysCommonExternal::getParentId, parentId);
        }
        query.orderByAsc(SysCommonExternal::getSortNo, SysCommonExternal::getId);
        List<SysCommonExternal> siblings = list(query);
        int index = -1;
        for (int i = 0; i < siblings.size(); i++) {
            if (current.getId().equals(siblings.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            return BaseResponse.fail("当前入口不在同级排序列表中");
        }
        int target = "up".equals(action) ? index - 1 : index + 1;
        if (target < 0 || target >= siblings.size()) {
            return BaseResponse.fail("已经是当前层级的第一项或最后一项");
        }
        Collections.swap(siblings, index, target);
        Date now = new Date();
        String userId = UserUtils.getUserInfo().getId();
        for (int i = 0; i < siblings.size(); i++) {
            SysCommonExternal item = siblings.get(i);
            long sortNo = i + 1L;
            if (Objects.equals(item.getSortNo(), sortNo)) {
                continue;
            }
            item.setSortNo(sortNo);
            item.setUpdateUid(userId);
            item.setUpdateTime(now);
            if (!updateById(item)) {
                throw new IllegalStateException("更新外部系统入口排序失败：" + item.getId());
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 检查外部跳转地址是否为有效的HTTP或HTTPS地址。
     *
     * @param url 待检查地址
     * @return 是否可用于外部跳转
     */
    private boolean validUrl(String url) {
        try {
            URI uri = new URI(url);
            return ("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme()))
                    && StringUtils.hasText(uri.getHost());
        } catch (URISyntaxException ex) {
            return false;
        }
    }

    /**
     * 将一条外部入口及其下级转为树节点，阻断错误数据中的父级环。
     *
     * @param item     当前入口
     * @param byParent 按父级归组的入口
     * @param path     当前递归路径
     * @param visited  已访问的入口ID
     * @return 当前入口树节点
     */
    private SysCommonExternalTreeVO buildNode(SysCommonExternal item,
                                              Map<String, List<SysCommonExternal>> byParent,
                                              Set<String> path, Set<String> visited) {
        path.add(item.getId());
        visited.add(item.getId());
        SysCommonExternalTreeVO node = new SysCommonExternalTreeVO();
        node.setKey(item.getId());
        node.setTitle(item.getName());
        node.setValue(item.getId());
        node.setId(item.getId());
        node.setParentId(item.getParentId());
        node.setName(item.getName());
        node.setUrl(item.getUrl());
        node.setIcon(item.getIcon());
        node.setStatus(item.getStatus());
        node.setSortNo(item.getSortNo());
        List<SysCommonExternalTreeVO> children = new ArrayList<>();
        for (SysCommonExternal child : byParent.getOrDefault(item.getId(), java.util.Collections.emptyList())) {
            if (!path.contains(child.getId())) {
                children.add(buildNode(child, byParent, path, visited));
            }
        }
        node.setChildren(children);
        path.remove(item.getId());
        return node;
    }

    /**
     * 保留符合名称和URL条件的节点及其祖先。
     *
     * @param node 待筛选节点
     * @param name 名称关键字
     * @param url  URL关键字
     * @return 当前节点或下级是否命中
     */
    private boolean retainMatches(SysCommonExternalTreeVO node, String name, String url) {
        List<SysCommonExternalTreeVO> matchingChildren = node.getChildren().stream()
                .filter(child -> retainMatches(child, name, url)).collect(Collectors.toList());
        node.setChildren(matchingChildren);
        boolean selfMatches = (name == null || node.getName() != null && node.getName().contains(name))
                && (url == null || node.getUrl() != null && node.getUrl().contains(url));
        return selfMatches || !matchingChildren.isEmpty();
    }
}
