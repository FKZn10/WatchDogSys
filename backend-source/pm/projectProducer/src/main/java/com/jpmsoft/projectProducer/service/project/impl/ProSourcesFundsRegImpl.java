package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Project.ProSourcesFundsRegModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.ProSourcesFundsRegMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProSourcesFundsReg;
import com.jpmsoft.projectProducer.service.project.IProSourcesFundsRegService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProSourcesFundsRegImpl extends ServiceImpl<ProSourcesFundsRegMapper, ProSourcesFundsReg> implements IProSourcesFundsRegService {
    @Override
    public void createOrUpdateProSourcesFundsReg(List<ProSourcesFundsRegModel> datas, String prId) {
        deleteProSourcesFundsRegByPrId(prId);
        if (datas != null && datas.size() > 0) {
            List<ProSourcesFundsReg> entitys = new ArrayList<>();
            datas.forEach(data -> {
                ProSourcesFundsReg entity = new ProSourcesFundsReg();
                BeanUtils.copyProperties(data, entity);
                entity.setFsId(GenerateUUID.getUUID());
                entity.setPrId(prId);
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public void deleteProSourcesFundsRegByPrId(String prId) {
        QueryWrapper<ProSourcesFundsReg> wrapper = new QueryWrapper<>();
        wrapper.eq("Pr_Id", prId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<ProSourcesFundsReg> getProSourcesFundsRegByPrId(String prId) {
        QueryWrapper<ProSourcesFundsReg> wrapper = new QueryWrapper<>();
        wrapper.eq("Pr_Id", prId);
        return baseMapper.selectList(wrapper);
    }
}
