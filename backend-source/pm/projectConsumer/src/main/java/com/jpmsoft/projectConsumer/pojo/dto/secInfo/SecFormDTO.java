package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 考核评价表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 16:55
 */
@Data
public class SecFormDTO extends BaseModeRequest implements Serializable {

    private static final long serialVersionUID = 3861629447402120912L;
    @TableId(type = IdType.INPUT)
    @JsonProperty("fId")
    private String fId;
    @JsonProperty("fName")
    private String fName;
    @JsonProperty("fType")
    private String fType;
    @JsonProperty("fVersion")
    private Integer fVersion;
    @JsonProperty("fCreateUid")
    private String fCreateUid;
    @JsonProperty("fCreateUname")
    private String fCreateUname;
    @JsonProperty("fCreateTime")
    private Date fCreateTime;
    @JsonProperty("fState")
    private String fState;
    @JsonProperty("fRemark")
    private String fRemark;

    private List<SecFormDetailDTO> secFormDetails;
}
