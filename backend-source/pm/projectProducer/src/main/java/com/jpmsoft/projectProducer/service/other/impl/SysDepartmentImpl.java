package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.other.SysDepartmentMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDepartment;
import com.jpmsoft.projectProducer.service.other.ISysDepartmentService;
import org.springframework.stereotype.Service;

@Service
public class SysDepartmentImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements ISysDepartmentService {
}
