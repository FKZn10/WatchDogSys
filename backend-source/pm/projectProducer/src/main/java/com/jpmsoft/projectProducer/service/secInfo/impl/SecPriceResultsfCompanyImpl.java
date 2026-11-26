package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecPriceResultsfCompanyModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecPriceResultsfCompanyMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecPriceResultsfCompany;
import com.jpmsoft.projectProducer.service.secInfo.ISecPriceResultsfCompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecPriceResultsfCompanyImpl extends ServiceImpl<SecPriceResultsfCompanyMapper, SecPriceResultsfCompany> implements ISecPriceResultsfCompanyService {
    @Override
    public Boolean havingPriceResultsfCompanyBySprId(String sprId) {
        QueryWrapper<SecPriceResultsfCompany> wrapper = new QueryWrapper<>();
        wrapper.eq("Spr_Id", sprId);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        else
            return false;
    }

    @Override
    public void createOrUpdatePriceResultsfCompany(List<SecPriceResultsfCompanyModel> datas, String sprId) {
        QueryWrapper<SecPriceResultsfCompany> wrapper = new QueryWrapper<>();
        wrapper.eq("Spr_Id", sprId);
        baseMapper.delete(wrapper);
        if (datas != null && datas.size() > 0) {
            List<SecPriceResultsfCompany> entitys = new ArrayList<>();
            for (SecPriceResultsfCompanyModel data : datas) {
                SecPriceResultsfCompany entity = new SecPriceResultsfCompany();
                BeanUtils.copyProperties(data, entity);
                entity.setSprId(sprId);
                entity.setSprcId(GenerateUUID.getUUID());
                entitys.add(entity);
            }
            saveBatch(entitys);
        }
    }

    @Override
    public List<SecPriceResultsfCompanyModel> getPriceResultsfCompanyBySprId(String sprId) {
        QueryWrapper<SecPriceResultsfCompany> wrapper = new QueryWrapper<>();
        wrapper.eq("Spr_Id", sprId);
        List<SecPriceResultsfCompany> datas = baseMapper.selectList(wrapper);
        List<SecPriceResultsfCompanyModel> models = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            datas.forEach(data -> {
                SecPriceResultsfCompanyModel model = new SecPriceResultsfCompanyModel();
                BeanUtils.copyProperties(data, model);
                models.add(model);
            });

        }
        return models;
    }
}
