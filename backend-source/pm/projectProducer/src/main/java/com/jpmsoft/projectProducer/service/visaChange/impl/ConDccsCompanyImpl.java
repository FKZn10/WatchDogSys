package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.visaChange.ConDccsCompanyMapper;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConDccsCompanyDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConDccsCompany;
import com.jpmsoft.projectProducer.service.visaChange.IConDccsCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/6 17:49
 */
@Service
public class ConDccsCompanyImpl extends ServiceImpl<ConDccsCompanyMapper, ConDccsCompany> implements IConDccsCompanyService {
    @Override
    public void createOrUpdateBath(List<ConDccsCompanyDTO> datas, String dccsId) {
        delDccsCompanyByDccsId(dccsId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<ConDccsCompany> coms = datas.stream().map(data -> {
                ConDccsCompany com = new ConDccsCompany();
                BeanUtils.copyProperties(data, com);
                com.setId(GenerateUUID.getUUID());
                com.setDccsId(dccsId);
                return com;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(coms)) saveBatch(coms);
        }
    }

    @Override
    public void delDccsCompanyByDccsId(String dccsId) {
        QueryWrapper<ConDccsCompany> wrapper = new QueryWrapper<>();
        wrapper.eq("Dccs_Id", dccsId);
        baseMapper.delete(wrapper);
    }
}
