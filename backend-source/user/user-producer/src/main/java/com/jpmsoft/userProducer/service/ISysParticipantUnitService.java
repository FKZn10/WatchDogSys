package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysParticipantUnitDto;
import com.jpmsoft.userProducer.pojo.entity.SysParticipantUnit;


public interface ISysParticipantUnitService extends IService<SysParticipantUnit> {

    BaseResponse createSysParticipantUnit(SysParticipantUnitDto request);

    BaseResponse getSysParticipantUnitList(SysParticipantUnitDto request,String token);

    BaseResponse getSysParticipantUnitDistinctList(SysParticipantUnitDto request,String token);

    BaseResponse deleteSysParticipantUnitModelById(String puId);

    BaseResponse getSysParticipantUnitByPidGroupType(String pId,String token);
}
