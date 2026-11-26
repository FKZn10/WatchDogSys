package com.jpmsoft.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.custom.mapper.SysConfigMapper;
import com.jpmsoft.custom.pojo.entity.SysConfig;
import com.jpmsoft.custom.service.ISysConfigService;
import org.springframework.stereotype.Service;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/28 11:35
 */
@Service
public class SysConfigImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Override
    public String getConfigByCode(String code) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        SysConfig entity = baseMapper.selectOne(wrapper);
        return entity == null ? null : entity.getConValue();
    }

    @Override
    public boolean endProcessGenerateCode() {
        String value = getConfigByCode("endProcessGenerateCode");
        if (ValidateUtil.isNotEmpty(value) && value.equals("1")) return true;
        return false;
    }
}
