package com.jpmsoft.projectProducer.service.performanceClaim.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Claim.PcClaimDtlRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.performanceClaim.PcClaimDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcClaimDtl;
import com.jpmsoft.projectProducer.service.performanceClaim.IPcClaimDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PcClaimDtlImpl extends ServiceImpl<PcClaimDtlMapper, PcClaimDtl> implements IPcClaimDtlService {
    @Override
    public void createOrUpdatePcClaimDtl(List<PcClaimDtlRequest> datas, String claimId) {
        deleteByClaimId(claimId);
        if (datas != null && datas.size() > 0) {
            List<PcClaimDtl> entitys = new ArrayList<>();
            datas.forEach(data -> {
                PcClaimDtl entity = new PcClaimDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setClaimId(claimId);
                entity.setCdId(GenerateUUID.getUUID());
                entitys.add(entity);
            });

            if (entitys.size() > 0)
                saveBatch(entitys);

        }
    }


    private void deleteByClaimId(String claimId) {
        QueryWrapper<PcClaimDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Claim_Id", claimId);
        baseMapper.delete(wrapper);
    }
}
