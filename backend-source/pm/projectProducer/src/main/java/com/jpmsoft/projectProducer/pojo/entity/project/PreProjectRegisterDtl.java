package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 项目注册清单-匡算清单子表
 */
@Data
@TableName("Pre_ProjectRegister_Dtl")
public class PreProjectRegisterDtl {
    @TableId(type = IdType.INPUT)
    private String prdId;
    private String prdNumber;

    private String prdFid;
    private String prdParenetId;
    private String prdCode;
    private String prdName;
    private String prdUnit;
    private Float prdCount;
    private BigDecimal prdPrice;
    private BigDecimal prdTotal;
    private String prdRemark;
    private Integer prdOrder;
    private Integer prdLevel;
    private Integer prdType;
    private Integer prdState;
    private String prdPath;
    private Integer prdLeafNode;
}
