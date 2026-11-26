package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.dto.FileTemplateDto;
import com.jpmsoft.userProducer.pojo.entity.FileTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface FileTemplateMapper extends BaseMapper<FileTemplate> {


    List<FileTemplateDto> getAllFileTemplateList(@Param("teId") String teId);
}
