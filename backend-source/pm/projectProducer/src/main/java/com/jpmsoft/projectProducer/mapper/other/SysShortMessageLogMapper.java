package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysShortMessageLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysShortMessageLogMapper extends BaseMapper<SysShortMessageLog> {
    List<SysShortMessageLog> selectSysShortMessage(@Param("id")String id, @Param("contentType")String contentType, @Param("code")String code);
}
