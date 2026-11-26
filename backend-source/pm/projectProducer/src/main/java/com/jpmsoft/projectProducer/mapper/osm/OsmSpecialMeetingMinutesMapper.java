package com.jpmsoft.projectProducer.mapper.osm;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSpecialMeetingMinutesDto;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmSpecialMeetingMinutes;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 专题会议纪要(OsmSpecialMeetingMinutes)表数据库访问层
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
public interface OsmSpecialMeetingMinutesMapper extends BaseMapper<OsmSpecialMeetingMinutes> {


    List<OsmSpecialMeetingMinutesDto> getSpecialMeetingMinutesList(@Param("params") Map<String, Object> params, @Param("page") IPage<OsmSpecialMeetingMinutesDto> page);

}

