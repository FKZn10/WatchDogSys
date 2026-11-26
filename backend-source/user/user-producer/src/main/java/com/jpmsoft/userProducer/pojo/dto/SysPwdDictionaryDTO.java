package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/26 11:46
 */
@Data
public class SysPwdDictionaryDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -6241020526703505281L;
    private String id;
    @NotBlank(message = BaseMsg.pwdNotNull)
    private String pdPwd;
    private String createUid;
    private String createUname;
    private Date createTime;
}
