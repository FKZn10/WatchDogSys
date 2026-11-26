package com.jpmsoft.userProducer.pojo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.userProducer.constants.BaseMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户岗位关系表
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/6 15:23
 */
@Data
public class SysPositionUserDto implements Serializable {

    private static final long serialVersionUID = -6061739374605638757L;

    private String puId;
    @NotBlank(message = BaseMsg.positionIsNull)
    @JsonProperty("pId")
    private String pId;
    @NotBlank(message = BaseMsg.uIdIsNull)
    @JsonProperty("uId")
    private String uId;
    private Integer puSort;
    private String type;
    @JsonProperty("dId")
    private String dId;
    private Boolean puMainDuty;
    private Boolean puDuty;
    private Boolean puMainDep;
}
