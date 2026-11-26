package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageModel;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysShortMessageMapper extends BaseMapper<SysShortMessage> {

    List<SysShortMessageModel> getSysShortMessageList(@Param("params") Map<String, Object> params);

    Integer getSysShortMessageListCount(@Param("params") Map<String, Object> params);
}
