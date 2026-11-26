package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 密码修改记录
 */
@Data
@TableName("SYS_User_Pwd_Change")
public class SysUserPwdChange {
    @TableId(type = IdType.INPUT)
    private String pcId;
    private String uId;
    private String pcOld;
    private String pcNew;
    private String pcCreateUid;
    private String pcCreateUname;
    private Date pcCreateTime;
    private String pcRemark;
}
