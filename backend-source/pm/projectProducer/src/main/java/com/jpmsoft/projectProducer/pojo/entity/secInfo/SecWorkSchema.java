package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 招标工作方案
 *
 * @Author: 李文涛 aaron
 * @date: 2024/2/23 9:30
 */
@Data
@TableName("Sec_Work_Schema")
public class SecWorkSchema {

    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;

    private String secId;
    private String wsRemark;
    //询价文件
    private String inquiryDoc;

    private String pId;

}