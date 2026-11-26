package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Project.ProFundingSourceModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.ProFundingSourceMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProFundingSource;
import com.jpmsoft.projectProducer.service.project.IProFundingSourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProFundingSourceImpl extends ServiceImpl<ProFundingSourceMapper, ProFundingSource> implements IProFundingSourceService {


    @Override
    public void createOrUpdateProFundingSource(List<ProFundingSourceModel> datas, String paId) {
        deleteProFundingSourceByPaId(paId);
        if (datas != null && datas.size() > 0) {
            List<ProFundingSource> entitys = new ArrayList<>();
            datas.forEach(data -> {
                ProFundingSource entity = new ProFundingSource();
                BeanUtils.copyProperties(data, entity);
                entity.setFsId(GenerateUUID.getUUID());
                entity.setPaId(paId);
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public void deleteProFundingSourceByPaId(String paId) {
        QueryWrapper<ProFundingSource> wrapper = new QueryWrapper<>();
        wrapper.eq("Pa_Id", paId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ProFundingSource> getProFundingSourceByPaId(String paId) {
        QueryWrapper<ProFundingSource> wrapper = new QueryWrapper<>();
        wrapper.eq("Pa_Id", paId);
        return baseMapper.selectList(wrapper);
    }
}
