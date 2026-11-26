package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.mapper.project.ProWnDetailMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProWnDetailDTO;

import com.jpmsoft.projectProducer.pojo.entity.project.ProWnDetail;
import com.jpmsoft.projectProducer.service.project.IProWnDetailService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProWnDetailImpl extends ServiceImpl<ProWnDetailMapper, ProWnDetail> implements IProWnDetailService  {
    @Resource
    private ProWnDetailMapper proWnDetailMapper;


    @Override
    public BaseResponse getProWnDetailById(String pwId) {
        return BaseResponse.ok(proWnDetailMapper.getProWnDetailById(pwId));
    }

    @Override
    public BaseResponse getProWnDetailByPid(String pwPid) {
        List<ProWnDetailDTO> datas = proWnDetailMapper.getProWnDetailByPid(pwPid);
        return BaseResponse.ok(datas, datas.size());
    }
}