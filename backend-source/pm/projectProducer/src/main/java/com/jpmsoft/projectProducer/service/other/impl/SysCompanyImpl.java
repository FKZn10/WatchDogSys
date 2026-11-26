package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.other.SysCompanyMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.service.other.ISysCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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

    @Override
    public List<SysCompany> getCompanyByIds(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) return new ArrayList<>();
        return baseMapper.selectBatchIds(ids);
    }

    @Override
    public SysCompany getComPanyName(String cId) {
        LambdaQueryWrapper<SysCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysCompany::getcId,cId);
        return baseMapper.selectOne(queryWrapper);
    }


}
