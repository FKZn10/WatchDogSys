package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConContractPayPlanMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConEstimateMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContractPayPlan;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimate;
import com.jpmsoft.projectProducer.service.contract.IConContractPayPlanService;
import com.jpmsoft.projectProducer.service.contract.IConEstimateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConEstimateImpl extends ServiceImpl<ConEstimateMapper, ConEstimate> implements IConEstimateService {

    @Override
    public void createOrUpdateConEstimate(List<ConEstimateModel> datas, String conId) {
        deleteAllConEstimateByConId(conId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<ConEstimate> entitys = new ArrayList<>();
            Date now = new Date();
            for (ConEstimateModel data : datas) {
                ConEstimate entity = new ConEstimate();
                BeanUtils.copyProperties(data, entity);
                entity.setConId(conId);
                entity.setCeId(GenerateUUID.getUUID());
                entitys.add(entity);
            }
            if (ValidateUtil.isNotEmpty(entitys))
                saveBatch(entitys);
        }
    }

    @Override
    public void deleteAllConEstimateByConId(String conId) {
        QueryWrapper<ConEstimate> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ConEstimateModel> getConEstimateByConIds(List<String> conIds) {
        QueryWrapper<ConEstimate> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conIds.get(0));
        for (int i = 1; i < conIds.size(); i++) {
            wrapper.or().eq("Con_Id", conIds.get(i));
        }
        List<ConEstimate> entitys = baseMapper.selectList(wrapper);
        List<ConEstimateModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            ConEstimateModel data = new ConEstimateModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
        });
        return datas;
    }

    @Override
    public List<ConEstimateModel> getConEstimateByConId(String conId) {
        QueryWrapper<ConEstimate> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        List<ConEstimate> entitys = baseMapper.selectList(wrapper);
        List<ConEstimateModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            ConEstimateModel data = new ConEstimateModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
        });
        return datas;
    }
}
