package com.jpmsoft.projectProducer.pojo.entity.projectOutline;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 体系模板风险指标
 *
 * @author tianhelin
 * @date 2025/6/10
 */
@Data
@TableName("Outline_Lib_Risk")
public class OutlineLibRisk {
    @TableId(type = IdType.INPUT)
    private String id;
    private String oslId;
    private String riskId;
    private Integer olrSort;
    private Date createTime;
    private String createUid;
    private String createUname;
}