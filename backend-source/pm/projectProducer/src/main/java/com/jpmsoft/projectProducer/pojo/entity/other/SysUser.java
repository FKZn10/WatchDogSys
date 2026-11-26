package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("SYS_User")
public class SysUser {
    @TableId(type = IdType.INPUT)
    private String uId;
    private String plId;
    private String cId;
    private String uName;
    private String uCode;
    private String uMtel;
    private String uTel;
    private String uEmail;
    private String uAccount;
    private String uPassword;
    private String uSex;
    private Date uBirthday;
    private String uState;
    private String uMdmNo;
    private Date uCreatetime;
    private String uCreateuId;
    private String uCreateuName;
    private String uType;
    private String uOffice;
    private String uCornet;
    private String uSalt;
    private Integer uFirstLogin;
    private String uIdcard;
    private Date uPassUpdateTime;
    private Integer uSort;
    private String uPushId;
    private String openId;
    private String uSignature;
    private String uQualification;
    /**
     * 身份证明文
     * 不对外仅用于内部逻辑
     */
    private String uPlaintextNum;

    private String uDhAccount;
    private String qzOpenId;
    private String qzBizNo;
}
