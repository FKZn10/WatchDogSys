package com.jpmsoft.userProducer.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/15 9:39
 */
@Data
public class SysMessageShieldDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 50994152605392956L;

    private String id;
    @NotBlank(message = BaseMsg.msgTempNoNull)
    private String smtId;
    @NotBlank(message = BaseMsg.uIdIsNull)
    @JsonProperty("uId")
    private String uId;
    private Boolean msSendSys;
    private Boolean msSendSms;
    private String createUid;
    private String createUname;
    private Date createTime;

    @JsonProperty("uName")
    private String uName;
    private String smtName;
    private String smtCode;
}
