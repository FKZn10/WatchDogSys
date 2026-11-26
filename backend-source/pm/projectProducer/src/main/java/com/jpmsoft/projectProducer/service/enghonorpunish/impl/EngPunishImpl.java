package com.jpmsoft.projectProducer.service.enghonorpunish.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngAwardForecastDto;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngPunishDto;
import com.jpmsoft.projectProducer.mapper.enghonorpunish.EngPunishMapper;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngPunish;
import com.jpmsoft.projectProducer.service.enghonorpunish.IEngPunishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EngPunishImpl extends ServiceImpl<EngPunishMapper, EngPunish> implements IEngPunishService {


    @Override
    public BaseResponse getEngPunishList(EngPunishDto request) {
        QueryMap<EngPunishDto> params = new QueryMap<>(request);
        params.put("punishName").put("punishType").put("punishProject").put("punishUnit")
                .put("punishTime").put("createUname").put("pName");
        IPage<EngPunishDto> page = new Page<>(request.getPage(), request.getSize());
        List<EngPunishDto> list = baseMapper.getEngPunishList(params.getQuery(),page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }


}
