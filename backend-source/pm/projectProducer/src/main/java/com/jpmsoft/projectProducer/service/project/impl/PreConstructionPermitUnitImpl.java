package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Project.PreConstructionPermitUnitModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.PreConstructionPermitUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreConstructionPermitUnit;
import com.jpmsoft.projectProducer.service.project.IPreConstructionPermitUnitService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreConstructionPermitUnitImpl extends ServiceImpl<PreConstructionPermitUnitMapper, PreConstructionPermitUnit> implements IPreConstructionPermitUnitService {
    @Override
    public void createOrUpdatePreConstructionPermitUnit(List<PreConstructionPermitUnitModel> datas, String cpId) {
        deletePreConstructionPermitUnitByComId(cpId);
        if (datas != null && datas.size() > 0) {
            List<PreConstructionPermitUnit> entitys = new ArrayList<>();
            datas.forEach(data -> {
                PreConstructionPermitUnit entity = new PreConstructionPermitUnit();
                BeanUtils.copyProperties(data, entity);
                entity.setCpId(cpId);
                entity.setCpuId(GenerateUUID.getUUID());
                entitys.add(entity);
            });

            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public void deletePreConstructionPermitUnitByComId(String cpId) {
        QueryWrapper<PreConstructionPermitUnit> wrapper = new QueryWrapper<>();
        wrapper.eq("Cp_Id", cpId);
        baseMapper.delete(wrapper);
    }
}
