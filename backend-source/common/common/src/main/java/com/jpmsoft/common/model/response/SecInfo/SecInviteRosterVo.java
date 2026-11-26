package com.jpmsoft.common.model.response.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 邀请名单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/23 10:29
 */
@Data
public class SecInviteRosterVo implements Serializable {


    private static final long serialVersionUID = -896215526362643580L;
    private String id;
    @JsonProperty("dId")
    private String dId;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    private String cName;
    @JsonProperty("cAbbName")
    private String cAbbName;
    private String sirAptitude;
    private String sirRemark;
    private Integer sirSort;
}
