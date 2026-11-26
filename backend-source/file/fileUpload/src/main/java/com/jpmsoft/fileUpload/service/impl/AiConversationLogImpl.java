package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.fileUpload.mapper.AiConversationLogMapper;
import com.jpmsoft.fileUpload.pojo.entity.AiConversationLog;
import com.jpmsoft.fileUpload.service.IAiConversationLogService;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AiConversationLogImpl extends ServiceImpl<AiConversationLogMapper, AiConversationLog> implements IAiConversationLogService {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public void createLog(String p, String answer, String aiType, String token) {
        AiConversationLog log = new AiConversationLog();
        log.setId(GenerateUUID.getUUID());
        log.setClPrompt(p);
        log.setClAnswer(answer);
        log.setAiType(aiType);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        log.setCreateUid(user.getId());
        log.setCreateUname(user.getName());
        log.setCreateTime(new Date());
        log.setClRawPrompt(p);
        log.setClRawAnswer(answer);
        baseMapper.insert(log);
    }
}
