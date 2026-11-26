package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.userProducer.mapper.AiConversationLogMapper;
import com.jpmsoft.userProducer.pojo.entity.AiConversationLog;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.IAiConversationLogService;
import com.jpmsoft.userProducer.utils.RedisUtils;
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
        baseMapper.insert(log);
    }
}
