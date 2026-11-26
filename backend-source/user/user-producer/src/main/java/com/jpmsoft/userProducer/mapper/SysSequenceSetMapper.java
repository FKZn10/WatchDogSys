package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceSet;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysSequenceSetMapper extends BaseMapper<SysSequenceSet> {

    List<SysSequenceSetDto> getSysSequenceSetList(@Param("params") Map<String, Object> params);

    Integer getSysSequenceSetListCount(@Param("params") Map<String, Object> params);

    SysSequenceSetDto getSysSequenceSetByTcode(@Param("tCode") String tCode,@Param("teId") String teId);
}
