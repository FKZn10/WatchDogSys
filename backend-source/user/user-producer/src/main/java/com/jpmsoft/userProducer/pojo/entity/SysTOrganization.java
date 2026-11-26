package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目组织机构模板
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/29 9:50
 */
@Data
@TableName("SYS_T_Organization")
public class SysTOrganization {
    @TableId(type = IdType.INPUT)
    private String toId;
    private String toName;
    private String toRemark;
    private String toCid;
    private String toStatus;
    private String toCreateUid;
    private String toCreateUname;
    private Date toCreateTime;
    private String teId;
}
