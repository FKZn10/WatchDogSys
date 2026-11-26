package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 施工图审查
 * @Author: 李文涛 aaron
 * @date: 2023/10/13 10:58
 */
@Data
@TableName("Design_Draw_Review")
public class DesignDrawReview extends BaseEntity {
    //标题
    private String drTitle;
    //项目id
    private String pId;
    //经办人
    private String drHandlers;
    //发起时间
    private Date drInitTime;
    private String drRemark;
    private String teId;
}
