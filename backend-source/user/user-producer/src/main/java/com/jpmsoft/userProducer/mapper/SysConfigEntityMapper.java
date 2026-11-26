package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysConfigEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface SysConfigEntityMapper extends BaseMapper<SysConfigEntity> {

    @Select("${sql}")
    Boolean executeConfigAction(@Param("sql") String sql);
}
