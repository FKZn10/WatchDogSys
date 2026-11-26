package com.jpmsoft.projectProducer.service.enghonorpunish.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngAwardForecastDto;
import com.jpmsoft.projectProducer.mapper.enghonorpunish.EngAwardForecastMapper;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngAwardForecast;
import com.jpmsoft.projectProducer.service.enghonorpunish.IEngAwardForecastService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EngAwardForecastImpl extends ServiceImpl<EngAwardForecastMapper, EngAwardForecast> implements IEngAwardForecastService {


    @Override
    public BaseResponse getEngAwardForecastList(EngAwardForecastDto request) {
        QueryMap<EngAwardForecastDto> params = new QueryMap<>(request);
        params.put("awardName").put("awardType").put("awardLevel").put("awardWinningProject").put("pName")
                .put("declarationUnit").put("declarationUnitName").put("awardDepartment").put("awardTime").put("createUname");
        IPage<EngAwardForecastDto> page = new Page<>(request.getPage(), request.getSize());
        List<EngAwardForecastDto> list = baseMapper.getEngAwardForecastList(params.getQuery(),page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }
}
