package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysLoggerInfoDto;
import com.jpmsoft.userProducer.pojo.entity.SysLoggerInfo;

public interface ISysLoggerInfoService extends IService<SysLoggerInfo> {

    BaseResponse GetLoggerInfoList(SysLoggerInfoDto entity);

    BaseResponse DeleteLoggerInfo(SysLoggerInfoDto entity);

    BaseResponse insertSysLoggerInfos(SysLoggerInfoDto entity);


    BaseResponse refreshIfIdAndMode(int size);

    BaseResponse importLowInterface(String token);
}
