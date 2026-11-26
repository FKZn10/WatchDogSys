package com.jpmsoft.userProducer.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;

public interface ISmsServerService {

    /**
     * 发送短信验证码
     * 根据配置识别短信平台
     *
     * @param phoneNumber 手机号
     * @param type        类型 login:登录 secret:改密
     * @return
     */
    BaseResponse sendCodeSms(String phoneNumber, String type);

    /**
     * 发送短信验证码
     * 根据配置识别短信平台
     *
     * @param phoneNumber 手机号
     * @param type        类型 login:登录 secret:改密 submit:提交
     * @param checkUser   是否校验发送人是系统内用户
     * @param tempCode    短信模板编码 缺省取同类型最新一条可用
     * @return
     */
    BaseResponse sendCodeSms(String phoneNumber, String type,String tempCode, boolean checkUser);

    /**
     * 发送短信验证码
     * 根据配置识别短信平台
     *
     * @param user 用户登录缓存信息
     * @param type 类型 login:登录 secret:改密
     * @return
     */
    BaseResponse sendCodeSmsByToken(LogUserInfoVo user, String type);

    /**
     * 校验验证码
     *
     * @param phoneNumber
     * @param type        类型 login:登录 secret:改密 sumit:表单提交
     * @param code
     * @return 验证通过 true
     */
    boolean checkSmsCode(String phoneNumber, String type, String code);
}
