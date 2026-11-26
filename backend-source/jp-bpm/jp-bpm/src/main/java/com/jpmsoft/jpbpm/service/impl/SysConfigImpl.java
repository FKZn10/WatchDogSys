package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.SysConfigMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysConfig;
import com.jpmsoft.jpbpm.service.ISysConfigService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/4/3 11:00
 */
@Service
public class SysConfigImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {



    @Override
    public String getSysConfigByCode(String code) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        //系统级默认为仅只有一个配置存在
        //wrapper.eq("Con_Mold", 1);
        List<SysConfig> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities)) return entities.get(0).getConValue();
        return null;
    }

}
