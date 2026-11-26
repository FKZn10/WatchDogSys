package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SysCompanyMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysCompany;
import com.jpmsoft.jpbpm.service.ISysCompanyService;
import org.springframework.stereotype.Service;

@Service
public class SysCompanyImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements ISysCompanyService {
}
