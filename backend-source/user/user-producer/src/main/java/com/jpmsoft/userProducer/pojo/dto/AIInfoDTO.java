package com.jpmsoft.userProducer.pojo.dto;


import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * AI配置信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AIInfoDTO extends BaseModeRequest {

    private String id;
    private String url;
    private String aiModel;
    private String apiKey;
    private String aiType;
    private String description;
    private String requestBody;
    private String status;
    private String instruction;
    private String createUid;
    private String createUname;
    private Date createTime;

}
