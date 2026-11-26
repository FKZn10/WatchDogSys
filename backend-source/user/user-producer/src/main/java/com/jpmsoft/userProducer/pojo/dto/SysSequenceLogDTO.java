package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

/**
 * 编码日志
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/15 9:37
 */
@Data
public class SysSequenceLogDTO extends BaseModeRequest {

    private String sslCode;
    private String sslRule;

}
