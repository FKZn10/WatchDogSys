package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.OutlineDatasourceDTO;
import com.jpmsoft.fileUpload.pojo.entity.OutlineDatasource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OutlineDatasourceMapper extends BaseMapper<OutlineDatasource> {
    List<OutlineDatasourceDTO> getOutlineDatasourceByIds(@Param("ids") List<String> ids);
}
