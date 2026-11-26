package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeTempRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineNodeDatasourceResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineNodeTempResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineNodeTempMapper;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineProjectTempMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineNodeDatasource;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineNodeTemp;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineNodeDatasourceService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineNodeTempService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineSystemLibService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OutlineNodeTempImpl extends ServiceImpl<OutlineNodeTempMapper, OutlineNodeTemp> implements IOutlineNodeTempService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IOutlineSystemLibService outlineSystemLibService;
    @Resource
    private OutlineProjectTempMapper outlineProjectTempMapper;
    @Resource
    private IOutlineNodeDatasourceService outlineNodeDatasourceService;

    @Override
    public BaseResponse createOutlineNodeTemp(OutlineNodeTempRequest request, String token) {
        if (tCodeExist(request.getTCode(), request.getOslId())) return BaseResponse.fail(ResMsg.recordExist);
        OutlineNodeTemp entity = new OutlineNodeTemp();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        entity.setCreateTime(new Date());
        entity.setCreateUname(user.getName());
        entity.setCreateUid(user.getId());
        Integer sort = baseMapper.selectSort(request.getOslId());
        entity.setOdtSort(sort == null ? 0 : sort + 1);
        baseMapper.insert(entity);
        outlineSystemLibService.refreshSystemManageLastUpdateTime(request.getOslId(), user);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getOutlineNodeTempList(OutlineNodeTempRequest request) {
        Page<OutlineNodeTemp> page = new Page<>(request.getPage(), request.getSize());
        List<OutlineNodeTempResponse> datas = baseMapper.getOutlineNodeTempList(page, request);
        return BaseResponse.ok(datas, Math.toIntExact(page.getTotal()));
    }

    @Override
    public Boolean tCodeExist(String tCode, String oslId) {
        LambdaQueryWrapper<OutlineNodeTemp> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OutlineNodeTemp::getTCode, tCode).eq(OutlineNodeTemp::getOslId, oslId);
        return baseMapper.exists(wrapper);
    }

    @Override
    public void deleteByOslId(String oslId) {
        baseMapper.deleteByOslId(oslId);
    }

    @Override
    public void deleteByOsmId(String osmId) {
        baseMapper.deleteByOsmId(osmId);
    }

    /**
     * 体系模板-目录节点ID获取体系模板-关联业务功能
     *
     * @param oslIds
     * @return
     */
    @Override
    public List<OutlineNodeTempResponse> getAllOutlineNodeTempByOslIds(List<String> oslIds) {
        if (ValidateUtil.isNotEmpty(oslIds)) {
            List<OutlineNodeTemp> list = this.list(Wrappers.<OutlineNodeTemp>lambdaQuery().in(OutlineNodeTemp::getOslId, oslIds));
            if (ValidateUtil.isNotEmpty(list)) {
                return list.stream().map(nodeTemp -> {
                    OutlineNodeTempResponse response = new OutlineNodeTempResponse();
                    BeanUtils.copyProperties(nodeTemp, response);
                    return response;
                }).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public BaseResponse updateOutlineNodeTemp(OutlineNodeTempRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (request.getId() == null || !tCodeExist(request.getTCode(), request.getOslId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineNodeTemp entity = new OutlineNodeTemp();
        BeanUtils.copyProperties(request, entity);
        Integer sort = baseMapper.selectSort(request.getOslId());
        entity.setOdtSort(sort == null ? 0 : sort + 1);
        baseMapper.updateById(entity);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineSystemLibService.refreshSystemManageLastUpdateTime(request.getOslId(), user);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteOutlineNodeTempById(String id, String token) {
        OutlineNodeTemp temp = this.getById(id);
        if (ValidateUtil.isNull(temp)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        this.removeById(id);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineSystemLibService.refreshSystemManageLastUpdateTime(temp.getOslId(), user);
        // 处理关联的数据源
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                // 删除之后现有的文档模板
                List<OutlineNodeTemp> list = list(Wrappers.<OutlineNodeTemp>lambdaQuery()
                        .eq(OutlineNodeTemp::getOslId, temp.getOslId())
                        .select(OutlineNodeTemp::getTCode)
                );
                if (ValidateUtil.isNotEmpty(list)) {
                    List<String> tCodes = list.stream().map(OutlineNodeTemp::getTCode).collect(Collectors.toList());
                    // 查询数据源的odId
                    List<String> odIds = outlineProjectTempMapper.getOdIdsByTCodes(tCodes);
                    // 现有的数据源
                    List<OutlineNodeDatasourceResponse> datasource = outlineNodeDatasourceService.getAllOutlineNodeDatasourceByOslIds(Collections.singletonList(temp.getOslId()));
                    if (ValidateUtil.isNotEmpty(datasource)) {
                        List<String> oldOdIds = datasource.stream().map(OutlineNodeDatasourceResponse::getOdId).collect(Collectors.toList());
                        // 将oldOdIds中排除与odIds相同的
                        oldOdIds.removeAll(odIds);
                        if (ValidateUtil.isNotEmpty(oldOdIds)) {
                            outlineNodeDatasourceService.remove(Wrappers.<OutlineNodeDatasource>lambdaQuery().in(OutlineNodeDatasource::getOdId, oldOdIds));
                        }
                    }
                } else {
                    // 如果没有文档模板，直接清空与当前oslId相关的数据源
                    outlineNodeDatasourceService.remove(Wrappers.<OutlineNodeDatasource>lambdaQuery().eq(OutlineNodeDatasource::getOslId, temp.getOslId()));
                }
            }
        });
        return BaseResponse.ok();
    }

    /**
     * 体系模板-大纲体系ID获取体系模板-目录节点
     *
     * @param osmId
     * @return
     */
    @Override
    public List<OutlineNodeTempResponse> getAllOutlineNodeTempByOsmId(String osmId) {
        if (ValidateUtil.isEmpty(osmId)) {
            return Collections.emptyList();
        }
        List<OutlineNodeTempResponse> responses = baseMapper.getAllOutlineNodeTempByOsmId(osmId);
        return ValidateUtil.isNotEmpty(responses) ? responses : Collections.emptyList();
    }

    /**
     * 根据oslId列表获取对应的oslid和tcode
     *
     * @param oslIds
     * @return
     */
    @Override
    public Map<String, Set<String>> getTCodesGroupedByOslIds(List<String> oslIds) {
        if (ValidateUtil.isNotEmpty(oslIds)) {
            List<OutlineNodeTemp> list = this.list(Wrappers.<OutlineNodeTemp>lambdaQuery()
                    .in(OutlineNodeTemp::getOslId, oslIds)
                    .select(OutlineNodeTemp::getOslId, OutlineNodeTemp::getTCode)
            );
            if (ValidateUtil.isNotEmpty(list)) {
                return list.stream()
                        .collect(Collectors.groupingBy(OutlineNodeTemp::getOslId, Collectors.mapping(OutlineNodeTemp::getTCode, Collectors.toSet())));
            }
        }
        return Collections.emptyMap();
    }
}
