package com.jpmsoft.userProducer.pojo.vo;

import com.jpmsoft.userProducer.pojo.dto.SysMessageTempDto;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:01
 */
@Data
public class SysMessageTempVo extends SysMessageTempDto implements Serializable {

    private String sscJson;
    private String sscContent;
}
