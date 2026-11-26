package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.PreProjectMapper;
import com.jpmsoft.userProducer.pojo.entity.PreProject;
import com.jpmsoft.userProducer.service.IPreProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class PreProjectImpl extends ServiceImpl<PreProjectMapper, PreProject> implements IPreProjectService {

    @Override
    public List<PreProject> getProjectList(List<String> idlist) {
        if (CollectionUtils.isEmpty(idlist)) return null;
        QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
        wrapper.in("P_Id", idlist);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public String getDidByPid(String pId) {
        return baseMapper.getDidByPid(pId);
    }

}
