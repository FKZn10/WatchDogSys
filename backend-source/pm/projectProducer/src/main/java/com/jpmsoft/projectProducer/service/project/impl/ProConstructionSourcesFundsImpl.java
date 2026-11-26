package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.project.ProConstructionSourcesFundsDto;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.project.ProConstructionSourcesFundsMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProConstructionSourcesFunds;
import com.jpmsoft.projectProducer.service.project.IProConstructionSourcesFundsService;
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
public class ProConstructionSourcesFundsImpl extends ServiceImpl<ProConstructionSourcesFundsMapper, ProConstructionSourcesFunds>
        implements IProConstructionSourcesFundsService {

    private List<ProConstructionSourcesFunds> setEntityProperties(List<ProConstructionSourcesFundsDto> datas, String cplId) {
        if (!CollectionUtils.isEmpty(datas)) {
            List<ProConstructionSourcesFunds> entitys = datas.stream().map(data -> {
                ProConstructionSourcesFunds entity = new ProConstructionSourcesFunds();
                BeanUtils.copyProperties(data, entity);
                entity.setCplId(cplId);
                entity.setFsId(GenerateUUID.getUUID(data.getFsId()));
                return entity;
            }).collect(Collectors.toList());
            return entitys;
        }
        return new ArrayList<>();
    }

    @Override
    public void createOrUpdateProConstructionSourcesFundsBath(List<List<ProConstructionSourcesFundsDto>> datas, List<String> cplId) {
        List<ProConstructionSourcesFunds> allEntitys = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++)
            allEntitys.addAll(setEntityProperties(datas.get(i), cplId.get(i)));
        if (!CollectionUtils.isEmpty(allEntitys)) saveBatch(allEntitys);
    }

    @Override
    public void delProConstructionSourcesFundsByCplId(String cplId) {
        QueryWrapper<ProConstructionSourcesFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("Cpl_Id", cplId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void delProConstructionSourcesFundsByPcpId(String pcpId) {
        if (ValidateUtil.isEmpty(pcpId)) return;
        baseMapper.delProConstructionSourcesFundsByPcpId(pcpId);
    }

    @Override
    public List<ProConstructionSourcesFundsDto> getProConstructionSourcesFundsByPcpId(String pcpId) {
        if (ValidateUtil.isEmpty(pcpId)) return new ArrayList<>();
        return baseMapper.getProConstructionSourcesFundsByPcpId(pcpId);
    }

    @Override
    public List<ProConstructionSourcesFundsDto> getProConstructionSourcesFundsByYcpId(String ycpId) {
        if (ValidateUtil.isEmpty(ycpId)) return new ArrayList<>();
        return baseMapper.getProConstructionSourcesFundsByYcpId(ycpId);
    }

    @Override
    public List<ProConstructionSourcesFundsDto> getProConstructionSourcesFundsByCplIds(List<String> cplIds) {
        if (CollectionUtils.isEmpty(cplIds)) return new ArrayList<>();
        return baseMapper.getProConstructionSourcesFundsByCplIds(cplIds);
    }
}
