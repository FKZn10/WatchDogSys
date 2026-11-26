package com.jpmsoft.userProducer.pojo.dto;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付模板
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:02
 */
@Data
public class SysPayTemplateDto extends BaseModeRequest implements Serializable {


    private static final long serialVersionUID = -175384472114210822L;

    private String id;
    private Boolean ptStatus;
    private String ptName;
    private String ptCode;
    private String ptPayType;
    private String ptConType;
    private String ptExplain;
    private String ptForm;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
}
