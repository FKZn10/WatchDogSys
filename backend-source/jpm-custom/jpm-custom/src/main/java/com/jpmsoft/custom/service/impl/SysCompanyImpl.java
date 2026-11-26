package com.jpmsoft.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.custom.mapper.SysCompanyMapper;
import com.jpmsoft.custom.pojo.entity.SysCompany;
import com.jpmsoft.custom.service.ISysCompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysCompanyImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements ISysCompanyService {

    @Override
    public Boolean havingCreditCode(String creditCode) {
        QueryWrapper<SysCompany> wrapper = new QueryWrapper<>();
        wrapper.eq("c_Creditcode", creditCode);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        return false;
    }

    @Override
    public SysCompany getByCreditCode(String creditCode) {
        QueryWrapper<SysCompany> wrapper = new QueryWrapper<>();
        wrapper.eq("c_Creditcode", creditCode);
        List<SysCompany> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0)
            return datas.stream().findFirst().get();
        return null;
    }


}
