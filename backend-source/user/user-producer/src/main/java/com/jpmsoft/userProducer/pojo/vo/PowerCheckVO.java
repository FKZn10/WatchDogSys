package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 根据权限ID+上级权限编码
 * 获取其按钮权限（同时标记是否勾选）
 *
 * @Author: 李文涛 aaron
 * @date: 2023/7/25 14:33
 */
@Data
public class PowerCheckVO implements Serializable {

    private static final long serialVersionUID = 7197690573151711107L;
    private String powerCode;
    private String powerName;
    private Boolean checkBox=Boolean.FALSE;
}
