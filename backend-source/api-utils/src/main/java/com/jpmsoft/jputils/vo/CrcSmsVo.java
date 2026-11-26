package com.jpmsoft.jputils.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/16 14:32
 */
@Data
public class CrcSmsVo implements Serializable {

    private static final long serialVersionUID = 1975605462434347496L;
    private Integer code;
    private String message;
    private Object data;
}
