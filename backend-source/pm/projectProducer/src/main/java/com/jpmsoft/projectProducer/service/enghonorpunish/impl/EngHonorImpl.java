package com.jpmsoft.projectProducer.service.enghonorpunish.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngAwardForecastDto;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngHonorDto;
import com.jpmsoft.projectProducer.mapper.enghonorpunish.EngHonorMapper;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngHonor;
import com.jpmsoft.projectProducer.service.enghonorpunish.IEngHonorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EngHonorImpl extends ServiceImpl<EngHonorMapper, EngHonor> implements IEngHonorService {


    @Override
    public BaseResponse getEngHonorList(EngHonorDto request) {
        QueryMap<EngHonorDto> params = new QueryMap<>(request);
        params.put("awardName").put("awardType").put("awardLevel").put("awardWinningProject").put("pName")
                .put("winningUnit").put("winningUnitName").put("awardDepartment").put("awardTime").put("createUname");
        IPage<EngHonorDto> page = new Page<>(request.getPage(), request.getSize());
        List<EngHonorDto> list = baseMapper.getEngHonorList(params.getQuery(),page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

}
