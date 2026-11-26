package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecQeGroupDtlRequest;
import com.jpmsoft.common.model.response.SecInfo.SecQeGroupDtlResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecQeGroupDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQeGroupDtl;
import com.jpmsoft.projectProducer.service.secInfo.ISecQeGroupDtlService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SecQeGroupDtlImpl extends ServiceImpl<SecQeGroupDtlMapper, SecQeGroupDtl> implements ISecQeGroupDtlService {
    @Override
    public Boolean havingQeGroupDtlByQegId(String qegId) {
        QueryWrapper<SecQeGroupDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Qeg_Id", qegId);
        if (baseMapper.selectCount(wrapper) > 0)
            return true;
        else
            return false;
    }

    @Override
    public void createOrUpdateQeGroupDtl(List<SecQeGroupDtlRequest> models, String qegId) {
        QueryWrapper<SecQeGroupDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Qeg_Id", qegId);
        baseMapper.delete(wrapper);
        if (models != null && models.size() > 0) {
            List<SecQeGroupDtl> entitys = new ArrayList<>();
            for (SecQeGroupDtlRequest model : models) {
                SecQeGroupDtl entity = new SecQeGroupDtl();
                entity.setQegId(qegId);
                entity.setQegdCreateTime(new Date());
                entity.setQegdId(GenerateUUID.getUUID());
                entity.setQegdUid(model.getQegdUid());
                entity.setQegdSort(model.getQegdSort());
                entity.setQegdMobilePhone(model.getQegdMobilePhone());
                entity.setQegdUserName(model.getQegdUserName());
                entity.setQegdCid(model.getQegdCid());
                entity.setQegdRemark(model.getQegdRemark());
                entitys.add(entity);
            }
            saveBatch(entitys);
        }
    }

    @Override
    public List<SecQeGroupDtlResponse> getSecQeGroupDtlByQegId(String qegId) {
        if (ValidateUtil.isEmpty(qegId))
            return new ArrayList<>();
        List<SecQeGroupDtlResponse> data = baseMapper.getSecQeGroupDtlByQegId(qegId);
        if (data != null && data.size() > 0)
            return data;
        else
            return new ArrayList<>();
    }

    @Override
    public List<SecQeGroupDtlResponse> getSecQeGroupDtlBySecId(String secId) {
        if (ValidateUtil.isEmpty(secId))
            return new ArrayList<>();
        List<SecQeGroupDtlResponse> data = baseMapper.getSecQeGroupDtlBySecId(secId);
        if (data != null && data.size() > 0)
            return data;
        else
            return new ArrayList<>();
    }
}
