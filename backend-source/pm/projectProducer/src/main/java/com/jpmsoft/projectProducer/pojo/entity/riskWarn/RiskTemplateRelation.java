package com.jpmsoft.projectProducer.pojo.entity.riskWarn;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 风险点文档模板关联配置
 *
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 13:42
 */
@Data
@TableName("Risk_Template_Relation")
public class RiskTemplateRelation {

    @TableId(type = IdType.INPUT)
    private String id;
    private String rpmId;
    private String tId;
    private String rtrFieldName;
    private String rtrFieldCode;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String teId;
}
