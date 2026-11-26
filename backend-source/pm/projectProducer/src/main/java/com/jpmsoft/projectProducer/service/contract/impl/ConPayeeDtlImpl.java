package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConPayeeDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConPayeeDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayeeDtl;
import com.jpmsoft.projectProducer.service.contract.IConPayeeDtlService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ConPayeeDtlImpl extends ServiceImpl<ConPayeeDtlMapper, ConPayeeDtl> implements IConPayeeDtlService {


    @Override
    public void createOrUpdateConPayeeDtl(List<ConPayeeDtlModel> datas, String conId, String createUid, String createUname) {
        deleteAllConPayeeDtlByConId(conId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<ConPayeeDtl> entitys = new ArrayList<>();
            Date now = new Date();
            for (ConPayeeDtlModel data : datas) {
                ConPayeeDtl pojo = new ConPayeeDtl();
                BeanUtils.copyProperties(data, pojo);
                pojo.setCpdId(GenerateUUID.getUUID());
                pojo.setConId(conId);
                pojo.setCpdCreateUid(createUid);
                pojo.setCpdCreateUname(createUname);
                pojo.setCpdCreateTime(JudgeUtil.checkObject(data.getCpdCreateTime()) ? now : data.getCpdCreateTime());
                entitys.add(pojo);
            }
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public List<ConPayeeDtlModel> getConPayeeDtlByConId(String conId) {
        QueryWrapper<ConPayeeDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.orderByAsc("Cpd_Create_Time");
        List<ConPayeeDtl> entitys = baseMapper.selectList(wrapper);
        List<ConPayeeDtlModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            ConPayeeDtlModel data = new ConPayeeDtlModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
            data = null;
        });
        entitys = null;
        return datas;
    }

    @Override
    public void deleteAllConPayeeDtlByConId(String conId) {
        QueryWrapper<ConPayeeDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        baseMapper.delete(wrapper);
    }
}
