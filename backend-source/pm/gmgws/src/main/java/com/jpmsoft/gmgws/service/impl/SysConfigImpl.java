package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.gmgws.mapper.SysConfigMapper;
import com.jpmsoft.gmgws.pojo.entity.SysConfig;
import com.jpmsoft.gmgws.pojo.vo.LogUserInfoVo;
import com.jpmsoft.gmgws.service.ISysConfigService;
import com.jpmsoft.gmgws.utils.RedisUtils;
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

    @Resource
    private RedisUtils redisUtils;

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

    @Override
    public String getConfigByCode(String code) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        LogUserInfoVo user = redisUtils.userInfo();
        if (user != null && user.isEnableTenant()) {
            wrapper.and(e -> e.eq("Con_Mold", 1).or().eq("Te_Id", user.getTeId()));
        }
        List<SysConfig> entities = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entities))
            return entities.get(0).getConValue();
        else return null;
    }

    @Override
    public InterfaceEncDecDTO getInterfaceEncDec() {
        String conf = getConfigByCode("interfaceEncDecConfig");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, InterfaceEncDecDTO.class);
        return new InterfaceEncDecDTO();
    }
}
