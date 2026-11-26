package com.jpmsoft.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/23 13:46
 */
@Data
public class KeyValueDTO implements Serializable {

    private static final long serialVersionUID = -6350478052747918027L;
    private String theKey;
    private String theValue;
}
