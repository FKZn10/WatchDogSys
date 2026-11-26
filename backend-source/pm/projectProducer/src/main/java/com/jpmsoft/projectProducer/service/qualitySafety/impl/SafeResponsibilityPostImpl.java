package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDTO;
import com.jpmsoft.common.model.requestAndResponse.SafeResponsibility.SafeResponsibilityPostDutyDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.qualitySafety.SafeResponsibilityPostMapper;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.SafeResponsibilityPost;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityChangeLogService;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityPostDutyService;
import com.jpmsoft.projectProducer.service.qualitySafety.ISafeResponsibilityPostService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 安全责任岗位服务实现。
 */
@Service
public class SafeResponsibilityPostImpl extends ServiceImpl<SafeResponsibilityPostMapper, SafeResponsibilityPost> implements ISafeResponsibilityPostService {
    @Resource
    private ISafeResponsibilityPostDutyService postDutyService;
    @Resource
    private ISafeResponsibilityChangeLogService changeLogService;

    /**
     * 新增或修改岗位，并整体同步岗位职责。
     *
     * @param request 岗位及职责数据
     * @param uid     操作人ID
     * @param uname   操作人姓名
     * @return 岗位ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSafeResponsibilityPost(SafeResponsibilityPostDTO request,
                                                             String uid, String uname) {
        if (request == null || StrUtil.isBlank(request.getSystemId())
                || StrUtil.isBlank(request.getLevelId()) || StrUtil.isBlank(request.getPostName())) {
            throw new JPMException("体系ID、层级ID和岗位名称不能为空");
        }
        ensurePostNameUnique(request);
        Date now = new Date();
        SafeResponsibilityPost before = null;
        String operationType = "ADD";
        if (StrUtil.isBlank(request.getId())) {
            request.setId(GenerateUUID.getUUID());
            request.setSortNo(request.getSortNo() == null ? nextSortNo(request.getLevelId()) : request.getSortNo());
            request.setStatus(request.getStatus() == null ? Boolean.TRUE : request.getStatus());
            request.setCreateUid(uid);
            request.setCreateUname(uname);
            request.setCreateTime(now);
        } else {
            before = requirePost(request.getId());
            if (!StrUtil.equals(before.getSystemId(), request.getSystemId())) {
                throw new JPMException("不能将岗位移动到其他责任体系");
            }
            operationType = "UPDATE";
            request.setCreateUid(before.getCreateUid());
            request.setCreateUname(before.getCreateUname());
            request.setCreateTime(before.getCreateTime());
        }
        request.setUpdateUid(uid);
        request.setUpdateUname(uname);
        request.setUpdateTime(now);
        SafeResponsibilityPost entity = toEntity(request);
        if (before == null) {
            save(entity);
        } else {
            updateById(entity);
        }
        postDutyService.replacePostDuties(request.getSystemId(), request.getId(), request.getDutyList(), uid, uname, now);
        SafeResponsibilityPostDTO after = toDTO(entity);
        after.setDutyList(postDutyService.listByPostId(entity.getId()));
        changeLogService.saveChangeLog(request.getSystemId(), "POST", request.getId(), request.getPostName(),
                operationType, before, after, uid, uname, "维护安全责任岗位", null);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 按请求字段筛选岗位，并附带职责列表。
     *
     * @param request 岗位筛选条件
     * @return 岗位列表
     */
    @Override
    public BaseResponse getSafeResponsibilityPostList(SafeResponsibilityPostDTO request) {
        SafeResponsibilityPostDTO condition = request == null ? new SafeResponsibilityPostDTO() : request;
        LambdaQueryWrapper<SafeResponsibilityPost> wrapper = buildWrapper(condition)
                .orderByAsc(SafeResponsibilityPost::getSortNo)
                .orderByAsc(SafeResponsibilityPost::getCreateTime);
        List<SafeResponsibilityPostDTO> result = list(wrapper).stream()
                .map(this::toDTOWithDuties)
                .collect(Collectors.toList());
        return BaseResponse.ok(result);
    }

    /**
     * 删除岗位及其全部职责，并记录变更台账。
     *
     * @param request 包含岗位ID的请求
     * @param uid     操作人ID
     * @param uname   操作人姓名
     * @return 操作结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteSafeResponsibilityPost(SafeResponsibilityPostDTO request, String uid, String uname) {
        if (request == null || StrUtil.isBlank(request.getId())) {
            throw new JPMException("岗位ID不能为空");
        }
        SafeResponsibilityPost entity = requirePost(request.getId());
        SafeResponsibilityPostDTO before = toDTOWithDuties(entity);
        postDutyService.deleteByPostId(entity.getId());
        removeById(entity.getId());
        changeLogService.saveChangeLog(entity.getSystemId(), "POST", entity.getId(), entity.getPostName(), "DELETE",
                before, null, uid, uname, "删除安全责任岗位", null);
        return BaseResponse.ok();
    }

    /**
     * 查询体系内岗位，按层级分组；关键字可匹配岗位字段及职责内容。
     *
     * @param systemId 体系ID
     * @param keyword  树筛选关键字
     * @return 以层级ID为键的岗位集合
     */
    @Override
    public Map<String, List<SafeResponsibilityPostDTO>> getPostMapBySystemId(String systemId, String keyword) {
        List<SafeResponsibilityPost> posts = list(new LambdaQueryWrapper<SafeResponsibilityPost>()
                .eq(SafeResponsibilityPost::getSystemId, systemId)
                .orderByAsc(SafeResponsibilityPost::getSortNo)
                .orderByAsc(SafeResponsibilityPost::getCreateTime));
        Map<String, List<SafeResponsibilityPostDTO>> result = new LinkedHashMap<>();
        for (SafeResponsibilityPost post : posts) {
            SafeResponsibilityPostDTO dto = toDTOWithDuties(post);
            if (StrUtil.isNotBlank(keyword) && !matchesKeyword(dto, keyword)) {
                continue;
            }
            result.computeIfAbsent(dto.getLevelId(), key -> new ArrayList<>()).add(dto);
        }
        return result;
    }

    /**
     * 删除体系下全部岗位及职责。
     *
     * @param systemId 体系ID
     */
    @Override
    public void deleteBySystemId(String systemId) {
        List<SafeResponsibilityPost> posts = list(new LambdaQueryWrapper<SafeResponsibilityPost>()
                .eq(SafeResponsibilityPost::getSystemId, systemId));
        for (SafeResponsibilityPost post : posts) {
            postDutyService.deleteByPostId(post.getId());
        }
        remove(new LambdaQueryWrapper<SafeResponsibilityPost>()
                .eq(SafeResponsibilityPost::getSystemId, systemId));
    }

    /**
     * 根据层级ID映射复制来源体系的岗位及职责。
     *
     * @param sourceSystemId 来源体系ID
     * @param targetSystemId 目标体系ID
     * @param levelIdMap     层级ID映射
     * @param uid            操作人ID
     * @param uname          操作人姓名
     */
    @Override
    public void copyBySystem(String sourceSystemId, String targetSystemId, Map<String, String> levelIdMap,
                             String uid, String uname) {
        Date now = new Date();
        List<SafeResponsibilityPost> sourcePosts = list(new LambdaQueryWrapper<SafeResponsibilityPost>()
                .eq(SafeResponsibilityPost::getSystemId, sourceSystemId)
                .orderByAsc(SafeResponsibilityPost::getSortNo));
        for (SafeResponsibilityPost source : sourcePosts) {
            List<SafeResponsibilityPostDutyDTO> duties = postDutyService.listByPostId(source.getId());
            SafeResponsibilityPost target = new SafeResponsibilityPost();
            BeanUtils.copyProperties(source, target);
            target.setId(GenerateUUID.getUUID());
            target.setSystemId(targetSystemId);
            target.setLevelId(levelIdMap.get(source.getLevelId()));
            target.setCreateUid(uid);
            target.setCreateUname(uname);
            target.setCreateTime(now);
            target.setUpdateUid(uid);
            target.setUpdateUname(uname);
            target.setUpdateTime(now);
            save(target);
            postDutyService.replacePostDuties(targetSystemId, target.getId(), duties, uid, uname, now);
        }
    }

    /**
     * 生成岗位字段筛选条件。
     */
    private LambdaQueryWrapper<SafeResponsibilityPost> buildWrapper(SafeResponsibilityPostDTO condition) {
        return new LambdaQueryWrapper<SafeResponsibilityPost>()
                .eq(StrUtil.isNotBlank(condition.getId()), SafeResponsibilityPost::getId, condition.getId())
                .eq(StrUtil.isNotBlank(condition.getSystemId()), SafeResponsibilityPost::getSystemId, condition.getSystemId())
                .eq(StrUtil.isNotBlank(condition.getLevelId()), SafeResponsibilityPost::getLevelId, condition.getLevelId())
                .like(StrUtil.isNotBlank(condition.getPostName()), SafeResponsibilityPost::getPostName, condition.getPostName())
                .like(StrUtil.isNotBlank(condition.getPostDescription()), SafeResponsibilityPost::getPostDescription,
                        condition.getPostDescription())
                .like(StrUtil.isNotBlank(condition.getDailyRequirement()), SafeResponsibilityPost::getDailyRequirement,
                        condition.getDailyRequirement())
                .like(StrUtil.isNotBlank(condition.getControlStandard()), SafeResponsibilityPost::getControlStandard,
                        condition.getControlStandard())
                .like(StrUtil.isNotBlank(condition.getAssessmentBasis()), SafeResponsibilityPost::getAssessmentBasis,
                        condition.getAssessmentBasis())
                .like(StrUtil.isNotBlank(condition.getAccountabilityScenario()),
                        SafeResponsibilityPost::getAccountabilityScenario, condition.getAccountabilityScenario())
                .eq(condition.getStatus() != null, SafeResponsibilityPost::getStatus, condition.getStatus());
    }

    /**
     * 判断岗位或其职责是否命中树筛选关键字。
     */
    private boolean matchesKeyword(SafeResponsibilityPostDTO post, String keyword) {
        if (StrUtil.containsIgnoreCase(post.getPostName(), keyword)
                || StrUtil.containsIgnoreCase(post.getPostDescription(), keyword)
                || StrUtil.containsIgnoreCase(post.getDailyRequirement(), keyword)
                || StrUtil.containsIgnoreCase(post.getControlStandard(), keyword)
                || StrUtil.containsIgnoreCase(post.getAssessmentBasis(), keyword)
                || StrUtil.containsIgnoreCase(post.getAccountabilityScenario(), keyword)) {
            return true;
        }
        return post.getDutyList() != null && post.getDutyList().stream()
                .anyMatch(duty -> StrUtil.containsIgnoreCase(duty.getDutyContent(), keyword));
    }

    /**
     * 校验同一层级下岗位名称唯一。
     */
    private void ensurePostNameUnique(SafeResponsibilityPostDTO post) {
        long count = count(new LambdaQueryWrapper<SafeResponsibilityPost>()
                .eq(SafeResponsibilityPost::getLevelId, post.getLevelId())
                .eq(SafeResponsibilityPost::getPostName, post.getPostName())
                .ne(StrUtil.isNotBlank(post.getId()), SafeResponsibilityPost::getId, post.getId()));
        if (count > 0) {
            throw new JPMException("同一层级下岗位名称不能重复");
        }
    }

    /**
     * 查询岗位，不存在时抛出业务异常。
     */
    private SafeResponsibilityPost requirePost(String postId) {
        SafeResponsibilityPost post = getById(postId);
        if (post == null) {
            throw new JPMException("安全责任岗位不存在");
        }
        return post;
    }

    /**
     * 计算层级下一个岗位排序号。
     */
    private int nextSortNo(String levelId) {
        return list(new LambdaQueryWrapper<SafeResponsibilityPost>()
                .eq(SafeResponsibilityPost::getLevelId, levelId)).stream()
                .map(SafeResponsibilityPost::getSortNo)
                .filter(Objects::nonNull)
                .max(Integer::compareTo)
                .orElse(0) + 1;
    }

    /**
     * 将岗位实体转换为DTO并加载职责。
     */
    private SafeResponsibilityPostDTO toDTOWithDuties(SafeResponsibilityPost entity) {
        SafeResponsibilityPostDTO dto = toDTO(entity);
        dto.setDutyList(postDutyService.listByPostId(entity.getId()));
        return dto;
    }

    /**
     * 将岗位实体转换为DTO。
     */
    private SafeResponsibilityPostDTO toDTO(SafeResponsibilityPost entity) {
        SafeResponsibilityPostDTO dto = new SafeResponsibilityPostDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    /**
     * 将岗位DTO转换为实体。
     */
    private SafeResponsibilityPost toEntity(SafeResponsibilityPostDTO dto) {
        SafeResponsibilityPost entity = new SafeResponsibilityPost();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
