package com.jpmsoft.projectProducer.mapper.enghonorpunish;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.enghonorpunish.EngPunishDto;
import com.jpmsoft.projectProducer.pojo.entity.enghonorpunish.EngPunish;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工程处罚(EngPunish)
 *
 * @since 2024-12-03 10:40:10
 */
public interface EngPunishMapper extends BaseMapper<EngPunish> {

    List<EngPunishDto> getEngPunishList(@Param("params") Map<String, Object> params, @Param("page") IPage<EngPunishDto> page);


}

