package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 考核评价表
 * @Author: 李文涛 aaron
 * @date: 2023/8/7 16:55
 */
@Data
@TableName("Sec_Form")
public class SecForm {

    @TableId(type = IdType.INPUT)
    private String fId;
    private String fName;
    private String fType;
    private Integer fVersion;
    private String fCreateUid;
    private String fCreateUname;
    private Date fCreateTime;
    private String fState;
    private String fRemark;
    private String teId;
}
