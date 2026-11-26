package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysMessageShieldDto;
import com.jpmsoft.userProducer.pojo.entity.SysMessageShield;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/15 9:40
 */
public interface ISysMessageShieldService extends IService<SysMessageShield> {

    BaseResponse createOrUpdateSysMessageShield(SysMessageShieldDto request);

    BaseResponse getSysMessageShieldList(SysMessageShieldDto request);

    List<SysMessageShieldDto> getSysMessageShieldBySmtId(String smtId);
}
