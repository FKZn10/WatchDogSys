package com.jpmsoft.custom.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用新增修改接口主逻辑执行完成后调用接口
 * 目前仅支持eureka内部接口
 *
 * @Author: 李文涛 aaron
 * @date: 2024/10/22 16:43
 */
@Data
public class CreateServiceCallApiDTO implements Serializable {

    private String apiType;
    private String apiUrl;
    private String customizeForm;
}
