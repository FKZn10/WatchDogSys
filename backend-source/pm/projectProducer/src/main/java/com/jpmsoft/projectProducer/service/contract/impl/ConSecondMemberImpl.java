package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.Contract.ConSecondMemberRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConSecondMemberMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEstimate;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConSecondMember;
import com.jpmsoft.projectProducer.service.contract.IConSecondMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConSecondMemberImpl extends ServiceImpl<ConSecondMemberMapper, ConSecondMember> implements IConSecondMemberService {

    @Override
    public void createOrUpdateConSecondMember(List<ConSecondMemberRequest> datas, String conId) {
        deleteAllConSecondMemberByConId(conId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<ConSecondMember> entitys = new ArrayList<>();
            for (ConSecondMemberRequest data : datas) {
                ConSecondMember entity = new ConSecondMember();
                BeanUtils.copyProperties(data, entity);
                entity.setConId(conId);
                entity.setId(GenerateUUID.getUUID());
                entitys.add(entity);
            }
            if (ValidateUtil.isNotEmpty(entitys))
                saveBatch(entitys);
        }
    }

    @Override
    public void deleteAllConSecondMemberByConId(String conId) {
        QueryWrapper<ConSecondMember> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ConSecondMember> getConSecondMemberByConIds(List<String> conIds) {
        QueryWrapper<ConSecondMember> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conIds.get(0));
        for (int i = 1; i < conIds.size(); i++) {
            wrapper.or().eq("Con_Id", conIds.get(i));
        }
        List<ConSecondMember> entitys = baseMapper.selectList(wrapper);
        return entitys;
    }

    @Override
    public List<ConSecondMemberRequest> getConSecondMemberByConId(String conId) {
        QueryWrapper<ConSecondMember> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        List<ConSecondMember> entitys = baseMapper.selectList(wrapper);
        List<ConSecondMemberRequest> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            ConSecondMemberRequest data = new ConSecondMemberRequest();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
        });
        return datas;
    }
}
