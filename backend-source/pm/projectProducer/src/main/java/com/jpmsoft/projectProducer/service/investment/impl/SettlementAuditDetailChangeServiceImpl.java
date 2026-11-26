package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.investment.SettlementAuditDetailChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditDetailChange;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditDetailVo;
import com.jpmsoft.projectProducer.service.investment.SettlementAuditDetailChangeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettlementAuditDetailChangeServiceImpl extends ServiceImpl<SettlementAuditDetailChangeMapper, SettlementAuditDetailChange> implements SettlementAuditDetailChangeService {
    @Override
    public void createSettlementAuditDetailChange(List<SettlementAuditDetailVo> detailList, String id,Integer type) {
        if(type.equals(1)){
            LambdaQueryWrapper<SettlementAuditDetailChange> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SettlementAuditDetailChange::getSettlementAuditChangeId,id);
            baseMapper.delete(queryWrapper);
        }
        if(!CollectionUtils.isEmpty(detailList)){
            List<SettlementAuditDetailChange> data  = new ArrayList<>();
            for (SettlementAuditDetailVo detail : detailList) {
                SettlementAuditDetailChange sett = new SettlementAuditDetailChange();
                BeanUtils.copyProperties(detail,sett);
                sett.setId(GenerateUUID.getUUID());
                sett.setDelFlag(1);
                sett.setStatus(1);
                sett.setSettlementAuditChangeId(id);
                data.add(sett);
            }
            if(!CollectionUtils.isEmpty(data)){
                saveBatch(data);
            }
        }

    }

    @Override
    public List<SettlementAuditDetailChange> settlementAuditDetailIdList(String id) {
        LambdaQueryWrapper<SettlementAuditDetailChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SettlementAuditDetailChange::getSettlementAuditChangeId,id);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<SettlementAuditDetailChange> getsettlementAuditById(String id) {
        return null;
    }

}
