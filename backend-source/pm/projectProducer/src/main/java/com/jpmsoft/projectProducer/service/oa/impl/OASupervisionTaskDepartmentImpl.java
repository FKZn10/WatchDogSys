package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.oa.OASupervisionTaskDepartmentMapper;
import com.jpmsoft.projectProducer.pojo.entity.oa.OASupervisionTaskDepartment;
import com.jpmsoft.projectProducer.service.oa.IOaSupervisionTaskDepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OASupervisionTaskDepartmentImpl extends ServiceImpl<OASupervisionTaskDepartmentMapper, OASupervisionTaskDepartment> implements IOaSupervisionTaskDepartmentService {

    @Override
    public List<OASupervisionTaskDepartment> GetOASupervisionTaskDepartmentPojoList(String tid) {
        QueryWrapper<OASupervisionTaskDepartment> stdwrapper = new QueryWrapper<>();
        stdwrapper.eq("St_Id", tid);
        return baseMapper.selectList(stdwrapper);
    }
}
