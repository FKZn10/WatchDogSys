package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.dto.FileTemplateTableAndPrimaryDto;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysFileTemplateDto;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysFileTemplateMapper extends BaseMapper<SysFileTemplate> {

    List<FileTemplateTableAndPrimaryDto> getFileTemplateTableAndPrimaryByPid(@Param("tCodes") List<String> tCodes);


    @Select("select * from SYS_FileTemplate")
    List<SysFileTemplateDto> getAllFileTemp();
}
