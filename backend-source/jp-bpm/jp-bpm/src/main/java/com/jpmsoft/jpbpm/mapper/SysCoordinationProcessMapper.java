package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.CoordinationProcessResponse;
import com.jpmsoft.jpbpm.pojo.entity.SysCoordinationProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysCoordinationProcessMapper extends BaseMapper<SysCoordinationProcess> {

    List<CoordinationProcessResponse> getUserCoordinationProcessList(@Param("params") Map<String, Object> params);
    Integer getUserCoordinationProcessListCount(@Param("params") Map<String, Object> params);


    List<CoordinationProcessResponse> getCoordinationProcessList(@Param("params") Map<String, Object> params);
    Integer getCoordinationProcessListCount(@Param("params") Map<String, Object> params);
}
