package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 登录信息
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/15 8:56
 */
@Data
public class LogInfoVo implements Serializable {

    private static final long serialVersionUID = 441519539834326796L;

    private String token;
    private Date expireAt;
    /**
     * 是否需要验证短信验证码
     */
    private boolean checkSmsCode = false;
    /**
     * 是否需要补全手机号
     */
    private boolean completionTel = false;
    private List<String> roles;
    private List<AuthorityBtnVo> btnRoles;
    private boolean authorizeBtn;
    private LogUserInfoVo user;
}
