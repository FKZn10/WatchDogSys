package com.jpmsoft.projectProducer.service.osm.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSpecialMeetingMinutesDto;
import com.jpmsoft.projectProducer.mapper.osm.OsmSpecialMeetingMinutesMapper;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmSpecialMeetingMinutes;
import com.jpmsoft.projectProducer.service.osm.IOsmSpecialMeetingMinutesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 专题会议纪要(OsmSpecialMeetingMinutes)表服务实现类
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
@Service
public class OsmSpecialMeetingMinutesServiceImpl extends ServiceImpl<OsmSpecialMeetingMinutesMapper, OsmSpecialMeetingMinutes> implements IOsmSpecialMeetingMinutesService {


    @Override
    public BaseResponse getSpecialMeetingMinutesList(OsmSpecialMeetingMinutesDto request) {
        QueryMap<OsmSpecialMeetingMinutesDto> params = new QueryMap<>(request);
        params.put("title").put("code").put("handlerUserName").put("state").put("createUname");
        IPage<OsmSpecialMeetingMinutesDto> page = new Page<>(request.getPage(), request.getSize());
        List<OsmSpecialMeetingMinutesDto> list = baseMapper.getSpecialMeetingMinutesList(params.getQuery(),page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }


}
