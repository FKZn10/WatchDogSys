package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.AiConversationLog;

public interface IAiConversationLogService extends IService<AiConversationLog> {


    void createLog(String p,String answer,String aiType,String token);
}
