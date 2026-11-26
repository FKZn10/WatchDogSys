package com.jpmsoft.gmgws.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.gmgws.mapper.SysCompanyMapper;
import com.jpmsoft.gmgws.pojo.entity.SysCompany;
import com.jpmsoft.gmgws.service.ISysCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysCompanyImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements ISysCompanyService {


    @Override
    public List<SysCompany> getComPany() {
        return baseMapper.selectList(null);
    }
}
