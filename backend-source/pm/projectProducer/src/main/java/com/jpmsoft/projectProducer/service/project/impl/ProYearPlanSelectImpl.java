package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.ProYearPlanSelectMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProYearPlanSelectDto;
import com.jpmsoft.projectProducer.pojo.entity.project.ProYearPlanSelect;
import com.jpmsoft.projectProducer.service.project.IProYearPlanSelectService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:14
 */
@Service
public class ProYearPlanSelectImpl extends ServiceImpl<ProYearPlanSelectMapper, ProYearPlanSelect>
        implements IProYearPlanSelectService {


    @Override
    public void createOrUpdateProYearPlanSelect(List<ProYearPlanSelectDto> datas, String ycpId) {
        delProYearPlanSelectByPcyId(ycpId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<ProYearPlanSelect> entitys = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getCplId()))
                    .map(data -> {
                        ProYearPlanSelect entity = new ProYearPlanSelect();
                        BeanUtils.copyProperties(data, entity);
                        entity.setYcpId(ycpId);
                        entity.setId(GenerateUUID.getUUID(data.getId()));
                        return entity;
                    }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entitys)) saveBatch(entitys);
        }
    }

    @Override
    public void delProYearPlanSelectByPcyId(String ycpId) {
        QueryWrapper<ProYearPlanSelect> wrapper = new QueryWrapper<>();
        wrapper.eq("Ycp_Id", ycpId);
        baseMapper.delete(wrapper);
    }
}
