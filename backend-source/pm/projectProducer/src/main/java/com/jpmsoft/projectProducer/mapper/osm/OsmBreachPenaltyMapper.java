package com.jpmsoft.projectProducer.mapper.osm;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmBreachPenaltyDto;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmBreachPenalty;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 违约处罚(OsmBreachPenalty)
 *
 * @author c
 * @since 2024-12-05 11:54:57
 */
public interface OsmBreachPenaltyMapper extends BaseMapper<OsmBreachPenalty> {


    List<OsmBreachPenaltyDto> getBreachPenaltyList(@Param("params") Map<String, Object> params, @Param("page") IPage<OsmBreachPenaltyDto> page);


}

