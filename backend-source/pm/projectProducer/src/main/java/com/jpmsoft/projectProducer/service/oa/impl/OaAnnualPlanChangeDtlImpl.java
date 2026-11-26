package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.oa.OaAnnualPlanChangeDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.oa.OaAnnualPlanChangeDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaAnnualPlanChangeDtl;
import com.jpmsoft.projectProducer.service.oa.IOaAnnualPlanChangeDtlService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:10
 */
@Service
public class OaAnnualPlanChangeDtlImpl extends ServiceImpl<OaAnnualPlanChangeDtlMapper, OaAnnualPlanChangeDtl> implements IOaAnnualPlanChangeDtlService {
    @Override
    public void createOrUpdateOaAnnualPlanChangeDtl(List<OaAnnualPlanChangeDtlDto> datas, String apcId) {
        delOaAnnualPlanChangeDtlByApcId(apcId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<OaAnnualPlanChangeDtl> entitys = new ArrayList<>();
            entitys = datas.stream().map(data -> {
                OaAnnualPlanChangeDtl entity = new OaAnnualPlanChangeDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setApcId(apcId);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delOaAnnualPlanChangeDtlByApcId(String apcId) {
        QueryWrapper<OaAnnualPlanChangeDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Apc_Id", apcId);
        baseMapper.delete(wrapper);
    }
}
