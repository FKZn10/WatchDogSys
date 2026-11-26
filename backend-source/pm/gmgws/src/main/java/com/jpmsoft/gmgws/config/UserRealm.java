package com.jpmsoft.gmgws.config;


import com.jpmsoft.gmgws.pojo.entity.SysUser;
import com.jpmsoft.gmgws.pojo.vo.LogUserInfoVo;
import com.jpmsoft.gmgws.utils.RedisUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/9 11:03
 */
public class UserRealm  extends AuthorizingRealm {

    @Resource
    private RedisUtils redisUtils;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser user = (SysUser) principalCollection.getPrimaryPrincipal();
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token.getUsername());
        if (user == null) {
            return null;
        }
        return new SimpleAuthenticationInfo(user, user.getCode(), getName());
    }
}
