package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.PreProjectMapper;
import com.jpmsoft.jpbpm.pojo.entity.PreProject;
import com.jpmsoft.jpbpm.service.PreProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreProjectImpl extends ServiceImpl<PreProjectMapper, PreProject> implements PreProjectService {
    public List<PreProject> getPreProjectList(List<String> pIds,String customerId){
        QueryWrapper<PreProject> projectQueryWrapper = new QueryWrapper<>();
        projectQueryWrapper.eq("P_Status","1");
        projectQueryWrapper.eq("State","7");
        projectQueryWrapper.in("P_ID",pIds);
        projectQueryWrapper.orderByAsc("P_Name");
        return baseMapper.selectList(projectQueryWrapper);
    }

    @Override
    public String getDidByPid(String pId) {
        return baseMapper.getDidByPid(pId);
    }
}
