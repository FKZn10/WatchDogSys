package com.jpmsoft.common.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/6/25 13:58
 */
@Data
public class FanoutInfoDTO implements Serializable {

    private static final long serialVersionUID = 3113656305787469597L;
    private String table;
    private String primaryKey;
    private String stateKey;
    private String timeKey;
    private String id;
    private Integer state;
    private Date time;
}
