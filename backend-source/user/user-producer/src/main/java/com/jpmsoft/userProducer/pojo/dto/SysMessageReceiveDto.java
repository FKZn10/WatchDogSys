package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息接收人员
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:26
 */
@Data
public class SysMessageReceiveDto extends BaseModeRequest implements Serializable {
    private static final long serialVersionUID = 6726743161010386333L;

    private String id;
    @NotBlank(message = BaseMsg.smIdNoNull)
    private String smId;
    private String smrUser;
    private String smrSendStatus;
    private Date smrSendTime;
    private Boolean smrRead;
    private Date smrReadTime;

    private String smrUserName;
    private String smTitle;
}
