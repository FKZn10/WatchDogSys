package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceSet;


public interface ISysSequenceSetService extends IService<SysSequenceSet> {

    BaseResponse createOrUpdateSysSequenceSet(SysSequenceSetDto request);

    BaseResponse getSysSequenceSetList(SysSequenceSetDto request);

    BaseResponse getSysSequenceSetByTcode(String tCode, String teId);

    String generateCodeByTCode(String tCode, Object request, String teId);

    String generateCodeByTCode(String tCode, String pId, String conId, String secId,
                               Object request, String teId);

    BaseResponse refershAutoNo(String tCode, Object request, String teId);

    BaseResponse refershAutoNoByCustomized(String tCode, String pId, String conId, String secId,
                                           Object request, String teId);

    Boolean sequenceRestEnable(String tCode, String teId);

    int updateEntity(SysSequenceSet entity);
}
