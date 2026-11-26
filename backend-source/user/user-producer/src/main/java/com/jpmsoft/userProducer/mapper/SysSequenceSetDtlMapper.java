package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDtlDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceSetDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysSequenceSetDtlMapper extends BaseMapper<SysSequenceSetDtl> {

    List<SysSequenceSetDtlDto> getSysSequenceSetDtlList(@Param("params") Map<String, Object> params);

    Integer getSysSequenceSetDtlListCount(@Param("params") Map<String, Object> params);
}
