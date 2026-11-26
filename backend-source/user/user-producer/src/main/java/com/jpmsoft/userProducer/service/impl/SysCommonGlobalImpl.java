package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.SysCommonGlobalMapper;
import com.jpmsoft.userProducer.pojo.dto.SysCommonGlobalDTO;
import com.jpmsoft.userProducer.pojo.entity.SysCommonExternal;
import com.jpmsoft.userProducer.pojo.entity.SysCommonGlobal;
import com.jpmsoft.userProducer.pojo.entity.SysPower;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.pojo.vo.SysCommonGlobalListVO;
import com.jpmsoft.userProducer.service.ISysCommonExternalService;
import com.jpmsoft.userProducer.service.ISysCommonGlobalService;
import com.jpmsoft.userProducer.service.ISysPowerService;
import com.jpmsoft.userProducer.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 全局默认功能菜单或外部系统入口
 */
@Service
public class SysCommonGlobalImpl extends ServiceImpl<SysCommonGlobalMapper, SysCommonGlobal> implements ISysCommonGlobalService {
    @Resource
    private ISysPowerService powerService;
    @Resource
    private ISysCommonExternalService externalService;

    /**
     * 创建或更新全局常用功能，保存显示名称；个人列表会合并新发布的配置。
     *
     * @param request 功能目标和展示设置
     * @return 保存后的配置
     */
    @Override
    @Transactional
    public BaseResponse createOrUpdateSysCommonGlobal(SysCommonGlobalDTO request) {
        LogUserInfoVo operator = UserUtils.getUserInfo();
        // if (operator == null || !("admin".equalsIgnoreCase(operator.getCode())
        //         || "jpmadmin".equalsIgnoreCase(operator.getCode()))) {
        //     return BaseResponse.fail("无全局常用功能管理权限");
        // }
        if (request == null) {
            return BaseResponse.fail("请传入全局常用功能信息");
        }
        if (request.getIsPinned() != null && request.getIsPinned() != 0 && request.getIsPinned() != 1) {
            return BaseResponse.fail("置顶状态只能为0或1");
        }
        if (request.getStatus() != null && request.getStatus() != 0 && request.getStatus() != 1) {
            return BaseResponse.fail("发布状态只能为0或1");
        }
        if (request.getIcon() != null && request.getIcon().length() > 500) {
            return BaseResponse.fail("图标标识不能超过500个字符");
        }
        String id = StringUtils.hasText(request.getId()) ? request.getId().trim() : null;
        SysCommonGlobal entity = id == null ? null : getById(id);
        if (id != null && entity == null) {
            return BaseResponse.fail("全局常用功能不存在");
        }
        Short targetType = request.getTargetType() != null ? request.getTargetType()
                : entity == null ? null : entity.getTargetType();
        String targetId = StringUtils.hasText(request.getTargetId()) ? request.getTargetId().trim()
                : entity == null ? null : entity.getTargetId();
        String tCode = StringUtils.hasText(request.getTCode()) ? request.getTCode().trim()
                : entity == null ? null : entity.getTCode();
        if (targetType == null || targetType != 1 && targetType != 2) {
            return BaseResponse.fail("目标类型只能为1或2");
        }
        if (targetType == 1) {
            if (!StringUtils.hasText(tCode) || tCode.length() > 500) {
                return BaseResponse.fail("请传入有效的菜单编码");
            }
            if (!powerAvailable(tCode)) {
                return BaseResponse.fail("功能菜单不存在或已禁用");
            }
        } else {
            if (!StringUtils.hasText(targetId)) {
                return BaseResponse.fail("请选择外部系统入口");
            }
            SysCommonExternal external = externalService.getById(targetId);
            if (external == null || external.getStatus() == null || external.getStatus() != 1
                    || !StringUtils.hasText(external.getUrl())) {
                return BaseResponse.fail("外部系统入口不存在或不可跳转");
            }
        }
        LambdaQueryWrapper<SysCommonGlobal> duplicateQuery = new LambdaQueryWrapper<SysCommonGlobal>()
                .eq(SysCommonGlobal::getTargetType, targetType);
        if (targetType == 1) {
            duplicateQuery.eq(SysCommonGlobal::getTCode, tCode);
        } else {
            duplicateQuery.eq(SysCommonGlobal::getTargetId, targetId);
        }
        SysCommonGlobal duplicate = getOne(duplicateQuery, false);
        if (duplicate != null && (entity == null || !duplicate.getId().equals(entity.getId()))) {
            return BaseResponse.fail("该功能已添加到全局常用功能");
        }
        String targetName = StringUtils.hasText(request.getTargetName()) ? request.getTargetName().trim()
                : entity != null && Objects.equals(entity.getTargetType(), targetType)
                && Objects.equals(entity.getTargetId(), targetType == 2 ? targetId : null)
                && Objects.equals(entity.getTCode(), targetType == 1 ? tCode : null)
                && StringUtils.hasText(entity.getTargetName()) ? entity.getTargetName()
                : resolveTargetName(targetType, targetId, tCode);
        if (!StringUtils.hasText(targetName) || targetName.length() > 500) {
            return BaseResponse.fail("常用功能名称不能为空且不能超过500个字符");
        }
        short pinned = request.getIsPinned() != null ? request.getIsPinned()
                : entity == null || entity.getIsPinned() == null ? (short) 0 : entity.getIsPinned();
        boolean newGroup = entity == null || !Objects.equals(entity.getIsPinned(), pinned);
        Date now = new Date();
        if (entity == null) {
            entity = new SysCommonGlobal();
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateUid(operator.getId());
            entity.setCreateTime(now);
            entity.setStatus((short) 1);
        }
        entity.setTargetType(targetType);
        entity.setTargetId(targetType == 2 ? targetId : null);
        entity.setTCode(targetType == 1 ? tCode : null);
        entity.setTargetName(targetName);
        entity.setIsPinned(pinned);
        if (request.getIcon() != null) {
            entity.setIcon(request.getIcon());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        if (request.getSortNo() != null) {
            entity.setSortNo(request.getSortNo());
        } else if (newGroup || entity.getSortNo() == null) {
            entity.setSortNo(firstSortNo(pinned));
        }
        entity.setUpdateUid(operator.getId());
        entity.setUpdateTime(now);
        return saveOrUpdate(entity) ? BaseResponse.ok(entity) : BaseResponse.fail("保存全局常用功能失败");
    }

    /**
     * 分页查询全局常用功能，从配置表读取名称并补齐当前页的地址与图标。
     *
     * @param request 分页参数及可选的ID、目标类型、菜单编码、外部入口ID、置顶和状态条件
     * @return 按置顶优先、组内排序值升序排列的当前页及总数
     */
    @Override
    public BaseResponse getSysCommonGlobalList(SysCommonGlobalDTO request) {
        LambdaQueryWrapper<SysCommonGlobal> query = new LambdaQueryWrapper<>();
        if (request != null) {
            if (StringUtils.hasText(request.getId())) {
                query.eq(SysCommonGlobal::getId, request.getId().trim());
            }
            if (request.getTargetType() != null) {
                query.eq(SysCommonGlobal::getTargetType, request.getTargetType());
            }
            if (StringUtils.hasText(request.getTargetId())) {
                query.eq(SysCommonGlobal::getTargetId, request.getTargetId().trim());
            }
            if (StringUtils.hasText(request.getTCode())) {
                query.eq(SysCommonGlobal::getTCode, request.getTCode().trim());
            }
            if (request.getIsPinned() != null) {
                query.eq(SysCommonGlobal::getIsPinned, request.getIsPinned());
            }
            if (request.getStatus() != null) {
                query.eq(SysCommonGlobal::getStatus, request.getStatus());
            }
        }
        query.orderByDesc(SysCommonGlobal::getIsPinned)
                .orderByAsc(SysCommonGlobal::getSortNo, SysCommonGlobal::getId);
        int pageNo = request != null && request.getPage() > 0 ? request.getPage() : 1;
        int pageSize = request != null && request.getSize() > 0 ? request.getSize() : 10;
        Page<SysCommonGlobal> page = page(new Page<>(pageNo, pageSize), query);
        List<SysCommonGlobal> rows = page.getRecords();
        Set<String> powerCodes = rows.stream()
                .filter(row -> row.getTargetType() != null && row.getTargetType() == 1)
                .map(SysCommonGlobal::getTCode).filter(StringUtils::hasText).collect(Collectors.toSet());
        Set<String> externalIds = rows.stream().filter(row -> row.getTargetType() != null && row.getTargetType() == 2)
                .map(SysCommonGlobal::getTargetId).filter(StringUtils::hasText).collect(Collectors.toSet());
        LambdaQueryWrapper<SysPower> powerQuery = new LambdaQueryWrapper<SysPower>()
                .in(!powerCodes.isEmpty(), SysPower::getPowerCode, powerCodes);
        Map<String, SysPower> powers = powerCodes.isEmpty() ? java.util.Collections.emptyMap()
                : powerService.list(powerQuery).stream()
                .collect(Collectors.toMap(SysPower::getPowerCode, Function.identity(), (first, second) -> first));
        Map<String, SysCommonExternal> externals = externalIds.isEmpty() ? java.util.Collections.emptyMap()
                : externalService.listByIds(externalIds).stream()
                .collect(Collectors.toMap(SysCommonExternal::getId, Function.identity()));
        List<SysCommonGlobalListVO> result = rows.stream().map(row -> {
            SysCommonGlobalListVO item = new SysCommonGlobalListVO();
            item.setId(row.getId());
            item.setTargetType(row.getTargetType());
            item.setTargetId(row.getTargetId());
            item.setTCode(row.getTCode());
            item.setTargetName(row.getTargetName());
            item.setIsPinned(row.getIsPinned());
            item.setStatus(row.getStatus());
            item.setSortNo(row.getSortNo());
            item.setCreateUid(row.getCreateUid());
            item.setCreateTime(row.getCreateTime());
            item.setUpdateUid(row.getUpdateUid());
            item.setUpdateTime(row.getUpdateTime());
            if (row.getTargetType() != null && row.getTargetType() == 1) {
                SysPower power = powers.get(row.getTCode());
                if (power != null) {
                    item.setTargetUrl(power.getPowerUrl());
                    item.setIcon(StringUtils.hasText(row.getIcon()) ? row.getIcon() : power.getPowerIcon());
                }
            } else if (row.getTargetType() != null && row.getTargetType() == 2) {
                SysCommonExternal external = externals.get(row.getTargetId());
                if (external != null) {
                    item.setTargetUrl(external.getUrl());
                    item.setIcon(StringUtils.hasText(row.getIcon()) ? row.getIcon() : external.getIcon());
                }
            }
            if (item.getIcon() == null) {
                item.setIcon(row.getIcon());
            }
            return item;
        }).collect(Collectors.toList());
        return BaseResponse.ok(result, page.getTotal());
    }

    /**
     * 按ID读取全局常用功能配置。
     *
     * @param id 配置ID
     * @return 配置详情或不存在提示
     */
    @Override
    public BaseResponse getSysCommonGlobalById(String id) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入全局常用功能ID");
        }
        SysCommonGlobal entity = getById(id.trim());
        return entity == null ? BaseResponse.fail("全局常用功能不存在") : BaseResponse.ok(entity);
    }

    /**
     * 删除指定全局配置；已有个人记录保留并作为个人功能显示。
     *
     * @param id 配置ID
     * @return 删除结果
     */
    @Override
    @Transactional
    public BaseResponse deleteSysCommonGlobalById(String id) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入全局常用功能ID");
        }
        String targetId = id.trim();
        if (getById(targetId) == null) {
            return BaseResponse.fail("全局常用功能不存在");
        }
        return removeById(targetId) ? BaseResponse.ok() : BaseResponse.fail("删除全局常用功能失败");
    }

    /**
     * 切换指定全局配置的发布状态；停用期间个人列表隐藏对应功能，重新发布后恢复原设置。
     *
     * @param id 配置ID
     * @return 更新后的配置
     */
    @Override
    @Transactional
    public BaseResponse switchSysCommonExternalById(String id) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入全局常用功能ID");
        }
        SysCommonGlobal entity = getById(id.trim());
        if (entity == null) {
            return BaseResponse.fail("全局常用功能不存在");
        }
        if (entity.getStatus() == null || entity.getStatus() != 0 && entity.getStatus() != 1) {
            return BaseResponse.fail("全局常用功能状态无效");
        }
        if (entity.getStatus() == 0 && !targetAvailable(entity)) {
            return BaseResponse.fail("功能菜单不存在、已禁用或外部入口不可用，无法发布");
        }
        entity.setStatus(entity.getStatus() == 1 ? (short) 0 : (short) 1);
        entity.setUpdateUid(UserUtils.getUserInfo().getId());
        entity.setUpdateTime(new Date());
        return updateById(entity) ? BaseResponse.ok(entity) : BaseResponse.fail("切换全局常用功能状态失败");
    }

    /**
     * 交换两个配置的列表位置；跨置顶分组时一并交换置顶状态。
     *
     * @param id      第一个配置ID
     * @param otherId 第二个配置ID
     * @return 交换结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse changeSort(String id, String otherId) {
        if (!StringUtils.hasText(id) || !StringUtils.hasText(otherId)) {
            return BaseResponse.fail("请传入两个全局常用功能ID");
        }
        String firstId = id.trim();
        String secondId = otherId.trim();
        if (firstId.equals(secondId)) {
            return BaseResponse.fail("请选择两个不同的全局常用功能");
        }
        List<SysCommonGlobal> rows = list(new LambdaQueryWrapper<SysCommonGlobal>()
                .orderByDesc(SysCommonGlobal::getIsPinned)
                .orderByAsc(SysCommonGlobal::getSortNo, SysCommonGlobal::getId));
        int firstIndex = -1;
        int secondIndex = -1;
        List<Short> originalGroups = new ArrayList<>(rows.size());
        for (int i = 0; i < rows.size(); i++) {
            SysCommonGlobal item = rows.get(i);
            originalGroups.add(item.getIsPinned());
            if (firstId.equals(item.getId())) {
                firstIndex = i;
            }
            if (secondId.equals(item.getId())) {
                secondIndex = i;
            }
        }
        if (firstIndex < 0 || secondIndex < 0) {
            return BaseResponse.fail("全局常用功能不存在");
        }
        Collections.swap(rows, firstIndex, secondIndex);
        Date now = new Date();
        String userId = UserUtils.getUserInfo().getId();
        long pinnedSort = 0;
        long normalSort = 0;
        for (int i = 0; i < rows.size(); i++) {
            SysCommonGlobal item = rows.get(i);
            Short pinned = originalGroups.get(i);
            long sortNo = pinned != null && pinned == 1 ? ++pinnedSort : ++normalSort;
            if (Objects.equals(item.getIsPinned(), pinned) && Objects.equals(item.getSortNo(), sortNo)) {
                continue;
            }
            item.setIsPinned(pinned);
            item.setSortNo(sortNo);
            item.setUpdateUid(userId);
            item.setUpdateTime(now);
            if (!updateById(item)) {
                throw new IllegalStateException("更新全局常用功能排序失败：" + item.getId());
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 在同一置顶分组内与相邻配置交换位置，并重写该分组的排序值。
     *
     * @param id     当前配置ID
     * @param action up表示上移，down表示下移
     * @return 移动结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse upOrDownSysCommonGlobalSort(String id, String action) {
        if (!StringUtils.hasText(id)) {
            return BaseResponse.fail("请传入全局常用功能ID");
        }
        if (!"up".equals(action) && !"down".equals(action)) {
            return BaseResponse.fail("action只能是up或down");
        }
        SysCommonGlobal current = getById(id.trim());
        if (current == null) {
            return BaseResponse.fail("全局常用功能不存在");
        }
        List<SysCommonGlobal> siblings = list(new LambdaQueryWrapper<SysCommonGlobal>()
                .eq(SysCommonGlobal::getIsPinned, current.getIsPinned())
                .orderByAsc(SysCommonGlobal::getSortNo, SysCommonGlobal::getId));
        int index = -1;
        for (int i = 0; i < siblings.size(); i++) {
            if (current.getId().equals(siblings.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index < 0) {
            return BaseResponse.fail("当前功能不在排序列表中");
        }
        int target = "up".equals(action) ? index - 1 : index + 1;
        if (target < 0 || target >= siblings.size()) {
            return BaseResponse.fail("已经是当前分组的第一项或最后一项");
        }
        Collections.swap(siblings, index, target);
        Date now = new Date();
        String operatorId = UserUtils.getUserInfo().getId();
        for (int i = 0; i < siblings.size(); i++) {
            SysCommonGlobal item = siblings.get(i);
            long sortNo = i + 1L;
            if (Objects.equals(item.getSortNo(), sortNo)) {
                continue;
            }
            item.setSortNo(sortNo);
            item.setUpdateUid(operatorId);
            item.setUpdateTime(now);
            if (!updateById(item)) {
                throw new IllegalStateException("更新全局常用功能排序失败：" + item.getId());
            }
        }
        return BaseResponse.ok();
    }

    /**
     * 检查全局配置关联的功能菜单或外部入口是否仍可使用。
     *
     * @param entity 全局配置
     * @return 目标是否可用
     */
    private boolean targetAvailable(SysCommonGlobal entity) {
        if (entity.getTargetType() == null) {
            return false;
        }
        if (entity.getTargetType() == 1) {
            return StringUtils.hasText(entity.getTCode()) && powerAvailable(entity.getTCode());
        }
        if (entity.getTargetType() == 2) {
            if (!StringUtils.hasText(entity.getTargetId())) {
                return false;
            }
            SysCommonExternal external = externalService.getById(entity.getTargetId());
            return external != null && external.getStatus() != null && external.getStatus() == 1
                    && StringUtils.hasText(external.getUrl());
        }
        return false;
    }

    /**
     * 根据菜单编码检查功能菜单是否存在且已启用。
     *
     * @param tCode 菜单编码
     * @return 菜单是否存在且已启用
     */
    private boolean powerAvailable(String tCode) {
        LambdaQueryWrapper<SysPower> query = new LambdaQueryWrapper<SysPower>()
                .likeLeft(SysPower::getPowerCode, tCode)
                .eq(SysPower::getPowerStatus, "1");
        return powerService.count(query) > 0;
    }

    /**
     * 未指定名称时，从当前关联的菜单或外部入口取得初始名称。
     *
     * @param targetType 目标类型
     * @param targetId 外部入口ID
     * @param tCode 菜单编码
     * @return 初始显示名称
     */
    private String resolveTargetName(Short targetType, String targetId, String tCode) {
        if (targetType == 1) {
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
     * 获取对应置顶分组第一位之前的排序值。
     *
     * @param pinned 置顶状态
     * @return 新的最小排序值
     */
    private long firstSortNo(short pinned) {
        return list(new LambdaQueryWrapper<SysCommonGlobal>()
                .eq(SysCommonGlobal::getIsPinned, pinned).eq(SysCommonGlobal::getStatus, 1)).stream()
                .map(SysCommonGlobal::getSortNo).filter(Objects::nonNull)
                .min(Long::compareTo).orElse(1L) - 1;
    }
}
