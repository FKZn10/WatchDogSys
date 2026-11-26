package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.projectProducer.mapper.project.PreProjectReserveDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveChangeDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserveDtl;
import com.jpmsoft.projectProducer.service.project.IPreProjectReserveDtlService;
import org.springframework.stereotype.Service;

@Service
public class PreProjectReserveDtlServiceImpl extends ServiceImpl<PreProjectReserveDtlMapper, PreProjectReserveDtl> implements IPreProjectReserveDtlService {
    @Override
    public PreProjectReserveDtl getpreProjectReservePprById(String id) {
        LambdaQueryWrapper<PreProjectReserveDtl> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PreProjectReserveDtl::getPprId,id).orderByDesc(PreProjectReserveDtl::getCreateTime).last(" limit 1 ");
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public PreProjectReserveChangeDTO getpreProjectReservePprByIdData(String id) {

        return baseMapper.getpreProjectReservePprByIdData(id);
    }
}
