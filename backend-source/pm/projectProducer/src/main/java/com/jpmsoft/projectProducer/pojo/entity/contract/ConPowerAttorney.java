package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/7 9:46
 */
@Data
@TableName("Con_Power_Attorney")
public class ConPowerAttorney extends BaseEntity {

    private String pId;
    private String conId;
    private String paConCode;
    private String paAuthPerson;
    private String paRemark;
    private String teId;
}
