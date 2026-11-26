package com.jpmsoft.projectProducer.pojo.entity.fundPlan;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 进度申报
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:50
 */
@Data
@TableName("Progress_Declaration")
public class ProgressDeclaration extends BaseEntity {

    private Date pdDate;
    private String pdRemark;
    private String pdCompany;
    private String pdDepartment;
}
