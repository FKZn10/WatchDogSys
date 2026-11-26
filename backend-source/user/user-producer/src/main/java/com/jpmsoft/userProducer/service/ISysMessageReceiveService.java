package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.MessageReceiveBathDto;
import com.jpmsoft.userProducer.pojo.dto.SysMessageReceiveDto;
import com.jpmsoft.userProducer.pojo.entity.SysMessageReceive;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:28
 */
public interface ISysMessageReceiveService extends IService<SysMessageReceive> {

    void createMessageReceive(List<String> uIds, String smId);

    BaseResponse createMessageReceiveBath(MessageReceiveBathDto request);

    BaseResponse createMessageReceive(SysMessageReceiveDto request);

    BaseResponse getMessageReceiveList(SysMessageReceiveDto request);

    BaseResponse updateMessageRead(String id);

}
