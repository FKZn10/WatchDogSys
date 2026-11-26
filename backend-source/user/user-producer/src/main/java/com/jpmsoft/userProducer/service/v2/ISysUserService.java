package com.jpmsoft.userProducer.service.v2;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.pojo.entity.SysUser;

import javax.servlet.http.HttpServletRequest;

@Deprecated
public interface ISysUserService extends IService<SysUser> {

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    BaseResponse login(UserDto user, String sessionId, HttpServletRequest request);

    /**
     * @param user
     * @param type web app 登录平台（区分web和APP  app无验证码）
     * @return
     */
    BaseResponse loginType(UserDto user, String type, HttpServletRequest request);


}
