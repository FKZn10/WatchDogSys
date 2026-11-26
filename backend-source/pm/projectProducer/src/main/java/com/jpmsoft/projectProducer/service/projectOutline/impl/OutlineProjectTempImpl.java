package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectTempRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjecDatasourceResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectTempResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineProjectTempMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjecDatasource;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectTemp;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjecDatasourceService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectLibService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectTempService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tianhelin
 * @date 2025/6/17
 */
@Service
public class OutlineProjectTempImpl extends ServiceImpl<OutlineProjectTempMapper, OutlineProjectTemp> implements IOutlineProjectTempService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IOutlineProjectLibService outlineProjectLibService;
    @Resource
    private IOutlineProjecDatasourceService outlineProjecDatasourceService;

    /**
     * 新增项目管理大纲体系-关联业务功能
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse createOutlineProjectTemp(OutlineProjectTempRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (tCodeExist(request.getTCode(), request.getOplId())) {
            return BaseResponse.fail(ResMsg.recordExist);
        }
        OutlineProjectTemp entity = new OutlineProjectTemp();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        entity.setCreateTime(new Date());
        entity.setCreateUname(user.getName());
        entity.setCreateUid(user.getId());
        Integer sort = baseMapper.selectSort(request.getOplId());
        entity.setOpdSort(sort == null ? 0 : sort + 1);
        baseMapper.insert(entity);
        outlineProjectLibService.refreshProjectManageLastUpdateTime(request.getOplId(), user);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 删除项目管理大纲体系-关联业务功能
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteOutlineProjectTempById(String id, String token) {
        OutlineProjectTemp projectTemp = this.getById(id);
        if (ValidateUtil.isNull(projectTemp)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        this.removeById(id);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineProjectLibService.refreshProjectManageLastUpdateTime(projectTemp.getOplId(), user);
        // 处理关联的数据源
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 删除之后现有的文档模板
                List<OutlineProjectTemp> list = list(Wrappers.<OutlineProjectTemp>lambdaQuery()
                        .eq(OutlineProjectTemp::getOplId, projectTemp.getOplId())
                        .select(OutlineProjectTemp::getTCode)
                );
                if (ValidateUtil.isNotEmpty(list)) {
                    List<String> tCodes = list.stream().map(OutlineProjectTemp::getTCode).collect(Collectors.toList());
                    // 查询数据源的odId
                    List<String> odIds = baseMapper.getOdIdsByTCodes(tCodes);
                    // 现有的数据源
                    List<OutlineProjecDatasourceResponse> datasource = outlineProjecDatasourceService.getOutlineProjecDatasourceByOplIds(Collections.singletonList(projectTemp.getOplId()));
                    if (ValidateUtil.isNotEmpty(datasource)) {
                        List<String> oldOdIds = datasource.stream().map(OutlineProjecDatasourceResponse::getOdId).collect(Collectors.toList());
                        // 将oldOdIds中排除与odIds相同的
                        oldOdIds.removeAll(odIds);
                        if (ValidateUtil.isNotEmpty(oldOdIds)) {
                            outlineProjecDatasourceService.remove(Wrappers.<OutlineProjecDatasource>lambdaQuery().in(OutlineProjecDatasource::getOdId, oldOdIds));
                        }
                    }
                } else {
                    // 如果没有文档模板，直接清空与当前oplId相关的数据源
                    outlineProjecDatasourceService.remove(Wrappers.<OutlineProjecDatasource>lambdaQuery().eq(OutlineProjecDatasource::getOplId, projectTemp.getOplId()));
                }
            }
        });
        return BaseResponse.ok();
    }

    /**
     * 根据tCode和oplId判断是否存在
     *
     * @param tCode
     * @param oplId
     * @return
     */
    @Override
    public Boolean tCodeExist(String tCode, String oplId) {
        return baseMapper.exists(Wrappers.<OutlineProjectTemp>lambdaQuery()
                .eq(OutlineProjectTemp::getTCode, tCode)
                .eq(OutlineProjectTemp::getOplId, oplId)
        );
    }

    /**
     * 修改项目管理大纲体系-关联业务功能
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse updateOutlineProjectTemp(OutlineProjectTempRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (request.getId() == null || !tCodeExist(request.getTCode(), request.getOplId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineProjectTemp entity = new OutlineProjectTemp();
        BeanUtils.copyProperties(request, entity);
        Integer sort = baseMapper.selectSort(request.getOplId());
        entity.setOpdSort(sort == null ? 0 : sort + 1);
        baseMapper.updateById(entity);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineProjectLibService.refreshProjectManageLastUpdateTime(request.getOplId(), user);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 查询项目大类
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectTempById(String id) {
        if (ValidateUtil.isNull(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectTemp entity = this.getById(id);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineProjectTempResponse response = new OutlineProjectTempResponse();
        BeanUtils.copyProperties(entity, response);
        return BaseResponse.ok(response);
    }

    /**
     * 分页查询项目大类
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getOutlineProjectTempPage(OutlineProjectTempRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<OutlineProjectTemp> page = new Page<>(request.getPage(), request.getSize());
        List<OutlineProjectTempResponse> data = baseMapper.getOutlineProjectTempPage(page, request);
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }

    /**
     * 根据oplIds查询项目大类
     *
     * @param oplIds
     * @return
     */
    @Override
    public List<OutlineProjectTempResponse> getOutlineProjectTempByOplIds(List<String> oplIds) {
        if (ValidateUtil.isNotEmpty(oplIds)) {
            List<OutlineProjectTempResponse> data = baseMapper.getOutlineProjectTempByOplIds(oplIds);
            if (ValidateUtil.isNotEmpty(data)) {
                return data;
            }
        }
        return Collections.emptyList();
    }
}
