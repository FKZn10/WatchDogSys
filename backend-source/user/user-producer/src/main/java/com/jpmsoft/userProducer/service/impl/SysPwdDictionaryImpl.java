package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysPwdDictionaryMapper;
import com.jpmsoft.userProducer.pojo.dto.SysPwdDictionaryDTO;
import com.jpmsoft.userProducer.pojo.entity.SysPwdDictionary;
import com.jpmsoft.userProducer.service.ISysPwdDictionaryService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/26 11:47
 */
@Service
public class SysPwdDictionaryImpl extends ServiceImpl<SysPwdDictionaryMapper, SysPwdDictionary> implements ISysPwdDictionaryService {


    @Override
    public BaseResponse createOrUpdatePwdDictionary(SysPwdDictionaryDTO request) {
        SysPwdDictionary entity = new SysPwdDictionary();
        BeanUtils.copyProperties(request, entity);
        if (!ValidateUtil.isEmpty(request.getId()))
            baseMapper.updateById(entity);
        else {
            if (pwdExist(request.getPdPwd())) return BaseResponse.fail(BaseMsg.infoIsExist);
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getPwdDictionaryList(SysPwdDictionaryDTO request) {
        QueryWrapper<SysPwdDictionary> queryWrapper = new QueryWrapper<>();
        if (!ValidateUtil.isEmpty(request.getCreateUname()))
            queryWrapper.like("Create_Uname", request.getCreateUname());
        if (!ValidateUtil.isEmpty(request.getPdPwd()))
            queryWrapper.like("Pd_Pwd", request.getPdPwd());
        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC")) {
                queryWrapper.orderByAsc(request.getSortFiled());
            } else {
                queryWrapper.orderByDesc(request.getSortFiled());
            }
        } else queryWrapper.orderByDesc("Create_Time");
        IPage<SysPwdDictionary> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long longTotal = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), longTotal.intValue());
    }

    @Override
    public BaseResponse deletePwdDictionaryById(String id) {
        SysPwdDictionary entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public boolean pwdExist(String pwd) {
        QueryWrapper<SysPwdDictionary> wrapper = new QueryWrapper<>();
        wrapper.eq("Pd_Pwd", pwd);
        return baseMapper.exists(wrapper);
    }
}
