package com.jpmsoft.projectProducer.pojo.entity.completion;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目移交
 */
@Data
@TableName("handover_info_sub")
public class CompletionHandoverInfoSub {

    /**
     * 主键ID
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 项目移交ID（对应CompletionHandover的chId）
     */
    private String birId;

    /**
     * 分项工程名称
     */
    private String bisName;

    /**
     * 分项工程分类
     */
    private String bisCategory;

    /**
     * 数量（Decimal(18,6)）
     */
    private BigDecimal bisNum;

    /**
     * 单位
     */
    private String bisUnit;

    /**
     * 质保起始时间
     */
    private Date bisWarrantyStart;

    /**
     * 质保结束时间
     */
    private Date bisWarrantyEnd;

    /**
     * 质保状态（未生效/在保/已过期）
     */
    private String bisWarranty;

    /**
     * 信息库分项ID
     */
    private String bisId;

    /**
     * 信息库父项ID
     */
    private String bisParentId;




}
