package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecOpenResultListModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecOpenResultListMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecOpenResultList;
import com.jpmsoft.projectProducer.service.secInfo.ISecOpenResultListService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecOpenResultListImpl extends ServiceImpl<SecOpenResultListMapper, SecOpenResultList> implements ISecOpenResultListService {
    @Override
    public void createOrUpdateSecOpenResultList(List<SecOpenResultListModel> datas, String orId) {
        deleteSecOpenResultListByOrId(orId);
        if (datas != null && datas.size() > 0) {
            List<SecOpenResultList> entitys = new ArrayList<>();
            datas.forEach(data -> {
                SecOpenResultList entity = new SecOpenResultList();
                BeanUtils.copyProperties(data, entity);
                entity.setSoplId(GenerateUUID.getUUID());
                entity.setOrId(orId);
                entitys.add(entity);
            });

            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }


    public void deleteSecOpenResultListByOrId(String orId) {
        QueryWrapper<SecOpenResultList> wrapper = new QueryWrapper<>();
        wrapper.eq("Or_Id", orId);
        baseMapper.delete(wrapper);
    }
}
