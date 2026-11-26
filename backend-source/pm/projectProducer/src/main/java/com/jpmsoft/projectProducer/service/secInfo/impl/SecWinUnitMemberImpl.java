package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecWinUnitMemberRequest;
import com.jpmsoft.common.model.response.SecInfo.SecWinUnitMemberResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecWinUnitMemberMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWinUnitMember;
import com.jpmsoft.projectProducer.service.secInfo.ISecWinUnitMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SecWinUnitMemberImpl extends ServiceImpl<SecWinUnitMemberMapper, SecWinUnitMember> implements ISecWinUnitMemberService {


    @Override
    public void crateOrUpdateWinUnitMember(List<SecWinUnitMemberRequest> models, String wnId) {
        deleteWinUnitMemberByWnId(wnId);
        if (!CollectionUtils.isEmpty(models)) {
            List<SecWinUnitMember> entitys = new ArrayList<>();
            models.forEach(model -> {
                SecWinUnitMember entity = new SecWinUnitMember();
                BeanUtils.copyProperties(model, entity);
                entity.setWumId(GenerateUUID.getUUID());
                entity.setWnId(wnId);
                entity.setWumCreateTime(model.getWumCreateTime() == null ? new Date() : model.getWumCreateTime());
                entitys.add(entity);
            });
            saveBatch(entitys);
        }
    }


    @Override
    public void deleteWinUnitMemberByWnId(String wnId) {
        QueryWrapper<SecWinUnitMember> wrapper = new QueryWrapper<>();
        wrapper.eq("Wn_Id", wnId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecWinUnitMemberResponse> getWinUnitMemberByWnId(String wnId) {
        QueryWrapper<SecWinUnitMember> wrapper = new QueryWrapper<>();
        wrapper.eq("Wn_Id", wnId);
        wrapper.orderByAsc("Wum_Sort");
        List<SecWinUnitMember> entitys = baseMapper.selectList(wrapper);
        List<SecWinUnitMemberResponse> datas = new ArrayList<>();
        if (entitys != null && entitys.size() > 0) {
            entitys.forEach(entity -> {
                SecWinUnitMemberResponse model = new SecWinUnitMemberResponse();
                BeanUtils.copyProperties(entity, model);
                datas.add(model);
            });
        }
        return datas;
    }

    @Override
    public Boolean havingWinUnitMemberByWnId(String wnId) {
        QueryWrapper<SecWinUnitMember> wrapper = new QueryWrapper<>();
        wrapper.eq("Wn_Id", wnId);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        else
            return false;
    }
}
