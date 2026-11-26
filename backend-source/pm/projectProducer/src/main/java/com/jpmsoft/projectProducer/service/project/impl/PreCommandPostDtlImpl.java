package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Project.PreCommandPostDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.PreCommandPostDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreCommandPostDtl;
import com.jpmsoft.projectProducer.service.project.IPreCommandPostDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreCommandPostDtlImpl extends ServiceImpl<PreCommandPostDtlMapper, PreCommandPostDtl> implements IPreCommandPostDtlService {

    @Override
    public void createOrUpdatePreCommandPostDtl(List<PreCommandPostDtlModel> datas, String comId) {
        deletePreCommandPostDtlByComId(comId);
        if (datas != null && datas.size() > 0) {
            List<PreCommandPostDtl> entitys = new ArrayList<>();
            datas.forEach(data -> {
                PreCommandPostDtl entity = new PreCommandPostDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setComId(comId);
                entity.setComdId(GenerateUUID.getUUID());
                entitys.add(entity);
            });

            if (entitys.size() > 0)
                saveBatch(entitys);
        }

    }

    @Override
    public void deletePreCommandPostDtlByComId(String comId) {
        QueryWrapper<PreCommandPostDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Com_Id", comId);
        baseMapper.delete(wrapper);
    }
}
