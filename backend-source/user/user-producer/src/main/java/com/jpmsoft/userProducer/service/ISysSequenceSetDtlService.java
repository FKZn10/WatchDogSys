package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDtlDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceSetDtl;


public interface ISysSequenceSetDtlService extends IService<SysSequenceSetDtl> {

    BaseResponse createOrUpdateSysSequenceSetDtl(SysSequenceSetDtlDto request);

    BaseResponse getSysSequenceSetDtlList(SysSequenceSetDtlDto request);

    BaseResponse deleteSysSequenceSetDtlById(String sdId);
}
