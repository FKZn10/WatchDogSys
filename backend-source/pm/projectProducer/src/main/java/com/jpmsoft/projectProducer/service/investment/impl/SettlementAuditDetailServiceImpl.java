package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.investment.SettlementAuditDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditDetail;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditDetailVo;
import com.jpmsoft.projectProducer.service.investment.SettlementAuditDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettlementAuditDetailServiceImpl   extends ServiceImpl<SettlementAuditDetailMapper, SettlementAuditDetail> implements SettlementAuditDetailService {
    @Override
    public void createSettlementAuditDetail(List<SettlementAuditDetailVo> detailList, String id,Integer type) {
        if(type.equals(1)){
            deleteSettlementAuditId(id);
        }
        if(!CollectionUtils.isEmpty(detailList)){
            List<SettlementAuditDetail> data  = new ArrayList<>();
            for (SettlementAuditDetailVo detail : detailList) {
                SettlementAuditDetail sett = new SettlementAuditDetail();
                BeanUtils.copyProperties(detail,sett);
                sett.setId(GenerateUUID.getUUID());
                sett.setDelFlag(1);
                sett.setStatus(1);
                sett.setSettlementAuditId(id);
                data.add(sett);
            }
            saveBatch(data);
        }

    }

    @Override
    public void deleteSettlementAuditId(String id) {
        LambdaQueryWrapper<SettlementAuditDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SettlementAuditDetail::getSettlementAuditId,id);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public List<SettlementAuditDetail> getSettAuditDetailId(String id) {
        LambdaQueryWrapper<SettlementAuditDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SettlementAuditDetail::getSettlementAuditId,id);
         List<SettlementAuditDetail> settlementAuditDetails = baseMapper.selectList(queryWrapper);
        return settlementAuditDetails;
    }
}
