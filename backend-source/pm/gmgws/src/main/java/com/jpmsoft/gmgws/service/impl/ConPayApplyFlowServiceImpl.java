package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.gmgws.mapper.ConPayApplyFlowMapper;
import com.jpmsoft.gmgws.pojo.entity.ConPayApplyFlow;
import com.jpmsoft.gmgws.service.ConPayApplyFlowService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ConPayApplyFlowServiceImpl extends ServiceImpl<ConPayApplyFlowMapper, ConPayApplyFlow> implements ConPayApplyFlowService {


    @Override
    public List<ConPayApplyFlow> getProjectDataList(List<String> collect) {
        LambdaQueryWrapper<ConPayApplyFlow> queryWrapper  = new LambdaQueryWrapper<>();
        queryWrapper.in(ConPayApplyFlow::getConId,collect);

        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<ConPayApplyFlow> getProjectData(String conId) {
        LambdaQueryWrapper<ConPayApplyFlow> queryWrapper  = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPayApplyFlow::getConId,conId).eq(ConPayApplyFlow::getState,7);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<ConPayApplyFlow> getProjectPidList(List<String> collect) {
        LambdaQueryWrapper<ConPayApplyFlow> queryWrapper  = new LambdaQueryWrapper<>();
        queryWrapper.in(ConPayApplyFlow::getPId,collect).eq(ConPayApplyFlow::getState,7);

        return baseMapper.selectList(queryWrapper);
    }
}
