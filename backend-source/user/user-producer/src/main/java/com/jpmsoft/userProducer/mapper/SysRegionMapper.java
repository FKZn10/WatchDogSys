package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysRegion;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/27 9:41
 */
public interface SysRegionMapper extends BaseMapper<SysRegion> {


    @Select("select Sr_Code from Sys_Region where sr_status=1")
    List<String> codes();

    void  bathInsert(@Param("data") List<SysRegion> data);
}
