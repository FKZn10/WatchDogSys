package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecWinUnitRequest;
import com.jpmsoft.common.model.response.SecInfo.SecWinUnitResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecWinUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinUnit;
import com.jpmsoft.projectProducer.service.secInfo.ISecWinUnitService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecWinUnitImpl extends ServiceImpl<SecWinUnitMapper, SecWinUnit> implements ISecWinUnitService {


    @Override
    public void createOrUpdateSecWinUnit(List<SecWinUnitRequest> models, String wnId) {
        deleteSecWinUnitByWnId(wnId);
        if (!CollectionUtils.isEmpty(models)) {
            List<SecWinUnit> entitys = new ArrayList<>();
            models.forEach(model -> {
                SecWinUnit entity = new SecWinUnit();
                entity.setWnId(wnId);
                entity.setWuId(GenerateUUID.getUUID());
                entity.setWuCompanyId(model.getWuCompanyId());
                entitys.add(entity);
            });
            saveBatch(entitys);
        }
    }

    @Override
    public void deleteSecWinUnitByWnId(String wnId) {
        QueryWrapper<SecWinUnit> wrapper = new QueryWrapper<>();
        wrapper.eq("Wn_Id", wnId);
        baseMapper.delete(wrapper);
    }

    @Override
    public Boolean havingSecWinUnitByWnId(String wnId) {
        QueryWrapper<SecWinUnit> wrapper = new QueryWrapper<>();
        wrapper.eq("Wn_Id", wnId);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        else
            return false;
    }

    @Override
    public List<SecWinUnitResponse> getWinUnitListByWnId(String wnId) {
        if (ValidateUtil.isEmpty(wnId))
            return new ArrayList<>();
        return baseMapper.getWinUnitListByWnId(wnId);
    }
}
