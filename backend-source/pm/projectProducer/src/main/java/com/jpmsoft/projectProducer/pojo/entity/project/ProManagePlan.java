package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 建设管理方案
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/18 10:18
 */
@Data
@TableName("Pro_Manage_Plan")
public class ProManagePlan extends BaseEntity {

    private String pId;
    private String pmpTitle;
    private String pmpRemark;
    private String teId;
}
