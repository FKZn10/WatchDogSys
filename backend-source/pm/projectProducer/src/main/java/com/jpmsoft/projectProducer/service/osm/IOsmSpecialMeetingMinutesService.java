package com.jpmsoft.projectProducer.service.osm;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSpecialMeetingMinutesDto;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmSpecialMeetingMinutes;

/**
 * 专题会议纪要(OsmSpecialMeetingMinutes)表服务接口
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
public interface IOsmSpecialMeetingMinutesService extends IService<OsmSpecialMeetingMinutes> {


    BaseResponse getSpecialMeetingMinutesList(OsmSpecialMeetingMinutesDto osmDesignBriefingDto);

}
