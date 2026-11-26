package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageLogModel;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessageLog;


public interface ISysShortMessageLogService extends IService<SysShortMessageLog> {

    BaseResponse getSysShortMessageLogList(SysShortMessageLogModel request);
}
