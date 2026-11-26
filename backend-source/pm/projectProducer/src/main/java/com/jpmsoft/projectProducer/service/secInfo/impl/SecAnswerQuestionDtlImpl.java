package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecAnswerQuestionDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecAnswerQuestionDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecAnswerQuestionDtl;
import com.jpmsoft.projectProducer.service.secInfo.ISecAnswerQuestionDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SecAnswerQuestionDtlImpl extends ServiceImpl<SecAnswerQuestionDtlMapper, SecAnswerQuestionDtl>
        implements ISecAnswerQuestionDtlService {

    @Override
    public void createOrUpdateSecAnswerQuestionDtl(List<SecAnswerQuestionDtlModel> datas, String aqId) {
        delSecAnswerQuestionDtlByAqId(aqId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SecAnswerQuestionDtl> entitys = new ArrayList<>();
            datas.forEach(data -> {
                SecAnswerQuestionDtl entity = new SecAnswerQuestionDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setAqId(aqId);
                entity.setAqdId(ValidateUtil.isEmpty(data.getAqdId()) ? GenerateUUID.getUUID() : data.getAqdId());
                entity.setAqdCreateTime(ValidateUtil.isNull(data.getAqdCreateTime()) ? new Date() : data.getAqdCreateTime());
                entitys.add(entity);
                entity = null;
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public void delSecAnswerQuestionDtlByAqId(String aqId) {
        QueryWrapper<SecAnswerQuestionDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Aq_Id", aqId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<SecAnswerQuestionDtlModel> getSecAnswerQuestionDtlByAqId(String aqId) {
        if (ValidateUtil.isEmpty(aqId)) return new ArrayList<>();
        QueryWrapper<SecAnswerQuestionDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Aq_Id", aqId);
        List<SecAnswerQuestionDtl> entitys = baseMapper.selectList(wrapper);
        if (ValidateUtil.isEmpty(entitys))
            return new ArrayList<>();
        List<SecAnswerQuestionDtlModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            SecAnswerQuestionDtlModel data = new SecAnswerQuestionDtlModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
            data = null;
        });
        return datas;
    }
}
