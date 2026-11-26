package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysLoggerInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysLoggerInfoMapper extends BaseMapper<SysLoggerInfo> {




    @Select("select * from sys_loggerinfo where if_id is null and l_Fun_Module is null limit #{size}")
    List<SysLoggerInfo> getAllNull(@Param("size")  int size);
}
