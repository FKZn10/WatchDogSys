package com.jpmsoft.projectProducer.mapper.construction;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.construction.TechnicalCheckDTO;
import com.jpmsoft.projectProducer.pojo.entity.construction.TechnicalCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 16:23
 */
public interface TechnicalCheckMapper extends BaseMapper<TechnicalCheck> {

    List<TechnicalCheckDTO> getTechnicalCheckList(@Param("page") IPage<TechnicalCheckDTO> page, @Param("params") Map<String, Object> params);


    TechnicalCheckDTO getTechnicalCheckById(String id);
}
