package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 本次开通应用
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 15:11
 */
@Data
public class SysPayApplicationDTO extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = -550335079407665858L;
    private String paId;
    private String payId;
    private String appId;
}
