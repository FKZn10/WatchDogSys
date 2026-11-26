package com.jpmsoft.projectProducer.pojo.entity.construction;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 技术核定单
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 16:19
 */
@Data
@TableName("Technical_Check")
public class TechnicalCheck extends BaseEntity {

    private String pId;
    private String tcBuildId;
    private String tcName;
    private String tcCode;
    private String tcTitle;
    private String tcConstructionId;
    private String tcDesignId;
    private String tcContent;
    private String tcRemark;
    private String tcImgName;
}
