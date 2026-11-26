package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceCurrentDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceCurrentCode;

import java.util.List;


public interface ISysSequenceCurrentCodeService extends IService<SysSequenceCurrentCode> {

    SysSequenceCurrentCode getSysSequenceCurrentCode(String sId, String ccReset);

    /**
     * 获取所有末尾规则为（或者，并且）
     *
     * @param sId
     * @param rule
     * @return
     */
    List<SysSequenceCurrentCode> getSysSequenceCurrentCodes(String sId, String rule);


    BaseResponse getSysSequenceCurrentCodeBySid(SysSequenceCurrentDto request);

    /**
     * 修改序号和重置标识
     *
     * @param request
     * @return
     */
    BaseResponse createOrUpdateSequenceCurrentCode(SysSequenceCurrentDto request);

    BaseResponse delSequenceCurrentCodeById(String id);
}
