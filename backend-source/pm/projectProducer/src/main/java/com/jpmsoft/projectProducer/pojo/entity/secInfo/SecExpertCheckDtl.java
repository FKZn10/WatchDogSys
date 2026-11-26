package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 专家考核内容
 *
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 9:50
 */
@Data
@TableName("Sec_Expert_Check_Dtl")
public class SecExpertCheckDtl {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String ecId;
    //考核内容
    private String secdContent;
    //考核结果
    private Integer secdResult;
    //扣分值
    private BigDecimal secdScore;
    //序号
    private Integer secdSort;

    private Integer selected;

    // 专家选取的详情表id 就是选取专家信息表的id 这个id可以忽略
    private String srdId;

    // 满分值
    private BigDecimal fullScore;

    // 中间表id
    private String scdId;

    private String parentId;

}
