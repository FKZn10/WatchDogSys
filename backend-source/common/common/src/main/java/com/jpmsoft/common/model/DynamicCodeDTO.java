package com.jpmsoft.common.model;


import lombok.Data;

import java.util.List;

/**
 * 动态代码入参
 */
@Data
public class DynamicCodeDTO {

    /**
     * 需要执行的代码
     */
    private String code;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * ID
     */
    private String dcId;
    /**
     * 入参
     */
    private List<Object> params;

    /**
     * 种类
     */
    private String dcCategory;
    /**
     * 类型
     */
    private String dcType;
}
