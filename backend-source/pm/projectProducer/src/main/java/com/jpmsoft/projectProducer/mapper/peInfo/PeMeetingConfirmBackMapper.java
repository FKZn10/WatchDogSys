package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.PeMeetingConfirmBackResponse;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeMeetingConfirmBack;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PeMeetingConfirmBackMapper extends BaseMapper<PeMeetingConfirmBack> {

    List<PeMeetingConfirmBackResponse> getMeetingConfirmBackList(@Param("params") Map<String, Object> params);

    Integer getMeetingConfirmBackListCount(@Param("params") Map<String, Object> params);
}
