package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConEcoDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEco;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:28
 */
public interface ConEcoMapper extends BaseMapper<ConEco> {

    List<ConEcoDTO> getConEcoList(@Param("page") IPage<ConEcoDTO> page, @Param("params") Map<String, Object> params);


    ConEcoDTO getConEcoById(@Param("ecoId") String ecoId);
}
