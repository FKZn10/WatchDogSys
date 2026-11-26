package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageModel;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessage;

import java.util.List;


public interface ISysShortMessageService extends IService<SysShortMessage> {

    BaseResponse getSysShortMessageList(SysShortMessageModel request);

    BaseResponse createOrUpdateShortMessage(SysShortMessageModel request);

    /**
     * 光明项目发送短信
     *
     * @return
     */
    BaseResponse gmSendSmsByMessage();

    /**
     * 批量插入待发池
     * @param request
     * @return
     */
    BaseResponse batchCreateShortMessage(List<SysShortMessageModel> request);


}
