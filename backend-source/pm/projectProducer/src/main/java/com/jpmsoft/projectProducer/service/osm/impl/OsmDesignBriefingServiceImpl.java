package com.jpmsoft.projectProducer.service.osm.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmDesignBriefingDto;
import com.jpmsoft.projectProducer.mapper.osm.OsmDesignBriefingMapper;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmDesignBriefing;
import com.jpmsoft.projectProducer.service.osm.IOsmDesignBriefingService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设计交底(OsmDesignBriefing)表服务实现类
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
@Service
public class OsmDesignBriefingServiceImpl extends ServiceImpl<OsmDesignBriefingMapper, OsmDesignBriefing> implements IOsmDesignBriefingService {


    @Override
    public BaseResponse getDesignBriefingList(OsmDesignBriefingDto request) {
        QueryMap<OsmDesignBriefingDto> params = new QueryMap<>(request);
        params.put("conId").put("conName").put("title").put("code")
                .put("handlerUserName").put("usingSeals").put("sealsType")
                .put("handlerUserName").put("state").put("createUname");
        IPage<OsmDesignBriefingDto> page = new Page<>(request.getPage(), request.getSize());
        List<OsmDesignBriefingDto> list = baseMapper.getDesignBriefingList(params.getQuery(),page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }


}
