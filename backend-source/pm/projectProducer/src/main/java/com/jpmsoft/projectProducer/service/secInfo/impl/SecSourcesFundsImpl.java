package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSourcesFundsModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecSourcesFundsMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSourcesFunds;
import com.jpmsoft.projectProducer.service.secInfo.ISecSourcesFundsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SecSourcesFundsImpl extends ServiceImpl<SecSourcesFundsMapper, SecSourcesFunds> implements ISecSourcesFundsService {
    @Override
    public void createOrUpdateSecSourcesFunds(List<SecSourcesFundsModel> datas, String secId) {
        delSecSourcesFundsBySecId(secId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SecSourcesFunds> entitys = new ArrayList<>();
            datas.forEach(data -> {
                SecSourcesFunds entity = new SecSourcesFunds();
                BeanUtils.copyProperties(data, entity);
                entity.setSsfId(ValidateUtil.isEmpty(data.getSsfId()) ? GenerateUUID.getUUID() : data.getSsfId());
                entity.setSsfCreateTime(ValidateUtil.isNull(data.getSsfCreateTime()) ? new Date() : data.getSsfCreateTime());
                entity.setSecId(secId);
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public void createOrUpdateSecSourcesFundsByBath(List<List<SecSourcesFundsModel>> datas, List<String> secIds) {
        //调用前已提前执行删除逻辑不在删除
        //delSecSourcesFundsBySecIds(secIds);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<SecSourcesFunds> entitys = new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                List<SecSourcesFundsModel> models = datas.get(i);
                String secId = secIds.get(i);
                models.forEach(data -> {
                    SecSourcesFunds entity = new SecSourcesFunds();
                    BeanUtils.copyProperties(data, entity);
                    entity.setSsfId(GenerateUUID.getUUID());
                    entity.setSecId(secId);
                    entity.setSsfCreateTime(ValidateUtil.isNull(data.getSsfCreateTime()) ? new Date() : data.getSsfCreateTime());
                    entitys.add(entity);
                });
            }
            if (entitys.size() > 0)
                saveBatch(entitys);
        }

    }

    @Override
    public void delSecSourcesFundsBySecId(String secId) {
        QueryWrapper<SecSourcesFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void delSecSourcesFundsBySecIds(List<String> secIds) {
        if (ValidateUtil.isEmpty(secIds)) return;
        QueryWrapper<SecSourcesFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secIds.get(0));
        for (int i = 1; i < secIds.size(); i++)
            wrapper.or().eq("Sec_Id", secIds.get(i));
        baseMapper.delete(wrapper);
    }

    @Override
    public void delSecSourcesFundsByBpId(String bpId) {
        if (ValidateUtil.isEmpty(bpId)) return;
        baseMapper.delSecSourcesFundsByBpId(bpId);
    }

    @Override
    public List<SecSourcesFundsModel> getSecSourcesFundsBySecIds(List<String> secIds) {
        if (ValidateUtil.isEmpty(secIds)) return new ArrayList<>();
        QueryWrapper<SecSourcesFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secIds.get(0));
        for (int i = 1; i < secIds.size(); i++)
            wrapper.or().eq("Sec_Id", secIds.get(i));
        List<SecSourcesFunds> entitys = baseMapper.selectList(wrapper);
        if (ValidateUtil.isEmpty(entitys))
            return new ArrayList<>();
        List<SecSourcesFundsModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            SecSourcesFundsModel data = new SecSourcesFundsModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
            data = null;
        });
        return datas;
    }

    @Override
    public List<SecSourcesFundsModel> getSecSourcesFundsBySecId(String secId) {
        if (ValidateUtil.isEmpty(secId)) return new ArrayList<>();
        QueryWrapper<SecSourcesFunds> wrapper = new QueryWrapper<>();
        wrapper.eq("Sec_Id", secId);
        List<SecSourcesFunds> entitys = baseMapper.selectList(wrapper);
        if (ValidateUtil.isEmpty(entitys))
            return new ArrayList<>();
        List<SecSourcesFundsModel> datas = new ArrayList<>();
        entitys.forEach(entity -> {
            SecSourcesFundsModel data = new SecSourcesFundsModel();
            BeanUtils.copyProperties(entity, data);
            datas.add(data);
            data = null;
        });
        return datas;
    }
}
