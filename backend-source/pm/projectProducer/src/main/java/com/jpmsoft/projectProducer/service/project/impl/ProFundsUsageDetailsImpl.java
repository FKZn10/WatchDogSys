package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.ProFundsUsageDetailsDto;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.ProFundsUsageDetailsMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProFundsUsageDetails;
import com.jpmsoft.projectProducer.service.project.IProFundsUsageDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/7 15:46
 */
@Service
public class ProFundsUsageDetailsImpl extends ServiceImpl<ProFundsUsageDetailsMapper, ProFundsUsageDetails>
        implements IProFundsUsageDetailsService {

    private List<ProFundsUsageDetails> setEntityProperties(List<ProFundsUsageDetailsDto> datas, String cplId) {
        if (!CollectionUtils.isEmpty(datas)) {
            List<ProFundsUsageDetails> entitys = datas.stream().map(data -> {
                ProFundsUsageDetails entity = new ProFundsUsageDetails();
                BeanUtils.copyProperties(data, entity);
                entity.setCplId(cplId);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                return entity;
            }).collect(Collectors.toList());
            return entitys;
        }
        return new ArrayList<>();
    }

    @Override
    public void createOrUpdateProFundsUsageDetailsBath(List<List<ProFundsUsageDetailsDto>> datas, List<String> cplId) {
        List<ProFundsUsageDetails> allEntitys = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
            allEntitys.addAll(setEntityProperties(datas.get(i), cplId.get(i)));
        if (!CollectionUtils.isEmpty(allEntitys)) saveBatch(allEntitys);
    }

    @Override
    public void delProFundsUsageDetailsByCplId(String cplId) {
        QueryWrapper<ProFundsUsageDetails> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpl_Id", cplId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void delProFundsUsageDetailsByPcpId(String pcpId) {
        if (ValidateUtil.isEmpty(pcpId)) return;
        baseMapper.delProFundsUsageDetailsByPcpId(pcpId);
    }

    @Override
    public List<ProFundsUsageDetailsDto> getProFundsUsageDetailsByPcpId(String pcpId) {
        if (ValidateUtil.isEmpty(pcpId)) return new ArrayList<>();
        return baseMapper.getProFundsUsageDetailsByPcpId(pcpId);
    }

    @Override
    public List<ProFundsUsageDetailsDto> getProFundsUsageDetailsByYcpId(String ycpId) {
        if (ValidateUtil.isEmpty(ycpId)) return new ArrayList<>();
        return baseMapper.getProFundsUsageDetailsByYcpId(ycpId);
    }

    @Override
    public List<ProFundsUsageDetailsDto> getProFundsUsageDetailsByCplIds(List<String> cplIds) {
        if (CollectionUtils.isEmpty(cplIds)) return new ArrayList<>();
        return baseMapper.getProFundsUsageDetailsByCplIds(cplIds);
    }
}
