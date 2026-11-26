package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface FileMapper extends BaseMapper<File> {


    @Select("select  count(*) from sys_file_${stCode} where Flow_Id=#{flowId}")
    Integer existsByFlow(@Param("flowId") String flowId,@Param("stCode") String stCode);
}
