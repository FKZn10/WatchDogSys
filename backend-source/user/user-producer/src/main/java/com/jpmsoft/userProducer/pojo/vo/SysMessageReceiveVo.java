package com.jpmsoft.userProducer.pojo.vo;

import com.jpmsoft.userProducer.pojo.dto.SysMessageReceiveDto;
import lombok.Data;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:56
 */
@Data
public class SysMessageReceiveVo extends SysMessageReceiveDto {

    private static final long serialVersionUID = 8721847466635088925L;

    private String smSendUserName;
    private String smType;
    private String smContent;
    private String smSendUser;
}
