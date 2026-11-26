package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 招标工作方案邀请名单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/23 10:29
 */
@Data
public class SecInviteDetailDTO extends SecInviteRosterDto implements Serializable {

    private static final long serialVersionUID = -5657226914576881927L;
    private String wsId;
    @JsonProperty("cName")
    private String cName;
}
