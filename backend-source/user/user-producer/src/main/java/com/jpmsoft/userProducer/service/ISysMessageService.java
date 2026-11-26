package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysMessageDto;
import com.jpmsoft.userProducer.pojo.entity.SysMessage;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:28
 */
public interface ISysMessageService extends IService<SysMessage> {

    BaseResponse createSysMessage(SysMessageDto request);

    BaseResponse getSysMessageList(SysMessageDto request);

    BaseResponse getSysMessageById(String id);
}
