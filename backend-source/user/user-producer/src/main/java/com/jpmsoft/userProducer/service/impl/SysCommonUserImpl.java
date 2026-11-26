package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.SysCommonUserMapper;
import com.jpmsoft.userProducer.pojo.dto.SysCommonUserDTO;
import com.jpmsoft.userProducer.pojo.entity.SysCommonExternal;
import com.jpmsoft.userProducer.pojo.entity.SysCommonGlobal;
import com.jpmsoft.userProducer.pojo.entity.SysCommonUser;
import com.jpmsoft.userProducer.pojo.entity.SysPower;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.pojo.vo.SysCommonUserListVO;
import com.jpmsoft.userProducer.service.ISysCommonExternalService;
import com.jpmsoft.userProducer.service.ISysCommonGlobalService;
import com.jpmsoft.userProducer.service.ISysCommonUserService;
import com.jpmsoft.userProducer.service.ISysPowerService;
import com.jpmsoft.userProducer.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户常用功能及全局功能的个人设置。
 */
@Service
public class SysCommonUserImpl extends ServiceImpl<SysCommonUserMapper, SysCommonUser> implements ISysCommonUserService {
    @Resource
    private ISysCommonGlobalService globalService;
    @Resource
    private ISysCommonExternalService externalService;
    @Resource
    private ISysPowerService powerService;

    /**
     * 批量添加或修改当前用户的常用功能，沿用单条保存的权限和初始化规则。
     *
     * @param requests 待添加或修改的功能及个人设置
     * @return 保存后的个人设置列表
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse batchCreateOrUpdateSysCommonUser(List<SysCommonUserDTO> requests) {
        if (requests == null || requests.isEmpty()) {
            return BaseResponse.fail("请传入至少一项常用功能");
        }
        List<SysCommonUser> saved = new ArrayList<>(requests.size());
        Set<String> targets = new HashSet<>();
        String userId = actor().getId();
        for (int i = 0; i < requests.size(); i++) {
            SysCommonUserDTO request = requests.get(i);
            if (request == null) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return BaseResponse.fail("第" + (i + 1) + "项不能为空");
            }
            String targetKey;
            if (StringUtils.hasText(request.getId())) {
                SysCommonUser own = ownById(request.getId().trim(), userId);
                SysCommonGlobal global = own == null ? globalService.getById(request.getId().trim()) : null;
                if (own == null && global == null) {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return BaseResponse.fail("第" + (i + 1) + "项常用功能不存在");
                }
                targetKey = own != null ? key(own.getTargetType(), own.getTargetId(), own.getTCode())
                        : key(global.getTargetType(), global.getTargetId(), global.getTCode());
            } else if (request.getTargetType() != null
                    && (request.getTargetType() == 1 && StringUtils.hasText(request.getTCode())
                    || request.getTargetType() == 2 && StringUtils.hasText(request.getTargetId()))) {
                targetKey = key(request.getTargetType(), request.getTargetId(), request.getTCode());
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return BaseResponse.fail("第" + (i + 1) + "项缺少功能类型或对应的菜单编码、外部入口ID");
            }
            if (!targets.add(targetKey)) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return BaseResponse.fail("第" + (i + 1) + "项与本批次已有功能重复");
            }
            BaseResponse result = createOrUpdateSysCommonUser(request);
            if (!result.isSuccess()) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return BaseResponse.fail("第" + (i + 1) + "项保存失败：" + result.getMessage());
            }
            saved.add((SysCommonUser) result.getData());
        }
        return BaseResponse.ok(saved);
    }

    /**
     * 添加或修改当前用户的常用功能及显示名称，重复添加会恢复隐藏项。
     *
     * @param request 目标和个人设置
     * @return 保存后的个人记录
     */
    @Override
    @Transactional
    public BaseResponse createOrUpdateSysCommonUser(SysCommonUserDTO request) {
        if (request == null) {
            return BaseResponse.fail("请传入常用功能信息");
        }
        if (!flag(request.getIsPinned()) || !flag(request.getIsHidden())) {
            return BaseResponse.fail("置顶和隐藏状态只能为0或1");
        }
        if (request.getIcon() != null && request.getIcon().length() > 500) {
            return BaseResponse.fail("图标标识不能超过500个字符");
        }
        if (request.getTargetName() != null && request.getTargetName().trim().length() > 500) {
            return BaseResponse.fail("常用功能名称不能超过500个字符");
        }
        LogUserInfoVo actor = actor();
        String id = StringUtils.hasText(request.getId()) ? request.getId().trim() : null;
        SysCommonUser own = id == null ? null : ownById(id, actor.getId());
        SysCommonGlobal globalById = id != null && own == null ? globalService.getById(id) : null;
        if (id != null && own == null && (globalById == null || globalById.getStatus() != 1)) {
            return BaseResponse.fail("常用功能不存在");
        }
        Short type = own != null ? own.getTargetType()
                : globalById != null ? globalById.getTargetType() : request.getTargetType();
        String targetId = own != null ? own.getTargetId()
                : globalById != null ? globalById.getTargetId() : request.getTargetId();
        String tCode = own != null ? own.getTCode()
                : globalById != null ? globalById.getTCode() : request.getTCode();
        if (type == null || type != 1 && type != 2
                || type == 1 && !StringUtils.hasText(tCode)
                || type == 2 && !StringUtils.hasText(targetId)) {
            return BaseResponse.fail("请选择功能菜单或外部系统入口");
        }
        targetId = type == 2 ? targetId.trim() : null;
        tCode = type == 1 ? tCode.trim() : null;
        if (tCode != null && tCode.length() > 500) {
            return BaseResponse.fail("菜单编码不能超过500个字符");
        }
        if (request.getTargetType() != null && !request.getTargetType().equals(type)
                || type == 2 && StringUtils.hasText(request.getTargetId())
                && !request.getTargetId().trim().equals(targetId)
                || type == 1 && StringUtils.hasText(request.getTCode())
                && !request.getTCode().trim().equals(tCode)) {
            return BaseResponse.fail("不能修改已有常用功能的目标");
        }
        if (!available(type, targetId, tCode)) {
            return BaseResponse.fail("功能菜单不存在、已禁用或外部入口不可用");
        }
        SysCommonGlobal global = activeGlobal(type, targetId, tCode);
        String targetName = StringUtils.hasText(request.getTargetName()) ? request.getTargetName().trim()
                : own != null && StringUtils.hasText(own.getTargetName()) ? own.getTargetName()
                : global != null && StringUtils.hasText(global.getTargetName()) ? global.getTargetName()
                : resolveTargetName(type, targetId, tCode);
        if (!StringUtils.hasText(targetName) || targetName.length() > 500) {
            return BaseResponse.fail("常用功能名称不能为空且不能超过500个字符");
        }
        if (count(new LambdaQueryWrapper<SysCommonUser>().eq(SysCommonUser::getUserId, actor.getId())) == 0) {
            initializeFromGlobal(actor);
        }
        if (own == null) {
            own = ownByTarget(actor.getId(), type, targetId, tCode);
        }
        boolean created = own == null;
        if (created) {
            own = new SysCommonUser();
            own.setId(GenerateUUID.getUUID());
            own.setUserId(actor.getId());
            own.setTargetType(type);
            own.setTargetId(targetId);
            own.setTCode(tCode);
            own.setCreateTime(new Date());
        }
        short oldPinned = own.getIsPinned() != null ? own.getIsPinned()
                : global != null && global.getIsPinned() != null ? global.getIsPinned() : (short) 0;
        short pinned = request.getIsPinned() == null ? oldPinned : request.getIsPinned();
        own.setTargetName(targetName);
        own.setIsPinned(pinned);
        own.setIsHidden(request.getIsHidden() == null ? (short) 0 : request.getIsHidden());
        if (request.getIcon() != null) {
            own.setIcon(request.getIcon());
        }
        if (request.getSortNo() != null) {
            own.setSortNo(request.getSortNo());
        } else if (created || oldPinned != pinned || own.getSortNo() == null) {
            own.setSortNo(lastSort(actor.getId(), pinned) + 1);
        }
        own.setUpdateTime(new Date());
        if (!saveOrUpdate(own)) {
            throw new IllegalStateException("保存个人常用功能失败");
        }
        return BaseResponse.ok(own);
    }

    /**
     * 分页返回当前用户的常用功能，并合并后续发布的全局配置。
     *
     * @param request 分页参数及可选目标类型、菜单编码、外部入口ID和置顶筛选
     * @return 当前页与筛选后的总数
     */
    @Override
    public BaseResponse getSysCommonUserList(SysCommonUserDTO request) {
        LogUserInfoVo actor = actor();
        List<SysCommonUserListVO> rows = visible(actor);
        if (request != null) {
            if (request.getTargetType() != null) {
                rows.removeIf(item -> !request.getTargetType().equals(item.getTargetType()));
            }
            if (StringUtils.hasText(request.getTargetId())) {
                rows.removeIf(item -> !request.getTargetId().trim().equals(item.getTargetId()));
            }
            if (StringUtils.hasText(request.getTCode())) {
                rows.removeIf(item -> !request.getTCode().trim().equals(item.getTCode()));
            }
            if (request.getIsPinned() != null) {
                rows.removeIf(item -> !request.getIsPinned().equals(item.getIsPinned()));
            }
        }
        int total = rows.size();
        int page = request != null && request.getPage() > 0 ? request.getPage() : 1;
        int size = request != null && request.getSize() > 0 ? request.getSize() : 10;
        long offset = (long) (page - 1) * size;
        if (offset >= total) {
            return BaseResponse.ok(Collections.emptyList(), total);
        }
        int start = (int) offset;
        int end = (int) Math.min(offset + size, total);
        return BaseResponse.ok(rows.subList(start, end), total);
    }

    /**
     * 首次个人编辑时，把当前可见的全局功能写成该用户的初始列表。
     *
     * @param actor 当前用户
     */
    private void initializeFromGlobal(LogUserInfoVo actor) {
        List<SysCommonUserListVO> globals = visible(actor, true);
        long pinnedSort = 0;
        long normalSort = 0;
        Date now = new Date();
        for (SysCommonUserListVO global : globals) {
            SysCommonUser own = new SysCommonUser();
            own.setId(GenerateUUID.getUUID());
            own.setUserId(actor.getId());
            own.setTargetType(global.getTargetType());
            own.setTargetId(global.getTargetId());
            own.setTCode(global.getTCode());
            own.setTargetName(global.getTargetName());
            own.setIcon(global.getIcon());
            short pinned = global.getIsPinned() != null && global.getIsPinned() == 1 ? (short) 1 : (short) 0;
            own.setIsPinned(pinned);
            own.setIsHidden((short) 0);
            own.setSortNo(pinned == 1 ? ++pinnedSort : ++normalSort);
            own.setCreateTime(now);
            own.setUpdateTime(now);
            if (!save(own)) {
                throw new IllegalStateException("初始化个人常用功能失败：" + key(global.getTargetType(), global.getTargetId(), global.getTCode()));
            }
        }
    }

    /**
     * 按个人记录ID或未个性化的全局配置ID读取当前用户的设置。
     *
     * @param id 列表项ID
     * @return 设置详情
     */
    @Override
    public BaseResponse getSysCommonUserById(String id) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入常用功能ID");
        }
        LogUserInfoVo actor = actor();
        SysCommonUser own = ownById(id.trim(), actor.getId());
        if (own != null) {
            return BaseResponse.ok(own);
        }
        SysCommonGlobal global = globalService.getById(id.trim());
        if (global == null || global.getStatus() != 1
                || !available(global.getTargetType(), global.getTargetId(), global.getTCode())) {
            return BaseResponse.fail("常用功能不存在");
        }
        own = ownByTarget(actor.getId(), global.getTargetType(), global.getTargetId(), global.getTCode());
        if (own != null) {
            return BaseResponse.ok(own);
        }
        SysCommonUserDTO result = new SysCommonUserDTO();
        result.setId(global.getId());
        result.setTargetType(global.getTargetType());
        result.setTargetId(global.getTargetId());
        result.setTCode(global.getTCode());
        result.setTargetName(global.getTargetName());
        result.setIcon(global.getIcon());
        result.setIsPinned(global.getIsPinned());
        result.setIsHidden((short) 0);
        result.setSortNo(global.getSortNo());
        return BaseResponse.ok(result);
    }

    /**
     * 移除个人功能；全局功能保留一条当前用户的隐藏记录。
     *
     * @param id 列表项ID
     * @return 移除结果
     */
    @Override
    @Transactional
    public BaseResponse deleteSysCommonUserById(String id) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入常用功能ID");
        }
        LogUserInfoVo actor = actor();
        Selection selected = selection(id.trim(), actor.getId());
        if (selected == null) {
            return BaseResponse.fail("常用功能不存在");
        }
        SysCommonGlobal global = globalByTarget(selected.type, selected.targetId, selected.tCode);
        if (global == null) {
            return selected.own != null && removeById(selected.own.getId())
                    ? BaseResponse.ok() : BaseResponse.fail("删除个人常用功能失败");
        }
        SysCommonUser own = selected.own == null ? newOverride(actor.getId(), global) : selected.own;
        own.setIsHidden((short) 1);
        own.setUpdateTime(new Date());
        return saveOrUpdate(own) ? BaseResponse.ok() : BaseResponse.fail("移除个人常用功能失败");
    }

    /**
     * 切换当前用户对功能的显示状态。
     *
     * @param id 个人记录ID或全局配置ID
     * @return 更新后的个人记录
     */
    @Override
    @Transactional
    public BaseResponse switchSysCommonUserById(String id) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入常用功能ID");
        }
        LogUserInfoVo actor = actor();
        Selection selected = selection(id.trim(), actor.getId());
        if (selected == null) {
            return BaseResponse.fail("常用功能不存在");
        }
        SysCommonUser own = selected.own;
        boolean show = own != null && own.getIsHidden() != null && own.getIsHidden() == 1;
        if (show && !available(selected.type, selected.targetId, selected.tCode)) {
            return BaseResponse.fail("功能菜单不存在、已禁用或外部入口不可用");
        }
        if (own == null) {
            SysCommonGlobal global = activeGlobal(selected.type, selected.targetId, selected.tCode);
            if (global == null) {
                return BaseResponse.fail("常用功能不存在");
            }
            own = newOverride(actor.getId(), global);
        }
        own.setIsHidden(show ? (short) 0 : (short) 1);
        own.setUpdateTime(new Date());
        return saveOrUpdate(own) ? BaseResponse.ok(own) : BaseResponse.fail("切换个人常用功能失败");
    }

    /**
     * 交换当前用户可见列表中两项的位置，保留其他项的相对顺序。
     *
     * @param id      第一个列表项ID
     * @param otherId 第二个列表项ID
     * @return 交换结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse changeSort(String id, String otherId) {
        if (!StringUtils.hasText(id) || !StringUtils.hasText(otherId)) {
            return BaseResponse.fail("请传入两个常用功能ID");
        }
        String firstId = id.trim(), secondId = otherId.trim();
        if (firstId.equals(secondId)) {
            return BaseResponse.fail("请选择两个不同的常用功能");
        }
        LogUserInfoVo actor = actor();
        List<SysCommonUserListVO> rows = visible(actor);
        int first = -1, second = -1;
        List<Short> slots = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            slots.add(rows.get(i).getIsPinned());
            if (firstId.equals(rows.get(i).getId())) {
                first = i;
            }
            if (secondId.equals(rows.get(i).getId())) {
                second = i;
            }
        }
        if (first < 0 || second < 0) {
            return BaseResponse.fail("常用功能不在当前用户的可见列表中");
        }
        Collections.swap(rows, first, second);
        long pinnedSort = 0, normalSort = 0;
        Date now = new Date();
        for (int i = 0; i < rows.size(); i++) {
            SysCommonUserListVO item = rows.get(i);
            short pinned = slots.get(i) != null && slots.get(i) == 1 ? (short) 1 : (short) 0;
            long sort = pinned == 1 ? ++pinnedSort : ++normalSort;
            SysCommonUser own = ownByTarget(actor.getId(), item.getTargetType(), item.getTargetId(), item.getTCode());
            if (own == null) {
                own = new SysCommonUser();
                own.setId(GenerateUUID.getUUID());
                own.setUserId(actor.getId());
                own.setTargetType(item.getTargetType());
                own.setTargetId(item.getTargetId());
                own.setTCode(item.getTCode());
                own.setTargetName(item.getTargetName());
                own.setCreateTime(now);
            }
            own.setIsHidden((short) 0);
            own.setIsPinned(pinned);
            own.setSortNo(sort);
            own.setUpdateTime(now);
            if (!saveOrUpdate(own)) {
                throw new IllegalStateException("保存个人常用功能排序失败");
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 在当前用户的同一置顶分组内移动一项，并保存可见列表的排序。
     *
     * @param id     当前列表项ID，可以是个人记录ID或全局配置ID
     * @param action up表示上移，down表示下移
     * @return 移动结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse upOrDownSysCommonUserSort(String id, String action) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入常用功能ID");
        }
        if (!"up".equals(action) && !"down".equals(action)) {
            return BaseResponse.fail("action只能是up或down");
        }
        LogUserInfoVo actor = actor();
        List<SysCommonUserListVO> rows = visible(actor);
        int index = -1;
        List<Short> slots = new ArrayList<>(rows.size());
        for (int i = 0; i < rows.size(); i++) {
            SysCommonUserListVO item = rows.get(i);
            slots.add(item.getIsPinned());
            if (id.trim().equals(item.getId())) {
                index = i;
            }
        }
        if (index < 0) {
            return BaseResponse.fail("常用功能不在当前用户的可见列表中");
        }
        int target = "up".equals(action) ? index - 1 : index + 1;
        if (target < 0 || target >= rows.size()
                || !Objects.equals(slots.get(index), slots.get(target))) {
            return BaseResponse.fail("已经是当前分组的第一项或最后一项");
        }
        Collections.swap(rows, index, target);
        long pinnedSort = 0;
        long normalSort = 0;
        Date now = new Date();
        for (int i = 0; i < rows.size(); i++) {
            SysCommonUserListVO item = rows.get(i);
            short pinned = slots.get(i) != null && slots.get(i) == 1 ? (short) 1 : (short) 0;
            long sort = pinned == 1 ? ++pinnedSort : ++normalSort;
            SysCommonUser own = ownByTarget(actor.getId(), item.getTargetType(), item.getTargetId(), item.getTCode());
            if (own == null) {
                own = new SysCommonUser();
                own.setId(GenerateUUID.getUUID());
                own.setUserId(actor.getId());
                own.setTargetType(item.getTargetType());
                own.setTargetId(item.getTargetId());
                own.setTCode(item.getTCode());
                own.setTargetName(item.getTargetName());
                own.setCreateTime(now);
            }
            own.setIsHidden((short) 0);
            own.setIsPinned(pinned);
            own.setSortNo(sort);
            own.setUpdateTime(now);
            if (!saveOrUpdate(own)) {
                throw new IllegalStateException("保存个人常用功能排序失败：" + item.getId());
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 合并已发布全局配置和用户记录，过滤不可访问的目标。
     *
     * @param actor 当前用户
     * @return 当前用户可见列表
     */
    private List<SysCommonUserListVO> visible(LogUserInfoVo actor) {
        return visible(actor, true);
    }

    /**
     * 合并全局功能和个人记录，返回来源记录的创建及更新时间；停用的全局目标暂不显示。
     *
     * @param actor         当前用户
     * @param includeGlobal 是否合并已发布的全局功能
     * @return 有序列表
     */
    private List<SysCommonUserListVO> visible(LogUserInfoVo actor, boolean includeGlobal) {
        List<SysCommonGlobal> allGlobals = globalService.list();
        List<SysCommonGlobal> globals = allGlobals.stream()
                .filter(row -> row.getStatus() != null && row.getStatus() == 1)
                .collect(Collectors.toList());
        Set<String> inactiveGlobalKeys = allGlobals.stream()
                .filter(row -> row.getStatus() == null || row.getStatus() != 1)
                .map(row -> key(row.getTargetType(), row.getTargetId(), row.getTCode()))
                .collect(Collectors.toSet());
        List<SysCommonUser> owns = list(new LambdaQueryWrapper<SysCommonUser>().eq(SysCommonUser::getUserId, actor.getId()));
        Map<String, SysCommonUser> ownMap = owns.stream().collect(Collectors.toMap(
                row -> key(row.getTargetType(), row.getTargetId(), row.getTCode()), row -> row,
                (first, second) -> first));
        Set<String> globalKeys = globals.stream().map(row -> key(row.getTargetType(), row.getTargetId(), row.getTCode()))
                .collect(Collectors.toSet());
        Set<String> powerCodes = new HashSet<>();
        for (SysCommonGlobal global : globals) {
            if (global.getTargetType() != null && global.getTargetType() == 1) {
                powerCodes.add(global.getTCode());
            }
        }
        for (SysCommonUser own : owns) {
            if (own.getTargetType() != null && own.getTargetType() == 1) {
                powerCodes.add(own.getTCode());
            }
        }
        powerCodes.remove(null);
        LambdaQueryWrapper<SysPower> powerQuery = new LambdaQueryWrapper<SysPower>()
                .in(!powerCodes.isEmpty(), SysPower::getPowerCode, powerCodes)
                .eq(SysPower::getPowerStatus, "1");
        Map<String, SysPower> powers = powerCodes.isEmpty() ? Collections.emptyMap()
                : powerService.list(powerQuery).stream()
                .collect(Collectors.toMap(SysPower::getPowerCode, row -> row, (first, second) -> first));
        Map<String, SysCommonExternal> externals = externalService.list().stream()
                .collect(Collectors.toMap(SysCommonExternal::getId, row -> row));
        List<SysCommonUserListVO> result = new ArrayList<>();
        for (SysCommonGlobal global : globals) {
            if (!includeGlobal) {
                continue;
            }
            SysCommonUser own = ownMap.get(key(global.getTargetType(), global.getTargetId(), global.getTCode()));
            if (own != null && own.getIsHidden() != null && own.getIsHidden() == 1) {
                continue;
            }
            String icon = own != null && StringUtils.hasText(own.getIcon()) ? own.getIcon() : global.getIcon();
            String targetName = own != null && StringUtils.hasText(own.getTargetName())
                    ? own.getTargetName() : global.getTargetName();
            SysCommonUserListVO item = view(global.getTargetType(), global.getTargetId(), global.getTCode(),
                    targetName, icon, powers, externals);
            if (item == null) {
                continue;
            }
            item.setId(own == null ? global.getId() : own.getId());
            item.setGlobal(true);
            item.setPersonalized(own != null);
            item.setIsPinned(own == null ? global.getIsPinned() : own.getIsPinned());
            item.setSortNo(own == null ? global.getSortNo() : own.getSortNo());
            item.setCreateTime(own == null ? global.getCreateTime() : own.getCreateTime());
            item.setUpdateTime(own == null ? global.getUpdateTime() : own.getUpdateTime());
            result.add(item);
        }
        for (SysCommonUser own : owns) {
            if (own.getIsHidden() != null && own.getIsHidden() == 1
                    || inactiveGlobalKeys.contains(key(own.getTargetType(), own.getTargetId(), own.getTCode()))
                    || includeGlobal && globalKeys.contains(key(own.getTargetType(), own.getTargetId(), own.getTCode()))) {
                continue;
            }
            SysCommonUserListVO item = view(own.getTargetType(), own.getTargetId(), own.getTCode(),
                    own.getTargetName(), own.getIcon(), powers, externals);
            if (item == null) {
                continue;
            }
            item.setId(own.getId());
            item.setGlobal(globalKeys.contains(key(own.getTargetType(), own.getTargetId(), own.getTCode())));
            item.setPersonalized(true);
            item.setIsPinned(own.getIsPinned());
            item.setSortNo(own.getSortNo());
            item.setCreateTime(own.getCreateTime());
            item.setUpdateTime(own.getUpdateTime());
            result.add(item);
        }
        result.sort(Comparator.comparing((SysCommonUserListVO item) -> item.getIsPinned() != null
                        && item.getIsPinned() == 1 ? 0 : 1)
                .thenComparing(item -> Boolean.TRUE.equals(item.getGlobal())
                        && !Boolean.TRUE.equals(item.getPersonalized()) ? 0 : 1)
                .thenComparing(item -> item.getSortNo() == null ? 0L : item.getSortNo())
                .thenComparing(SysCommonUserListVO::getId));
        return result;
    }

    /**
     * 从个人或全局记录读取名称，并补齐目标的跳转地址和图标。
     *
     * @param type      目标类型
     * @param targetId  外部入口ID
     * @param tCode     菜单编码
     * @param targetName 已保存的显示名称
     * @param icon      自定义图标
     * @param powers    功能菜单索引
     * @param externals 外部入口索引
     * @return 可展示且启用的目标，不可用时返回null
     */
    private SysCommonUserListVO view(Short type, String targetId, String tCode, String targetName, String icon,
                                     Map<String, SysPower> powers, Map<String, SysCommonExternal> externals) {
        SysCommonUserListVO item = new SysCommonUserListVO();
        item.setTargetType(type);
        item.setTargetId(targetId);
        item.setTCode(tCode);
        item.setTargetName(targetName);
        if (type != null && type == 1) {
            SysPower power = powers.get(tCode);
            if (power == null) {
                return null;
            }
            item.setTargetUrl(power.getPowerUrl());
            item.setIcon(StringUtils.hasText(icon) ? icon : power.getPowerIcon());
        } else if (type != null && type == 2) {
            SysCommonExternal external = externals.get(targetId);
            if (!activeExternal(external, externals)) {
                return null;
            }
            item.setTargetUrl(external.getUrl());
            item.setIcon(StringUtils.hasText(icon) ? icon : external.getIcon());
        } else {
            return null;
        }
        return item;
    }

    /**
     * 检查功能菜单是否启用或外部系统入口是否可用。
     *
     * @param type     目标类型
     * @param targetId 外部入口ID
     * @param tCode    菜单编码
     * @return 是否可用
     */
    private boolean available(Short type, String targetId, String tCode) {
        if (type == null) {
            return false;
        }
        if (type == 1) {
            if (!StringUtils.hasText(tCode)) {
                return false;
            }
            LambdaQueryWrapper<SysPower> query = new LambdaQueryWrapper<SysPower>()
                    .likeLeft(SysPower::getPowerCode, tCode)
                    .eq(SysPower::getPowerStatus, "1");
            return powerService.count(query) > 0;
        }
        if (type == 2) {
            if (!StringUtils.hasText(targetId)) {
                return false;
            }
            Map<String, SysCommonExternal> externals = externalService.list().stream()
                    .collect(Collectors.toMap(SysCommonExternal::getId, row -> row));
            return activeExternal(externals.get(targetId), externals);
        }
        return false;
    }

    /**
     * 未指定名称时，从启用的菜单或外部入口取得初始名称。
     *
     * @param type 目标类型
     * @param targetId 外部入口ID
     * @param tCode 菜单编码
     * @return 初始显示名称
     */
    private String resolveTargetName(Short type, String targetId, String tCode) {
        if (type == 1) {
            return powerService.list(new LambdaQueryWrapper<SysPower>()
                            .likeLeft(SysPower::getPowerCode, tCode)
                            .eq(SysPower::getPowerStatus, "1"))
                    .stream().map(SysPower::getPowerName).filter(StringUtils::hasText)
                    .findFirst().orElse(null);
        }
        SysCommonExternal external = externalService.getById(targetId);
        return external == null ? null : external.getName();
    }

    /**
     * 检查外部入口及全部上级是否启用。
     *
     * @param external  外部入口
     * @param externals 外部入口索引
     * @return 是否可跳转
     */
    private boolean activeExternal(SysCommonExternal external, Map<String, SysCommonExternal> externals) {
        if (external == null || external.getStatus() == null || external.getStatus() != 1
                || !StringUtils.hasText(external.getUrl())) {
            return false;
        }
        Set<String> seen = new HashSet<>(Collections.singleton(external.getId()));
        String parentId = external.getParentId();
        while (StringUtils.hasText(parentId) && !"0".equals(parentId)) {
            if (!seen.add(parentId)) {
                return false;
            }
            SysCommonExternal parent = externals.get(parentId);
            if (parent == null || parent.getStatus() == null || parent.getStatus() != 1) {
                return false;
            }
            parentId = parent.getParentId();
        }
        return true;
    }

    /**
     * 按当前用户及个人记录ID查询。
     *
     * @param id     个人记录ID
     * @param userId 当前用户ID
     * @return 本人的记录
     */
    private SysCommonUser ownById(String id, String userId) {
        return getOne(new LambdaQueryWrapper<SysCommonUser>()
                .eq(SysCommonUser::getId, id).eq(SysCommonUser::getUserId, userId), false);
    }

    /**
     * 按当前用户及功能目标查询。
     *
     * @param userId   当前用户ID
     * @param type     目标类型
     * @param targetId 外部入口ID
     * @param tCode    菜单编码
     * @return 本人的记录
     */
    private SysCommonUser ownByTarget(String userId, Short type, String targetId, String tCode) {
        LambdaQueryWrapper<SysCommonUser> query = new LambdaQueryWrapper<SysCommonUser>()
                .eq(SysCommonUser::getUserId, userId).eq(SysCommonUser::getTargetType, type);
        if (type != null && type == 1) {
            query.eq(SysCommonUser::getTCode, tCode);
        } else {
            query.eq(SysCommonUser::getTargetId, targetId);
        }
        return getOne(query, false);
    }

    /**
     * 查询已发布的全局功能。
     *
     * @param type     目标类型
     * @param targetId 外部入口ID
     * @param tCode    菜单编码
     * @return 全局配置
     */
    private SysCommonGlobal activeGlobal(Short type, String targetId, String tCode) {
        SysCommonGlobal global = globalByTarget(type, targetId, tCode);
        return global != null && global.getStatus() != null && global.getStatus() == 1 ? global : null;
    }

    /**
     * 查询目标对应的全局配置，包括暂时停用的配置。
     *
     * @param type     目标类型
     * @param targetId 外部入口ID
     * @param tCode    菜单编码
     * @return 全局配置
     */
    private SysCommonGlobal globalByTarget(Short type, String targetId, String tCode) {
        LambdaQueryWrapper<SysCommonGlobal> query = new LambdaQueryWrapper<SysCommonGlobal>()
                .eq(SysCommonGlobal::getTargetType, type);
        if (type != null && type == 1) {
            query.eq(SysCommonGlobal::getTCode, tCode);
        } else {
            query.eq(SysCommonGlobal::getTargetId, targetId);
        }
        return globalService.getOne(query, false);
    }

    /**
     * 解析列表项ID。
     *
     * @param id     列表项ID
     * @param userId 当前用户ID
     * @return 目标与个人记录
     */
    private Selection selection(String id, String userId) {
        SysCommonUser own = ownById(id, userId);
        if (own != null) {
            return new Selection(own.getTargetType(), own.getTargetId(), own.getTCode(), own);
        }
        SysCommonGlobal global = globalService.getById(id);
        if (global == null || global.getStatus() != 1) {
            return null;
        }
        return new Selection(global.getTargetType(), global.getTargetId(), global.getTCode(),
                ownByTarget(userId, global.getTargetType(), global.getTargetId(), global.getTCode()));
    }

    /**
     * 为全局功能创建个人覆盖记录。
     *
     * @param userId 当前用户ID
     * @param global 全局配置
     * @return 未保存的个人记录
     */
    private SysCommonUser newOverride(String userId, SysCommonGlobal global) {
        SysCommonUser own = new SysCommonUser();
        own.setId(GenerateUUID.getUUID());
        own.setUserId(userId);
        own.setTargetType(global.getTargetType());
        own.setTargetId(global.getTargetId());
        own.setTCode(global.getTCode());
        own.setTargetName(global.getTargetName());
        own.setIcon(global.getIcon());
        own.setIsPinned(global.getIsPinned());
        own.setSortNo(global.getSortNo());
        own.setCreateTime(new Date());
        return own;
    }

    /**
     * 查询当前用户在一个置顶分组中的最大排序值。
     *
     * @param userId 当前用户ID
     * @param pinned 置顶状态
     * @return 最大排序值
     */
    private long lastSort(String userId, short pinned) {
        return list(new LambdaQueryWrapper<SysCommonUser>()
                .eq(SysCommonUser::getUserId, userId).eq(SysCommonUser::getIsPinned, pinned)).stream()
                .map(SysCommonUser::getSortNo).filter(Objects::nonNull).max(Long::compareTo).orElse(0L);
    }

    /**
     * 判断二值状态是否合法。
     *
     * @param value 状态
     * @return 是否为null、0或1
     */
    private boolean flag(Short value) {
        return value == null || value == 0 || value == 1;
    }

    /**
     * 组合功能目标的去重键。
     *
     * @param type     目标类型
     * @param targetId 外部入口ID
     * @param tCode    菜单编码
     * @return 去重键
     */
    private String key(Short type, String targetId, String tCode) {
        return type + ":" + (type != null && type == 1 ? tCode : targetId);
    }

    /**
     * 获取当前登录用户。
     *
     * @return 用户信息
     */
    private LogUserInfoVo actor() {
        return UserUtils.getUserInfo();
    }

    /**
     * 列表项ID解析结果。
     */
    private static class Selection {
        private final Short type;
        private final String targetId;
        private final String tCode;
        private final SysCommonUser own;

        private Selection(Short type, String targetId, String tCode, SysCommonUser own) {
            this.type = type;
            this.targetId = targetId;
            this.tCode = tCode;
            this.own = own;
        }
    }
}
