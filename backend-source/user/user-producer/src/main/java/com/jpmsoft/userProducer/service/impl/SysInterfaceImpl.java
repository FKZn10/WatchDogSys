package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysInterfaceMapper;
import com.jpmsoft.userProducer.pojo.dto.SysInterfaceDTO;
import com.jpmsoft.userProducer.pojo.entity.SysInterface;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.ISysInterfaceRequestService;
import com.jpmsoft.userProducer.service.ISysInterfaceResponseService;
import com.jpmsoft.userProducer.service.ISysInterfaceService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/11/19 10:48
 */
@Service
public class SysInterfaceImpl extends ServiceImpl<SysInterfaceMapper, SysInterface> implements ISysInterfaceService {
    @Resource
    private ISysInterfaceResponseService sysInterfaceResponseService;
    @Resource
    private ISysInterfaceRequestService sysInterfaceRequestService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse createOrUpdate(SysInterfaceDTO request,String token) {
        SysInterface entity=new SysInterface();
        BeanUtils.copyProperties(request,entity);
        LogUserInfoVo user=redisUtils.getUserInfoByToken(token);
        //斜杠统一 替换掉所有双斜杠
        if (ValidateUtil.isNotNull(entity.getIfUrl()))
            entity.setIfUrl(xgFiter(entity.getIfUrl().replaceAll("\\\\","/")));
        if (ValidateUtil.isNotEmpty(request.getId())) {
            entity.setUpdateTime(new Date());
            entity.setUpdateUid(user.getId());
            entity.setUpdateUname(user.getName());
            updateById(entity);
        }
        else {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            entity.setCreateUid(user.getId());
            entity.setCreateUname(user.getName());
            save(entity);
        }
        sysInterfaceRequestService.createOrUpdate(request.getRequests(),entity.getId());
        sysInterfaceResponseService.createOrUpdate(request.getResponses(),entity.getId());
        refreshRedis();
        return BaseResponse.ok(entity.getId());
    }

    private String xgFiter(String url)
    {
        url=url.replaceAll("//","/");
        if (url.contains("//")) xgFiter(url);
        return url;
    }

    @Override
    public BaseResponse getSysInterfaceList(SysInterfaceDTO request) {
        QueryWrapper<SysInterface> queryWrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getIfPower()))
            queryWrapper.eq("If_Power", request.getIfPower());
        if (ValidateUtil.isNotEmpty(request.getIfStatus()))
            queryWrapper.eq("If_Status", request.getIfStatus());
        if (ValidateUtil.isNotEmpty(request.getIfName()))
            queryWrapper.like("If_Name", request.getIfName());
        if (ValidateUtil.isNotEmpty(request.getIfType()))
            queryWrapper.like("If_Type", request.getIfType());
        if (ValidateUtil.isNotEmpty(request.getIfUrl()))
            queryWrapper.like("If_Url", request.getIfUrl());
        if (ValidateUtil.isNotEmpty(request.getCreateUname()))
            queryWrapper.like("create_uname", request.getCreateUname());
        if (ValidateUtil.isNotEmpty(request.getUpdateUname()))
            queryWrapper.like("update_uname", request.getUpdateUname());

        if (!CheckNullUtil.checkString(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                queryWrapper.orderByAsc(request.getSortFiled());
            else
                queryWrapper.orderByDesc(request.getSortFiled());
        } else
            queryWrapper.orderByDesc("create_time");
        IPage<SysInterface> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), queryWrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }


    @Override
    public BaseResponse getSysInterfaceById(String id) {
        SysInterface entity=baseMapper.selectById(id);
        SysInterfaceDTO d=new SysInterfaceDTO();
        BeanUtils.copyProperties(entity,d);
        d.setRequests(sysInterfaceRequestService.selectById(id));
        d.setResponses(sysInterfaceResponseService.selectById(id));
        return BaseResponse.ok(d);
    }

    @Override
    public BaseResponse delSysInterfaceById(String id) {
        sysInterfaceResponseService.deleteByIfId(id);
        sysInterfaceRequestService.deleteByIfId(id);
        baseMapper.deleteById(id);
        refreshRedis();
        return BaseResponse.ok();
    }

    @Override
    public  List<SysInterface> refreshRedis() {
        QueryWrapper<SysInterface> wrapper=new QueryWrapper<>();
        wrapper.eq("if_status","01");
        List<SysInterface> data=list(wrapper);
        redisUtils.set("sys_interface_list", JacksonUtils.toJSONString(data));
        return data;
    }

    @Override
    public List<SysInterface> getAll() {
        Object json=redisUtils.get("sys_interface_list");
        if (ValidateUtil.isNotNull(json))
        {
            return JacksonUtils.parseArray(json.toString(),SysInterface.class);
        }else {
            return refreshRedis();
        }
    }

}
