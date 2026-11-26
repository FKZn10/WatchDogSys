package com.jpmsoft.userProducer.pojo.dto;


import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * ai规则配置
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AiRuleConfigurationDTO extends BaseModeRequest {

    private String id;
    private String type;
    private String prompt;
    private String status;
    private String createUid;
    private String createUname;
    private Date createTime;
}
