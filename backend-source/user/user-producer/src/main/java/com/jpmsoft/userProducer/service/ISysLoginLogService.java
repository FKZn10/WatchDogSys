package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysLoginLogDto;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.pojo.entity.SysLoginLog;
import com.jpmsoft.userProducer.pojo.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:26
 */
public interface ISysLoginLogService extends IService<SysLoginLog> {

    void create(SysLoginLog pojo);

    void create(HttpServletRequest request, String uId, String loginType, UserDto user);

    Long getCount();

    BaseResponse getSysLoginLogList(SysLoginLogDto request);

    /**
     * 获取用户多久没有登录过系统
     *
     * @param user
     * @param loginType 登录方式
     * @return
     */
    Integer getUserUseInterval(SysUser user, String loginType);

}
