package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.sms.SysSmsConfDto;
import com.jpmsoft.userProducer.pojo.entity.SysSmsConf;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/6 9:53
 */
public interface ISysSmsConfService extends IService<SysSmsConf> {

    BaseResponse createOrUpdateSmsConf(SysSmsConfDto request);

    BaseResponse getSmsConfList(SysSmsConfDto request);

    BaseResponse getSmsConfById(String id);

    SysSmsConf getCodeSms(String tempCode);
}
