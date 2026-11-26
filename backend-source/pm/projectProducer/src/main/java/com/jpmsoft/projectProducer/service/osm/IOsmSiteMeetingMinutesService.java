package com.jpmsoft.projectProducer.service.osm;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSiteMeetingMinutesDto;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmSiteMeetingMinutes;

/**
 * 工地例会纪要(OsmSiteMeetingMinutes)表服务接口
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
public interface IOsmSiteMeetingMinutesService extends IService<OsmSiteMeetingMinutes> {


    BaseResponse getSiteMeetingMinutesList(OsmSiteMeetingMinutesDto osmSiteMeetingMinutesDto);


}
