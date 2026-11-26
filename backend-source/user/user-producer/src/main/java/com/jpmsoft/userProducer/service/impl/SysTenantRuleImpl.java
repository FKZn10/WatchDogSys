package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysTenantRuleMapper;
import com.jpmsoft.userProducer.pojo.entity.SysTenantRule;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.ISysTenantRuleService;
import com.jpmsoft.userProducer.utils.DateUtil;
import com.jpmsoft.userProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/28 16:29
 */
@Service
public class SysTenantRuleImpl extends ServiceImpl<SysTenantRuleMapper, SysTenantRule> implements ISysTenantRuleService {

    @Resource
    private RedisUtils redisUtils;


    @Override
    public SysTenantRule getTenantRuleByTeId(String teId) {
        QueryWrapper<SysTenantRule> wrapper = new QueryWrapper<>();
        wrapper.eq("Te_Id", teId);
        List<SysTenantRule> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) return data.get(0);
        return null;
    }

    @Override
    public LogUserInfoVo setRedis(LogUserInfoVo user) {
        if (user.isEnableTenant()) {
            if (ValidateUtil.isNotEmpty(user.getTeId())) {
                SysTenantRule rule = getTenantRuleByTeId(user.getTeId());
                user.setTrDate(rule.getTrDate());
                user.setTrMaxProject(rule.getTrMaxProject());
                user.setTrMaxUser(rule.getTrMaxUser());
                checkTenantRule(user, "date");
            }
        }
        return user;
    }


    @Override
    public void checkTenantRuleByUid(String id, String rule) {
        LogUserInfoVo userInfo = redisUtils.getUserInfoByUid(id);
        checkTenantRule(userInfo, rule);
    }

    @Override
    public void checkTenantRuleByToken(String token, String rule) {
        LogUserInfoVo userInfo = redisUtils.getUserInfoByToken(token);
        checkTenantRule(userInfo, rule);
    }


    public void checkTenantRule(LogUserInfoVo userInfo, String rule) {
        if (userInfo.isEnableTenant()) {//开启租户模式则有效
            if (rule.equalsIgnoreCase("date")) {
                if (DateUtil.dayCompare(new Date(), userInfo.getTrDate()))
                    throw new JPMException(BaseMsg.tenantExpire);
            }
            if (rule.equalsIgnoreCase("user")) {
                Integer userCount = baseMapper.getTenantUserCount(userInfo.getTeId());
                if (userCount != null && userCount >= userInfo.getTrMaxUser())
                    throw new JPMException(BaseMsg.tenantUserMax);
            }
            if (rule.equalsIgnoreCase("project")) {
                Integer projectCount = baseMapper.getTenantProjectCount(userInfo.getTeId());
                if (projectCount != null && projectCount >= userInfo.getTrMaxProject())
                    throw new JPMException(BaseMsg.tenantProjectMax);
            }
        }
    }
}
