package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.dto.SysParticipantUnitDto;
import com.jpmsoft.userProducer.pojo.entity.SysParticipantUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysParticipantUnitMapper extends BaseMapper<SysParticipantUnit> {

    List<SysParticipantUnitDto> getSysParticipantUnitList(@Param("params") Map<String, Object> params);


    List<SysParticipantUnitDto> getSysParticipantUnitDistinctList(@Param("page") IPage<SysParticipantUnitDto> page,@Param("params")  SysParticipantUnitDto params);
    Integer getSysParticipantUnitListCount(@Param("params") Map<String, Object> params);


    List<SysParticipantUnitDto> getSysParticipantUnitByPidGroupType(@Param("pId") String pId);
}
