package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.MeEnter.MeEnterApplyDtlRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeUsedNumberMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeUsedNumber;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeUsedNumberService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/4 9:50
 */
@Service
public class MeUsedNumberImpl extends ServiceImpl<MeUsedNumberMapper, MeUsedNumber> implements IMeUsedNumberService {


    @Override
    public void createOrUpdateMeUsedNumber(List<MeEnterApplyDtlRequest> applyDtls, String uaId) {
        if (!CollectionUtils.isEmpty(applyDtls)) {
            deleteOld(applyDtls, uaId);
            List<MeUsedNumber> data = new ArrayList<>();
            applyDtls.forEach(a -> {
                MeUsedNumber e = new MeUsedNumber();
                e.setId(GenerateUUID.getUUID());
                e.setUaId(uaId);
                e.setEadId(a.getEadId());
                e.setMunNumber(a.getMunNumber());
                data.add(e);
            });
            if (!CollectionUtils.isEmpty(data)) saveBatch(data);
        }
    }


    private void deleteOld(List<MeEnterApplyDtlRequest> applyDtls, String uaId) {
        QueryWrapper<MeUsedNumber> wrapper = new QueryWrapper<>();
        wrapper.eq("Ua_Id", uaId);
        wrapper.in("Ead_Id", applyDtls.stream().map(MeEnterApplyDtlRequest::getEadId).collect(Collectors.toList()));
        baseMapper.delete(wrapper);
    }
}
