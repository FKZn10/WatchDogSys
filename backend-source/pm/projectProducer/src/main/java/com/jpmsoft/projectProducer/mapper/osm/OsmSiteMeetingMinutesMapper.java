package com.jpmsoft.projectProducer.mapper.osm;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmSiteMeetingMinutesDto;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmSiteMeetingMinutes;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

/**
 * 工地例会纪要(OsmSiteMeetingMinutes)表数据库访问层
 *
 * @author c
 * @since 2024-12-05 11:55:00
 */
public interface OsmSiteMeetingMinutesMapper  extends BaseMapper<OsmSiteMeetingMinutes> {


    List<OsmSiteMeetingMinutesDto> getSiteMeetingMinutesList(@Param("params") Map<String, Object> params, @Param("page") IPage<OsmSiteMeetingMinutesDto> page);


}

