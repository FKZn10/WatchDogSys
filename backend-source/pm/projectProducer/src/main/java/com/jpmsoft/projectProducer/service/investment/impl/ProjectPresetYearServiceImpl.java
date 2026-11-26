package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.ProjectPresetYearRequest;
import com.jpmsoft.common.model.response.InvestmentControl.ProjectPresetYearResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.ProjectPresetYearMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.ProjectPresetYear;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.service.investment.ProjectPresetYearService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectPresetYearServiceImpl extends ServiceImpl<ProjectPresetYearMapper, ProjectPresetYear> implements ProjectPresetYearService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ISysUserService iSysUserService;

    @Override
    public BaseResponse saveOrUpdatePresetYear(ProjectPresetYearRequest request, String token) {
         String uid = redisUtils.getUid(token);
         if(ValidateUtil.isEmpty(uid)) return BaseResponse.fail("令牌已失效,请重新登录");
        if(ValidateUtil.isEmpty(request)) return BaseResponse.fail("缺少必填参数");
        if(ValidateUtil.isEmpty(request.getpId())) return BaseResponse.fail("项目不能为空");
        if(ValidateUtil.isEmpty(request.getPyEclareYear())) return BaseResponse.fail("年份不能为空");
         SysUser user = iSysUserService.getById(uid);
        //保存
        if(ValidateUtil.isEmpty(request.getId())){
            LambdaQueryWrapper<ProjectPresetYear> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(ProjectPresetYear::getPId,request.getpId()).eq(ProjectPresetYear::getPyEclareYear,request.getPyEclareYear());
             Long aLong = baseMapper.selectCount(queryWrapper1);
             if(aLong >0){
                 return BaseResponse.fail("该项目在该年份已预设");
             }else {
                 ProjectPresetYear presetYear = new ProjectPresetYear();
                 BeanUtils.copyProperties(request,presetYear);
                 presetYear.setId(GenerateUUID.getUUID());
                 presetYear.setCreateTime(new Date());
                 presetYear.setCreateUname(user.getUName());
                 presetYear.setCreateUid(user.getUId());
                 baseMapper.insert(presetYear);
             }
        }else{
            ProjectPresetYear preset = new ProjectPresetYear();
            BeanUtils.copyProperties(request,preset);
            baseMapper.updateById(preset);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getProjectPresetYearId(String id) {
        if(ValidateUtil.isEmpty(id)) return BaseResponse.fail("缺少必填参数");
        ProjectPresetYearResponse data = baseMapper.getProjectPresetYearId(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getProjectPresetYearList(ProjectPresetYearRequest request) {
        IPage<ProjectPresetYear> page = new Page<>(request.getPage(), request.getSize());
        IPage<ProjectPresetYearResponse> pages = baseMapper.getProjectPresetYearList(page, request);
        return BaseResponse.ok(pages.getRecords(),(int)pages.getTotal());
    }



}
