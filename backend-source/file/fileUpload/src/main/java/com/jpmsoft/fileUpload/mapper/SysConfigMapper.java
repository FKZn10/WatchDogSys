package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysConfigDto;
import com.jpmsoft.fileUpload.pojo.entity.SysConfig;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysConfigMapper extends BaseMapper<SysConfig> {


    @Select("select * from SYS_Config")
    List<SysConfigDto> getAllConfig();

    @Select("select Con_Code from SYS_Config")
    List<String> getAllConfigCode();
}
