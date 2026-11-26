package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 招标工作方案及询价文件邀请名单
 *
 */
@Data
public class SecInviteDetailInquiryDocDTO extends SecInviteRosterDto implements Serializable {

    private static final long serialVersionUID = -5657226914576881927L;
    private String wsidId;
    @JsonProperty("cName")
    private String cName;

}
