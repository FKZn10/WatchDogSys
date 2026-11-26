package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 专家考核申诉
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:52
 */
@Data
@TableName("Sec_Expert_Appeal")
public class SecExpertAppeal extends BaseEntity {

    private String exId;
    private String pId;
    private String secId;
    //申诉日期
    private Date seaTime;
    private String teId;
}
