package com.jpmsoft.projectConsumer.pojo.dto.other;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统外部用户注册功能
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OuterUserRegisterDTO extends BaseFileRequest {

    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    //用户信息
    private String ourUserName;
    private String ourUserPhone;
    private String ourUserIdcard;
    private String ourUserSex;
    private Date ourUserBirthday;
    private String ourUserEmail;
    private String ourUserType;
    private String ourAccount;
    private String ourPwd;
    private String ourUserSalt;
    //单位信息
    private String ourCompanyName;
    private String ourCreditCode;
    private String ourCompanyType;

}
