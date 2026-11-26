package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 专家补抽
 *
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:14
 */
@Data
@TableName("Sec_Expert_Supplement")
public class SecExpertSupplement extends BaseEntity {
    //专家请假申请ID
    private String sevId;
    //选取记录ID
    private String srId;
    private String sesRemark;
    private String teId;
}
