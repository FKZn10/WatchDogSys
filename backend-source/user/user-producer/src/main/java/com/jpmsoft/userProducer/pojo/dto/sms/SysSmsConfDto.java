package com.jpmsoft.userProducer.pojo.dto.sms;

import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信配置
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/6 9:50
 */
@Data
public class SysSmsConfDto extends BaseModeRequest implements Serializable {

    public String id;
    public String sscType;
    public String sscPlatform;
    public String sscUrl;
    public String sscTempCode;
    public String sscSign;
    public String sscAccess;
    public String sscSecret;
    public String sscRegion;
    public String sscRemark;
    public Integer sscStatus;
    public String createUname;
    public Date createTime;
    public String createUid;
    private String sscJson;
    private String sscContent;
    private String sscTenant;
    private String sscSubmiter;
}
