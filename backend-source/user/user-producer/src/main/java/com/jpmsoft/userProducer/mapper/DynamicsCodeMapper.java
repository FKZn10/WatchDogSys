package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.DynamicsCode;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface DynamicsCodeMapper extends BaseMapper<DynamicsCode> {


    @Select("${sql}")
    List<Map> selectList(@Param("sql") String sql);


    @Select("${sql}")
    Map selectOne(@Param("sql") String sql);

    @Select("${sql}")
    Integer selectCount(@Param("sql") String sql);
}
