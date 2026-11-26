package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 质询
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:35
 */
@Data
@TableName("Sec_Question")
public class SecQuestion {

    @TableId(type = IdType.INPUT)
    private String sqId;
    private String secId;
    private String sqTenderee;
    private String sqBiddingType;
    private String sqScope;
    private Boolean sqWhether;
    private String sqResult;
    private String sqRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
}
