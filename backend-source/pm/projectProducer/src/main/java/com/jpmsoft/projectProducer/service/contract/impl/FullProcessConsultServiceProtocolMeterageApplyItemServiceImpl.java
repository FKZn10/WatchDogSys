package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.contract.FullProcessConsultServiceProtocolMeterageApplyItemMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.FullProcessConsultServiceProtocolMeterageApplyItem;
import com.jpmsoft.projectProducer.service.contract.IFullProcessConsultServiceProtocolMeterageApplyItemService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FullProcessConsultServiceProtocolMeterageApplyItemServiceImpl extends ServiceImpl<FullProcessConsultServiceProtocolMeterageApplyItemMapper, FullProcessConsultServiceProtocolMeterageApplyItem> implements IFullProcessConsultServiceProtocolMeterageApplyItemService {
    @Override
    public List<FullProcessConsultServiceProtocolMeterageApplyItem> getAppIdList(String id) {
        LambdaQueryWrapper<FullProcessConsultServiceProtocolMeterageApplyItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FullProcessConsultServiceProtocolMeterageApplyItem::getFullProcessConsultServiceProtocolMeterageApplyId,id);
//        queryWrapper.orderByDesc()

        return baseMapper.selectList(queryWrapper);
    }
}
