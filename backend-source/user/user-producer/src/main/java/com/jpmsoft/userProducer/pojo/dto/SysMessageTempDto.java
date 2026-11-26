package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import com.jpmsoft.userProducer.constants.BaseMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 13:58
 */
@Data
public class SysMessageTempDto extends BaseModeRequest implements Serializable {

    private String id;
    private String smtName;
    @NotBlank(message = BaseMsg.smtCodeNoNull)
    private String smtCode;
    private String smtType;
    private String smtContent;
    private String smtSql;
    private Date createTime;
    private String createUid;
    private Date updateTime;
    private String updateUid;
    public boolean smtState;
    public String smtFrequency;
    public boolean smtNotHoliday;
    public String smtSmCode;
    public String smtSmContent;

    private String createUname;
    private String updateUname;
}
