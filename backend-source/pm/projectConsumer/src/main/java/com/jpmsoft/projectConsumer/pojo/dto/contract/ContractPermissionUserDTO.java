package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同权限配置-合同参与人员
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 15:16
 */
@Data
public class ContractPermissionUserDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = -6522314873552930815L;

    private String id;
    @JsonProperty("uId")
    private String uId;
    private String conId;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Integer cpuSort;

    @JsonProperty("uName")
    private String uName;
    @JsonProperty("cName")
    private String cName;
    private String conName;
    @JsonProperty("pName")
    private String pName;
}
