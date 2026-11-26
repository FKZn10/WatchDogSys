package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 重点专家库使用申请
 */
@Data
@TableName("Sec_Focus_Expert")
public class SecFocusExpert {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人姓名
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 流程状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    private Date stateTime;

    /**
     * 项目id
     */
    private String pId;

    /**
     * 标段id
     */
    private String secId;

    /**
     * 启用条件1
     */
    private String conditionA;

    /**
     * 启用条件2
     */
    private String conditionB;

    /**
     * 启用条件3
     */
    private String conditionC;

    /**
     * 启用条件4
     */
    private String conditionD;

    /**
     * 认定依据1
     */
    private String accordingA;

    /**
     * 认定依据1依据标准
     */
    private String accordingADetails;

    /**
     * 认定依据2
     */
    private String accordingB;

    /**
     * 认定依据2依据标准
     */
    private String accordingBDetails;

    /**
     * 认定依据2的补充
     */
    private String accordingC;

    /**
     * 申请原因
     */
    private String reason;

    /**
     * 拟抽取人数
     */
    private Integer extractNumber;

    /**
     * 专家论证附件
     */
    private String expertDemonstration;

    /**
     * 招标内容
     */
    private String secDetails;
}
