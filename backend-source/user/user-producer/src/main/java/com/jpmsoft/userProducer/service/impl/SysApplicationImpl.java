package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysApplicationMapper;
import com.jpmsoft.userProducer.pojo.dto.SysApplicationDTO;
import com.jpmsoft.userProducer.pojo.dto.SysApplicationPowerDTO;
import com.jpmsoft.userProducer.pojo.dto.SysApplicationPriceDTO;
import com.jpmsoft.userProducer.pojo.entity.SysApplication;
import com.jpmsoft.userProducer.pojo.vo.SysApplicationVO;
import com.jpmsoft.userProducer.service.ISysApplicationPowerService;
import com.jpmsoft.userProducer.service.ISysApplicationPriceService;
import com.jpmsoft.userProducer.service.ISysApplicationService;
import com.jpmsoft.userProducer.service.ISysSequenceSetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/6 17:55
 */
@Service
public class SysApplicationImpl extends ServiceImpl<SysApplicationMapper, SysApplication> implements ISysApplicationService {
    @Resource
    private ISysSequenceSetService sysSequenceSetService;

    @Resource
    private ISysApplicationPowerService sysApplicationPowerService;

    @Resource
    private ISysApplicationPriceService sysApplicationPriceService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSysApplication(SysApplicationDTO request) {
        SysApplication entity = new SysApplication();
        BeanUtils.copyProperties(request, entity);
        String codeRule = null;
        if (ValidateUtil.isNotEmpty(request.getAppId())) {
            SysApplication tenant = baseMapper.selectById(request.getAppId());
            if (tenant == null) return BaseResponse.fail(BaseMsg.infoNotExist);
            baseMapper.updateById(entity);
        } else {
            if (sysSequenceSetService.sequenceRestEnable("SysApplication",request.getTeId())) {
                entity.setAppCode(sysSequenceSetService.generateCodeByTCode("SysApplication", request,request.getTeId()));
                if (ValidateUtil.isEmpty(entity.getAppCode()))
                    return BaseResponse.fail(BaseMsg.codeGenerateFail);
                codeRule = "SysApplication";
            }
            entity.setAppId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
        }
        sysApplicationPowerService.createOrUpdateSysAppPower(request.getSysApplicationPowers(), entity.getAppId());
        sysApplicationPriceService.createOrUpdateSysAppPrice(request.getSysApplicationPrices(), entity.getAppId());
        if (ValidateUtil.isNotEmpty(codeRule)) sysSequenceSetService.refershAutoNo(codeRule, request,request.getTeId());
        return BaseResponse.ok(entity.getAppId());
    }

    @Override
    public BaseResponse getSysApplicationList(SysApplicationDTO request) {
        QueryMap<SysApplicationDTO> query = new QueryMap<>(request);
        query.put("appName").put("appCode").put("appType").put("appStatus")
                .put("appRemark").put("createUname").put("state");
        IPage<SysApplicationVO> page = new Page<>(request.getPage(), request.getSize());
        List<SysApplicationVO> datas = baseMapper.getSysApplicationList(page, query.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSysApplicationById(String id) {
        SysApplication tenant = baseMapper.selectById(id);
        if (tenant == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        SysApplicationVO vo = new SysApplicationVO();
        BeanUtils.copyProperties(tenant, vo);
        List<SysApplicationPowerDTO> power = sysApplicationPowerService.getSysAppPowerByAppId(id);
        vo.setSysApplicationPowers(power);
        List<SysApplicationPriceDTO> price = sysApplicationPriceService.getSysAppPriceByAppId(id);
        vo.setSysApplicationPrices(price);
        return BaseResponse.ok(vo);
    }
}
