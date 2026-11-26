package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统消息
 *
 * @author: 李文涛 aaron
 * @date: 2022/11/11 14:25
 */
@Data
public class SysMessageDto extends BaseModeRequest implements Serializable {

    private String id;
    private String smTitle;
    private String smType;
    private String smContent;
    @NotBlank(message = BaseMsg.smtCodeNoNull)
    private String smtCode;
    private String smSendUser;
    private String smSendType;
    private String smRemark;
    private Date createTime;
    private String createUid;
    private String createUname;
    private String smSendUserName;
    private List<String> receiveUserIds;
}
