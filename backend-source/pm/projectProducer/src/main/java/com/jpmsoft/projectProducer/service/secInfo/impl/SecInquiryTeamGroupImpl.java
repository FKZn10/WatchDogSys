package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInquiryTeamGroupModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInquiryTeamGroupMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInquiryTeamGroup;
import com.jpmsoft.projectProducer.service.secInfo.ISecInquiryTeamGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecInquiryTeamGroupImpl extends ServiceImpl<SecInquiryTeamGroupMapper, SecInquiryTeamGroup> implements ISecInquiryTeamGroupService {
    @Override
    public Boolean havingInquiryTeamGroupBySitId(String sitId) {
        QueryWrapper<SecInquiryTeamGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("Sit_Id", sitId);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        else
            return false;
    }

    @Override
    public void createOrUpdateInquiryTeamGroup(List<SecInquiryTeamGroupModel> models, String sitId) {
        QueryWrapper<SecInquiryTeamGroup> wrapper = new QueryWrapper<>();
        wrapper.eq("Sit_Id", sitId);
        baseMapper.delete(wrapper);
        if (models != null && models.size() > 0) {
            List<SecInquiryTeamGroup> entitys = new ArrayList<>();
            for (SecInquiryTeamGroupModel model : models) {
                SecInquiryTeamGroup entity = new SecInquiryTeamGroup();
                BeanUtils.copyProperties(model, entity);
                entity.setSitId(sitId);
                entity.setSitgId(GenerateUUID.getUUID());
                entitys.add(entity);
            }
            saveBatch(entitys);
        }
    }

    @Override
    public List<SecInquiryTeamGroupModel> getSecInquiryTeamGroupBySitId(String sitId) {
        if (ValidateUtil.isEmpty(sitId))
            return new ArrayList<>();
        List<SecInquiryTeamGroupModel> data = baseMapper.getSecInquiryTeamGroupBySitId(sitId);
        if (data != null && data.size() > 0)
            return data;
        else
            return new ArrayList<>();
    }

    @Override
    public List<SecInquiryTeamGroupModel> getSecInquiryTeamGroupBySecId(String secId) {
        if (ValidateUtil.isEmpty(secId))
            return new ArrayList<>();
        List<SecInquiryTeamGroupModel> data = baseMapper.getSecInquiryTeamGroupBySecId(secId);
        if (data != null && data.size() > 0)
            return data;
        else
            return new ArrayList<>();
    }
}
