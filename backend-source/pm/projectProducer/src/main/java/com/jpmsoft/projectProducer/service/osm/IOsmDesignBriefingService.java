package com.jpmsoft.projectProducer.service.osm;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmDesignBriefingDto;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmDesignBriefing;

/**
 * 设计交底(OsmDesignBriefing)表服务接口
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
public interface IOsmDesignBriefingService extends IService<OsmDesignBriefing> {


    BaseResponse getDesignBriefingList(OsmDesignBriefingDto osmDesignBriefingDto);

}
