package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/25 22:29
 */
@Data
public class SysAuditLogQueryDto extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 8631282504799168197L;
    @NotBlank(message = "id不能为空！")
    private String salFormId;
    private String createUname;
    private String salType;
    private String fileTitle;
}
