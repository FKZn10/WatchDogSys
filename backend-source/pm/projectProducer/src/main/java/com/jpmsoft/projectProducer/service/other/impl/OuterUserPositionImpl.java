package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.other.OuterUserPositionMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.OuterUserPosition;
import com.jpmsoft.projectProducer.service.other.IOuterUserPositionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OuterUserPositionImpl extends ServiceImpl<OuterUserPositionMapper, OuterUserPosition>
        implements IOuterUserPositionService {

    @Override
    public List<OuterUserPosition> selectByOuterId(String ourId) {
        LambdaQueryWrapper<OuterUserPosition> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OuterUserPosition::getOurId, ourId);
        return baseMapper.selectList(wrapper);
    }
}
