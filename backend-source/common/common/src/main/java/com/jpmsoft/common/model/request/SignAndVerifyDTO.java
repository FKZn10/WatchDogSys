package com.jpmsoft.common.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 签名验签入参
 *
 * @Author: 李文涛 aaron
 * @date: 2024/8/6 17:42
 */
@Data
public class SignAndVerifyDTO implements Serializable {


    private String content;
    private String sign;
}
