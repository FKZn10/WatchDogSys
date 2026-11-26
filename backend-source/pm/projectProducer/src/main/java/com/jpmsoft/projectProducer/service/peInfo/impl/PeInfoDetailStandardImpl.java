package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoDetailStandardModel;
import com.jpmsoft.projectProducer.mapper.peInfo.PeInfoDetailStandardMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoDetailStandard;
import com.jpmsoft.projectProducer.service.peInfo.IPeInfoDetailStandardService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PeInfoDetailStandardImpl extends ServiceImpl<PeInfoDetailStandardMapper, PeInfoDetailStandard> implements IPeInfoDetailStandardService {
    @Override
    public void createPeInfoStandard(List<PeInfoDetailStandardModel> models) {
        //新增
        baseMapper.insertBatchs(models);
    }

    @Override
    public void deleteOldAllPeInfoStandard(List<String> pedIds) {
        //删除所有
        baseMapper.deletePeInfoStandardByPedIds(pedIds);
    }

    @Override
    public List<PeInfoDetailStandardModel> getPeInfoDetailStandardByPeId(String peId) {
        return baseMapper.getPeInfoDetailStandardByPeId(peId);
    }
}
