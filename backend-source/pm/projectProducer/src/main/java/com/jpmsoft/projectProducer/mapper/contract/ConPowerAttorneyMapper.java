package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConPowerAttorneyDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPowerAttorney;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/7 9:48
 */
public interface ConPowerAttorneyMapper extends BaseMapper<ConPowerAttorney> {

    List<ConPowerAttorneyDTO> getConPowerAttorneyList(@Param("page") IPage<ConPowerAttorneyDTO> page, @Param("params") Map<String, Object> params);

    ConPowerAttorneyDTO getConPowerAttorneyById(@Param("id") String id);
}
