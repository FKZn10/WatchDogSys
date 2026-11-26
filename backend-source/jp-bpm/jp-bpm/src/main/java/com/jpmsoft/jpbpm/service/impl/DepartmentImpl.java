package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.DepartmentMapper;
import com.jpmsoft.jpbpm.pojo.entity.Department;
import com.jpmsoft.jpbpm.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class DepartmentImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {


    public List<Department>   getDepartmentAll(String customerId){
        QueryWrapper<Department> Wrapper = new QueryWrapper<>();
        if(!"All".equals(customerId) ){
            Wrapper.eq("Te_Id", customerId);
        }
        return  baseMapper.selectList(Wrapper);
    }


}
