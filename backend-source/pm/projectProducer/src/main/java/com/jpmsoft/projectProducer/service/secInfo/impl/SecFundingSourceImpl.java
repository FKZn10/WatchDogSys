package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecFundingSourceModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecFundingSourceMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecFundingSource;
import com.jpmsoft.projectProducer.service.secInfo.ISecFundingSourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecFundingSourceImpl extends ServiceImpl<SecFundingSourceMapper, SecFundingSource> implements ISecFundingSourceService {


    @Override
    public void createOrUpdateSecFundingSource(List<SecFundingSourceModel> datas, String foreignId) {
        deleteSecFundingSourceByForeignId(foreignId);
        if (datas != null && datas.size() > 0) {
            List<SecFundingSource> entitys = new ArrayList<>();
            datas.forEach(data -> {
                SecFundingSource entity = new SecFundingSource();
                BeanUtils.copyProperties(data, entity);
                entity.setSfsId(GenerateUUID.getUUID());
                entity.setSfsForeignId(foreignId);
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public void deleteSecFundingSourceByForeignId(String paId) {
        QueryWrapper<SecFundingSource> wrapper = new QueryWrapper<>();
        wrapper.eq("Sfs_Foreign_Id", paId);
        baseMapper.delete(wrapper);
    }
}
