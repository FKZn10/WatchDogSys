package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.gmgws.mapper.PreProjectMapper;
import com.jpmsoft.gmgws.pojo.entity.PreProject;
import com.jpmsoft.gmgws.service.PreProjectService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PreProjectServiceImpl  extends ServiceImpl<PreProjectMapper, PreProject> implements PreProjectService {


    @Override
    public List<PreProject>  getProjectMame(List<String> name) {
        LambdaQueryWrapper<PreProject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(PreProject::getPName,name);
        return   baseMapper.selectList(queryWrapper);

    }
}
