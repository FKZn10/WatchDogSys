package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOpenResultCandidateModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecOpenResultCandidateMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenResultCandidate;
import com.jpmsoft.projectProducer.service.secInfo.ISecOpenResultCandidateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecOpenResultCandidateImpl extends ServiceImpl<SecOpenResultCandidateMapper, SecOpenResultCandidate> implements ISecOpenResultCandidateService {


    @Override
    public void createOrUpdateSecOpenResultCandidate(List<SecOpenResultCandidateModel> datas, String orId) {
        delSecOpenResultCandidateByOrId(orId);
        if(CollectionUtil.isNotEmpty(datas)){
            List<SecOpenResultCandidate> entitys = new ArrayList<>();
            datas.forEach(data -> {
                SecOpenResultCandidate entity = new SecOpenResultCandidate();
                BeanUtils.copyProperties(data, entity);
                entity.setOrcId(GenerateUUID.getUUID());
                entity.setOrId(orId);
                entitys.add(entity);
            });

            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    private void delSecOpenResultCandidateByOrId(String orId) {
        QueryWrapper<SecOpenResultCandidate> wrapper = new QueryWrapper<>();
        wrapper.eq("Or_Id", orId);
        baseMapper.delete(wrapper);
    }


}
