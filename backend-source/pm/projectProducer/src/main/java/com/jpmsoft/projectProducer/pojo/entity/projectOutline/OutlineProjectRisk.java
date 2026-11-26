package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目管理大纲体系管理风险指标
 *
 * @author tianhelin
 * @date 2025/6/10
 */
@Data
@TableName("Outline_Project_Risk")
public class OutlineProjectRisk {
    @TableId(type = IdType.INPUT)
    private String id;
    private String riskId;
    private Integer oprSort;
    private String oplId;
    private Date createTime;
    private String createUid;
    private String createUname;
}