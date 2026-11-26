package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.entity.SysTenantRule;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/28 16:28
 */
public interface ISysTenantRuleService extends IService<SysTenantRule> {

    SysTenantRule getTenantRuleByTeId(String teId);

    LogUserInfoVo setRedis(LogUserInfoVo user);


    /**
     * 校验租户是否满足规则
     *
     * @param id   用户ID
     * @param rule user:创建用户 project:创建项目  date:租户是否到期
     */
    void checkTenantRuleByUid(String id, String rule);

    /**
     * 校验租户是否满足规则
     *
     * @param token 用户token
     * @param rule  user:创建用户 project:创建项目  date:租户是否到期
     */
    void checkTenantRuleByToken(String token, String rule);
}
