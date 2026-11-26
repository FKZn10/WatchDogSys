package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.SysConfigBaseDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.ResMsg;
import com.jpmsoft.userProducer.mapper.SysConfigBaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysConfigBase;
import com.jpmsoft.userProducer.pojo.entity.SysConfigEntity;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.ISysConfigBaseService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.utils.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统配置表
 */
@Service
public class SysConfigBaseImpl extends ServiceImpl<SysConfigBaseMapper, SysConfigBase> implements ISysConfigBaseService {
    @Resource
    private ISysConfigEntityService sysConfigEntityService;

    /**
     * 新增或更新配置信息
     *
     * @param request 请求参数
     * @return 响应结果
     */
    @Override
    public BaseResponse createOrUpdateSysConfigBase(SysConfigBaseDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // code重复校验
        long count = count(Wrappers.<SysConfigBase>lambdaQuery()
                .eq(SysConfigBase::getConfCode, request.getConfCode())
                .ne(ValidateUtil.isNotEmpty(request.getId()), SysConfigBase::getId, request.getId())
        );
        if (count > 0) {
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        }
        SysConfigBase entity = new SysConfigBase();
        BeanUtils.copyProperties(request, entity);
        LogUserInfoVo userInfo = UserUtils.getUserInfo();
        if (ValidateUtil.isEmpty(entity.getId())) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateUid(userInfo.getId());
            entity.setCreateUname(userInfo.getName());
            entity.setCreateTime(new Date());
            this.save(entity);
        } else {
            entity.setUpdateUid(userInfo.getId());
            entity.setUpdateUname(userInfo.getName());
            entity.setUpdateTime(new Date());
            this.updateById(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 根据codes获取配置信息
     *
     * @param request 请求参数
     * @return 响应结果
     */
    @Override
    public BaseResponse getSysConfigBaseList(SysConfigBaseDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<SysConfigBase> page = new Page<>(request.getPage(), request.getSize());
        LambdaQueryWrapper<SysConfigBase> query = new LambdaQueryWrapper<>();
        query.eq(ValidateUtil.isNotEmpty(request.getId()), SysConfigBase::getId, request.getId());
        query.eq(ValidateUtil.isNotNull(request.getStatus()), SysConfigBase::getStatus, request.getStatus());
        query.eq(ValidateUtil.isNotNull(request.getCreateTime()), SysConfigBase::getCreateTime, request.getCreateTime());
        query.eq(ValidateUtil.isNotNull(request.getUpdateTime()), SysConfigBase::getUpdateTime, request.getUpdateTime());
        query.like(ValidateUtil.isNotEmpty(request.getCreateUname()), SysConfigBase::getCreateUname, request.getCreateUname());
        query.like(ValidateUtil.isNotEmpty(request.getUpdateUname()), SysConfigBase::getUpdateUname, request.getUpdateUname());
        query.like(ValidateUtil.isNotEmpty(request.getConfCode()), SysConfigBase::getConfCode, request.getConfCode());
        query.like(ValidateUtil.isNotEmpty(request.getConfType()), SysConfigBase::getConfType, request.getConfType());
        query.like(ValidateUtil.isNotEmpty(request.getConfCategory()), SysConfigBase::getConfCategory, request.getConfCategory());
        query.like(ValidateUtil.isNotEmpty(request.getConfContent()), SysConfigBase::getConfContent, request.getConfContent());
        query.orderByDesc(SysConfigBase::getCreateTime);
        Page<SysConfigBase> paged = this.page(page, query);
        return BaseResponse.ok(paged, Math.toIntExact(paged.getTotal()));
    }

    /**
     * 根据id获取配置信息
     *
     * @param id 配置信息id
     * @return 响应结果
     */
    @Override
    public BaseResponse getSysConfigBaseById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        SysConfigBase sysConfigBase = this.getById(id);
        return BaseResponse.ok(sysConfigBase);
    }

    /**
     * 根据codes获取配置信息
     *
     * @param codes 配置信息codes
     * @return 响应结果
     */
    @Override
    public BaseResponse getSysConfigBaseByCodes(String codes) {
        if (ValidateUtil.isEmpty(codes)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        List<String> codeList = Arrays.stream(codes.split(","))
                .map(String::trim)
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.collectingAndThen(
                        Collectors.toCollection(LinkedHashSet::new),
                        ArrayList::new
                ));

        if (ValidateUtil.isEmpty(codeList)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        List<SysConfigBase> sysConfigBaseList = this.list(Wrappers.<SysConfigBase>lambdaQuery()
                .in(SysConfigBase::getConfCode, codeList));

        Set<String> existingConfCodeSet = sysConfigBaseList.stream()
                .map(SysConfigBase::getConfCode)
                .collect(Collectors.toSet());

        List<String> oldConfigCodeList = codeList.stream()
                .filter(code -> !existingConfCodeSet.contains(code))
                .collect(Collectors.toList());

        if (ValidateUtil.isEmpty(oldConfigCodeList)) {
            return BaseResponse.ok(sysConfigBaseList);
        }

        List<SysConfigEntity> sysConfigList = sysConfigEntityService.list(Wrappers.<SysConfigEntity>lambdaQuery()
                .in(SysConfigEntity::getConCode, oldConfigCodeList));

        List<SysConfigBase> oldConfigList = sysConfigList.stream()
                .map(this::convertToSysConfigBase)
                .collect(Collectors.toList());

        sysConfigBaseList.addAll(oldConfigList);
        return BaseResponse.ok(sysConfigBaseList);
    }

    private SysConfigBase convertToSysConfigBase(SysConfigEntity sysConfig) {
        SysConfigBase base = new SysConfigBase();
        base.setId(sysConfig.getConId());
        base.setCreateUid(sysConfig.getConCreateuId());
        base.setCreateUname(sysConfig.getConCreateuName());
        base.setCreateTime(sysConfig.getConCreatetime());
        base.setConfType(sysConfig.getConType() == null ? null : sysConfig.getConType().toString());
        base.setConfCategory(sysConfig.getConCategory());
        base.setConfCode(sysConfig.getConCode());
        base.setConfContent(sysConfig.getConValue());
        base.setStatus(sysConfig.getConStatus());
        return base;
    }

    /**
     * 根据id删除配置信息
     *
     * @param id 配置信息id
     * @return 响应结果
     */
    @Override
    public BaseResponse deleteSysConfigBaseById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        boolean result = this.removeById(id);
        return BaseResponse.ok(result);
    }
}
