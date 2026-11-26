package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.SysMessageShieldMapper;
import com.jpmsoft.userProducer.pojo.dto.SysMessageShieldDto;
import com.jpmsoft.userProducer.pojo.entity.SysMessageShield;
import com.jpmsoft.userProducer.pojo.vo.SysMessageShieldVo;
import com.jpmsoft.userProducer.service.ISysMessageShieldService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/15 9:40
 */
@Service
public class SysMessageShieldImpl extends ServiceImpl<SysMessageShieldMapper, SysMessageShield>
        implements ISysMessageShieldService {

    @Override
    public BaseResponse createOrUpdateSysMessageShield(SysMessageShieldDto request) {
        SysMessageShield entity = getSysMessageShieldBySmtIdAndUid(request.getSmtId(), request.getUId());
        if (entity == null) {
            entity = new SysMessageShield();
            BeanUtils.copyProperties(request, entity);
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
        } else {
            if (request.getMsSendSys() != null)
                entity.setMsSendSys(request.getMsSendSys());
            if (request.getMsSendSms() != null)
                entity.setMsSendSms(request.getMsSendSms());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSysMessageShieldList(SysMessageShieldDto request) {
        QueryMap<SysMessageShieldDto> query = new QueryMap<>(request);
        query.put("msSendSys").put("msSendSms").put("createUname").put("uName")
                .put("smtName").put("smtCode").put("uName").put("smtId").put("uId");
        IPage<SysMessageShieldVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysMessageShieldVo> datas = baseMapper.getSysMessageShieldList(page, query.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public List<SysMessageShieldDto> getSysMessageShieldBySmtId(String smtId) {
        QueryWrapper<SysMessageShield> wrapper = new QueryWrapper<>();
        wrapper.eq("Smt_Id", smtId);
        List<SysMessageShield> datas=baseMapper.selectList(wrapper);
        List<SysMessageShieldDto> ress=new ArrayList<>();
        for (SysMessageShield data : datas) {
            SysMessageShieldDto res=new SysMessageShieldDto();
            BeanUtils.copyProperties(data,res);
            ress.add(res);
        }
        return ress;
    }

    private SysMessageShield getSysMessageShieldBySmtIdAndUid(String smtId, String uId) {
        QueryWrapper<SysMessageShield> wrapper = new QueryWrapper<>();
        wrapper.eq("Smt_Id", smtId);
        wrapper.eq("U_Id", uId);
        wrapper.orderByDesc("Create_Time");
        List<SysMessageShield> datas = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(datas)) return datas.get(0);
        return null;
    }
}
