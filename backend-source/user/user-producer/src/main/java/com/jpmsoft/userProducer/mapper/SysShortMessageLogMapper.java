package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageLogModel;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessageLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysShortMessageLogMapper extends BaseMapper<SysShortMessageLog> {

    List<SysShortMessageLogModel> getSysShortMessageLogList(@Param("params") Map<String, Object> params);

    Integer getSysShortMessageLogListCount(@Param("params") Map<String, Object> params);
}
