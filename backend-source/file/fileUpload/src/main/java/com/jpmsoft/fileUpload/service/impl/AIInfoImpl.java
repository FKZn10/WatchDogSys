package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.fileUpload.mapper.AIInfoMapper;
import com.jpmsoft.fileUpload.pojo.entity.AIInfo;
import com.jpmsoft.fileUpload.service.IAIInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class AIInfoImpl extends ServiceImpl<AIInfoMapper, AIInfo> implements IAIInfoService {
    @Override
    public AIInfo selectByType(String type) {
        LambdaQueryWrapper<AIInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AIInfo::getAiType, type);
        wrapper.eq(AIInfo::getStatus, 1);
        List<AIInfo> list = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(list))
            return list.stream().findFirst().get();
        return null;
    }
}
