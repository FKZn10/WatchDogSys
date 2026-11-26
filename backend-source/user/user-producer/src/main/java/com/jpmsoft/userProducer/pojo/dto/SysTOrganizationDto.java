package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/29 9:52
 */
@Data
public class SysTOrganizationDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 4974312991176499900L;

    private String toId;
    private String toName;
    private String toRemark;
    private String toCid;
    private String toStatus;
    private String toCreateUid;
    private String toCreateUname;
    private Date toCreateTime;

    private String toCName;
    private String toCAbbName;
}
