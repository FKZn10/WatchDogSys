package com.jpmsoft.projectProducer.service.contract.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateMaintenanceDtlModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.contract.ConEstimateMaintenanceDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimateMaintenanceDtl;
import com.jpmsoft.projectProducer.service.contract.IConEstimateMaintenanceDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConEstimateMaintenanceDtlImpl extends ServiceImpl<ConEstimateMaintenanceDtlMapper, ConEstimateMaintenanceDtl> implements IConEstimateMaintenanceDtlService {
    @Override
    public void createOrUpdateConEstimateMaintenanceDtl(List<ConEstimateMaintenanceDtlModel> datas, String cemid) {
        deleteConEstimateMaintenanceDtlByCemId(cemid);
        if (!CollectionUtils.isEmpty(datas)) {
            List<ConEstimateMaintenanceDtl> entities = datas.stream().map(e -> {
                ConEstimateMaintenanceDtl entity = new ConEstimateMaintenanceDtl();
                BeanUtils.copyProperties(e, entity);
                entity.setCemdId(GenerateUUID.getUUID());
                entity.setCemId(cemid);
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entities);
        }
    }


    @Override
    public void deleteConEstimateMaintenanceDtlByCemId(String cemid) {
        QueryWrapper<ConEstimateMaintenanceDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Cem_Id", cemid);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ConEstimateMaintenanceDtlModel> getConEstimateMaintenanceDtlByCemId(String cemid) {
        return baseMapper.getConEstimateMaintenanceDtlByCemId(cemid);
    }

}
