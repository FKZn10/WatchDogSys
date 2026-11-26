package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DynamicsCodeDTO extends BaseModeRequest {

    private String dcName;
    private String dcCode;
    private String dcDescription;
    private String className;
    private String methodName;
    private String status;
    private String createUid;
    private String createUname;
    private Date createTime;
    /**
     * 种类
     */
    private String dcCategory;
    /**
     * 类型
     */
    private String dcType;
}
