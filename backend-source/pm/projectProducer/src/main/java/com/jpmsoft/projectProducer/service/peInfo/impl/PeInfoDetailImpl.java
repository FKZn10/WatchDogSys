package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoDetailModel;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoDetailStandardModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.peInfo.PeInfoDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoDetail;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoDetailService;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoDetailStandardService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeInfoDetailImpl extends ServiceImpl<PeInfoDetailMapper, PeInfoDetail> implements IPeInfoDetailService {

    @Resource
    private IPeInfoDetailStandardService peInfoDetailStandardService;

    @Override
    public void createOrUpdatePeInfoDetail(String peId, List<PeInfoDetailModel> model) {
        QueryWrapper<PeInfoDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("Pe_Id", peId);
        List<PeInfoDetail> entitys = baseMapper.selectList(wrapper);
        List<String> pedIds = new ArrayList<>();
        entitys.forEach(entity -> {
            pedIds.add(entity.getPedId());
        });
        List<PeInfoDetailStandardModel> models = new ArrayList<>();
        List<PeInfoDetailModel> pojos = new ArrayList<>();
        model.forEach(entity -> {
            List<PeInfoDetailStandardModel> datas = entity.getPeInfoDetailStandards();
            entity.setPedId(GenerateUUID.getUUID());
            entity.setPeId(peId);
            if (datas != null) {
                for (PeInfoDetailStandardModel data : datas) {
                    data.setPedsId(GenerateUUID.getUUID());
                    data.setPedId(entity.getPedId());
                    models.add(data);
                }
            }
            pojos.add(entity);
        });
        //先删除老的记录
        if (pedIds.size() > 0)
            peInfoDetailStandardService.deleteOldAllPeInfoStandard(pedIds);
        baseMapper.deletePeInfoDetailByPeId(peId);
        //新增新记录
        if (pojos.size() > 0)
            baseMapper.insertBatchs(pojos);
        if (models.size() > 0)
            peInfoDetailStandardService.createPeInfoStandard(models);
    }

    @Override
    public List<PeInfoDetailModel> getPeInfoDetailByPeId(String peId) {
        List<PeInfoDetailModel> data = new ArrayList<>();
        QueryWrapper<PeInfoDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("Pe_Id", peId);
        wrapper.orderByAsc("Ped_Order");
        List<PeInfoDetail> list = baseMapper.selectList(wrapper);
        List<PeInfoDetailStandardModel> standardModels = peInfoDetailStandardService.getPeInfoDetailStandardByPeId(peId);
        list.forEach(pojo -> {
            PeInfoDetailModel model = new PeInfoDetailModel();
            BeanUtils.copyProperties(pojo, model);
            if (standardModels != null && standardModels.size() > 0)
                model.setPeInfoDetailStandards(standardModels.stream().filter(s -> s.getPedId().equals(model.getPedId())).collect(Collectors.toList()));
            data.add(model);
        });
        return data;
    }

    @Override
    public void deletePeInfoToDeleteDetail(String peId) {
        QueryWrapper<PeInfoDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("Pe_Id", peId);
        List<PeInfoDetail> entitys = baseMapper.selectList(wrapper);
        List<String> pedIds = new ArrayList<>();
        entitys.forEach(entity -> {
            pedIds.add(entity.getPedId());
        });
        //先删除老的记录
        if (pedIds.size() > 0)
            peInfoDetailStandardService.deleteOldAllPeInfoStandard(pedIds);
        baseMapper.deletePeInfoDetailByPeId(peId);
    }
}
