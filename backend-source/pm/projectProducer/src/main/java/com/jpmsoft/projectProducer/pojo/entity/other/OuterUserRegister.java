package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统外部用户注册功能
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("OUTER_USER_REGISTER")
public class OuterUserRegister extends BaseEntity {

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
