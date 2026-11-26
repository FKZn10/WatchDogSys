package com.jpmsoft.projectProducer.service.osm.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSiteMeetingMinutesDto;
import com.jpmsoft.projectProducer.mapper.osm.OsmSiteMeetingMinutesMapper;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmSiteMeetingMinutes;
import com.jpmsoft.projectProducer.service.osm.IOsmSiteMeetingMinutesService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工地例会纪要(OsmSiteMeetingMinutes)表服务实现类
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
@Service
public class OsmSiteMeetingMinutesServiceImpl extends ServiceImpl<OsmSiteMeetingMinutesMapper, OsmSiteMeetingMinutes> implements IOsmSiteMeetingMinutesService {


    @Override
    public BaseResponse getSiteMeetingMinutesList(OsmSiteMeetingMinutesDto request) {
        QueryMap<OsmSiteMeetingMinutesDto> params = new QueryMap<>(request);
        params.put("title").put("code").put("handlerUserName").put("state").put("createUname");
        IPage<OsmSiteMeetingMinutesDto> page = new Page<>(request.getPage(), request.getSize());
        List<OsmSiteMeetingMinutesDto> list = baseMapper.getSiteMeetingMinutesList(params.getQuery(),page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

}
