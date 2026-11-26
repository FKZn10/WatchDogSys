package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.entity.AiConversationLog;

public interface IAiConversationLogService extends IService<AiConversationLog> {


    void createLog(String p,String answer,String aiType,String token);
}
