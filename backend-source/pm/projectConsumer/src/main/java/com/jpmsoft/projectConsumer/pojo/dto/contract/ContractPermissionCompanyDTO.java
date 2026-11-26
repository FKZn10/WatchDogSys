package com.jpmsoft.projectConsumer.pojo.dto.contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 合同权限配置-合同参与单位
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 16:44
 */
@Data
public class ContractPermissionCompanyDTO extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = -16779815024658550L;
    private String id;
    @JsonProperty("cId")
    private String cId;
    private String conId;
    private Date createTime;
    private String createUid;
    private String createUname;
    private Integer cpcSort;

    @JsonProperty("cType")
    private String cType;
    @JsonProperty("cCreditcode")
    private String cCreditcode;
    @JsonProperty("cContactperson")
    private String cContactperson;
    @JsonProperty("cContactpersontel")
    private String cContactpersontel;
    @JsonProperty("cUnitlegalperson")
    private String cUnitlegalperson;
    @JsonProperty("cName")
    private String cName;
}
