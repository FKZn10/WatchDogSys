package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jpmsoft.userProducer.pojo.dto.OutlineDatasourceDto;
import com.jpmsoft.userProducer.pojo.entity.OutlineDatasource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OutlineDatasourceMapper extends BaseMapper<OutlineDatasource> {
    @Select("SELECT * FROM outline_datasource ${ew.customSqlSegment}")
    IPage<OutlineDatasource> selectOutlineByWrapper(IPage<OutlineDatasource> page, @Param(Constants.WRAPPER) QueryWrapper<OutlineDatasourceDto> wrapper);

    List<String> getOdIdsByOplId(@Param("oplId") String oplId);

    List<String> getOdIdsByOslId(@Param("oslId") String oslId);
}
